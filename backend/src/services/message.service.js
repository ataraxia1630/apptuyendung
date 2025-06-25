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
        include: {
          Sender: true, 
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
      const existing = await prisma.message.findFirst({
        where: { id },
      });
      if (!existing) throw new Error('Not found');
      if (existing.isDeleted)
        throw new Error('This message is already deleted');
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
          Sender: true,
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
