const { ApplicantSkillService } = require('../services/applicantSkill.service');

const SkillController = {
  getAll: async (req, res) => {
    const { applicantId } = req.params;
    try {
      const applicantSkills = await ApplicantSkillService.getAll(applicantId);
      res.status(200).json(applicantSkills);
    } catch {
      res.status(501).json('Error fetching applicant skills');
    }
  },

  createNew: async (req, res) => {
    const { applicantId } = req.params;
    const data = req.body;
    try {
      const newSkill = await ApplicantSkillService.createNew(applicantId, data);
      res.status(200).json(newSkill);
    } catch {
      res.status(501).json('Error creating new skill');
    }
  },

  updateSkill: async (req, res) => {
    const { id } = req.params;
    const data = req.body;
    try {
      const updatedSkill = await ApplicantSkillService.updateSkill(id, data);
      res.status(200).json(updatedSkill);
    } catch {
      res.status(501).json('Error updating skill');
    }
  },

  deleteSkill: async (req, res) => {
    const { id } = req.params;
    try {
      const deletedSkill = await ApplicantSkillService.deleteSkill(id);
      res.status(200).json(deletedSkill);
    } catch {
      res.status(501).json('Error deleting skill');
    }
  },

  deleteAll: async (req, res) => {
    const { applicantId } = req.params;
    try {
      const deletedSkills = await ApplicantSkillService.deleteAll(applicantId);
      res.status(200).json(deletedSkills);
    } catch {
      res.status(501).json('Error deleting applicant skills');
    }
  },
};

module.exports = { SkillController };
