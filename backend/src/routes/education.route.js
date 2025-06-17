const { Router } = require('express');
const { EducationController } = require('../controllers/education.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { cache } = require('../middlewares/cache.middleware');
const { validate } = require('../middlewares/validate.middleware');
const { EduSchema } = require('../validators/Education/edu.validator');
const {
  ApplicantEduSchema,
} = require('../validators/Education/applicantEdu.validator');
const route = Router();

route.get('/', verifyToken, cache, EducationController.getAllEdu);
route.post(
  '/',
  verifyToken,
  validate(EduSchema),
  EducationController.createNewEdu
);

// thao tac tren applicantEdu
route.get('/:applicantId', verifyToken, EducationController.getAll);
route.post(
  '/:applicantId',
  verifyToken,
  validate(ApplicantEduSchema),
  EducationController.createNew
);
route.put(
  '/:id',
  verifyToken,
  validate(ApplicantEduSchema),
  EducationController.updateEdu
);
route.delete('/:id', verifyToken, EducationController.deleteEdu);
route.delete('/all/:applicantId', verifyToken, EducationController.deleteAll);

module.exports = route;
