const { ApplicationService } = require('../services/applicant.service');

const ApplicantController = {
  getAllApplicants: async (req, res) => {
    try {
      const applicants = await ApplicationService.getAllApplicants();
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
      const applicants = await ApplicationService.getApplicantsByName(name);
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
      const applicant = await ApplicationService.getApplicantById(id);
      return res.status(200).json(applicant);
    } catch (error) {
      return res
        .status(500)
        .json({ message: 'Error fetching applicant', error });
    }
  },
};
