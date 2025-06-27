const express = require('express');
const router = express.Router();
const { PostController } = require('../controllers/post.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { requireRole } = require('../middlewares/role.middleware');
const { checkOwnership } = require('../middlewares/checkOwnership.middleware');
const { cache } = require('../middlewares/cache.middleware');

router.get('/all', cache, PostController.getAllPosts);
router.get('/:id', cache, PostController.getPostById);
router.post('/', verifyToken, requireRole("COMPANY"), PostController.createPost);
router.put('/:id', verifyToken, checkOwnership('Post', 'companyId'), PostController.updatePost);
router.delete('/:id', verifyToken, checkOwnership('Post', 'companyId'), PostController.deletePost);
router.get('/search/query', cache, PostController.searchPosts);
router.get('/company/:companyId', verifyToken, PostController.getPostsByCompany);

router.get('/status/:status', verifyToken, requireRole('ADMIN'), PostController.getPostsByStatus);
router.put('/status/:id', verifyToken, requireRole('ADMIN'), PostController.updatePostStatus);


module.exports = router;
