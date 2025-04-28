const { JobPostService } = require('../services/jobPost.service');

const JobPostController = {
    // Lấy tất cả các bài đăng công việc
    getAllJobPosts: async (req, res) => {
        try {
            const jobPosts = await JobPostService.getAllJobPosts();
            return res.status(200).json(jobPosts);
        } catch (error) {
            return res
                .status(500)
                .json({ message: 'Error fetching job posts', error });
        }
    },

    // Lấy bài đăng công việc theo ID
    getJobPostById: async (req, res) => {
        const { id } = req.params;
        try {
            const jobPost = await JobPostService.getJobPostById(id);
            if (!jobPost) {
                return res.status(404).json({ message: 'Job post not found' });
            }
            return res.status(200).json(jobPost);
        } catch (error) {
            return res
                .status(500)
                .json({ message: 'Error fetching job post', error });
        }
    },

    // Tạo một bài đăng công việc mới
    createJobPost: async (req, res) => {
        try {
            const newJobPost = await JobPostService.createJobPost(req.body);
            return res.status(201).json(newJobPost);
        } catch (error) {
            return res
                .status(500)
                .json({ message: 'Error creating job post', error });
        }
    },

    // Cập nhật bài đăng công việc theo ID
    updateJobPost: async (req, res) => {
        const { id } = req.params;
        try {
            const jobPost = await JobPostService.updateJobPost(id, req.body);
            return res.status(200).json(jobPost);
        } catch (error) {
            return res
                .status(500)
                .json({ message: 'Error updating job post', error });
        }
    },

    // Xóa bài đăng công việc theo ID
    deleteJobPost: async (req, res) => {
        const { id } = req.params;
        try {
            await JobPostService.deleteJobPost(id);
            return res.status(200).json({ message: 'Delete successfully' });
        } catch (error) {
            return res
                .status(500)
                .json({ message: 'Error deleting job post', error });
        }
    },

    // Tìm kiếm bài đăng công việc
    searchJobPosts: async (req, res) => {
        const { keyword } = req.query;
        if (!keyword) {
            return res.status(400).json({ message: 'Keyword is required' });
        }

        try {
            const jobPosts = await JobPostService.searchJobPosts(keyword);
            return res.status(200).json(jobPosts);
        } catch (error) {
            return res
                .status(500)
                .json({ message: 'Error searching job posts', error });
        }
    },
};

module.exports = { JobPostController };
