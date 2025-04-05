const prisma = require('../config/db/prismaClient');

const ApplicationService = {
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
};
