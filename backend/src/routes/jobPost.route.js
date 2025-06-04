const express = require('express');
const router = express.Router();
const { JobPostController } = require('../controllers/jobPost.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
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
router.get('/search/query', JobPostController.searchJobPosts);
router.get('/company/:id', verifyToken, JobPostController.getJobPostsByCompany);

module.exports = router;
