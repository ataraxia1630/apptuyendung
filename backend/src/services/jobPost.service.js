const prisma = require('../config/db/prismaClient');

const JobPostService = {
    getAllJobPosts: async (skip = 0, take = 10) => {
        try {
            const [jobPosts, total] = await Promise.all([
                prisma.jobPost.findMany({
                    skip,
                    take,
                    orderBy: { created_at: 'desc' },
                    include: {
                        Company: true,
                        JobType: true,
                        JobCategory: true,

                    },
                }),
                prisma.jobPost.count(),
            ]);
            return { jobPosts, total };
        } catch (error) {
            throw new Error(`Error fetching job posts: ${error.message}`);
        }
    },

    getJobPostById: async (id) => {
        if (!id) throw new Error('JobPost ID is required');
        try {
            const jobPost = await prisma.jobPost.findUnique({
                where: { id },
                include: {
                    Company: true,
                    JobType: true,
                    JobCategory: true,
                },
            });
            if (!jobPost) throw new Error('Job post not found');
            return jobPost;
        } catch (error) {
            throw new Error(`Error fetching job post by ID: ${error.message}`);
        }
    },

    createJobPost: async (data) => {
        if (!data?.title || !data?.companyId) {
            throw new Error('Title and CompanyId are required');
        }

        const {
            companyId,
            jobCategoryId,
            jobTypeId,
            title,
            description,
            position,
            workingAddress,
            skillRequirement,
            responsibility,
            salary_start,
            salary_end,
            currency,
            status,
            apply_until,
            educationRequirement,
        } = data;

        function convertDDMMYYYYtoISO(str) {
            const [day, month, year] = str.split("-");
            return `${year}-${month}-${day}T00:00:00.000Z`;
        }

        try {
            const jobPost = await prisma.jobPost.create({
                data: {
                    title,
                    description,
                    position,
                    workingAddress,
                    skillRequirement,
                    responsibility,
                    salary_start,
                    salary_end,
                    currency,
                    status,
                    educationRequirement,
                    apply_until: new Date(convertDDMMYYYYtoISO(apply_until)),

                    companyId,
                    jobCategoryId: jobCategoryId,
                    jobTypeId: jobTypeId,
                },
            });
            return jobPost;
        } catch (error) {
            throw new Error(`Error creating job post: ${error.message}`);
        }
    },




    updateJobPost: async (id, data) => {
        if (!id) throw new Error('JobPost ID is required');

        // Hàm chuyển ngày định dạng "dd-mm-yyyy" sang Date ISO
        function convertDDMMYYYYtoISO(str) {
            if (!str) return null;
            const [day, month, year] = str.split("-");
            return new Date(`${year}-${month}-${day}T00:00:00.000Z`);
        }

        // Tạo object mới để update, tránh thay đổi trực tiếp data đầu vào
        const updateData = {};

        if (data.title !== undefined) updateData.title = data.title;
        if (data.description !== undefined) updateData.description = data.description;
        if (data.position !== undefined) updateData.position = data.position;
        if (data.workingAddress !== undefined) updateData.workingAddress = data.workingAddress;
        if (data.skillRequirement !== undefined) updateData.skillRequirement = data.skillRequirement;
        if (data.responsibility !== undefined) updateData.responsibility = data.responsibility;
        if (data.salary_start !== undefined) updateData.salary_start = data.salary_start;
        if (data.salary_end !== undefined) updateData.salary_end = data.salary_end;
        if (data.currency !== undefined) updateData.currency = data.currency;
        if (data.status !== undefined) updateData.status = data.status;
        if (data.educationRequirement !== undefined) updateData.educationRequirement = data.educationRequirement;

        if (data.apply_until) {
            updateData.apply_until = convertDDMMYYYYtoISO(data.apply_until);
        }

        // Chuyển đổi các FK nếu có
        if (data.companyId !== undefined) updateData.companyId = data.companyId;
        if (data.jobCategory !== undefined) updateData.jobCategoryId = data.jobCategory;
        if (data.jobType !== undefined) updateData.jobTypeId = data.jobType;

        try {
            const jobPost = await prisma.jobPost.update({
                where: { id: id },  // giữ nguyên string id
                data: updateData,
            });
            return jobPost;
        } catch (error) {
            throw new Error(`Error updating job post: ${error.message}`);
        }
    },

    searchJobPosts: async (filters, skip, take) => {
        // filters là object chứa các trường có thể search, ví dụ:
        // { title, location, position, companyName, educationRequirement }

        const where = {};

        if (filters.title) {
            where.title = { contains: filters.title.trim(), mode: 'insensitive' };
        }
        if (filters.location) {
            where.location = { contains: filters.location.trim(), mode: 'insensitive' };
        }
        if (filters.position) {
            where.position = { contains: filters.position.trim(), mode: 'insensitive' };
        }
        if (filters.educationRequirement) {
            where.educationRequirement = { contains: filters.educationRequirement.trim(), mode: 'insensitive' };
        }
        if (filters.companyName) {
            where.Company = {
                name: { contains: filters.companyName.trim(), mode: 'insensitive' }
            };
        }
        // Bạn có thể thêm các trường khác tương tự...

        const [jobPosts, total] = await Promise.all([
            prisma.jobPost.findMany({
                where,
                skip,
                take,
                orderBy: { created_at: 'desc' },
                include: {
                    Company: true,
                    JobType: true,
                    JobCategory: true,
                }
            }),
            prisma.jobPost.count({ where }),
        ]);

        return { jobPosts, total };
    },
    deleteJobPost: async (id) => {
        const jobPost = await prisma.jobPost.findUnique({ where: { id: id } });
        if (!jobPost) {
            throw new Error('Job post not found');
        }

        await prisma.jobPost.delete({ where: { id: id } });
    },

    getJobPostsByCompany: async (companyId, skip = 0, take = 10) => {
        if (!companyId) throw new Error('Company ID is required');
        try {
            const [jobPosts, total] = await Promise.all([
                prisma.jobPost.findMany({
                    where: { companyId },
                    skip,
                    take,
                    orderBy: { created_at: 'desc' },
                    include: {
                        Company: true,
                        JobType: true,
                        JobCategory: true,
                    },
                }),
                prisma.jobPost.count({
                    where: { companyId },
                }),
            ]);

            return { jobPosts, total };
        } catch (error) {
            throw new Error(`Error fetching job posts by company: ${error.message}`);
        }
    },
    getJobPostsByStatus: async (status, skip = 0, take = 10) => {
        if (!status) throw new Error('Status is required');

        const validStatuses = ['PENDING', 'APPROVED', 'REJECTED'];
        if (!validStatuses.includes(status)) {
            throw new Error('Invalid status');
        }

        try {
            const where = { approvalStatus: status };

            const [jobPosts, total] = await Promise.all([
                prisma.jobPost.findMany({
                    where,
                    skip,
                    take,
                    orderBy: { created_at: 'desc' },
                    include: {
                        Company: true,
                        JobType: true,
                        JobCategory: true,
                    },
                }),
                prisma.jobPost.count({ where }),
            ]);

            return { jobPosts, total };
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
                data: { approvalStatus: status },
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
    // services/jobPost.service.js hoặc .ts
    getMyJobsWithApplications: async (companyId, skip = 0, take = 10) => {
        try {
            const [jobPosts, total] = await Promise.all([
                prisma.jobPost.findMany({
                    where: { companyId },
                    orderBy: { created_at: 'desc' },
                    skip,
                    take,
                    include: {
                        JobApplied: {
                            include: {
                                CV: true,
                                applicant: true
                            }
                        },
                        JobType: true,
                        JobCategory: true,
                    }
                }),
                prisma.jobPost.count({
                    where: { companyId }
                })
            ]);

            return { jobPosts, total };
        } catch (error) {
            throw new Error(`Error fetching company job posts with applications: ${error.message}`);
        }
    },
    getJobPostByIdForCompany: async (jobPostId, companyId) => {
        if (!jobPostId || !companyId) throw new Error('JobPost ID and Company ID are required');

        try {
            const jobPost = await prisma.jobPost.findFirst({
                where: {
                    id: jobPostId,
                    companyId,
                },
                include: {
                    JobApplied: {
                        include: {
                            applicant: true,
                            CV: true
                        }
                    },
                    JobType: true,
                    JobCategory: true,
                }
            });

            if (!jobPost) throw new Error('Job post not found or not owned by your company');

            return jobPost;
        } catch (error) {
            throw new Error(`Error fetching job post for company: ${error.message}`);
        }
    }


};

module.exports = { JobPostService };
