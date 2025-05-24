const { AvatarService } = require('../services/avatar.service');
const { AvatarHelper } = require('../helpers/avatar.helper');

const AvatarController = {
  uploadAvatar: async (req, res) => {
    const userId = req.user.userId;
    if (!req.file) {
      return res.status(400).json({ message: 'No file uploaded' });
    }

    try {
      const user = await AvatarService.uploadAvatar(userId, req.file);
      return res
        .status(201)
        .json({ message: 'Avatar uploaded successfully', user: user });
    } catch (error) {
      console.error('Error uploading CV:', error);
      return res
        .status(500)
        .json({ message: 'Internal server error', error: error.message });
    }
  },

  getAvatarURL: async (req, res) => {
    try {
      const url = await AvatarHelper.getAvatarSignedUrl(req.params.path);
      return res
        .status(200)
        .json({ message: 'Get URL successfully', url: url });
    } catch (error) {
      console.error('Error fetching URL:', error);
      return res
        .status(500)
        .json({ message: 'Internal server error', error: error.message });
    }
  },
};

module.exports = { AvatarController };
