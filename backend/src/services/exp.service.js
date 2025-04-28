const prisma = require('../config/db/prismaClient');

const ExpService = {
  getAllExp: async (applicantId) => {
    try {
      return await prisma.experience.findMany({
        where: { applicantId },
        orderBy: [{ work_start: 'desc' }],
      });
    } catch (error) {
      throw new Error(
        'Error fetching all experience of applicant (service): ' + error.message
      );
    }
  },

  createExp: async (applicantId, data) => {
    try {
      return await prisma.experience.create({
        data: { ...data, applicantId },
      });
    } catch (error) {
      throw new Error('Error creating experience (service): ' + error.message);
    }
  },

  updateExp: async (id, data) => {
    try {
      return await prisma.experience.update({
        where: { id },
        data,
      });
    } catch (error) {
      throw new Error('Error updating experience (service): ' + error.message);
    }
  },

  deleteExp: async (id) => {
    try {
      return await prisma.experience.delete({
        where: { id },
      });
    } catch (error) {
      throw new Error('Error deleting experience (service): ' + error.message);
    }
  },
};

module.exports = { ExpService };
