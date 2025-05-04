const prisma = require('../config/db/prismaClient');

const CommentService = {
    createComment: async ({ userId, postId, CommentDetail, CommentId = null }) => {
        if (!userId || !postId || !CommentDetail) {
            throw new Error('Missing required fields');
        }
        return await prisma.comment.create({
            data: { userId, postId, CommentDetail, CommentId }
        });
    },

    getCommentsByPost: async (postId) => {
        if (!postId) throw new Error('Post ID required');
        return await prisma.comment.findMany({
            where: { postId, CommentId: null },
            include: {
                User: true,
                childComment: {
                    include: { User: true }
                }
            },
            orderBy: { created_at: 'desc' }
        });
    },

    deleteComment: async (id) => {
        if (!id) throw new Error('Comment ID required');
        return await prisma.comment.delete({ where: { id } });
    }
};

module.exports = { CommentService };
