const { prisma } = require('../prismaClient');
const ApplicantEduService = {
  getAll: async (applicantId) => {
    try {
      const applicantEdu = await prisma.applicantEdu.findMany({
        where: { applicantId },
        order: [['startDate', 'DESC']],
        include: { achievements: true },
      });
      return applicantEdu;
    } catch (error) {
      throw new Error('Error fetching education records');
    }
  },

  createNew: async (applicantId, data) => {
    try {
      const newEdu = await prisma.applicantEdu.create({
        data: { ...data, applicantId },
      });
      return newEdu;
    } catch (error) {
      throw new Error('Error creating education record');
    }
  },

  updateEdu: async (id, data) => {
    try {
      const updatedEdu = await prisma.applicantEdu.update({
        where: { id },
        data,
      });
      return updatedEdu;
    } catch (error) {
      throw new Error('Error updating education record');
    }
  },

  deleteEdu: async (id) => {
    try {
      await prisma.applicantEdu.delete({
        where: { id },
      });
    } catch (error) {
      throw new Error('Error deleting education record');
    }
  },

  deleteAll: async (applicantId) => {
    try {
      await prisma.applicantEdu.deleteMany({
        where: { applicantId },
      });
    } catch (error) {
      throw new Error('Error deleting all education records');
    }
  },
};

module.exports = { ApplicantEduService };
