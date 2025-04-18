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
      const { username, email, password } = req.body;
      const { token, role } = await AuthService.login(
        username,
        email,
        password
      );
      return res.status(200).json({ message: 'Login successful', token, role });
    } catch (error) {
      return res.status(401).json({ error: error.message });
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
};

module.exports = { AuthController };
