const { EducationService } = require('../services/education.service');

const EducationController = {
  getAllEdu: async (req, res) => {
    try {
      const educations = await EducationService.getAll();
      res.status(200).json(educations);
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  },

  createNewEdu: async (req, res) => {
    try {
      const newEdu = await EducationService.createNewEdu(req.body);
      res.status(201).json(newEdu);
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  },

  getAll: async (req, res) => {
    try {
      const { applicantId } = req.params;
      const applicantEdu = await EducationService.getAll(applicantId);
      res.status(200).json(applicantEdu);
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  },

  createNew: async (req, res) => {
    try {
      const { applicantId } = req.params;
      const newEdu = await EducationService.createNew(applicantId, req.body);
      res.status(201).json(newEdu);
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  },

  updateEdu: async (req, res) => {
    try {
      const { id } = req.params;
      const updatedEdu = await EducationService.updateEdu(id, req.body);
      res.status(200).json(updatedEdu);
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  },

  deleteEdu: async (req, res) => {
    try {
      const { id } = req.params;
      await EducationService.deleteEdu(id);
      res.status(204).send();
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  },

  deleteAll: async (req, res) => {
    try {
      const { applicantId } = req.params;
      await EducationService.deleteAll(applicantId);
      res.status(204).send();
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  },
};

module.exports = { EducationController };
