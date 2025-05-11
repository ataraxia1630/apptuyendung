const express = require('express');
const router = express.Router();
const { PostController } = require('../controllers/post.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { requireRole } = require('../middlewares/role.middleware');
const { checkOwnership } = require('../middlewares/checkOwnership.middleware');

router.get('/all', PostController.getAllPosts);
router.get('/:id', PostController.getPostById);
router.post('/', verifyToken, requireRole("COMPANY"), PostController.createPost);
router.put('/:id', verifyToken, checkOwnership('Post', 'companyId'), PostController.updatePost);
router.delete('/:id', verifyToken, checkOwnership('Post', 'companyId'), PostController.deletePost);
router.get('/search/query', PostController.searchPosts);

module.exports = router;
