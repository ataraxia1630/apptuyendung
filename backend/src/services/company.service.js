const { prisma } = require('../config/db/prismaClient');

const CompanyService = {
  getAllCompanies: async () => {
    return await prisma.company.findMany({
      include: {
        users: true,
      },
    });
  },

  searchCompaniesByName: async (name) => {
    return await prisma.company.findMany({
      where: {
        name: {
          contains: name,
          mode: 'insensitive',
        },
      },
      include: {
        users: true,
      },
    });
  },

  getCompanyById: async (id) => {
    return await prisma.company.findUnique({
      where: { id },
      include: {
        users: true,
      },
    });
  },

  updateCompany: async (id, data) => {
    return await prisma.company.update({
      where: { id },
      data,
    });
  },

  deleteCompany: async (id) => {
    return await prisma.company.delete({
      where: { id },
    });
  },
};

module.exports = { CompanyService };
