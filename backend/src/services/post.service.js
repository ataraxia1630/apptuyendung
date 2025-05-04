const prisma = require('../config/db/prismaClient');

const PostService = {
    getAllPosts: async () => {
        try {
            const posts = await prisma.post.findMany({
                include: {
                    Company: true,
                    Image: true,
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
            return posts;
        } catch (error) {
            console.error('Error fetching posts:', error.message);
            throw new Error('Error fetching posts: ' + error.message);
        }
    },

    getPostById: async (id) => {
        if (!id) throw new Error('Post ID is required');
        try {
            const post = await prisma.post.findUnique({
                where: { id },
                include: {
                    Company: true,
                    Image: true,
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
        } catch (error) {
            console.error('Error fetching post:', error.message);
            throw new Error('Error fetching post: ' + error.message);
        }
    },

    createPost: async (data) => {
        if (!data.title || !data.companyId || !data.detail) {
            throw new Error('Missing required fields');
        }
        try {
            const post = await prisma.post.create({ data });
            return post;
        } catch (error) {
            console.error('Error creating post:', error.message);
            throw new Error('Error creating post: ' + error.message);
        }
    },

    updatePost: async (id, data) => {
        if (!id) throw new Error('Post ID is required');
        try {
            const post = await prisma.post.update({
                where: { id },
                data,
            });
            return post;
        } catch (error) {
            console.error('Error updating post:', error.message);
            throw new Error('Error updating post: ' + error.message);
        }
    },

    deletePost: async (id) => {
        if (!id) throw new Error('Post ID is required');
        try {
            await prisma.post.delete({ where: { id } });
            return { message: 'Post deleted successfully' };
        } catch (error) {
            console.error('Error deleting post:', error.message);
            throw new Error('Error deleting post: ' + error.message);
        }
    },

    searchPosts: async (query) => {
        if (!query) throw new Error('Search query is required');
        const keyword = query.trim().toLowerCase();
        try {
            const posts = await prisma.post.findMany({
                where: {
                    OR: [
                        { title: { contains: keyword, mode: 'insensitive' } },
                        { detail: { contains: keyword, mode: 'insensitive' } },
                    ],
                },
                include: { Company: true },
            });
            return posts;
        } catch (error) {
            console.error('Error searching posts:', error.message);
            throw new Error('Error searching posts: ' + error.message);
        }
    },
};

module.exports = { PostService };
