const { UserService } = require('../services/user.service');

const UserController = {
  getAllUsers: async (req, res) => {
    try {
      const users = await UserService.getAllUsers();
      return res.status(200).json(users);
    } catch (error) {
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  searchUsersByName: async (req, res) => {
    try {
      const { name } = req.params;
      const users = await UserService.searchUsersByName(name);
      if (!user) {
        return res.status(404).json({ message: 'No results' });
      }
      return res.status(200).json(users);
    } catch (error) {
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  getUserById: async (req, res) => {
    try {
      const { id } = req.params;
      const user = await UserService.getUserById(id);
      if (!user) {
        return res.status(404).json({ message: 'User not found' });
      }
      return res.status(200).json(user);
    } catch (error) {
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  updateUser: async (req, res) => {
    try {
      const { id } = req.params;
      const user = await UserService.updateUser(id, req.body);
      if (!user) {
        return res.status(404).json({ message: 'User not found' });
      }
      return res.status(200).json(user);
    } catch (error) {
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  deleteUser: async (req, res) => {
    try {
      const { id } = req.params;
      const user = await UserService.deleteUser(id);
      if (!user) {
        return res.status(404).json({ message: 'User not found' });
      }
      return res.status(200).json({ message: 'User deleted successfully' });
    } catch (error) {
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  changePassword: async (req, res) => {
    try {
      const { id } = req.params;
      const { newPassword } = req.body;
      const user = await UserService.changePassword(id, newPassword);
      if (!user) {
        return res.status(404).json({ message: 'User not found' });
      }
      return res.status(200).json({ message: 'Password changed successfully' });
    } catch (error) {
      return res.status(500).json({ message: 'Internal server error' });
    }
  },
};

module.exports = { UserController };
