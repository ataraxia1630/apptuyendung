const prisma = require('../config/db/prismaClient');

const PostService = {
    getAllPosts: async (skip, take) => {
        const [posts, total] = await Promise.all([
            prisma.post.findMany({
                skip,
                take,
                orderBy: { created_at: 'desc' },
                include: {
                    Company: true,
                    contents: true,
                    Reaction: true,
                    Comment: true,
                },
            }),
            prisma.post.count(),
        ]);
        return { posts, total };
    },

    getPostById: async (id) => {
        return prisma.post.findUnique({
            where: { id },
            include: {
                Company: true,
                contents: true,
                Reaction: true,
                Comment: true,
            },
        });
    },

    createPost: async ({ companyId, title, contents }) => {
        return prisma.post.create({
            data: {
                companyId,
                title,
                contents: {
                    create: contents.map((c) => ({ content: c })),
                },
            },
            include: {
                Company: true,
                contents: true,
            },
        });
    },

    updatePost: async (id, data) => {
        return prisma.post.update({
            where: { id },
            data,
        });
    },

    deletePost: async (id) => {
        return prisma.post.delete({ where: { id } });
    },

    searchPosts: async (keyword, skip, take) => {
        const lowered = keyword.toLowerCase().trim();
        const [posts, total] = await Promise.all([
            prisma.post.findMany({
                where: {
                    OR: [
                        { title: { contains: lowered, mode: 'insensitive' } },
                        {
                            contents: {
                                some: {
                                    content: { contains: lowered, mode: 'insensitive' },
                                },
                            },
                        },
                        {
                            Company: {
                                name: { contains: lowered, mode: 'insensitive' },
                            },
                        },
                    ],
                },
                include: {
                    Company: true,
                    contents: true,
                },
                skip,
                take,
                orderBy: { created_at: 'desc' },
            }),
            prisma.post.count({
                where: {
                    OR: [
                        { title: { contains: lowered, mode: 'insensitive' } },
                        {
                            contents: {
                                some: {
                                    content: { contains: lowered, mode: 'insensitive' },
                                },
                            },
                        },
                        {
                            Company: {
                                name: { contains: lowered, mode: 'insensitive' },
                            },
                        },
                    ],
                },
            }),
        ]);

        return { posts, total };
    },
};

module.exports = { PostService };
