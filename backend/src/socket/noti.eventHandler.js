const { sendToUser } = require('./index');
const { supabase } = require('../config/db/supabase');
const { FcmService } = require('../services/fcm.service');

const NotiEventHandler = {
  notify: async (userId, title, message, type) => {
    // 1. Lưu vào DB
    // 2. Emit socket để cập nhật UI
    if (type == 'both' || type == 'save') {
      const created_at = new Date();
      await supabase
        .from('Notification')
        .insert([{ userId: userId, title, message, created_at }]);

      sendToUser(userId, 'notification.new', { title, message, created_at });
    }

    // 3. Gửi FCM
    if (type === 'both' || type === 'fcm') {
      await FcmService.sendFCM(userId, title, message);
    }
  },
};

module.exports = { NotiEventHandler };
