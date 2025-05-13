const { ExpService } = require('../services/exp.service');

const ExpController = {
  async getAllExp(req, res) {
    try {
      const { applicantId } = req.params;
      if (!applicantId) {
        return res.status(400).json({ message: 'Applicant ID is required' });
      }
      const exp = await ExpService.getAllExp(applicantId);
      return res.status(200).json({ experiences: exp });
    } catch (error) {
      return res.status(500).json({ message: error.message });
    }
  },

  async createExp(req, res) {
    try {
      const { applicantId } = req.params;
      const exp = await ExpService.createExp(applicantId, req.body);
      return res.status(201).json({ experience: exp });
    } catch (error) {
      return res.status(500).json({ message: error.message });
    }
  },

  async updateExp(req, res) {
    try {
      const { id } = req.params;
      const exp = await ExpService.updateExp(id, req.body);
      return res.status(200).json({ experience: exp });
    } catch (error) {
      return res.status(500).json({ message: error.message });
    }
  },

  async deleteExp(req, res) {
    try {
      const { id } = req.params;
      await ExpService.deleteExp(id);
      return res.status(204).send();
    } catch (error) {
      return res.status(500).json({ message: error.message });
    }
  },
};

module.exports = { ExpController };
