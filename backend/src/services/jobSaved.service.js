const prisma = require('../config/db/prismaClient');

const JobSavedService = {
  getAllJobSaved: async (applicantId, sortOrder) => {
    try {
      const jobSaveds = await prisma.jobSaved.findMany({
        where: { applicantId },
        orderBy: { created_at: sortOrder },
        include: {
          JobPost: true,
        },
      });

      return jobSaveds.map((saved) => saved.JobPost);
    } catch (error) {
      throw new Error(
        'Error fetching all job posts saved by an applicant (service): ' +
          error.message
      );
    }
  },

  createJobSaved: async (applicantId, jobpostId) => {
    try {
      return await prisma.jobSaved.create({
        data: { applicantId, jobpostId },
      });
    } catch (error) {
      throw new Error('Error saving job post (service): ' + error.message);
    }
  },

  deleteJobSaved: async (applicantId, jobpostId) => {
    try {
      return await prisma.jobSaved.delete({
        where: {
          jobpostId_applicantId: {
            jobpostId,
            applicantId,
          },
        },
      });
    } catch (error) {
      throw new Error('Error unsaving job post (service): ' + error.message);
    }
  },
};

module.exports = { JobSavedService };
