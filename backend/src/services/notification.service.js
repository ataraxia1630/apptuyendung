const admin = require('../config/firebase/firebase');
const { supabase } = require('../config/db/supabase');
const { SocketService } = require('./socket.service');
const { FcmService } = require('./fcm.service');

const NotificationService = {
  notify: async ({ userId, title, message, type }) => {
    // 1. Lưu vào DB
    await supabase
      .from('Notification')
      .insert([{ userId: userId, title, message }]);

    // 2. Emit socket để cập nhật UI
    SocketService.emitToUser(userId, 'new-notification', { title, message });

    // 3. (Tuỳ chọn) Gửi FCM nếu là thông báo loại 'reminder'
    if (type === 'reminder') {
      await FcmService.sendFCM(userId, title, message);
    }
  },
};

module.exports = {
  NotificationService,
};
