const { ApplicantEduService } = require('../services/applicantEdu.service');

const ApplicantEduController = {
  getAll: async (req, res) => {
    try {
      const { applicantId } = req.params;
      const applicantEdu = await ApplicantEduService.getAll(applicantId);
      res.status(200).json(applicantEdu);
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  },

  createNew: async (req, res) => {
    try {
      const { applicantId } = req.params;
      const newEdu = await ApplicantEduService.createNew(applicantId, req.body);
      res.status(201).json(newEdu);
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  },

  updateEdu: async (req, res) => {
    try {
      const { id } = req.params;
      const updatedEdu = await ApplicantEduService.updateEdu(id, req.body);
      res.status(200).json(updatedEdu);
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  },

  deleteEdu: async (req, res) => {
    try {
      const { id } = req.params;
      await ApplicantEduService.deleteEdu(id);
      res.status(204).send();
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  },

  deleteAll: async (req, res) => {
    try {
      const { applicantId } = req.params;
      await ApplicantEduService.deleteAll(applicantId);
      res.status(204).send();
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  },
};

module.exports = { ApplicantEduController };
