const { PostService } = require('../services/post.service');
const { getPagination, buildMeta } = require('../utils/paginate');
const { FollowerService } = require('../services/follower.service');

const PostController = {
    getAllPosts: async (req, res) => {
        const page = parseInt(req.query.page) || 1;
        const pageSize = parseInt(req.query.pageSize) || 10;
        const { skip, take } = getPagination(page, pageSize);
        try {
            const { posts, total } = await PostService.getAllPosts(skip, take);
            const meta = buildMeta(total, page, pageSize);
            return res.status(200).json({ posts: posts, meta });
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
            return res.status(200).json({ post: post });
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
            const post = await PostService.createPost({ companyId, title, contents });
            const companyUser = await prisma.user.findFirst({
                where: {
                    companyId: post.companyId,
                },
                select: { id: true }
            });

            const companyUserId = companyUser?.id;

            const followers = await FollowerService.getFollowers(companyUserId);
            const followerIds = followers.map((f) => f.followId); // vì follower.followId là người theo dõi

            // Lấy tên công ty
            const company = await prisma.company.findUnique({
                where: { id: post.companyId },
                select: { name: true }, // hoặc username / displayName
            });
            const companyName = company?.name || 'Một công ty';

            // Emit sự kiện
            NotiEmitter.emit('post.created', {
                followerIds,
                companyName,
            });
            return res.status(201).json({ post: post });
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
            console.log(error);
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

    // PostController.js
    searchPosts: async (req, res) => {
        const { page = 1, pageSize = 10, title, companyName, contents } = req.query;
        const { skip, take } = getPagination(parseInt(page), parseInt(pageSize));

        const filters = { title, companyName, contents };

        try {
            const { posts, total } = await PostService.searchPosts(filters, skip, take);
            const meta = buildMeta(total, parseInt(page), parseInt(pageSize));
            return res.status(200).json({ posts: posts, meta });
        } catch (error) {
            console.error(error);
            return res.status(500).json({ message: 'Error searching posts', error });
        }
    },


    getPostsByCompany: async (req, res) => {
        const { companyId } = req.params;
        const page = parseInt(req.query.page) || 1;
        const pageSize = parseInt(req.query.pageSize) || 10;
        const { skip, take } = getPagination(page, pageSize);

        try {
            const { posts, total } = await PostService.getPostsByCompany(companyId, skip, take);
            const meta = buildMeta(total, page, pageSize);
            return res.status(200).json({ posts: posts, meta });
        } catch (error) {
            console.error(error);
            return res.status(500).json({ message: 'Error fetching posts by company', error });
        }
    },
    getPostsByStatus: async (req, res) => {
        const { status } = req.params;
        const page = parseInt(req.query.page) || 1;
        const pageSize = parseInt(req.query.pageSize) || 10;
        const { skip, take } = getPagination(page, pageSize);

        const validStatuses = ['OPENING', 'TERMINATED', 'CANCELLED', 'NOT_EXIST'];

        if (!validStatuses.includes(status)) {
            return res.status(400).json({ message: 'Invalid status' });
        }

        try {
            const { posts, total } = await PostService.getPostsByStatus(status, skip, take);
            const meta = buildMeta(total, page, pageSize);
            return res.status(200).json({ posts: posts, meta });
        } catch (error) {
            return res.status(500).json({ message: 'Error fetching posts by status', error });
        }
    },

    updatePostStatus: async (req, res) => {
        const { id } = req.params;
        const { status } = req.body;

        const validStatuses = ['OPENING', 'TERMINATED', 'CANCELLED', 'NOT_EXIST'];

        if (!validStatuses.includes(status)) {
            return res.status(400).json({ message: 'Invalid status' });
        }

        try {
            const post = await PostService.updatePostStatus(id, status);
            NotiEmitter.emit('job.adminUpdatedStatus', {
                userId: post.companyId,
                jobTitle: post.title,
                status: status,
            });
            return res.status(200).json({ post: post });
        } catch (error) {
            return res.status(500).json({ message: 'Failed to update post status', error: error.message });
        }
    },
};

module.exports = { PostController };
