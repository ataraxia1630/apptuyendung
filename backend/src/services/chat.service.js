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
              id: true,
              name: true,
              Message: {
                select: { senderId: true, content: true },
                orderBy: { sent_at: 'desc' },
                take: 1,
              },
              members: {
                include: { User: { select: { id: true, avatar: true } } },
              },
            },
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
            select: { senderId: true, content: true },
            orderBy: { sent_at: 'desc' },
            take: 1,
          },
          members: {
            include: { User: { select: { id: true, avatar: true } } },
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

  getAllGroupChats: async (userId) => {
    try {
      const groupchats = await prisma.conversation.findMany({
        where: {
          isGroup: true,
          members: {
            some: {
              userId,
              left_at: null,
            },
          },
        },
        include: {
          Message: {
            select: { senderId: true, content: true },
            orderBy: { sent_at: 'desc' },
            take: 1,
          },
          members: {
            include: { User: { select: { id: true, avatar: true } } },
          },
        },
        take: 10,
      });

      return groupchats;
    } catch (error) {
      console.error('Error fetching group chats:', error);
      throw new Error('Failed to fetch group chats');
    }
  },

  createChat: async (userId, friendId) => {
    try {
      const existingChat = await prisma.conversation.findFirst({
        where: {
          isGroup: false,
          members: {
            some: {
              userId: userId,
            },
          },
          AND: {
            members: {
              some: {
                userId: friendId,
              },
            },
          },
        },
      });

      console.log('chat is already existed');
      if (existingChat) return existingChat;

      const chat = await prisma.conversation.create({
        data: { isGroup: false },
        select: {
          id: true,
          name: true,
          isGroup: true,
        },
      });
      const members = await prisma.conversationUser.createManyAndReturn({
        data: [
          { conversationId: chat.id, userId },
          { conversationId: chat.id, userId: friendId },
        ],
      });

      return { chat, members };
    } catch (error) {
      console.error('Error creating chat:', error);
      throw new Error('Failed to create chat');
    }
  },
};

module.exports = { ChatService };
