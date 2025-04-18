const prisma = require('../config/db/prismaClient');

const ApplicantService = {
  getAllApplicants: async () => {
    console.log('Fetching all applicants...');
    const applicants = await prisma.applicant.findMany({
      include: {
        User: true,
      },
    });
    console.log('Applicants:', applicants);
    return applicants;
  },

  searchApplicantsByName: async (fullName) => {
    if (!fullName) {
      throw new Error('Name is required');
    }
    const name = fullName.toLowerCase().trim();

    try {
      const applicants = await prisma.applicant.findMany({
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
      throw new Error('Error searching applicants: ' + error.message);
    }

    return applicants;
  },

  getApplicantById: async (id) => {
    const applicant = await prisma.applicant.findFirst({
      where: { id },
      include: {
        User: true,
      },
    });
    return applicant;
  },

  updateApplicant: async (id, data) => {
    const applicant = await prisma.applicant.update({
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
    return applicant;
  },

  deleteApplicant: async (id) => {
    await prisma.applicant.delete({
      where: { id },
      include: {
        User: true,
      },
    });
  },
};

module.exports = { ApplicantService };
