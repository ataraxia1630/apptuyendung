const { Router } = require('express');
const { EducationController } = require('../controllers/education.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const route = Router();

route.get('/', verifyToken, EducationController.getAllEdu);
route.post('/', verifyToken, EducationController.createNewEdu);

// thao tac tren applicantEdu
route.get('/:applicantId', verifyToken, EducationController.getAll);
route.post('/:applicantId', verifyToken, EducationController.createNew);
route.put('/:id', verifyToken, EducationController.updateEdu);
route.delete('/:id', verifyToken, EducationController.deleteEdu);
route.delete('/all/:applicantId', verifyToken, EducationController.deleteAll);

module.exports = route;
