const { Router } = require('express');
const { AuthController } = require('../controllers/auth.controller');
const { validate } = require('../middlewares/validate.middleware');
const { RegisterSchema } = require('../validators/Auth/register.validator');
const { LoginSchema } = require('../validators/Auth/login.validator');

const route = Router();
route.post('/register', validate(RegisterSchema), AuthController.register);
route.post('/login', validate(LoginSchema), AuthController.login);
route.post('/logout', AuthController.logout);
route.post('/google', AuthController.loginGoogle);

module.exports = route;
