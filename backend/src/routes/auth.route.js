const { Router } = require('express');
const { AuthController } = require('../controllers/AuthController');

const route = Router();
route.post('/register', AuthController.register);
route.post('/login', AuthController.login);
route.post('/logout', AuthController.logout);

export default route;
