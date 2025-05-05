const { Router } = require('express');
const { AuthController } = require('../controllers/auth.controller');
const { validate } = require('../middlewares/validate.middleware');
const { RegisterSchema } = require('../validators/Auth/register.validator');
const { LoginSchema } = require('../validators/Auth/login.validator');

const route = Router();

route.post('/exist', AuthController.checkExistUser);

// B1: send request to send OTP to email
route.post('/send-otp', AuthController.sendOtp);

// B1.1: resend OTP
route.post('/resend-otp', AuthController.resendOtp);

//B2: verify OTP
route.post('/verify-otp', AuthController.verifyOtp);

// B3: register
route.post('/register', validate(RegisterSchema), AuthController.register);

// B4: login
route.post('/login', validate(LoginSchema), AuthController.login);

// B5: logout
route.post('/logout', AuthController.logout);

module.exports = route;
