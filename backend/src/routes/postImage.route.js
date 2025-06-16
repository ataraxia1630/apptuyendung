const { Router } = require('express');
const { verifyToken } = require('../middlewares/auth.middleware');
const { uploadFile } = require('../middlewares/uploadFile.middleware');
const { PostImageController } = require('../controllers/postImage.controller');

const route = new Router();

route.post('/', verifyToken, uploadFile, PostImageController.uploadImage);
route.get('/:filePath', verifyToken, PostImageController.getImageURL);

module.exports = route;
