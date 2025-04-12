const { Router } = require('express');
const {
  ApplicantEduController,
} = require('../controllers/applicantEdu.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const router = Router();

router.get('/:applicantId', verifyToken, ApplicantEduController.getAll);
router.post('/:applicantId', verifyToken, ApplicantEduController.createNew);
router.put('/:id', verifyToken, ApplicantEduController.updateEdu);
router.delete('/:id', verifyToken, ApplicantEduController.deleteEdu);
router.delete(
  '/all/:applicantId',
  verifyToken,
  ApplicantEduController.deleteAll
);
