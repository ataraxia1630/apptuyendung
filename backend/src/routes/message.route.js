const { Router } = require('express');
const { MessController } = require('../controllers/message.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { cache } = require('../middlewares/cache.middleware');
const { validate } = require('../middlewares/validate.middleware');

const route = Router();

// gửi tin nhắn
route.post('/', verifyToken, MessController.sendMess);

// xóa tin nhắn
route.delete('/:id', verifyToken, MessController.deleteMess);

// sửa tin nhắn
route.put('/:id', verifyToken, MessController.editMess);

// lấy ds tin nhắn của 1 chat
route.get('/:chatId', verifyToken, MessController.getAll);

module.exports = route;
