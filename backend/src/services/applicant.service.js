const prisma = require('../config/db/prismaClient');

const ApplicantService = {
  getAllApplicants: async () => {
    const applicants = await prisma.applicant.findMany({
      include: {
        User: true,
      },
    });
    return applicants;
  },
  getApplicantsByName: async (fullName) => {
    const name = fullName.toLowerCase().trim();

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
