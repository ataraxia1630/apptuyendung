const { JobPostService } = require('../services/jobPost.service');
const { getPagination, buildMeta } = require('../utils/paginate');
const NotiEmitter = require('../emitters/notification.emitter');
const { JobAppliedService } = require('../services/jobApplied.service');
const { FollowerService } = require('../services/follower.service');


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

    recommendJobs: async (req, res) => {
        const userId = req.user.userId;

        const page = parseInt(req.query.page) || 1;
        const pageSize = parseInt(req.query.pageSize) || 10;
        const { skip, take } = getPagination(page, pageSize);

        try {
            const user = await prisma.user.findUnique({ where: { id: userId } });
            if (!user?.applicantId) {
                console.log('❌ Không phải applicant');
                return res.status(403).json({ message: 'Forbidden' });
            }

            const { jobPosts, total } = await JobPostService.recommendJobs(user.applicantId, skip, take);

            const meta = buildMeta(total, page, pageSize);
            return res.status(200).json({ jobPosts: jobPosts, meta });
        } catch (error) {
            return res.status(500).json({
                message: 'Error recommending jobs',
                error: error.message || error,
            });
        }
    },

    // Tạo một bài đăng công việc mới
    createJobPost: async (req, res) => {
        try {
            const jobPost = await JobPostService.createJobPost(req.body);

            // Lấy danh sách follower
            const companyUser = await prisma.user.findFirst({
                where: {
                    companyId: jobPost.companyId,
                },
                select: { id: true }
            });

            const companyUserId = companyUser?.id;

            const followers = await FollowerService.getFollowers(companyUserId);
            const followerIds = followers.map((f) => f.followId); // vì follower.followId là người theo dõi

            // Lấy tên công ty
            const company = await prisma.company.findUnique({
                where: { id: jobPost.companyId },
                select: { name: true }, // hoặc username / displayName
            });
            const companyName = company?.name || 'Một công ty';

            // Emit sự kiện
            NotiEmitter.emit('post.created', {
                followerIds,
                companyName,
            });

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

            const userIds = await JobAppliedService.getUserIdsAppliedToJobPost(id);

            userIds.forEach(userId => {
                NotiEmitter.emit('job.updated', {
                    userId,
                    jobTitle: jobPost.title,
                });
            });

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
            const jobPost = await JobPostService.getJobPostById(id);
            if (!jobPost) {
                return res.status(404).json({ message: 'Job post not found' });
            }
            const userIds = await JobAppliedService.getUserIdsAppliedToJobPost(id);

            userIds.forEach(userId => {
                NotiEmitter.emit('job.deleted', {
                    userId,
                    jobTitle: jobPost.title,
                });
            });
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


        try {
            const { jobPosts, total } = await JobPostService.getJobPostsByCompany(companyId, skip, take);
            const meta = buildMeta(total, page, pageSize);
            return res.status(200).json({ jobPosts: jobPosts, meta });
        } catch (error) {
            console.error(error);
            return res.status(500).json({ message: 'Error fetching job posts by company', error });
        }
    },
    getJobPostsByStatus: async (req, res) => {
        const page = parseInt(req.query.page) || 1;
        const pageSize = parseInt(req.query.pageSize) || 10;
        const { skip, take } = getPagination(page, pageSize);
        const { status } = req.query;

        const validStatuses = ['OPENING', 'TERMINATED', 'CANCELLED', 'NOT_EXIST'];
        if (!validStatuses.includes(status)) {
            return res.status(400).json({ message: 'Invalid post status' });
        }

        try {
            const { jobPosts, total } = await JobPostService.getJobPostsByStatus(status, skip, take);
            const meta = buildMeta(total, page, pageSize);
            return res.status(200).json({ jobPosts: jobPosts, meta });
        } catch (error) {
            console.error(error);
            return res.status(500).json({ message: 'Failed to fetch job posts by status', error: error.message });
        }
    },
    toggleJobPostStatus: async (req, res) => {
        const { id } = req.params;
        const { status } = req.body;

        const validStatuses = ['OPENING', 'TERMINATED', 'CANCELLED', 'NOT_EXIST'];
        if (!validStatuses.includes(status)) {
            return res.status(400).json({ message: 'Invalid post status' });
        }

        try {
            const currentPost = await prisma.jobPost.findUnique({
                where: { id },
                select: { status: true }
            });
            if (!currentPost) {
                return res.status(404).json({ message: 'Job post not found' });
            }
            if (currentPost.status === status) {
                return res.status(200).json({ message: 'Status is already up to date' });
            }
            const jobPost = await JobPostService.updateJobPostStatus(id, status);

            const user = await prisma.user.findFirst({
                where: { companyId: jobPost.companyId },
                select: { id: true }
            });
            NotiEmitter.emit('job.adminUpdatedStatusforCompany', {
                userId: user.id,
                jobTitle: jobPost.title,
                status: status,
            });

            const userIds = await JobAppliedService.getUserIdsAppliedToJobPost(id);
            userIds.forEach(userId => {
                NotiEmitter.emit('job.adminUpdatedStatusforUser', {
                    userId,
                    jobTitle: jobPost.title,
                    status: status,
                });
            });

            return res.status(200).json({ jobPost: jobPost });
        } catch (error) {
            return res.status(500).json({ message: 'Failed to update post status', error: error.message });
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

            return res.status(200).json({ jobPosts: jobPosts, meta });
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
