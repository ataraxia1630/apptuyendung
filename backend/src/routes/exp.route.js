const { Router } = require('express');
const { ExpController } = require('../controllers/exp.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { validate } = require('../middlewares/validate.middleware');
const { ExpSchema } = require('../validators/Experience/exp.validator');

const route = Router();
route.get('/:applicantId', verifyToken, ExpController.getAllExp);
route.post(
  '/:applicantId',
  verifyToken,
  validate(ExpSchema),
  ExpController.createExp
);
route.put(':/id', verifyToken, validate(ExpSchema), ExpController.updateExp);
route.delete('/:id', verifyToken, ExpController.deleteExp);

module.exports = route;
