const { PostService } = require('../services/post.service');

const PostController = {
    getAllPosts: async (req, res) => {
        try {
            const posts = await PostService.getAllPosts();
            return res.status(200).json(posts);
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
            return res.status(200).json(post);
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
            return res.status(201).json(newPost);
        } catch (error) {
            return res.status(500).json({ message: 'Error creating post', error });
        }
    },

    updatePost: async (req, res) => {
        const { id } = req.params;
        try {
            const post = await PostService.updatePost(id, req.body);
            return res.status(200).json(post);
        } catch (error) {
            return res.status(500).json({ message: 'Error updating post', error });
        }
    },

    deletePost: async (req, res) => {
        const { id } = req.params;
        try {
            await PostService.deletePost(id);
            return res.status(200).json({ message: 'Delete successfully' });
        } catch (error) {
            return res.status(500).json({ message: 'Error deleting post', error });
        }
    },

    searchPosts: async (req, res) => {
        const { keyword } = req.query;
        if (!keyword) {
            return res.status(400).json({ message: 'Keyword is required' });
        }

        try {
            const posts = await PostService.searchPosts(keyword);
            return res.status(200).json(posts);
        } catch (error) {
            return res.status(500).json({ message: 'Error searching posts', error });
        }
    }
};

module.exports = { PostController };
