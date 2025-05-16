const { EducationService } = require('../services/education.service');

const EducationController = {
  getAllEdu: async (req, res) => {
    try {
      const educations = await EducationService.getAllEdu();
      return res.status(200).json({ educations: educations });
    } catch (error) {
      return res.status(500).json({
        message: 'Error fetching educations',
        error: error.message || error,
      });
    }
  },

  createNewEdu: async (req, res) => {
    try {
      const newEdu = await EducationService.createNewEdu(req.body);
      return res.status(201).json({ education: newEdu });
    } catch (error) {
      return res.status(500).json({
        message: 'Error creating education',
        error: error.message || error,
      });
    }
  },

  getAll: async (req, res) => {
    try {
      const { applicantId } = req.params;
      if (!applicantId) {
        return res.status(400).json({
          message: 'Applicant ID is required',
        });
      }
      const applicantEdu = await EducationService.getAll(applicantId);
      return res.status(200).json({ educations: applicantEdu });
    } catch (error) {
      return res.status(500).json({
        message: 'Error fetching educations for applicant',
        error: error.message || error,
      });
    }
  },

  createNew: async (req, res) => {
    try {
      const { applicantId } = req.params;
      if (!applicantId) {
        return res.status(400).json({
          message: 'Applicant ID is required',
        });
      }
      const newEdu = await EducationService.createNew(applicantId, req.body);
      return res.status(201).json({ education: newEdu });
    } catch (error) {
      return res.status(500).json({
        message: 'Error creating new edu for applicant',
        error: error.message || error,
      });
    }
  },

  updateEdu: async (req, res) => {
    try {
      const { id } = req.params;
      if (!id) {
        return res.status(400).json({
          message: 'Education ID is required',
        });
      }
      const updatedEdu = await EducationService.updateEdu(id, req.body);
      return res.status(200).json({ education: updatedEdu });
    } catch (error) {
      return res.status(500).json({
        message: 'Error updating education',
        error: error.message || error,
      });
    }
  },

  deleteEdu: async (req, res) => {
    try {
      const { id } = req.params;
      if (!id) {
        return res.status(400).json({
          message: 'Education ID is required',
        });
      }
      await EducationService.deleteEdu(id);
      return res.status(204).send();
    } catch (error) {
      return res.status(500).json({
        message: 'Error deleting education',
        error: error.message || error,
      });
    }
  },

  deleteAll: async (req, res) => {
    try {
      const { applicantId } = req.params;
      if (!applicantId) {
        return res.status(400).json({
          message: 'Applicant ID is required',
        });
      }
      await EducationService.deleteAll(applicantId);
      return res.status(204).send();
    } catch (error) {
      return res.status(500).json({
        message: 'Error deleting all educations',
        error: error.message || error,
      });
    }
  },
};

module.exports = { EducationController };
