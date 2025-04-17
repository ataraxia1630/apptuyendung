const { prisma } = require('../config/db/prismaClient');

const CompanyService = {
  getAllCompanies: async () => {
    return await prisma.company.findMany();
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
        User: true,
      },
    });
  },

  getCompanyById: async (id) => {
    return await prisma.company.findUnique({
      where: { id },
      include: {
        User: true,
      },
    });
  },

  updateCompany: async (id, data) => {
    return await prisma.company.update({
      where: { id },
      data,
      include: {
        User: true,
      },
    });
  },

  deleteCompany: async (id) => {
    return await prisma.company.delete({
      where: { id },
      include: {
        User: true,
      },
    });
  },
};

module.exports = { CompanyService };
