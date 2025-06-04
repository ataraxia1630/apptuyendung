const { UserService } = require('../services/user.service');

const UserController = {
  getAllUsers: async (req, res) => {
    try {
      const users = await UserService.getAllUsers();
      return res.status(200).json({ users: users });
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
      if (!name) {
        return res.status(400).json({
          message: 'Name parameter is required',
        });
      }
      const users = await UserService.searchUsersByName(name);
      return res.status(200).json({ users: users });
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
      if (!id) {
        return res.status(400).json({
          message: 'User ID is required',
        });
      }
      const user = await UserService.getUserById(id);
      return res.status(200).json({ user: user });
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
      if (!id) {
        return res.status(400).json({
          message: 'User ID is required',
        });
      }
      const user = await UserService.updateUser(id, req.body);
      console.log("co update");
      return res.status(200).json({ user: user });
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
      if (!id) {
        return res.status(400).json({
          message: 'User ID is required',
        });
      }
      await UserService.deleteUser(id);
      return res.status(204).json(user);
    } catch (error) {
      return res.status(500).json({
        message: 'Error deleting educations',
        error: error.message || error,
      });
    }
  },
  changeUserRole: async (req, res) => {
    try {
      const { role } = req.body;
      if (!role) return res.status(400).json({ message: 'Role is required' });
      const user = await UserService.changeUserRole(req.params.id, role);
      return res.status(200).json({ user });
    } catch (error) {
      return res.status(500).json({ message: 'Failed to change user role', error: error.message });
    }
  },

  toggleUserAccountStatus: async (req, res) => {
    try {
      const { status } = req.body;
      if (!status) return res.status(400).json({ message: 'Status is required' });

      const user = await UserService.toggleUserAccountStatus(req.params.id, status);
      return res.status(200).json({ user });
    } catch (error) {
      return res.status(500).json({ message: 'Failed to update account status', error: error.message });
    }
  },


  changeSetting: async (req, res) => {
    try {
      const { id } = req.params;
      if (!id) {
        return res.status(400).json({
          message: 'User ID is required',
        });
      }
      const user = await UserService.changeSetting(id, req.body);
      return res.status(200).json({ message: 'Setting changed successfully' });
    } catch (error) {
      return res.status(500).json({
        message: 'Error changing setting',
        error: error.message || error,
      });
    }
  },
};

module.exports = { UserController };
