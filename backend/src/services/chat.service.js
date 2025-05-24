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

  createGroupChat: async (adminId, data) => {
    try {
      const result = await prisma.$transaction(async (tx) => {
        // Tạo conversation (group chat)
        const chat = await tx.conversation.create({
          data: { name: data.name, isGroup: true },
          select: {
            id: true,
            name: true,
            isGroup: true,
          },
        });

        const conversationId = chat.id;

        // Chuẩn bị dữ liệu thành viên
        const memData = [{ conversationId, userId: adminId, isAdmin: true }];
        for (const mem of data.members) {
          memData.push({ conversationId, userId: mem });
        }

        // Thêm các thành viên vào nhóm
        const members = await tx.conversationUser.createManyAndReturn({
          data: memData,
        });

        return { chat, members };
      });

      return result;
    } catch (error) {
      console.error('Error creating group chat (with rollback):', error);
      throw new Error('Failed to create group chat');
    }
  },

  deleteChat: async (userId, chatId) => {
    try {
      const chat = await ChatService.getChatById(chatId);
      const role = await ChatService.getRelationWithChat(userId, chatId);
      // xóa group chat
      if (chat.isGroup) {
        if (!role.isAdmin) throw new Error('Only admin of group can delete!');

        await prisma.conversation.delete({
          where: { id: chatId },
        });
      }

      // xoá conversation từ 1 phía
      else {
        await prisma.conversationUser.delete({
          where: {
            conversationId_userId: { userId, conversationId: chatId },
          },
        });
      }
    } catch (error) {
      console.error('Error deleting chat:', error);
      throw new Error(error);
    }
  },

  updateChat: async (userId, id, data) => {
    try {
      const chat = await ChatService.getChatById(id);
      const role = await ChatService.getRelationWithChat(userId, id);
      if (!chat.isGroup)
        throw new Error('This function is only for group chat!');

      if (!role.isAdmin) throw new Error('Only admin of group can update!');
      const updatedChat = await prisma.conversation.update({
        data,
        where: { id },
      });
      return updatedChat;
    } catch (error) {
      console.error('Error updating chat:', error);
      throw new Error(error);
    }
  },

  getChatById: async (id) => {
    try {
      const chat = await prisma.conversation.findUnique({
        where: { id },
      });
      if (!chat) throw new Error('Chat not found!');
      return chat;
    } catch (error) {
      throw new Error(error);
    }
  },

  getRelationWithChat: async (userId, chatId) => {
    try {
      const relation = await prisma.conversationUser.findUnique({
        where: {
          conversationId_userId: { userId, conversationId: chatId },
        },
      });
      if (!relation) throw new Error('You are not part of this chat!');
      return relation;
    } catch (error) {
      throw new Error(error);
    }
  },
};

module.exports = { ChatService };
