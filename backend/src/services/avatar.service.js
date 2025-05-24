const prisma = require('../config/db/prismaClient');
const { AvatarHelper } = require('../helpers/avatar.helper');

const AvatarService = {
  uploadAvatar: async (userId, file) => {
    try {
      const filePath = await AvatarHelper.uploadAvatar(file, userId);
      console.log('File uploaded to Supabase:', filePath);
      const user = await prisma.user.update({
        data: { avatar: filePath },
        where: { id: userId },
      });
      return user;
    } catch (error) {
      console.error('Error uploading file to Supabase:', error);
      throw new Error('File upload failed');
    }
  },
};

module.exports = { AvatarService };
