const { Router } = require('express');
const { verifyToken } = require('../middlewares/auth.middleware');
const { cache } = require('../middlewares/cache.middleware');
const { uploadFile } = require('../middlewares/uploadFile.middleware');
const { CVController } = require('../controllers/cv.controller');

const route = new Router();

// upload cv
route.post(
  '/upload/:applicantId',
  verifyToken,
  uploadFile,
  CVController.uploadCV
);

// download cv
route.get('/download/:id', verifyToken, CVController.downloadCV);

// get all cv
route.get('/all/:applicantId', verifyToken, cache, CVController.getAllCV);

// delete all cv
route.delete('/all/:applicantId', verifyToken, CVController.deleteAllCV);

// get cv by id
route.get('/:id', verifyToken, cache, CVController.getCV);
route.put('/:id', verifyToken, CVController.updateCV);
route.delete('/:id', verifyToken, CVController.deleteCV);

module.exports = route;
