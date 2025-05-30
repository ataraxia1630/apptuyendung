const prisma = require('../config/db/prismaClient');
const EducationService = {
  getAllEdu: async () => {
    try {
      return await prisma.education.findMany();
    } catch (error) {
      throw new Error(
        'Error fetching all educations (service): ' + error.message
      );
    }
  },

  createNewEdu: async (data) => {
    try {
      return await prisma.education.create({
        data,
      });
    } catch (error) {
      throw new Error('Error creating education (service): ' + error.message);
    }
  },

  getAll: async (applicantId) => {
    try {
      return await prisma.applicantEducation.findMany({
        where: { applicantId },
        orderBy: { edu_start: 'desc' },
        include: { achievement: true, education: true },
      });
    } catch (error) {
      throw new Error(
        'Error fetching all education of applicant (service): ' + error.message
      );
    }
  },

  createNew: async (applicantId, data) => {
    try {
      return await prisma.applicantEducation.create({
        data: { ...data, applicantId },
      });
    } catch (error) {
      throw new Error('Error creating education (service): ' + error.message);
    }
  },

  updateEdu: async (id, data) => {
    try {
      return await prisma.applicantEducation.update({
        where: { id },
        data,
      });
    } catch (error) {
      throw new Error('Error updating education (service): ' + error.message);
    }
  },

  deleteEdu: async (id) => {
    try {
      return await prisma.applicantEducation.delete({
        where: { id },
      });
    } catch (error) {
      throw new Error('Error deleting education (service): ' + error.message);
    }
  },

  deleteAll: async (applicantId) => {
    try {
      return await prisma.applicantEducation.deleteMany({
        where: { applicantId },
      });
    } catch (error) {
      throw new Error(
        'Error deleting all educations (service): ' + error.message
      );
    }
  },
};

module.exports = { EducationService };
