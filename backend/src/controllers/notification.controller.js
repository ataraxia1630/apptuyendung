const { NotificationService } = require('../services/notification.service');
const NotificationController = {
  getAll: async (req, res) => {
    try {
      const userId = req.user.userId;
      const notifications = await NotificationService.getAll(userId);
      return res.status(200).json({ notifications });
    } catch (error) {
      return res.status(500).json({
        message: 'Error fetching all notifications',
        error: error.message || error,
      });
    }
  },

  delete: async (req, res) => {
    try {
      const userId = req.user.userId;
      const { id } = req.params;
      await NotificationService.delete(userId, id);
      return res.status(204).json({ message: 'Success' });
    } catch (error) {
      return res.status(500).json({
        message: 'Error deleting notification',
        error: error.message || error,
      });
    }
  },

  deleteAll: async (req, res) => {
    try {
      const userId = req.user.userId;
      await NotificationService.deleteAll(userId);
      return res.status(204).json({ message: 'Success' });
    } catch (error) {
      return res.status(500).json({
        message: 'Error deleting all notifications',
        error: error.message || error,
      });
    }
  },
};

module.exports = { NotificationController };
