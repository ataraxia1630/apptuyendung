const { Router } = require('express');
const {
  NotificationController,
} = require('../controllers/notification.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const route = Router();

// lay tat ca thong bao cua user hien tai
route.get('/', verifyToken, NotificationController.getAll);

// xoa 1 thong bao
route.delete('/:id', verifyToken, NotificationController.delete);

// xoa tat ca thong bao cua user hien tai
route.delete('/', verifyToken, NotificationController.deleteAll);

module.exports = route;
