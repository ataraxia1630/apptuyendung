const { Router } = require('express');
const { AuthController } = require('../controllers/auth.controller');

const route = Router();
route.post('/register', AuthController.register);
route.post('/login', AuthController.login);
route.post('/logout', AuthController.logout);

module.exports = route;
