const admin = require('../config/firebase/firebase');
const { supabase } = require('../config/db/supabase');
const prisma = require('../config/db/prismaClient');

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

  createNew: async (user_id, fcm_token) => {
    try {
      const user_token = await prisma.user_token.upsert({
        where: { user_id },
        update: { fcm_token },
        create: { user_id, fcm_token },
      });
      return user_token;
    } catch (error) {
      throw new Error('Error:' + error.message);
    }
  },
};

module.exports = {
  FcmService,
};
