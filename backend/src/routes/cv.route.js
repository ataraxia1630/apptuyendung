const { Router } = require('express');
const { verifyToken } = require('../middleware/auth.middleware');
const { CVController } = require('../controllers/cv.controller');

const route = new Router();

// upload cv
route.post('/upload/:applicantId', verifyToken, CVController.uploadCV);

// download cv
route.get('/download/:id', verifyToken, CVController.downloadCV);

// get all cv
route.get('/all/:applicantId', verifyToken, CVController.getAllCV);

// delete all cv
route.delete('/all/:applicantId', verifyToken, CVController.deleteAllCV);

// get cv by id
route.get('/:id', verifyToken, CVController.getCV);
route.put('/:id', verifyToken, CVController.updateCV);
route.delete('/:id', verifyToken, CVController.deleteCV);

module.exports = route;
