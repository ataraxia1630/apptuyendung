const { ChatService } = require('../services/chat.service');

const ChatController = {
  // lấy tất cả các cuộc trò chuyện của user
  getAllChats: async (req, res) => {
    try {
      const chats = await ChatService.getAllChats(req.user.userId);
      return res.status(200).json({ chats });
    } catch (error) {
      console.error('Error fetching chats:', error);
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  // lấy tất cả đoạn chat có tin nhắn chưa đọc
  getAllUnreadChats: async (req, res) => {
    try {
      const chats = await ChatService.getAllUnreadChats(req.user.userId);
      return res.status(200).json({ chats });
    } catch (error) {
      console.error('Error fetching chats:', error);
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  // lấy tất cả các nhóm chat của user
  getAllGroupChats: async (req, res) => {
    try {
      const chats = await ChatService.getAllGroupChats(req.user.userId);
      return res.status(200).json({ chats });
    } catch (error) {
      console.error('Error fetching chats:', error);
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  // lấy thông tin của một cuộc trò chuyện có id là chatId
  getChatById: async (req, res) => {
    try {
      const chat = await ChatService.getChatById(req.params.chatId);
      return res.status(200).json({ chat });
    } catch (error) {
      console.error('Error fetching chats:', error);
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  // tạo một cuộc trò chuyện mới
  createChat: async (req, res) => {
    try {
      const { friendId } = req.body;
      const chat = await ChatService.createChat(req.user.userId, friendId);
      return res.status(201).json({ chat });
    } catch (error) {
      console.error('Error creating chat:', error);
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  // cập nhật thông tin của một cuộc trò chuyện
  updateChat: async (req, res) => {
    try {
      const { chatId } = req.params;
      const chatData = req.body;
      const updatedChat = await ChatService.updateChat(chatId, chatData);
      return res.status(200).json({ updatedChat });
    } catch (error) {
      console.error('Error updating chat:', error);
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  // xóa một cuộc trò chuyện
  deleteChat: async (req, res) => {
    try {
      const { chatId } = req.params;
      await ChatService.deleteChat(chatId);
      return res.status(204).send();
    } catch (error) {
      console.error('Error deleting chat:', error);
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  // tìm kiếm cuộc trò chuyện theo tên
  searchChat: async (req, res) => {
    try {
      const { query } = req.query;
      const chats = await ChatService.searchChat(query);
      return res.status(200).json(chats);
    } catch (error) {
      console.error('Error searching chat:', error);
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  // tạo một group chat mới
  createGroupChat: async (req, res) => {
    try {
    } catch (error) {}
  },

  // thêm thành viên vào nhóm chat, chỉ admin nhóm mới có quyền này
  addMemberToGroupChat: async (req, res) => {
    try {
      const { chatId } = req.params;
      const { userId } = req.body;
      const updatedChat = await ChatService.addMemberToGroupChat(
        chatId,
        userId
      );
      return res.status(200).json(updatedChat);
    } catch (error) {
      console.error('Error adding member to group chat:', error);
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  // xóa thành viên khỏi nhóm chat, chỉ admin nhóm mới có quyền này
  removeMemberFromGroupChat: async (req, res) => {
    try {
      const { chatId } = req.params;
      const { userId } = req.body;
      const updatedChat = await ChatService.removeMemberFromGroupChat(
        chatId,
        userId
      );
      return res.status(200).json(updatedChat);
    } catch (error) {
      console.error('Error removing member from group chat:', error);
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  // tham gia vào nhóm chat
  joinGroupChat: async (req, res) => {
    try {
      const { chatId } = req.params;
      const { userId } = req.body;
      const updatedChat = await ChatService.joinGroupChat(chatId, userId);
      return res.status(200).json(updatedChat);
    } catch (error) {
      console.error('Error joining group chat:', error);
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  // rời khỏi nhóm chat
  leaveGroupChat: async (req, res) => {
    try {
      const { chatId } = req.params;
      const { userId } = req.body;
      const updatedChat = await ChatService.leaveGroupChat(chatId, userId);
      return res.status(200).json(updatedChat);
    } catch (error) {
      console.error('Error leaving group chat:', error);
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  // tắt thông báo của nhóm chat
  muteGroupChat: async (req, res) => {
    try {
      const { chatId } = req.params;
      const { userId } = req.body;
      const updatedChat = await ChatService.muteGroupChat(chatId, userId);
      return res.status(200).json(updatedChat);
    } catch (error) {
      console.error('Error muting group chat:', error);
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  // bật thông báo của nhóm chat
  unmuteGroupChat: async (req, res) => {
    try {
      const { chatId } = req.params;
      const { userId } = req.body;
      const updatedChat = await ChatService.unmuteGroupChat(chatId, userId);
      return res.status(200).json(updatedChat);
    } catch (error) {
      console.error('Error unmuting group chat:', error);
      return res.status(500).json({ message: 'Internal server error' });
    }
  },
};

module.exports = { ChatController };
