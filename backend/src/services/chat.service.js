const prisma = require('../config/db/prismaClient');
const ChatService = {
  getAllChats: async (userId) => {
    try {
      const chats = await prisma.conversationUser.findMany({
        where: {
          userId,
          left_at: null,
        },
        include: {
          Conversation: {
            select: {
              id,
              name,
              type,
              Message: {
                select: { senderId, content },
                orderBy: { sent_at: 'desc' },
                take: 1,
              },
            },
          },
          members: {
            include: { User: { select: { id, avatar } } },
          },
        },
        take: 10,
      });
      return chats;
    } catch (error) {
      console.error('Error fetching chats:', error);
      throw new Error('Error fetching chats');
    }
  },

  getAllUnreadChats: async (userId) => {
    try {
      const unreadChats = await prisma.conversation.findMany({
        where: {
          members: {
            some: { userId, left_at: null },
          },
          Message: {
            some: {
              senderId: { not: userId },
              isRead: false,
              isDeleted: false,
            },
          },
        },
        include: {
          Message: {
            select: { senderId, content },
            orderBy: { sent_at: 'desc' },
            take: 1,
          },
          members: {
            include: { User: { select: { id, avatar } } },
          },
        },
        take: 10,
      });

      return unreadChats;
    } catch (error) {
      console.error('Error fetching unread chats:', error);
      throw new Error('Failed to fetch unread chats');
    }
  },
};

module.exports = { ChatService };
