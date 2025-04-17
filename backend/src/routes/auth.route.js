const { Router } = require('express');
const { AuthController } = require('../controllers/auth.controller');
const { validate } = require('../middlewares/validate.middleware');
const { RegisterSchema } = require('../validators/Auth/register.validator');

const route = Router();
route.post('/register', validate(RegisterSchema), AuthController.register);
route.post('/login', AuthController.login);
route.post('/logout', AuthController.logout);

module.exports = route;
