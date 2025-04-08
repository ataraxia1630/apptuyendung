const prisma = require('../config/db/prismaClient');

const ApplicantSkillService = {
  getAll: async (applicantId) => {
    if (!applicantId) throw new Error('applicantId is required');
    const skills = await prisma.skill.findMany({
      where: { applicantId },
    });
    return skills;
  },

  createNew: async (applicantId, data) => {
    if (!applicantId) throw new Error('applicantId is required');
    if (!data) throw new Error('data is required');
    const { skillName, certificate } = data;
    if (!skillName || !certificate)
      throw new Error('skillName and certificate is requireds');
    const skill = await prisma.skill.create({
      data: { ...data, applicantId },
    });
    return skill;
  },

  updateSkill: async (id, data) => {
    if (!data) throw new Error('data is required');
    if (!id) throw new Error('id is required');
    const skill = await prisma.skill.update({
      where: { id },
      data,
    });
    return skill;
  },

  deleteSkill: async (id) => {
    if (!id) throw new Error('id is required');
    const skill = await prisma.skill.delete({
      where: { id },
    });
    return skill;
  },

  deleteAll: async (applicantId) => {
    if (!applicantId) throw new Error('applicantId is required');
    const skills = await prisma.skill.deleteMany({
      where: { applicantId },
    });
    return skills;
  },
};

module.exports = { ApplicantSkillService };
