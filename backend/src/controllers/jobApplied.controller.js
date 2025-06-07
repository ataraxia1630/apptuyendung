const { JobAppliedService } = require('../services/jobApplied.service');
const { JobPostService } = require('../services/jobPost.service');
const { CompanyService } = require('../services/company.service');
const { ApplicantService } = require('../services/applicant.service');
const NotiEmitter = require('../emitters/notification.emitter');

const JobAppliedController = {
  getAllCvAppliedToJob: async (req, res) => {
    const { jobpostId } = req.params;
    if (!jobpostId)
      return res.status(400).json({ message: 'jobpostId is required' });

    try {
      const appliedCVs = await JobAppliedService.getAllCvAppliedToJob(
        jobpostId
      );
      return res.status(200).json({ appliedCVs: appliedCVs });
    } catch (error) {
      return res.status(500).json({ message: '', error: error.message });
    }
  },

  getAllApplicantAppliedToJob: async (req, res) => {
    const { jobpostId } = req.params;
    if (!jobpostId)
      return res.status(400).json({ message: 'jobpostId is required' });

    try {
      const appliedApplicants =
        await JobAppliedService.getAllApplicantAppliedToJob(jobpostId);
      return res.status(200).json({ appliedApplicants: appliedApplicants });
    } catch (error) {
      return res.status(500).json({ message: '', error: error.message });
    }
  },

  getAllJobAppliedOfApplicant: async (req, res) => {
    const { applicantId } = req.params;
    if (!applicantId)
      return res.status(400).json({ message: 'applicantId is required' });
    try {
      const appliedJobposts =
        await JobAppliedService.getAllJobAppliedOfApplicant(applicantId);
      return res.status(200).json({ appliedJobposts: appliedJobposts });
    } catch (error) {
      return res.status(500).json({ message: '', error: error.message });
    }
  },

  applyJob: async (req, res) => {
    try {
      const apply = await JobAppliedService.applyJob(req.body);
      res.status(201).json({ apply: apply })(async () => {
        try {
          const jobpost = await JobPostService.getJobPostById(
            req.body.jobpostId
          );
          const company = await CompanyService.getCompanyById(
            jobpost.companyId
          );

          NotiEmitter.emit('job.applied', {
            userId: company.User.id,
            jobTitle: jobpost.title,
          });
        } catch (err) {
          console.error('Error while sending notification:', err.message);
        }
      })();
    } catch (error) {
      return res.status(500).json({ message: '', error: error.message });
    }
  },

  withdraw: async (req, res) => {
    try {
      const { jobpostId, applicantId } = req.params;
      if (!jobpostId)
        return res.status(400).json({ message: 'jobpostId is required' });
      if (!applicantId)
        return res.status(400).json({ message: 'applicantId is required' });
      await JobAppliedService.withdraw(applicantId, jobpostId);
      return res.status(204).send();
    } catch (error) {
      return res.status(500).json({ message: error.message });
    }
  },

  processCV: async (req, res) => {
    try {
      const jobApplied = await JobAppliedService.processCV(req.body);
      res.status(200).json({ jobApplied });
      if (jobApplied.status === 'SUCCESS' || jobApplied.status === 'FAILURE') {
        (async () => {
          try {
            const jobpost = await JobPostService.getJobPostById(
              jobApplied.jobpostId
            );
            const applicant = await ApplicantService.getApplicantById(
              jobApplied.applicantId
            );

            NotiEmitter.emit(
              jobApplied.status === 'SUCCESS'
                ? 'jobApplied.success'
                : 'jobApplied.fail',
              {
                userId: applicant.User.id,
                jobTitle: jobpost.title,
              }
            );
          } catch (err) {
            console.error('Error while sending notification:', err.message);
          }
        })();
      }
    } catch (error) {
      return res.status(500).json({ message: error.message });
    }
  },
};

module.exports = { JobAppliedController };
