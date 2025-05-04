const express = require('express');
const router = express.Router();
const { JobPostController } = require('../controllers/jobPost.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { requireRole } = require('../middlewares/role.middleware');
const { checkCompanyOwnership } = require('../middlewares/checkId.middleware');

router.get('/all', verifyToken, JobPostController.getAllJobPosts);
router.get('/:id', verifyToken, JobPostController.getJobPostById);
router.put('/:id', verifyToken, checkCompanyOwnership('JobPost'), JobPostController.updateJobPost);
router.delete('/:id', verifyToken, checkCompanyOwnership('JobPost'), JobPostController.deleteJobPost);
router.post('/', verifyToken, requireRole('COMPANY'), JobPostController.createJobPost);
router.get('/search/query', verifyToken, JobPostController.searchJobPosts);

module.exports = router;
