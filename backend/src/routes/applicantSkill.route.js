const { Router } = require('express');
const { SkillController } = require('../controllers/applicantSkill.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { validate } = require('../middlewares/validate.middleware');
const { SkillSchema } = require('../validators/Skill/skillSchema');
const route = new Router();

route.get('/:applicantId', verifyToken, SkillController.getAll);
route.post(
  '/:applicantId',
  verifyToken,
  validate(SkillSchema),
  SkillController.createNew
);
route.put(
  '/:id',
  verifyToken,
  validate(SkillSchema),
  SkillController.updateSkill
);
route.delete('/:id', verifyToken, SkillController.deleteSkill);
route.delete('/all/:applicantId', verifyToken, SkillController.deleteAll);

module.exports = route;
