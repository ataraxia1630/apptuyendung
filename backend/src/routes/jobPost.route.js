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
router.post('/', verifyToken, requireRole('COMPANY'), validate(JobPostSchema), JobPostController.createJobPost);
router.get('/search/query', cache, JobPostController.searchJobPosts);
router.get('/company/:id', verifyToken, cache, JobPostController.getJobPostsByCompany);
//Danh cho admin
router.get('/admin/pending', verifyToken, verifyAdmin, cache, JobPostController.getPendingJobPosts);
router.put('/admin/toggle/:id', verifyToken, verifyAdmin, JobPostController.toggleJobPostStatus);

module.exports = router;
