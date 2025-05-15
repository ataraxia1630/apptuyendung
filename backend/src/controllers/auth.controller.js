const { AuthService } = require('../services/auth.service');

const AuthController = {
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
