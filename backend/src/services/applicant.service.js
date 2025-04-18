const prisma = require('../config/db/prismaClient');

const ApplicantService = {
  getAllApplicants: async () => {
    try {
      return await prisma.applicant.findMany({
        include: {
          User: true,
        },
      });
    } catch (error) {
      throw new Error('Error fetching applicants (service): ' + error.message);
    }
  },

  searchApplicantsByName: async (fullName) => {
    if (!fullName) {
      throw new Error('Name is required');
    }
    const name = fullName.toLowerCase().trim();

    try {
      return await prisma.applicant.findMany({
        where: {
          AND: name.split(' ').map((part) => ({
            OR: [
              { firstName: { contains: part, mode: 'insensitive' } },
              { lastName: { contains: part, mode: 'insensitive' } },
            ],
          })),
        },
        include: {
          User: true,
        },
      });
    } catch (error) {
      throw new Error('Error searching applicants (service): ' + error.message);
    }
  },

  getApplicantById: async (id) => {
    try {
      return await prisma.applicant.findFirst({
        where: { id },
        include: {
          User: true,
        },
      });
    } catch (error) {
      throw new Error(
        'Error fetching applicant by ID (service): ' + error.message
      );
    }
  },

  updateApplicant: async (id, data) => {
    try {
      return await prisma.applicant.update({
        where: {
          id,
        },
        data: {
          ...data,
        },
        include: {
          User: true,
        },
      });
    } catch (error) {
      throw new Error('Error updating applicant (service): ' + error.message);
    }
  },

  deleteApplicant: async (id) => {
    try {
      return await prisma.applicant.delete({
        where: {
          id,
        },
      });
    } catch (error) {
      throw new Error('Error deleting applicant (service): ' + error.message);
    }
  },
};

module.exports = { ApplicantService };
