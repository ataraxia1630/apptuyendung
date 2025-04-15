const { Router } = require('express');
const { AuthController } = require('../controllers/auth.controller');
const { validate } = require('../middlewares/validate.middleware');
const { UserSchema } = require('../validators/user.validator');

const route = Router();
route.post('/register', validate(UserSchema), AuthController.register);
route.post('/login', AuthController.login);
route.post('/logout', AuthController.logout);

module.exports = route;
