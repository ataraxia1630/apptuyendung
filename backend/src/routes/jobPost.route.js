const express = require('express');
const router = express.Router();
const { JobPostController } = require('../controllers/jobPost.controller');
const { verifyToken } = require('../middlewares/auth.middleware');

router.get('/all', verifyToken, JobPostController.getAllJobPosts);
router.get('/:id', verifyToken, JobPostController.getJobPostById);
router.put('/:id', verifyToken, JobPostController.updateJobPost);
router.delete('/:id', verifyToken, JobPostController.deleteJobPost);
router.post('/', verifyToken, JobPostController.createJobPost);
router.get('/search/query', verifyToken, JobPostController.searchJobPosts);

module.exports = router;
