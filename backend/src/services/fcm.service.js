const admin = require('../config/firebase/firebase');
const { supabase } = require('../config/db/supabase');

const FcmService = {
  sendFCM: async (userId, title, body) => {
    try {
      // Fetch the user's FCM token from the database
      const { data, error } = await supabase
        .from('user_token')
        .select('fcm_token')
        .eq('user_id', userId)
        .single();

      if (error || !data) {
        throw new Error(`Error fetching FCM token: ${error.message}`);
      }

      const fcmToken = data.fcm_token;

      if (!fcmToken) {
        throw new Error('No FCM token found for the user');
      }

      // Send the notification using Firebase Cloud Messaging
      const message = {
        notification: {
          title,
          body,
        },
        token: fcmToken,
      };

      const response = await admin.messaging().send(message);
      console.log('Notification sent successfully:', response);
    } catch (error) {
      console.error('Error sending notification:', error);
    }
  },
};

module.exports = {
  FcmService,
};
