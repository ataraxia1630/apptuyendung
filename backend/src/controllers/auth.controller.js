const { AuthService } = require('../services/auth.service');

const AuthController = {
  register: async (req, res) => {
    try {
      const { username, password, role } = req.body;
      if (!username || !password || !role) {
        return res.status(400).json({ error: 'Missing fields' });
      }
      const user = await AuthService.register(username, password, role);
      res.status(201).json({ message: 'Registered successfully', user });
    } catch (error) {
      res.status(400).json({ error: error.message });
    }
  },

  login: async (req, res) => {
    try {
      const { username, password } = req.body;
      if (!username || !password) {
        return res.status(400).json({ error: 'Missing fields' });
      }
      const { token, role } = await AuthService.login(username, password);
      res.status(200).json({ message: 'Login successful', token, role });
    } catch (error) {
      res.status(401).json({ error: error.message });
    }
  },

  logout: async (req, res) => {
    try {
      const { token } = req.body;
      if (!token) {
        return res.status(400).json({ error: 'Missing token' });
      }
      await AuthService.logout(token);
      res.status(200).json({ message: 'Logout successful' });
    } catch (error) {
      res.status(401).json({ error: error.message });
    }
  },
};

module.exports = { AuthController };
