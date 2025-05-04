const ReactionService = {
    toggleReaction: async ({ postId, userId, reactionType }) => {
        const existing = await prisma.reaction.findUnique({ where: { postId_userId: { postId, userId } } });
        if (existing) {
            return await prisma.reaction.update({
                where: { postId_userId: { postId, userId } },
                data: { reactionType }
            });
        }
        return await prisma.reaction.create({ data: { postId, userId, reactionType } });
    },

    removeReaction: async ({ postId, userId }) => {
        return await prisma.reaction.delete({
            where: { postId_userId: { postId, userId } }
        });
    }
};

module.exports = { ReactionService };
