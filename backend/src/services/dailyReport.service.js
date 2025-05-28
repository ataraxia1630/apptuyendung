const prisma = require('../config/db/prismaClient');

const DailyReportService = {
  getDailyReport: async () => {
    try {
      const today = new Date().toISOString().split('T')[0];
      const startOfToday = new Date();
      startOfToday.setHours(0, 0, 0);
      const endOfToday = new Date();
      endOfToday.setHours(23, 59, 59);
      const newJobCount = await prisma.jobPost.count({
        where: {
          created_at: {
            gte: startOfToday,
            lte: endOfToday,
          },
        },
      });
      const openingJobCount = await prisma.jobPost.count({
        where: {
          status: 'OPENING',
        },
      });
      const recrutingCompanies = await prisma.jobPost.groupBy({
        by: ['companyId'],
        where: {
          status: 'OPENING',
        },
      });

      const recrutingCompanyCount = recrutingCompanies.length;

      const openingJobs = await prisma.jobPost.findMany({
        where: {
          status: 'OPENING',
        },
        include: {
          JobCategory: {
            include: {
              Field: {
                select: { id: true, name: true },
              },
            },
          },
        },
      });

      const fieldMap = new Map();

      for (const job of openingJobs) {
        const field = job.JobCategory?.Field;
        if (!field) continue;

        if (!fieldMap.has(field.id)) {
          fieldMap.set(field.id, { id: field.id, name: field.name, count: 1 });
        } else {
          fieldMap.get(field.id).count += 1;
        }
      }

      const fieldStats = [...fieldMap.values()]
        .sort((a, b) => b.count - a.count)
        .slice(0, 5);

      return {
        today,
        newJobCount,
        openingJobCount,
        recrutingCompanyCount,
        fieldStats,
      };
    } catch (error) {
      throw new Error('Error calculating stats (service)' + error);
    }
  },
};
module.exports = { DailyReportService };
