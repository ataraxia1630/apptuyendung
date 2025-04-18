const { EducationService } = require('../services/education.service');

const EducationController = {
  getAllEdu: async (req, res) => {
    try {
      const educations = await EducationService.getAll();
      res.status(200).json(educations);
    } catch (error) {
      res.status(500).json({
        message: 'Error fetching educations',
        error: error.message || error,
      });
    }
  },

  createNewEdu: async (req, res) => {
    try {
      const newEdu = await EducationService.createNewEdu(req.body);
      res.status(201).json(newEdu);
    } catch (error) {
      res.status(500).json({
        message: 'Error creating education',
        error: error.message || error,
      });
    }
  },

  getAll: async (req, res) => {
    try {
      const { applicantId } = req.params;
      const applicantEdu = await EducationService.getAll(applicantId);
      res.status(200).json(applicantEdu);
    } catch (error) {
      res.status(500).json({
        message: 'Error fetching educations for applicant',
        error: error.message || error,
      });
    }
  },

  createNew: async (req, res) => {
    try {
      const { applicantId } = req.params;
      const newEdu = await EducationService.createNew(applicantId, req.body);
      res.status(201).json(newEdu);
    } catch (error) {
      res.status(500).json({
        message: 'Error creating new edu for applicant',
        error: error.message || error,
      });
    }
  },

  updateEdu: async (req, res) => {
    try {
      const { id } = req.params;
      const updatedEdu = await EducationService.updateEdu(id, req.body);
      res.status(200).json(updatedEdu);
    } catch (error) {
      res.status(500).json({
        message: 'Error updating education',
        error: error.message || error,
      });
    }
  },

  deleteEdu: async (req, res) => {
    try {
      const { id } = req.params;
      await EducationService.deleteEdu(id);
      res.status(204).send();
    } catch (error) {
      res.status(500).json({
        message: 'Error deleting education',
        error: error.message || error,
      });
    }
  },

  deleteAll: async (req, res) => {
    try {
      const { applicantId } = req.params;
      await EducationService.deleteAll(applicantId);
      res.status(204).send();
    } catch (error) {
      res.status(500).json({
        message: 'Error deleting all educations',
        error: error.message || error,
      });
    }
  },
};

module.exports = { EducationController };
