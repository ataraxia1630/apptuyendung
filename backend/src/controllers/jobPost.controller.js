const { AdminApprovalStatus } = require('@prisma/client');
const { user } = require('../config/db/prismaClient');
const { JobPostService } = require('../services/jobPost.service');
const { getPagination, buildMeta } = require('../utils/paginate');

const JobPostController = {
    // Lấy tất cả các bài đăng công việc
    getAllJobPosts: async (req, res) => {
        const page = parseInt(req.query.page) || 1;
        const pageSize = parseInt(req.query.pageSize) || 10;
        const { skip, take } = getPagination(page, pageSize);
        try {
            const { jobPosts, total } = await JobPostService.getAllJobPosts(skip, take);
            const meta = buildMeta(total, page, pageSize);
            return res.status(200).json({ jobPosts: jobPosts, meta });
        } catch (error) {
            console.error('Error fetching job posts:', error);
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
            return res.status(200).json({ jobPost: jobPost });
        } catch (error) {
            return res
                .status(500)
                .json({ message: 'Error fetching job post', error });
        }
    },

    // Tạo một bài đăng công việc mới
    createJobPost: async (req, res) => {
        try {
            const jobPost = await JobPostService.createJobPost(req.body);
            return res.status(201).json({ jobPost: jobPost });
        } catch (error) {
            console.log(error);
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
            return res.status(200).json({ jobPost: jobPost });
        } catch (error) {
            console.log(error);
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
            console.error('Error deleting job post:', error);
            return res
                .status(500)
                .json({ message: 'Error deleting job post', error });
        }
    },

    // Tìm kiếm bài đăng công việc
    searchJobPosts: async (req, res) => {
        const page = parseInt(req.query.page) || 1;
        const pageSize = parseInt(req.query.pageSize) || 10;
        const { skip, take } = getPagination(page, pageSize);
        const filters = req.query;

        try {
            const { jobPosts, total } = await JobPostService.searchJobPosts(filters, skip, take);
            const meta = buildMeta(total, page, pageSize);
            return res.status(200).json({ jobPosts: jobPosts, meta });
        } catch (error) {
            return res
                .status(500)
                .json({ message: 'Error searching job posts', error });
        }
    },
    getJobPostsByCompany: async (req, res) => {
        const { id: companyId } = req.params;
        const page = parseInt(req.query.page) || 1;
        const pageSize = parseInt(req.query.pageSize) || 10;
        const { skip, take } = getPagination(page, pageSize);

        const role = req.user?.role;
        const userCompanyId = req.user?.companyId;
        console.log(userCompanyId, companyId, role)

        if (role !== 'ADMIN' && userCompanyId !== companyId) {
            return res.status(403).json({ message: 'Forbidden: You do not own this company' });
        }

        try {
            const { jobPosts, total } = await JobPostService.getJobPostsByCompany(companyId, skip, take);
            const meta = buildMeta(total, page, pageSize);
            return res.status(200).json({ jobPosts: jobPosts, meta });
        } catch (error) {
            console.error(error);
            return res.status(500).json({ message: 'Error fetching job posts by company', error });
        }
    },
    getPendingJobPosts: async (req, res) => {
        const page = parseInt(req.query.page) || 1;
        const pageSize = parseInt(req.query.pageSize) || 10;
        const { skip, take } = getPagination(page, pageSize);

        try {
            const { jobPosts, total } = await JobPostService.getJobPostsByStatus('PENDING', skip, take);
            const meta = buildMeta(total, page, pageSize);
            res.status(200).json({ jobPosts: jobPosts, meta });
        } catch (error) {
            console.error(error);
            res.status(500).json({ message: 'Failed to fetch pending posts', error: error.message });
        }
    },
    toggleJobPostStatus: async (req, res) => {
        const { id } = req.params;
        const { status } = req.body;
        const validStatuses = ['PENDING', 'APPROVED', 'REJECTED'];

        if (!validStatuses.includes(status)) {
            return res.status(400).json({ message: 'Invalid status' });
        }

        try {
            const jobPost = await JobPostService.updateJobPostStatus(id, status);
            res.status(200).json({ jobPost: jobPost });
        } catch (error) {
            res.status(500).json({ message: 'Failed to update status', error: error.message });
        }
    },
    getMyJobsWithApplications: async (req, res) => {
        const companyId = req.user.id;
        const page = parseInt(req.query.page) || 1;
        const pageSize = parseInt(req.query.pageSize) || 10;
        const { skip, take } = getPagination(page, pageSize);

        try {
            const { jobPosts, total } = await JobPostService.getMyJobsWithApplications(companyId, skip, take);
            const meta = buildMeta(total, page, pageSize);

            return res.status(200).json({ data: jobPosts, meta });
        } catch (error) {
            console.error('Error fetching company job posts with applications:', error);
            return res.status(500).json({ message: 'Lỗi khi lấy danh sách công việc và ứng tuyển', error });
        }
    },
    getMyJobPostById: async (req, res) => {
        const jobPostId = req.params.id;

        try {
            const companyId = req.user?.companyId;
            const jobPost = await JobPostService.getJobPostByIdForCompany(jobPostId, companyId);
            if (!jobPost) {
                return res.status(404).json({ message: 'Job post not found' });
            }
            return res.status(200).json({ jobPost: jobPost });
        } catch (error) {
            return res.status(500).json({ message: 'Error fetching job post', error: error.message });
        }
    },
};

module.exports = { JobPostController };
