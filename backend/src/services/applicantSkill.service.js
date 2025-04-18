const prisma = require('../config/db/prismaClient');

const ApplicantSkillService = {
  getAll: async (applicantId) => {
    try {
      if (!applicantId) throw new Error('applicantId is required');
      return await prisma.skill.findMany({
        where: { applicantId },
      });
    } catch (error) {
      throw new Error('Error fetching skills (service): ' + error.message);
    }
  },

  createNew: async (applicantId, data) => {
    if (!applicantId) throw new Error('applicantId is required');
    if (!data) throw new Error('data is required');
    const { skillName, certificate } = data;
    if (!skillName || !certificate)
      throw new Error('skillName and certificate is requireds');

    try {
      return await prisma.skill.create({
        data: { ...data, applicantId },
      });
    } catch (error) {
      throw new Error('Error creating skill (service): ' + error.message);
    }
  },

  updateSkill: async (id, data) => {
    if (!data) throw new Error('data is required');
    if (!id) throw new Error('id is required');
    try {
      return await prisma.skill.update({
        where: { id },
        data,
      });
    } catch (error) {
      throw new Error('Error updating skill (service): ' + error.message);
    }
  },

  deleteSkill: async (id) => {
    if (!id) throw new Error('id is required');

    try {
      return await prisma.skill.delete({
        where: { id },
      });
    } catch (error) {
      throw new Error('Error deleting skill (service): ' + error.message);
    }
  },

  deleteAll: async (applicantId) => {
    if (!applicantId) throw new Error('applicantId is required');
    try {
      return await prisma.skill.deleteMany({
        where: { applicantId },
      });
    } catch (error) {
      throw new Error('Error deleting all skills (service): ' + error.message);
    }
  },
};

module.exports = { ApplicantSkillService };
