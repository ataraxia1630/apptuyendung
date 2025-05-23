const { Router } = require('express');
const { ChatController } = require('../controllers/chat.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { cache } = require('../middlewares/cache.middleware');
const { validate } = require('../middlewares/validate.middleware');

const route = Router();

// lấy tất cả các cuộc trò chuyện của user
route.get('/all', verifyToken, cache, ChatController.getAllChats);

// lấy tất cả đoạn chat có tin nhắn chưa đọc của user
route.get('/unread', verifyToken, cache, ChatController.getAllUnreadChats);

// lấy tất cả các nhóm chat của user
route.get('/group', verifyToken, cache, ChatController.getAllGroupChats);

// lấy thông tin của một cuộc trò chuyện có id là chatId
route.get('/:chatId', verifyToken, cache, ChatController.getChatById);

// tạo một cuộc trò chuyện mới
route.post('/', verifyToken, cache, ChatController.createChat);

// tạo một group chat mới
route.post('/group', verifyToken, cache, ChatController.createGroupChat);

// cập nhật thông tin của một cuộc trò chuyện (name of group chat)
route.put('/:chatId', verifyToken, cache, ChatController.updateChat);

// xóa một cuộc trò chuyện
route.delete('/:chatId', verifyToken, cache, ChatController.deleteChat);

// thêm thành viên vào nhóm chat, chỉ admin nhóm mới có quyền này
route.post(
  '/add-member',
  verifyToken,
  cache,
  ChatController.addMemberToGroupChat
);

// xóa thành viên khỏi nhóm chat, chỉ admin nhóm mới có quyền này
route.delete(
  '/remove-member',
  verifyToken,
  cache,
  ChatController.removeMemberFromGroupChat
);

// tham gia vào nhóm chat
route.post('/join', verifyToken, cache, ChatController.joinGroupChat);

// rời khỏi nhóm chat
route.put('/leave', verifyToken, cache, ChatController.leaveGroupChat);

// tắt thông báo của nhóm chat
route.put('/mute', verifyToken, cache, ChatController.muteGroupChat);

// bật thông báo của nhóm chat
route.put('/unmute', verifyToken, cache, ChatController.unmuteGroupChat);

module.exports = route;
