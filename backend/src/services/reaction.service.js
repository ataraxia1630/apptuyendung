const prisma = require('../config/db/prismaClient');

const ReactionService = {
    // Thêm hoặc cập nhật reaction
    toggleReaction: async ({ postId, userId, reactionType }) => {
        const existing = await prisma.reaction.findUnique({
            where: { postId_userId: { postId, userId } }
        });

        if (existing) {
            if (existing.reactionType === reactionType) {
                await prisma.reaction.delete({
                    where: { postId_userId: { postId, userId } }
                });
                return { removed: true };
            } else {
                return await prisma.reaction.update({
                    where: { postId_userId: { postId, userId } },
                    data: { reactionType }
                });
            }
        }

        return await prisma.reaction.create({
            data: { postId, userId, reactionType }
        });
    },

};

module.exports = { ReactionService };
