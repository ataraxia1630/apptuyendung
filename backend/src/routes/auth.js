const express = require('express');
const authRouter = express.Router();

const authController = require('../controllers/AuthController');
authRouter.use('/signup', authController.signup);
authRouter.use('/', authController.index);

module.exports = authRouter;
