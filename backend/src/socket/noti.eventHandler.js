const { sendToUser } = require('./index');
const { supabase } = require('../config/db/supabase');
const { FcmService } = require('../services/fcm.service');

const NotiEventHandler = {
  notify: async (userId, title, message, type) => {
    // 1. Lưu vào DB
    // 2. Emit socket để cập nhật UI
    if (type === 'both' || type === 'save') {
      const created_at = new Date();
      try {
        const { error } = await supabase.from('Notification').insert([
          {
            id: created_at + userId,
            userId,
            title,
            message,
            created_at,
            status: 'UNREAD',
          },
        ]);
        if (error) {
          console.error(
            'Error inserting notification into Supabase:',
            error.message
          );
          throw error; // Optional: rethrow to stop execution or handle differently
        }
      } catch (error) {
        console.error('Failed to save notification:', error.message);
      }
      sendToUser(userId, 'notification.new', { title, message, created_at });
    }

    // 3. Gửi FCM
    if (type === 'both' || type === 'fcm') {
      await FcmService.sendFCM(userId, title, message);
    }
  },
};

module.exports = { NotiEventHandler };
