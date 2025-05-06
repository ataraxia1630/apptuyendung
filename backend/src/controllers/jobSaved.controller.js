const { JobSavedService } = require('../services/jobSaved.service');

const JobSavedController = {
  getAllJobSaved: async (req, res) => {
    const { applicantId } = req.params;
    const { sortOrder = 'desc' } = req.query;
    if (!applicantId)
      return res.status(400).json({ message: 'applicantId are required' });

    try {
      const jobPosts = await JobSavedService.getAllJobSaved(
        applicantId,
        sortOrder
      );
      return res.status(200).json(jobPosts);
    } catch (error) {
      return res.status(500).json({
        message: 'Error fetching all job posts saved by an applicant: ',
        error: error.message || error,
      });
    }
  },

  createJobSaved: async (req, res) => {
    const { applicantId, jobpostId } = req.body;
    if (!applicantId || !jobpostId)
      return res
        .status(400)
        .json({ message: 'applicantId and jobpostId are required' });

    try {
      const jobSaved = await JobSavedService.createJobSaved(
        applicantId,
        jobpostId
      );
      return res.status(201).json(jobSaved);
    } catch (error) {
      return res.status(500).json({
        message: 'Error saving job post: ',
        error: error.message || error,
      });
    }
  },

  deleteJobSaved: async (req, res) => {
    const { applicantId, jobpostId } = req.params;
    if (!applicantId || !jobpostId)
      return res
        .status(400)
        .json({ message: 'applicantId and jobpostId are required' });

    try {
      const jobSaved = await JobSavedService.deleteJobSaved(
        applicantId,
        jobpostId
      );
      return res.status(204).send();
    } catch (error) {
      return res.status(500).json({
        message: 'Error unsaving job post: ',
        error: error.message || error,
      });
    }
  },
};

module.exports = { JobSavedController };
