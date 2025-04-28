const { applicant } = require('../config/db/prismaClient');
const { ApplicantService } = require('../services/applicant.service');

const ApplicantController = {
  getAllApplicants: async (req, res) => {
    try {
      const applicants = await ApplicantService.getAllApplicants();
      return res.status(200).json(applicants);
    } catch (error) {
      return res.status(500).json({
        message: 'Error fetching applicants',
        error: error.message || error,
      });
    }
  },

  searchApplicantsByName: async (req, res) => {
    const { name } = req.params;
    try {
      const applicants = await ApplicantService.searchApplicantsByName(name);
      return res.status(200).json(applicants);
    } catch (error) {
      return res.status(500).json({
        message: 'Error fetching applicants',
        error: error.message || error,
      });
    }
  },

  getApplicantById: async (req, res) => {
    const { id } = req.params;
    try {
      const applicant = await ApplicantService.getApplicantById(id);
      return res.status(200).json(applicant);
    } catch (error) {
      return res.status(500).json({
        message: 'Error fetching applicant',
        error: error.message || error,
      });
    }
  },

  updateApplicant: async (req, res) => {
    const { id } = req.params;
    try {
      const applicant = await ApplicantService.updateApplicant(id, req.body);
      return res.status(200).json(applicant);
    } catch (error) {
      return res.status(500).json({
        message: 'Error updating applicant',
        error: error.message || error,
      });
    }
  },

  deleteApplicant: async (req, res) => {
    const { id } = req.params;
    try {
      await ApplicantService.deleteApplicant(id);
      return res.status(204).send();
    } catch (error) {
      return res.status(500).json({
        message: 'Error deleting applicant',
        error: error.message || error,
      });
    }
  },
};

module.exports = { ApplicantController };
