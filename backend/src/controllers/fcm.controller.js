const { FcmService } = require('../services/fcm.service');

const FcmController = {
  createNew: async (req, res) => {
    try {
      const userId = req.user.userId;
      const { fcm_token } = req.body;
      if (!fcm_token) throw new Error('fcm_token is required');
      const user_token = await FcmService.createNew(userId, fcm_token);
      return res.status(201).json({ message: 'Success', user_token });
    } catch (error) {
      return res.status(500).json({
        message: 'Error creating user_tokens',
        error: error.message || error,
      });
    }
  },
};

module.exports = { FcmController };
