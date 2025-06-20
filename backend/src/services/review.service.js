const prisma = require('../config/db/prismaClient');

const ReviewService = {
    createOrUpdate: async ({ userId, reviewed_userId, detail, rating }) => {
        if (userId === reviewed_userId) {
            throw new Error('You cannot review yourself');
        }
        const existing = await prisma.review.findFirst({
            where: { userId, reviewed_userId },
        });

        if (existing) {
            return await prisma.review.update({
                where: { id: existing.id },
                data: { detail, rating },
            });
        }

        return await prisma.review.create({
            data: {
                userId,
                reviewed_userId,
                detail,
                rating,
            },
        });
    },

    getByUser: async (reviewed_userId, skip = 0, take = 10) => {
        const [total, items] = await Promise.all([
            prisma.review.count({ where: { reviewed_userId } }),
            prisma.review.findMany({
                where: { reviewed_userId },
                include: {
                    User: {
                        select: { id: true, username: true, avatar: true },
                    },
                },
                skip,
                take,
                orderBy: { created_at: 'desc' },
            }),
        ]);

        return { items, total };
    },

    getAverageRating: async (reviewed_userId) => {
        const reviews = await prisma.review.findMany({
            where: { reviewed_userId },
            select: { rating: true },
        });

        if (reviews.length === 0) return 0;

        const sum = reviews.reduce((acc, cur) => acc + cur.rating, 0);
        return parseFloat((sum / reviews.length).toFixed(2));
    },
};

module.exports = { ReviewService };
