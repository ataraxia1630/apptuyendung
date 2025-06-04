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
router.get('/search/query', PostController.searchPosts);
router.get('/company/:companyId', verifyToken, PostController.getPostsByCompany);


module.exports = router;
