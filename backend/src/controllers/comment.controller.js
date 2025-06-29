const { sendToPostRoom } = require('../socket');
const { CommentService } = require('../services/comment.service');
const NotiEmitter = require('../emitters/notification.emitter');

const CommentController = {
    createComment: async (req, res) => {
        try {
            const userId = req.user.userId;
            const { comment, postOwnerId, parentChainUserIds } =
                await CommentService.createComment({ ...req.body, userId });

            // 1) Socket: phát lên room post
            sendToPostRoom(comment.postId, 'comment.new', { comment });

            // 2) Noti cho chủ post
            if (postOwnerId && postOwnerId !== userId) {
                NotiEmitter.emit('comment.onPost', {
                    userId: postOwnerId,
                    fromUserId: userId,
                    postId: comment.postId,
                    commentId: comment.id
                });
            }

            // 3) Noti cho từng chủ comment cha
            const notified = new Set([userId, postOwnerId]);
            for (const uid of parentChainUserIds) {
                if (!notified.has(uid)) {
                    NotiEmitter.emit('comment.onReply', {
                        userId: uid,
                        fromUserId: userId,
                        postId: comment.postId,
                        commentId: comment.id
                    });
                    notified.add(uid);
                }
            }

            return res.status(201).json({ comment: comment });
        } catch (err) {
            return res.status(500).json({ message: err.message });
        }
    },

    getCommentsByPost: async (req, res) => {
        try {
            const comments = await CommentService.getCommentsByPost(req.params.postId);
            return res.json({ comments: comments });
        } catch (err) {
            return res.status(500).json({ message: err.message });
        }
    },

    deleteComment: async (req, res) => {
        try {
            const userId = req.user.userId;
            const comment = await CommentService.deleteComment(req.params.id, userId);
            sendToPostRoom(comment.postId, 'comment.deleted', {
                commentId: comment.id
            });
            return res.json({ message: 'Comment deleted' });
        } catch (err) {
            return res.status(500).json({ message: err.message });
        }
    }
};

module.exports = { CommentController };
