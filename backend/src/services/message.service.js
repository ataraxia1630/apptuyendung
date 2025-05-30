const prisma = require('../config/db/prismaClient');

const MessService = {
  createNew: async (senderId, conversationId, content) => {
    try {
      const mess = await prisma.message.create({
        data: {
          conversationId,
          senderId,
          content,
        },
      });
      return mess;
    } catch (error) {
      throw new Error(error);
    }
  },

  deleteMess: async (id) => {
    try {
      const mess = await prisma.message.update({
        data: {
          isDeleted: true,
        },
        where: {
          id,
        },
      });
      return mess;
    } catch (error) {
      throw new Error(error);
    }
  },

  editMess: async (id, content) => {
    try {
      const mess = await prisma.message.update({
        where: {
          id,
        },
        data: { content, isEdited: true },
      });
      return mess;
    } catch (error) {
      throw new Error(error);
    }
  },

  getAll: async (chatId) => {
    try {
      const messages = await prisma.message.findMany({
        where: {
          conversationId: chatId,
        },
        include: {
          attachments: true,
        },
      });
      return messages;
    } catch (error) {
      throw new Error(error);
    }
  },
};

module.exports = { MessService };
