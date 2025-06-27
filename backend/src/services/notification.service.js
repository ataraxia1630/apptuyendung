const prisma = require('../config/db/prismaClient');
const NotificationService = {
  getAll: async (userId) => {
    try {
      return await prisma.notification.findMany({
        where: { userId },
      });
    } catch (error) {
      throw new Error('Error: ' + error.message);
    }
  },

  delete: async (userId, id) => {
    try {
      const noti = await prisma.notification.findFirst({
        where: { userId, id },
      });

      if (!noti) throw new Error('Not found!');
      await prisma.notification.delete({ where: { id } });
    } catch (error) {
      throw new Error('Error: ' + error.message);
    }
  },

  deleteAll: async (userId) => {
    try {
      await prisma.notification.deleteMany({ where: { userId } });
    } catch (error) {
      throw new Error('Error: ' + error.message);
    }
  },
};

module.exports = { NotificationService };
