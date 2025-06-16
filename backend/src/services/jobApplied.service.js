const prisma = require('../config/db/prismaClient');

const JobAppliedService = {
  getAllCvAppliedToJob: async (jobpostId) => {
    try {
      const jobApplieds = await prisma.jobApplied.findMany({
        where: { jobpostId },
        include: {
          CV: true,
          applicant: true
        },
      });
      return jobApplieds.map((applied) => applied.CV);
    } catch (error) {
      throw new Error(
        'Error fetching CVs applied to jobpost (service): ' + error.message
      );
    }
  },

  getAllApplicantAppliedToJob: async (jobpostId) => {
    try {
      const jobApplieds = await prisma.jobApplied.findMany({
        where: { jobpostId },
        include: {
          applicant: true,
        },
      });
      return jobApplieds.map((applied) => applied.applicant);
    } catch (error) {
      throw new Error(
        'Error fetching applicants applied to jobpost (service): ' +
        error.message
      );
    }
  },

  getAllJobAppliedOfApplicant: async (applicantId) => {
    try {
      const jobApplieds = await prisma.jobApplied.findMany({
        where: { applicantId },
        include: {
          JobPost: true,
          applicant: true
        },
      });
      return jobApplieds.map((applied) => applied.JobPost);
    } catch (error) {
      throw new Error(
        'Error fetching jobposts applied by applicant (service): ' +
        error.message
      );
    }
  },

  applyJob: async (data) => {
    try {
      const { jobpostId, applicantId, cvId } = data;

      const validCV = await prisma.cV.findFirst({
        where: { applicantId },
      });

      if (!validCV) throw new Error("CV doesn't match applicantId");

      const existingApply = await prisma.jobApplied.findFirst({
        where: {
          jobpostId,
          applicantId,
          cvId,
        },
      });

      if (existingApply) throw new Error('Already applied to this job');

      return await prisma.jobApplied.create({ data });
    } catch (error) {
      throw new Error('Error applying to job (service): ' + error.message);
    }
  },

  withdraw: async (applicantId, jobpostId) => {
    try {
      const jobpost = await prisma.jobPost.findUnique({
        where: { id: jobpostId },
      });
      if (!jobpost) throw new Error('Jobpost is not existed');
      if (jobpost.status !== 'OPENING')
        throw new Error(
          'Cannot withdraw because this post was closed or cancelled'
        );
      const existing = await prisma.jobApplied.findFirst({
        where: {
          jobpostId,
          applicantId,
        },
      });
      if (!existing) throw new Error('Not applied yet');
      console.log(existing);
      if (existing.status !== 'PENDING')
        throw new Error(
          'Cannot withdraw because the company processed your CV'
        );
      await prisma.jobApplied.delete({
        where: {
          id: existing.id,
        },
      });
    } catch (error) {
      throw new Error(error || 'Server error');
    }
  },

  processCV: async (data) => {
    try {
      const { applicantId, jobpostId, status } = data;
      const existing = await prisma.jobApplied.findFirst({
        where: {
          jobpostId,
          applicantId,
        },
      });
      if (!existing) throw new Error('Not applied yet');
      return await prisma.jobApplied.update({
        data: {
          status,
        },
        where: { id: existing.id },
      });
    } catch (error) {
      throw new Error(error || 'Server error');
    }
  },
};

module.exports = { JobAppliedService };
