const express = require('express');
const router = express.Router();
const { JobPostController } = require('../controllers/jobPost.controller');
const { verifyToken, verifyAdmin } = require('../middlewares/auth.middleware');
const { requireRole } = require('../middlewares/role.middleware');
const { checkOwnership } = require('../middlewares/checkOwnership.middleware');
const { cache } = require('../middlewares/cache.middleware');
const { validate } = require('../middlewares/validate.middleware');
const { JobPostSchema } = require('../validators/JobPost/jobPost.validator');
const { JobPostUpdateSchema } = require('../validators/JobPost/jobPostUpdate.validator');

router.get('/all', cache, JobPostController.getAllJobPosts);
router.get('/:id', cache, JobPostController.getJobPostById);
router.put('/:id', verifyToken, checkOwnership('JobPost', 'companyId'), validate(JobPostUpdateSchema), JobPostController.updateJobPost);
router.delete('/:id', verifyToken, checkOwnership('JobPost', 'companyId'), JobPostController.deleteJobPost);
router.get('/search/query', cache, JobPostController.searchJobPosts);
router.get('/company/:id', verifyToken, JobPostController.getJobPostsByCompany);
//Danh cho admin
router.get(
    '/admin/by-status',
    verifyToken,
    requireRole('ADMIN'),
    cache,
    JobPostController.getJobPostsByStatus
);

router.put('/admin/toggle/:id', verifyToken, requireRole('ADMIN'), JobPostController.toggleJobPostStatus);
//Danh cho company
router.get('/company/me/jobs-applications', verifyToken, requireRole('COMPANY'), JobPostController.getMyJobsWithApplications);
router.post('/', verifyToken, requireRole('COMPANY'), validate(JobPostSchema), JobPostController.createJobPost);
router.get('/company/me/:id', verifyToken, requireRole('COMPANY'), checkOwnership('JobPost', 'companyId'), JobPostController.getMyJobPostById);
module.exports = router;
