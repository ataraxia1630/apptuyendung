const prisma = require('../config/db/prismaClient');

const JobCategoryService = {
  getAll: async () => {
    try {
      return await prisma.jobCategory.findMany();
    } catch (error) {
      throw new Error(
        'Error fetching job categories (service): ' + error.message
      );
    }
  },

  createMany: async (jobCategories) => {
    try {
      const categories = await prisma.jobCategory.createManyAndReturn({
        data: jobCategories.map((category) => ({
          name: category.name,
          fieldId: category.fieldId,
        })),
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
