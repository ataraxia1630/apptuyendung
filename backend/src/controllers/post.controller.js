const { PostService } = require('../services/post.service');
const { getPagination, buildMeta } = require('../utils/paginate');

const PostController = {
    getAllPosts: async (req, res) => {
        const page = parseInt(req.query.page) || 1;
        const pageSize = parseInt(req.query.pageSize) || 10;
        const { skip, take } = getPagination(page, pageSize);
        try {
            const { posts, total } = await PostService.getAllPosts(skip, take);
            const meta = buildMeta(total, page, pageSize);
            return res.status(200).json({ data: posts, meta });
        } catch (error) {
            return res.status(500).json({ message: 'Error fetching posts', error });
        }
    },

    getPostById: async (req, res) => {
        const { id } = req.params;
        try {
            const post = await PostService.getPostById(id);
            if (!post) {
                return res.status(404).json({ message: 'Post not found' });
            }
            return res.status(200).json({ posts: post });
        } catch (error) {
            return res.status(500).json({ message: 'Error fetching post', error });
        }
    },

    createPost: async (req, res) => {
        try {
            const { companyId, title, contents } = req.body;
            if (!contents || !Array.isArray(contents) || contents.length === 0) {
                return res.status(400).json({ message: 'Post contents are required' });
            }
            const newPost = await PostService.createPost({ companyId, title, contents });
            return res.status(201).json({ newPost: newPost });
        } catch (error) {
            return res.status(500).json({ message: 'Error creating post', error });
        }
    },

    updatePost: async (req, res) => {
        const { id } = req.params;
        try {
            const post = await PostService.updatePost(id, req.body);
            return res.status(200).json({ post: post });
        } catch (error) {
            return res.status(500).json({ message: 'Error updating post', error });
        }
    },

    deletePost: async (req, res) => {
        const { id } = req.params;
        try {
            await PostService.deletePost(id);
            return res.status(200).json({ message: 'Deleted successfully' });
        } catch (error) {
            return res.status(500).json({ message: 'Error deleting post', error });
        }
    },

    searchPosts: async (req, res) => {
        const { keyword } = req.query;
        const page = parseInt(req.query.page) || 1;
        const pageSize = parseInt(req.query.pageSize) || 10;
        const { skip, take } = getPagination(page, pageSize);

        if (!keyword) {
            return res.status(400).json({ message: 'Keyword is required' });
        }

        try {
            const { posts, total } = await PostService.searchPosts(keyword, skip, take);
            const meta = buildMeta(total, page, pageSize);
            return res.status(200).json({ data: posts, meta });
        } catch (error) {
            return res.status(500).json({ message: 'Error searching posts', error });
        }
    },
};

module.exports = { PostController };
