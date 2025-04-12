const { Router } = require('express');
const { FieldController } = require('../controllers/field.controller');

const { verifyToken } = require('../middlewares/auth.middleware');

const route = new Router();
route.get('/', verifyToken, FieldController.getAllFields);
route.get('/:fieldName', verifyToken, FieldController.getFieldByName);

// ADMIN only??
route.post('/', verifyToken, FieldController.createField);
route.put('/:id', verifyToken, FieldController.updateField);

// thao tac tren interestedField cua applicant
route.get(
  '/interested/:applicantId',
  verifyToken,
  FieldController.getAllInterestedFields
);

route.post(
  '/interested/:applicantId',
  verifyToken,
  FieldController.addInterestedFields
);

route.delete(
  '/interested/:applicantId',
  verifyToken,
  FieldController.removeAllInterestedFields
);

route.delete(
  '/interested/:applicantId/:fieldId',
  verifyToken,
  FieldController.removeInterestedField
);

module.exports = route;
