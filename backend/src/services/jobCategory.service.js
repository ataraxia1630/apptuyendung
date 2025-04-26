const prisma = require('../config/db/prismaClient');

const JobCategoryService = {
  getAll: async () => {
    try {
      return await new prisma.jobCategory.findMany();
    } catch (error) {
      throw new Error(
        'Error fetching job categories (service): ' + error.message
      );
    }
  },

  createMany: async (jobCategories) => {
    try {
      const categories = await new prisma.jobCategory.createMany({
        data: jobCategories,
        skipDuplicates: true,
      });
      return categories;
    } catch (error) {
      throw new Error(
        'Error creating job categories (service): ' + error.message
      );
    }
  },
};

module.exports = { JobCategoryService };
