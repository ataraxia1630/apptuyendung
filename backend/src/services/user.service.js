const { prisma, user } = require('../config/db/prismaClient');
const { ApplicantService } = require('./applicant.service');
const { CompanyService } = require('./company.service');

const UserService = {
  getAllUsers: async () => {
    return await prisma.user.findMany({
      include: {
        OR: [
          {
            Company: true,
          },
          {
            Applicant: true,
          },
        ],
      },
    });
  },

  searchUsersByName: async (name) => {
    const applicants = await ApplicantService.searchApplicantsByName(name);
    const companies = await CompanyService.searchCompaniesByName(name);

    return [...applicants, ...companies];
  },

  getUserById: async (id) => {
    return await prisma.user.findUnique({
      where: { id },
      include: {
        OR: [
          {
            Company: true,
          },
          {
            Applicant: true,
          },
        ],
      },
    });
  },

  updateUser: async (id, data) => {
    return await prisma.user.update({
      where: { id },
      data,
    });
  },

  deleteUser: async (id) => {
    return await prisma.user.delete({
      where: { id },
    });
  },

  changePassword: async (id, password) => {
    return await prisma.user.update({
      where: { id },
      data: { password },
    });
  },
};
