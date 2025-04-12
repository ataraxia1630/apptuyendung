const prisma = require('../config/db/prismaClient');
const EducationService = {
  getAllEdu: async () => {
    try {
      const educations = await prisma.education.findMany();
      return educations;
    } catch (error) {
      throw new Error('Error fetching education records');
    }
  },

  createNewEdu: async (data) => {
    try {
      const newEdu = await prisma.education.create({
        data,
      });
      return newEdu;
    } catch (error) {
      throw new Error('Error creating education record');
    }
  },

  getAll: async (applicantId) => {
    try {
      const applicantEdu = await prisma.applicantEducation.findMany({
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
      const newEdu = await prisma.applicantEducation.create({
        data: { ...data, applicantId },
      });
      return newEdu;
    } catch (error) {
      throw new Error('Error creating education record');
    }
  },

  updateEdu: async (id, data) => {
    try {
      const updatedEdu = await prisma.applicantEducation.update({
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
      await prisma.applicantEducation.delete({
        where: { id },
      });
    } catch (error) {
      throw new Error('Error deleting education record');
    }
  },

  deleteAll: async (applicantId) => {
    try {
      await prisma.applicantEducation.deleteMany({
        where: { applicantId },
      });
    } catch (error) {
      throw new Error('Error deleting all education records');
    }
  },
};

module.exports = { EducationService };
