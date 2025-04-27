const prisma = require('../config/db/prismaClient');

const CompanyService = {
  getAllCompanies: async () => {
    try {
      return await prisma.company.findMany();
    } catch (error) {
      throw new Error('Error fetching companies (service): ' + error.message);
    }
  },

  searchCompaniesByName: async (name) => {
    try {
      return await prisma.company.findMany({
        where: {
          name: {
            contains: name,
            mode: 'insensitive',
          },
        },
        include: {
          User: true,
        },
      });
    } catch (error) {
      throw new Error('Error searching companies (service): ' + error.message);
    }
  },

  getCompanyById: async (id) => {
    try {
      return await prisma.company.findUnique({
        where: { id },
        include: {
          User: true,
        },
      });
    } catch (error) {
      throw new Error(
        'Error fetching company by ID (service): ' + error.message
      );
    }
  },

  updateCompany: async (id, data) => {
    try {
      return await prisma.company.update({
        where: { id },
        data,
        include: {
          User: true,
        },
      });
    } catch (error) {
      throw new Error('Error updating company (service): ' + error.message);
    }
  },

  deleteCompany: async (id) => {
    try {
      return await prisma.company.delete({
        where: { id },
        include: {
          User: true,
        },
      });
    } catch (error) {
      throw new Error('Error deleting company (service): ' + error.message);
    }
  },
};

module.exports = { CompanyService };
