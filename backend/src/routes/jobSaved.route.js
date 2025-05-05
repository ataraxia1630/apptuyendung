const { Router } = require('express');
const { JobSavedController } = require('../controllers/jobSaved.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { cache } = require('../middlewares/cache.middleware');

const route = Router();

// get all job posts saved by an applicant
route.get(
  '/:applicantId',
  verifyToken,
  cache,
  JobSavedController.getAllJobSaved
);

// saved a job post
route.post('/', verifyToken, JobSavedController.createJobSaved);

// unsaved a job post
route.delete(
  '/:applicantId/:jobpostId',
  verifyToken,
  JobSavedController.deleteJobSaved
);

module.exports = route;
