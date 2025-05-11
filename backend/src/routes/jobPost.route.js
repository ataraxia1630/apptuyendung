const express = require('express');
const router = express.Router();
const { JobPostController } = require('../controllers/jobPost.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { requireRole } = require('../middlewares/role.middleware');
const { checkOwnership } = require('../middlewares/checkOwnership.middleware');

router.get('/all', JobPostController.getAllJobPosts);
router.get('/:id', JobPostController.getJobPostById);
router.put('/:id', verifyToken, checkOwnership('JobPost', 'companyId'), JobPostController.updateJobPost);
router.delete('/:id', verifyToken, checkOwnership('JobPost', 'companyId'), JobPostController.deleteJobPost);
router.post('/', verifyToken, requireRole('COMPANY'), JobPostController.createJobPost);
router.get('/search/query', JobPostController.searchJobPosts);

module.exports = router;
