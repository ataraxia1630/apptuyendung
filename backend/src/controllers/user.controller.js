const { UserService } = require('../services/user.service');

const UserController = {
  getAllUsers: async (req, res) => {
    try {
      const users = await UserService.getAllUsers();
      return res.status(200).json(users);
    } catch (error) {
      return res.status(500).json({
        message: 'Error fetching all users',
        error: error.message || error,
      });
    }
  },

  searchUsersByName: async (req, res) => {
    try {
      const { name } = req.params;
      const users = await UserService.searchUsersByName(name);
      return res.status(200).json(users);
    } catch (error) {
      return res.status(500).json({
        message: 'Error searching users',
        error: error.message || error,
      });
    }
  },

  getUserById: async (req, res) => {
    try {
      const { id } = req.params;
      const user = await UserService.getUserById(id);
      return res.status(200).json(user);
    } catch (error) {
      return res.status(500).json({
        message: 'Error fetching user',
        error: error.message || error,
      });
    }
  },

  updateUser: async (req, res) => {
    try {
      const { id } = req.params;
      const user = await UserService.updateUser(id, req.body);
      return res.status(200).json(user);
    } catch (error) {
      return res.status(500).json({
        message: 'Error updating educations',
        error: error.message || error,
      });
    }
  },

  deleteUser: async (req, res) => {
    try {
      const { id } = req.params;
      const user = await UserService.deleteUser(id);
      return res.status(200).json({ message: 'User deleted successfully' });
    } catch (error) {
      return res.status(500).json({
        message: 'Error deleting educations',
        error: error.message || error,
      });
    }
  },

  changePassword: async (req, res) => {
    try {
      const { id } = req.params;
      const { newPassword } = req.body;
      const user = await UserService.changePassword(id, newPassword);
      return res.status(200).json({ message: 'Password changed successfully' });
    } catch (error) {
      return res.status(500).json({
        message: 'Error changing password',
        error: error.message || error,
      });
    }
  },
};

module.exports = { UserController };
