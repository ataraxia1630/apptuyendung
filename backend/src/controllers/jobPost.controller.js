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
            return res.status(200).json({ data: jobPosts, meta });
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
            const newJobPost = await JobPostService.createJobPost(req.body);
            return res.status(201).json({ newJobPost: newJobPost });
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
        const { keyword } = req.query;
        if (!keyword) {
            return res.status(400).json({ message: 'Keyword is required' });
        }

        try {
            const { jobPosts, total } = await JobPostService.searchJobPosts(keyword, skip, take);
            const meta = buildMeta(total, page, pageSize);
            return res.status(200).json({ jobPosts, meta });
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
            return res.status(200).json({ jobPosts, meta });
        } catch (error) {
            console.error(error);
            return res.status(500).json({ message: 'Error fetching job posts by company', error });
        }
    },
    getPendingJobPosts: async (req, res) => {
        try {
            const pendingPosts = await JobPostService.getJobPostsByStatus('PENDING');
            res.status(200).json(pendingPosts);
        } catch (error) {
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
            const updated = await JobPostService.updateJobPostStatus(id, status);
            res.status(200).json({ message: 'Status updated', jobPost: updated });
        } catch (error) {
            res.status(500).json({ message: 'Failed to update status', error: error.message });
        }
    },
    getJobPostsByStatus: async (status) => {
        if (!status) throw new Error('Status is required');
        try {
            const jobPosts = await prisma.jobPost.findMany({
                where: { AdminApprovalStatus: status },
                orderBy: { created_at: 'desc' },
                include: {
                    Company: true,
                    JobType: true,
                    JobCategory: true,
                },
            });
            return jobPosts;
        } catch (error) {
            throw new Error(`Error fetching job posts by status: ${error.message}`);
        }
    },
    updateJobPostStatus: async (id, status) => {
        if (!id) throw new Error('JobPost ID is required');
        if (!status) throw new Error('Status is required');

        try {
            const updatedJobPost = await prisma.jobPost.update({
                where: { id },
                data: { AdminApprovalStatus: status },
                include: {
                    Company: true,
                    JobType: true,
                    JobCategory: true,
                },
            });
            return updatedJobPost;
        } catch (error) {
            throw new Error(`Error updating job post status: ${error.message}`);
        }
    },

};

module.exports = { JobPostController };
