const prisma = require('../config/db/prismaClient');
const { ApplicantService } = require('./applicant.service');
const { CompanyService } = require('./company.service');

const UserService = {
  getAllUsers: async () => {
    try {
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
    } catch (error) {
      throw new Error('Error fetching all users (service): ' + error.message);
    }
  },

  searchUsersByName: async (name) => {
    try {
      const applicants = await ApplicantService.searchApplicantsByName(name);
      const companies = await CompanyService.searchCompaniesByName(name);

      return [...applicants, ...companies];
    } catch (error) {
      throw new Error('Error searching users (service): ' + error.message);
    }
  },

  getUserById: async (id) => {
    try {
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
    } catch (error) {
      throw new Error('Error fetching user (service): ' + error.message);
    }
  },

  updateUser: async (id, data) => {
    try {
      return await prisma.user.update({
        where: { id },
        data,
      });
    } catch (error) {
      throw new Error('Error updating user (service): ' + error.message);
    }
  },

  deleteUser: async (id) => {
    try {
      return await prisma.user.delete({
        where: { id },
      });
    } catch (error) {
      throw new Error('Error deleting user (service): ' + error.message);
    }
  },

  changePassword: async (id, password) => {
    try {
      return await prisma.user.update({
        where: { id },
        data: { password },
      });
    } catch (error) {
      throw new Error('Error changing password (service): ' + error.message);
    }
  },
};
