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

    searchJobPosts: async (keyword, skip = 0, take = 10) => {
        if (!keyword) throw new Error('Search keyword is required');

        const query = keyword.toLowerCase().trim();

        try {
            const [jobPosts, total] = await Promise.all([
                prisma.jobPost.findMany({
                    where: {
                        OR: [
                            { title: { contains: query, mode: 'insensitive' } },
                            { description: { contains: query, mode: 'insensitive' } },
                            { location: { contains: query, mode: 'insensitive' } },
                            {
                                Company: {
                                    name: { contains: query, mode: 'insensitive' }
                                }
                            }
                        ],
                    },
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
                    where: {
                        OR: [
                            { title: { contains: query, mode: 'insensitive' } },
                            { description: { contains: query, mode: 'insensitive' } },
                            { location: { contains: query, mode: 'insensitive' } },
                            {
                                Company: {
                                    name: { contains: query, mode: 'insensitive' }
                                }
                            }
                        ],
                    },
                }),
            ]);

            return { jobPosts, total };
        } catch (error) {
            throw new Error(`Error searching job posts: ${error.message}`);
        }
    },
};

module.exports = { JobPostService };
