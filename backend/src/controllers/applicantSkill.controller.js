const { ApplicantSkillService } = require('../services/applicantSkill.service');

const SkillController = {
  getAll: async (req, res) => {
    const { applicantId } = req.params;
    try {
      const applicantSkills = await ApplicantSkillService.getAll(applicantId);
      return res.status(200).json({ skills: applicantSkills });
    } catch (error) {
      return res.status(500).json({
        message: 'Error fetching applicant skills',
        error: error.message || error,
      });
    }
  },

  createNew: async (req, res) => {
    const { applicantId } = req.params;
    const data = req.body;
    try {
      const newSkill = await ApplicantSkillService.createNew(applicantId, data);
      return res.status(201).json({ skill: newSkill });
    } catch (error) {
      return res.status(500).json({
        message: 'Error creating new skill',
        error: error.message || error,
      });
    }
  },

  updateSkill: async (req, res) => {
    const { id } = req.params;
    const data = req.body;
    try {
      const updatedSkill = await ApplicantSkillService.updateSkill(id, data);
      return res.status(200).json({ skill: updatedSkill });
    } catch (error) {
      return res.status(500).json({
        message: 'Error updating skill',
        error: error.message || error,
      });
    }
  },

  deleteSkill: async (req, res) => {
    const { id } = req.params;
    try {
      await ApplicantSkillService.deleteSkill(id);
      return res.status(204).send();
    } catch (error) {
      return res.status(500).json({
        message: 'Error deleting skill',
        error: error.message || error,
      });
    }
  },

  deleteAll: async (req, res) => {
    const { applicantId } = req.params;
    try {
      await ApplicantSkillService.deleteAll(applicantId);
      return res.status(204).send();
    } catch (error) {
      return res.status(500).json({
        message: 'Error deleting all skills',
        error: error.message || error,
      });
    }
  },
};

module.exports = { SkillController };
