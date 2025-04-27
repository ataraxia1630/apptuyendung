const { ApplicantSkillService } = require('../services/applicantSkill.service');

const SkillController = {
  getAll: async (req, res) => {
    const { applicantId } = req.params;
    try {
      const applicantSkills = await ApplicantSkillService.getAll(applicantId);
      return res.status(200).json(applicantSkills);
    } catch {
      return res.status(500).json('Error fetching applicant skills');
    }
  },

  createNew: async (req, res) => {
    const { applicantId } = req.params;
    const data = req.body;
    try {
      const newSkill = await ApplicantSkillService.createNew(applicantId, data);
      return res.status(201).json(newSkill);
    } catch {
      return res.status(500).json('Error creating new skill');
    }
  },

  updateSkill: async (req, res) => {
    const { id } = req.params;
    const data = req.body;
    try {
      const updatedSkill = await ApplicantSkillService.updateSkill(id, data);
      return res.status(200).json(updatedSkill);
    } catch {
      return res.status(500).json('Error updating skill');
    }
  },

  deleteSkill: async (req, res) => {
    const { id } = req.params;
    try {
      await ApplicantSkillService.deleteSkill(id);
      return res.status(204).send();
    } catch {
      return res.status(500).json('Error deleting skill');
    }
  },

  deleteAll: async (req, res) => {
    const { applicantId } = req.params;
    try {
      await ApplicantSkillService.deleteAll(applicantId);
      return res.status(204).send();
    } catch {
      return res.status(500).json('Error deleting applicant skills');
    }
  },
};

module.exports = { SkillController };
