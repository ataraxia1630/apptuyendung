const express = require('express');
const router = express.Router();
const { CommentController } = require('../controllers/comment.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { checkOwnership } = require('../middlewares/checkOwnership.middleware');

router.post('/', verifyToken, CommentController.createComment);
router.get('/post/:postId', CommentController.getCommentsByPost);
router.delete('/:id', verifyToken, checkOwnership('Comment', 'userId'), CommentController.deleteComment);

module.exports = router;
