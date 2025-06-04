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
                    create: contents.map(c => ({
                        type: c.type,
                        value: c.value,
                        order: c.order,
                    })),
                },
            },
            include: {
                Company: true,
                contents: true,
            },
        });
    },


    updatePost: async (id, data) => {
        if (!id) throw new Error('Post ID is required');

        const { title, contents } = data;

        try {
            const result = await prisma.$transaction([
                prisma.post.update({
                    where: { id },
                    data: { title },
                }),

                prisma.postContent.deleteMany({
                    where: { postId: id },
                }),

                ...(Array.isArray(contents) && contents.length > 0
                    ? [prisma.postContent.createMany({
                        data: contents.map(c => ({
                            postId: id,
                            type: c.type,
                            value: c.value,
                            order: c.order,
                        })),
                    })]
                    : [])
            ]);

            return result[0]; // post đã update
        } catch (error) {
            throw new Error(`Error updating post: ${error.message}`);
        }
    },


    deletePost: async (id) => {
        return prisma.post.delete({ where: { id } });
    },

    searchPosts: async (filters, skip, take) => {
        const orConditions = [];

        if (filters.title) {
            orConditions.push({
                title: {
                    contains: filters.title.trim(),
                    mode: 'insensitive',
                },
            });
        }

        if (filters.contents) {
            orConditions.push({
                contents: {
                    some: {
                        content: {
                            contains: filters.contents.trim(),
                            mode: 'insensitive',
                        },
                    },
                },
            });
        }

        if (filters.companyName) {
            const matchedCompanies = await prisma.company.findMany({
                where: {
                    name: {
                        contains: filters.companyName.trim(),
                        mode: 'insensitive',
                    },
                },
                select: { id: true },
            });

            const companyIds = matchedCompanies.map((c) => c.id);
            if (companyIds.length > 0) {
                orConditions.push({
                    companyId: {
                        in: companyIds,
                    },
                });
            }
        }

        const where = orConditions.length > 0 ? { OR: orConditions } : {};

        const [posts, total] = await Promise.all([
            prisma.post.findMany({
                where,
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
            prisma.post.count({ where }),
        ]);

        return { posts, total };
    },


    getPostsByCompany: async (companyId, skip = 0, take = 10) => {
        if (!companyId) throw new Error('Company ID is required');
        try {
            const [posts, total] = await Promise.all([
                prisma.post.findMany({
                    where: { companyId },
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
                prisma.post.count({
                    where: { companyId },
                }),
            ]);
            return { posts, total };
        } catch (error) {
            throw new Error(`Error fetching posts by company: ${error.message}`);
        }
    },
};

module.exports = { PostService };
