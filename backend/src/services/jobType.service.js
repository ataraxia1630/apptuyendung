const prisma = require('../config/db/prismaClient');

const JobTypeService = {
  getAll: async () => {
    try {
      return await prisma.jobType.findMany();
    } catch (error) {
      throw new Error('Error fetching job types (service): ' + error.message);
    }
  },

  createMany: async (jobTypes) => {
    try {
      const types = await prisma.jobType.createManyAndReturn({
        data: jobTypes.map((type) => ({ name: type })),
        skipDuplicates: true,
      });
      return types;
    } catch (error) {
      throw new Error('Error creating job types (service): ' + error.message);
    }
  },
};

module.exports = { JobTypeService };
