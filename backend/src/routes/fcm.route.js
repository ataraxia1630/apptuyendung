const { Router } = require('express');
const { FcmController } = require('../controllers/fcm.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const route = Router();

route.post('/', verifyToken, FcmController.createNew);

module.exports = route;
