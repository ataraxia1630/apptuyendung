const { sendToChatRoom } = require('../socket');
const { MessService } = require('../services/message.service');
const { ChatService } = require('../services/chat.service');
const NotiEmitter = require('../emitters/notification.emitter');

const MessController = {
  sendMess: async (req, res) => {
    try {
      const { userId } = req.user; // ID của người gửi tin nhắn
      const { conversationId, content } = req.body;
      const mess = await MessService.createNew(userId, conversationId, content);
      sendToChatRoom(conversationId, 'newMess', { mess });
      res.status(201).json({ mess });
      (async () => {
        try {
          const chat = await ChatService.getChatById(conversationId);
          const members = chat.members;

          // Lọc bỏ người gửi tin nhắn khỏi danh sách members
          const otherMembers = members.filter(
            (member) => member.userId !== userId
          );

          // Gửi thông báo cho các thành viên khác
          otherMembers.forEach((member) => {
            NotiEmitter.emit('mess.new', {
              userId: member.userId,
            });
          });
        } catch (err) {
          console.error('Error while sending notification:', err.message);
        }
      })();
    } catch (error) {
      return res.status(500).json({ message: error.message || 'Server error' });
    }
  },

  deleteMess: async (req, res) => {
    try {
      const { id } = req.params;
      const mess = await MessService.deleteMess(id);
      sendToChatRoom(mess.conversationId, 'messDeleted', { mess });
      return res.status(200).json({ mess });
    } catch (error) {
      return res.status(500).json({ message: error.message || 'Server error' });
    }
  },

  editMess: async (req, res) => {
    try {
      const { id } = req.params;
      const { content } = req.body;
      const mess = await MessService.editMess(id, content);
      sendToChatRoom(mess.conversationId, 'messEdited', { mess });
      return res.status(200).json({ mess });
    } catch (error) {
      return res.status(500).json({ message: error.message || 'Server error' });
    }
  },

  getAll: async (req, res) => {
    try {
      const { chatId } = req.params;
      const messages = await MessService.getAll(chatId);
      return res.status(200).json({ messages });
    } catch (error) {
      return res.status(500).json({ message: error.message || 'Server error' });
    }
  },
};

module.exports = { MessController };
