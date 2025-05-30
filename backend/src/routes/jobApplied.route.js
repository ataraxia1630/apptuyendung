const { Router } = require('express');
const {
  JobAppliedController,
} = require('../controllers/jobApplied.controller');
const {
  ApplySchema,
  ProcessSchema,
} = require('../validators/JobApplied/apply.validator');
const { verifyToken } = require('../middlewares/auth.middleware');
const { requireRole } = require('../middlewares/role.middleware');
const { cache } = require('../middlewares/cache.middleware');
const { validate } = require('../middlewares/validate.middleware');
const route = Router();

// for COMPANY: get all applicant cv applied to a job
route.get(
  '/:jobpostId/cvs',
  verifyToken,
  requireRole('COMPANY'),
  cache,
  JobAppliedController.getAllCvAppliedToJob
);

// for COMPANY: get all applicant applied to a job
route.get(
  '/:jobpostId/applicants',
  verifyToken,
  requireRole('COMPANY'),
  cache,
  JobAppliedController.getAllApplicantAppliedToJob
);

// for COMPANY: process CV applied
route.put(
  '/process-cv',
  verifyToken,
  requireRole('COMPANY'),
  validate(ProcessSchema),
  cache,
  JobAppliedController.processCV
);

// for APPLICANT: get all job applied of a applicant
route.get(
  '/:applicantId',
  verifyToken,
  requireRole('APPLICANT'),
  cache,
  JobAppliedController.getAllJobAppliedOfApplicant
);

// for APPLICANT: apply a job
route.post(
  '/',
  verifyToken,
  requireRole('APPLICANT'),
  validate(ApplySchema),
  JobAppliedController.applyJob
);

// for APPLICANT: withdraw a job applied
route.delete(
  '/:applicantId/:jobpostId',
  verifyToken,
  requireRole('APPLICANT'),
  JobAppliedController.withdraw
);

module.exports = route;
