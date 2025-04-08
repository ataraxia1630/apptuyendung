const { Router } = require('express');
const { SkillController } = require('../controllers/applicantSkill.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const route = new Router();

route.get('/:applicantId', verifyToken, SkillController.getAll);
route.post('/:applicantId', verifyToken, SkillController.createNew);
route.put('/:id', verifyToken, SkillController.updateSkill);
route.delete('/id', verifyToken, SkillController.deleteSkill);
route.delete('/:applicantId', verifyToken, SkillController.deleteAll);

module.exports = route;
