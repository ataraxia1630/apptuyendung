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
                where: { id: Number(id) },
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
        try {
            const jobPost = await prisma.jobPost.create({
                data,
            });
            return jobPost;
        } catch (error) {
            throw new Error(`Error creating job post: ${error.message}`);
        }
    },

    updateJobPost: async (id, data) => {
        if (!id) throw new Error('JobPost ID is required');
        try {
            const jobPost = await prisma.jobPost.update({
                where: { id: Number(id) },
                data,
            });
            return jobPost;
        } catch (error) {
            throw new Error(`Error updating job post: ${error.message}`);
        }
    },

    deleteJobPost: async (id) => {
        if (!id) throw new Error('JobPost ID is required');
        try {
            await prisma.jobPost.delete({
                where: { id: Number(id) },
            });
            return true;
        } catch (error) {
            throw new Error(`Error deleting job post: ${error.message}`);
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

};

module.exports = { JobPostService };
