const { Router } = require('express');
const { ChatController } = require('../controllers/chat.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { cache } = require('../middlewares/cache.middleware');
const { validate } = require('../middlewares/validate.middleware');

const route = Router();

// lấy tất cả các cuộc trò chuyện của user
route.get('/all', verifyToken, ChatController.getAllChats);

// lấy tất cả đoạn chat có tin nhắn chưa đọc của user
route.get('/unread', verifyToken, ChatController.getAllUnreadChats);

// lấy tất cả các nhóm chat của user
route.get('/group', verifyToken, ChatController.getAllGroupChats);

// lấy thông tin của một cuộc trò chuyện có id là chatId
route.get('/:chatId', verifyToken, cache, ChatController.getChatById);

// lấy tất cả tin nhắn của 1 cuộc trò chuyện
route.get('/:chatId/mess', verifyToken, ChatController.getAllMessages);

// tạo một cuộc trò chuyện mới
route.post('/', verifyToken, ChatController.createChat);

// tạo một group chat mới
route.post('/group', verifyToken, ChatController.createGroupChat);

// cập nhật thông tin của một cuộc trò chuyện (name of group chat)
route.put('/:chatId', verifyToken, ChatController.updateChat);

// xóa một cuộc trò chuyện
route.delete('/:chatId', verifyToken, ChatController.deleteChat);

// thêm thành viên vào nhóm chat, chỉ admin nhóm mới có quyền này
route.post(
  '/add-member/:chatId',
  verifyToken,
  ChatController.addMemberToGroupChat
);

// xóa thành viên khỏi nhóm chat, chỉ admin nhóm mới có quyền này
route.put(
  '/remove-member/:chatId',
  verifyToken,
  ChatController.removeMemberFromGroupChat
);

// tham gia vào nhóm chat
route.post('/join', verifyToken, ChatController.joinGroupChat);

// rời khỏi nhóm chat
route.put('/leave', verifyToken, ChatController.leaveGroupChat);

// tắt thông báo của nhóm chat
route.put('/mute', verifyToken, ChatController.muteGroupChat);

// bật thông báo của nhóm chat
route.put('/unmute', verifyToken, ChatController.unmuteGroupChat);

module.exports = route;
