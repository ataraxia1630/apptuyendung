const prisma = require('../config/db/prismaClient');
const bcrypt = require('bcryptjs');
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

  changeSetting: async (id, data) => {
    const { oldPassword, newPassword, confirmPassword, ...updateData } = data;

    if (oldPassword) {
      try {
        const user = await prisma.user.findUnique({
          where: { id },
        });
        if (!user) {
          throw new Error('User not found');
        }

        const isPasswordValid = await bcrypt.compare(
          oldPassword,
          user.password
        );
        if (!isPasswordValid) {
          throw new Error('Old password is incorrect');
        }
      } catch (error) {
        throw new Error('Error fetching user (service): ' + error.message);
      }
    }

    try {
      const hashedPassword = await bcrypt.hash(newPassword, 10);
      return await prisma.user.update({
        where: { id },
        data: {
          password: hashedPassword,
          ...updateData,
        },
      });
    } catch (error) {
      throw new Error('Error changing setting (service): ' + error.message);
    }
  },
};

module.exports = { UserService };
