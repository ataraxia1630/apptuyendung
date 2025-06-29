const { Router } = require('express');
const { verifyToken } = require('../middlewares/auth.middleware');
const { cache } = require('../middlewares/cache.middleware');
const { uploadFile } = require('../middlewares/uploadFile.middleware');
const { AvatarController } = require('../controllers/avatar.controller');

const route = new Router();

// upload avatar
route.post(
  '/upload',
  verifyToken,
  uploadFile,
  AvatarController.uploadAvatar
);

// get avatar URL
route.get('/url/:path', verifyToken, AvatarController.getAvatarURL);

module.exports = route;
