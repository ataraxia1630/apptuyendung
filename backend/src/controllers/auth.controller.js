const { AuthService } = require('../services/auth.service');

const AuthController = {
  sendOtp: async (req, res) => {
    try {
      if (!req.body.email) {
        return res.status(400).json({ error: 'Email is required' });
      }

      const otp = await AuthService.sendOtp(req.body.email);
      console.log('OTP sent:', otp);
      return res.status(201).json({ message: 'Send OTP successfully' });
    } catch (error) {
      return res.status(500).json({ error: error.message });
    }
  },

  resendOtp: async (req, res) => {
    try {
      if (!req.body.email) {
        return res.status(400).json({ error: 'Email is required' });
      }

      const otp = await AuthService.resendOtp(req.body.email);
      console.log('OTP resent:', otp);
      return res.status(201).json({ message: 'Resend OTP successfully' });
    } catch (error) {
      return res.status(500).json({ error: error.message });
    }
  },

  verifyOtp: async (req, res) => {
    try {
      await AuthService.verifyOtp(req.body.email, req.body.otp);
      return res.status(200).json({ message: 'Verify OTP successfully' });
    } catch (error) {
      return res.status(500).json({ error: error.message });
    }
  },

  checkExistUser: async (req, res) => {
    try {
      await AuthService.checkExistUser(req.body);
      return res.status(200).json({ message: 'Can create new user' });
    } catch (error) {
      return res.status(400).json({ error: error.message });
    }
  },

  register: async (req, res) => {
    try {
      const user = await AuthService.register(req.body);
      return res.status(201).json({ message: 'Registered successfully', user });
    } catch (error) {
      return res.status(400).json({ error: error.message });
    }
  },

  login: async (req, res) => {
    try {
      const { username, password } = req.body;
      const { token, user } = await AuthService.login(username, password);
      res.status(200).json({ message: 'Login successful', token, user });
    } catch (error) {
      res.status(401).json({ error: error.message });
    }
  },

  logout: async (req, res) => {
    try {
      const { token } = req.body;
      await AuthService.logout(token);
      return res.status(200).json({ message: 'Logout successful' });
    } catch (error) {
      return res.status(401).json({ error: error.message });
    }
  },

  loginGoogle: async (req, res) => {
    try {
      const { idToken, role = 'APPLICANT' } = req.body;
      const { token, user } = await AuthService.loginGoogle(idToken, role);
      return res.status(200).json({ message: 'Login successful', token, user });
    } catch (error) {
      return res.status(401).json({ error: error.message });
    }
  },
};

module.exports = { AuthController };
