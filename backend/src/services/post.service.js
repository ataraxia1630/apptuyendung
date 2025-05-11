const prisma = require('../config/db/prismaClient');

const PostService = {
    getAllPosts: async () => {
        return await prisma.post.findMany({
            include: {
                Company: true,
                contents: true,
                Reaction: true,
                Comment: {
                    include: {
                        User: true,
                        childComment: {
                            include: { User: true }
                        }
                    }
                }
            }
        });
    },

    getPostById: async (id) => {
        if (!id) throw new Error('Post ID is required');
        const post = await prisma.post.findUnique({
            where: { id },
            include: {
                Company: true,
                contents: true,
                Reaction: true,
                Comment: {
                    include: {
                        User: true,
                        childComment: {
                            include: { User: true }
                        }
                    }
                }
            }
        });
        if (!post) throw new Error('Post not found');
        return post;
    },

    createPost: async ({ companyId, title, contents }) => {
        if (!companyId || !title || !Array.isArray(contents) || contents.length === 0) {
            throw new Error('Missing required fields or empty contents');
        }

        return await prisma.post.create({
            data: {
                companyId,
                title,
                contents: {
                    createMany: {
                        data: contents.map((block, index) => ({
                            type: block.type,
                            value: block.value,
                            order: index + 1
                        }))
                    }
                }
            },
            include: {
                contents: true
            }
        });
    },

    updatePost: async (id, { title, contents }) => {
        if (!id) throw new Error('Post ID is required');

        const updatedPost = await prisma.post.update({
            where: { id },
            data: { title },
        });

        if (contents && Array.isArray(contents)) {
            // Xóa content cũ trước
            await prisma.postContent.deleteMany({ where: { postId: id } });

            // Tạo lại content mới
            await prisma.postContent.createMany({
                data: contents.map((block, index) => ({
                    postId: id,
                    type: block.type,
                    value: block.value,
                    order: index + 1
                }))
            });
        }

        return await prisma.post.findUnique({
            where: { id },
            include: { contents: true }
        });
    },

    deletePost: async (id) => {
        if (!id) throw new Error('Post ID is required');
        await prisma.postContent.deleteMany({ where: { postId: id } }); // xóa nội dung
        await prisma.post.delete({ where: { id } });
        return { message: 'Post deleted successfully' };
    },

    searchPosts: async (keyword) => {
        if (!keyword) throw new Error('Search keyword is required');
        const lowerKeyword = keyword.trim().toLowerCase();

        const posts = await prisma.post.findMany({
            where: {
                OR: [
                    { title: { contains: lowerKeyword, mode: 'insensitive' } },
                    {
                        contents: {
                            some: {
                                value: {
                                    contains: lowerKeyword,
                                    mode: 'insensitive'
                                }
                            }
                        }
                    }
                ]
            },
            include: {
                contents: true,
                Company: true
            }
        });

        return posts;
    }
};

module.exports = { PostService };
