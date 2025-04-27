const { Router } = require('express');
const { ApplicantController } = require('../controllers/applicant.controller');

const { verifyToken } = require('../middlewares/auth.middleware');
const { validate } = require('../middlewares/validate.middleware');
const {
  ApplicantSchema,
} = require('../validators/Applicant/updateApplicant.validator');

const route = Router();

// lấy tất cả người dùng
route.get('/all', verifyToken, ApplicantController.getAllApplicants);

// search người dùng theo tên
route.get(
  '/search/:name',
  verifyToken,
  ApplicantController.searchApplicantsByName
);

// lấy 1 người dùng theo id / username
route.get('/:id', verifyToken, ApplicantController.getApplicantById);
route.put(
  '/:id',
  verifyToken,
  validate(ApplicantSchema),
  ApplicantController.updateApplicant
);

// xóa người dùng theo id
route.delete('/:id', verifyToken, ApplicantController.deleteApplicant);

module.exports = route;
