const prisma = require('../config/db/prismaClient');

const ReactionService = {
    toggleReaction: async ({ postId, userId, reactionType }) => {
        const post = await prisma.post.findUnique({
            where: { id: postId },
            select: { companyId: true }
        });

        if (!post) throw new Error('Post not found');

        const postOwner = await prisma.user.findFirst({
            where: { companyId: post.companyId },
            select: { id: true }
        });

        if (!postOwner) throw new Error('Post owner not found');

        const existing = await prisma.reaction.findUnique({
            where: { postId_userId: { postId, userId } }
        });

        if (existing) {
            if (existing.reactionType === reactionType) {
                await prisma.reaction.delete({
                    where: { postId_userId: { postId, userId } }
                });

                return {
                    removed: true,
                    postOwnerId: postOwner.id
                };
            } else {
                const updated = await prisma.reaction.update({
                    where: { postId_userId: { postId, userId } },
                    data: { reactionType }
                });

                return {
                    ...updated,
                    postOwnerId: postOwner.id
                };
            }
        }

        const created = await prisma.reaction.create({
            data: { postId, userId, reactionType }
        });

        return {
            ...created,
            postOwnerId: postOwner.id
        };
    },
};

module.exports = { ReactionService };
