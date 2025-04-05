const { Router } = require('express');
const { ApplicantController } = require('../controllers/applicant.controller');

const { verifyToken } = require('../middlewares/auth.middleware');

const route = Router();

// lấy tất cả người dùng
route.get('/all', verifyToken, ApplicantController.getAllApplicants);

// lấy người dùng theo tên
route.get('/name/:name', verifyToken, ApplicantController.getApplicantsByName);

// lấy 1 người dùng theo id / username
route.get('/:id', verifyToken, ApplicantController.getApplicantById);
route.put('/:id', verifyToken, ApplicantController.updateApplicant);
route.delete('/:id', verifyToken, ApplicantController.deleteApplicant);

module.exports = route;
