const { sendToPostRoom } = require('../socket');
const { CommentService } = require('../services/comment.service');

const CommentController = {
    createComment: async (req, res) => {
        try {
            const userId = req.user.userId;
            const comment = await CommentService.createComment({ ...req.body, userId });
            sendToPostRoom(comment.postId, 'comment.new', { comment });

            res.status(201).json(comment);
        } catch (err) {
            res.status(500).json({ message: err.message });
        }
    },

    getCommentsByPost: async (req, res) => {
        try {
            const comments = await CommentService.getCommentsByPost(req.params.postId);
            res.json( {comments: comments} );
        } catch (err) {
            res.status(500).json({ message: err.message });
        }
    },

    deleteComment: async (req, res) => {
        try {
            const userId = req.user.userId;
            await CommentService.deleteComment(req.params.id, userId);
            sendToPostRoom(comment.postId, 'comment.deleted', { commentId: comment.id });
            res.json({ message: 'Comment deleted' });
        } catch (err) {
            res.status(500).json({ message: err.message });
        }
    }
};

module.exports = CommentController;
