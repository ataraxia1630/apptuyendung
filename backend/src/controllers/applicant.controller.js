const { applicant } = require('../config/db/prismaClient');
const { ApplicantService } = require('../services/applicant.service');

const ApplicantController = {
  getAllApplicants: async (req, res) => {
    try {
      const applicants = await ApplicantService.getAllApplicants();
      return res.status(200).json(applicants);
    } catch (error) {
      return res
        .status(500)
        .json({ message: 'Error fetching applicants', error });
    }
  },

  getApplicantsByName: async (req, res) => {
    const { name } = req.params;
    try {
      const applicants = await ApplicantService.getApplicantsByName(name);
      return res.status(200).json(applicants);
    } catch (error) {
      return res
        .status(500)
        .json({ message: 'Error fetching applicants', error });
    }
  },

  getApplicantById: async (req, res) => {
    const { id } = req.params;
    try {
      const applicant = await ApplicantService.getApplicantById(id);
      if (!applicant) {
        return res.status(404).json({ message: 'Applicant not found' });
      }
      return res.status(200).json(applicant);
    } catch (error) {
      return res
        .status(500)
        .json({ message: 'Error fetching applicant', error });
    }
  },

  updateApplicant: async (req, res) => {
    const { id } = req.params;
    try {
      const applicant = await ApplicantService.updateApplicant(id, req.body);
      return res.status(200).json(applicant);
    } catch (error) {
      return res
        .status(500)
        .json({ message: 'Error updating applicant', error });
    }
  },

  deleteApplicant: async (req, res) => {
    const { id } = req.params;
    try {
      await ApplicantService.deleteApplicant(id);
      return res.status(200).json({ message: 'Delete successfully' });
    } catch (error) {
      return res
        .status(500)
        .json({ message: 'Error deleting applicant', error });
    }
  },
};

module.exports = { ApplicantController };
