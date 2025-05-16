const prisma = require('../config/db/prismaClient');

const CommentService = {
    createComment: async ({ postId, CommentId = null, CommentDetail, userId }) => {
        if (!postId || !CommentDetail || !userId) {
            throw new Error('Missing required fields');
        }

        return await prisma.comment.create({
            data: {
                postId,
                CommentId,
                CommentDetail,
                userId
            },
            include: {
                User: true,
                childComment: true
            }
        });
    },

    getCommentsByPost: async (postId) => {
        return await prisma.comment.findMany({
            where: {
                postId,
                CommentId: null // chỉ lấy comment cha
            },
            include: {
                User: true,
                childComment: {
                    include: {
                        User: true
                    }
                }
            },
            orderBy: { created_at: 'desc' }
        });
    },

    deleteComment: async (id, userId) => {
        const comment = await prisma.comment.findUnique({ where: { id } });
        if (!comment) throw new Error('Comment not found');
        if (comment.userId !== userId) throw new Error('Not authorized to delete');

        // Nếu là comment cha thì xóa luôn comment con
        await prisma.comment.deleteMany({ where: { CommentId: id } });

        await prisma.comment.delete({ where: { id } });
    }
};

module.exports = { CommentService };
