const prisma = require('../config/db/prismaClient');

const CommentService = {
    createComment: async ({ postId, CommentId = null, CommentDetail, userId }) => {
        if (!postId || !CommentDetail || !userId) {
            throw new Error('Missing required fields');
        }

        // 1. Tạo comment
        const created = await prisma.comment.create({
            data: { postId, CommentId, CommentDetail, userId },
            include: { User: true, childComment: true }
        });

        // 2. Lấy userId của người đăng post (theo companyId)
        const post = await prisma.post.findUnique({
            where: { id: postId },
            select: {
                companyId: true
            }
        });
        // tìm user đại diện công ty
        const owner = await prisma.user.findFirst({
            where: { companyId: post.companyId },
            select: { id: true }
        });
        const postOwnerId = owner?.id || null;

        // 3. Duyệt đệ quy lên comment cha để gom userId
        const parentChainUserIds = [];
        let currentId = CommentId;
        while (currentId) {
            const parent = await prisma.comment.findUnique({
                where: { id: currentId },
                select: { userId: true, CommentId: true }
            });
            if (!parent) break;
            parentChainUserIds.push(parent.userId);
            currentId = parent.CommentId;
        }

        // 4. Trả về đủ cho controller
        return { comment: created, postOwnerId, parentChainUserIds };
    },

    getCommentsByPost: async (postId) => {
        return prisma.comment.findMany({
            where: { postId, CommentId: null },
            include: {
                User: true,
                childComment: { include: { User: true } }
            },
            orderBy: { created_at: 'desc' }
        });
    },

    deleteComment: async (id, userId) => {
        const comment = await prisma.comment.findUnique({ where: { id } });
        if (!comment) throw new Error('Comment not found');
        if (comment.userId !== userId) throw new Error('Not authorized to delete');
        // xóa con trước
        await prisma.comment.deleteMany({ where: { CommentId: id } });
        await prisma.comment.delete({ where: { id } });
        return comment;
    }
};

module.exports = { CommentService };
