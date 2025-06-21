const prisma = require('../config/db/prismaClient');

const FollowerService = {
    toggleFollow: async ({ followId, followedId }) => {
        const existing = await prisma.follower.findUnique({
            where: {
                followId_followedId: {
                    followId,
                    followedId
                }
            }
        });

        if (existing) {
            await prisma.follower.delete({
                where: {
                    followId_followedId: {
                        followId,
                        followedId
                    }
                }
            });

            return { removed: true };
        }

        await prisma.follower.create({
            data: {
                followId,
                followedId
            }
        });

        return { removed: false };
    },

    getFollowers: async (userId) => {
        return await prisma.follower.findMany({
            where: { followedId: userId },
            include: {
                followUser: true
            }
        });
    },

    getFollowing: async (userId) => {
        return await prisma.follower.findMany({
            where: { followId: userId },
            include: {
                followedUser: true
            }
        });
    }
};

module.exports = { FollowerService };
