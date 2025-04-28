const prisma = require('../config/db/prismaClient');

const JobPostService = {
    getAllJobPosts: async () => {
        console.log('Fetching all job posts...');
        try {
            const jobPosts = await prisma.jobPost.findMany({
                include: {
                    Company: true,
                    JobType: true,
                    JobCategory: true,
                    JobSaved: true,
                },
            });
            console.log('JobPosts fetched:', jobPosts.length);
            return jobPosts;
        } catch (error) {
            console.error('Error fetching job posts:', error.message);
            throw new Error(`Error fetching job posts: ${error.message}`);
        }
    },

    getJobPostById: async (id) => {
        if (!id) throw new Error('JobPost ID is required');
        try {
            const jobPost = await prisma.jobPost.findUnique({
                where: { id },
                include: { Company: true },
            });
            if (!jobPost) throw new Error('Job post not found');
            return jobPost;
        } catch (error) {
            console.error('Error fetching job post by ID:', error.message);
            throw new Error(`Error fetching job post by ID: ${error.message}`);
        }
    },

    createJobPost: async (data) => {
        if (!data || !data.title || !data.companyId) {
            throw new Error('Title and CompanyId are required');
        }
        console.log('Creating JobPost with data:', data);
        try {
            const jobPost = await prisma.jobPost.create({
                data,
            });
            console.log('JobPost created:', jobPost.id);
            return jobPost;
        } catch (error) {
            console.error('Error creating job post:', error.message);
            throw new Error(`Error creating job post: ${error.message}`);
        }
    },

    updateJobPost: async (id, data) => {
        if (!id) throw new Error('JobPost ID is required');
        if (!data) throw new Error('Update data is required');

        console.log('Updating JobPost with ID:', id, 'and data:', data);
        try {
            const jobPost = await prisma.jobPost.update({
                where: { id },
                data,
            });
            console.log('JobPost updated:', jobPost.id);
            return jobPost;
        } catch (error) {
            console.error('Error updating job post:', error.message);
            throw new Error(`Error updating job post: ${error.message}`);
        }
    },

    deleteJobPost: async (id) => {
        if (!id) throw new Error('JobPost ID is required');
        try {
            await prisma.jobPost.delete({
                where: { id },
            });
            console.log('JobPost deleted:', id);
            return { message: 'Job post deleted successfully' };
        } catch (error) {
            console.error('Error deleting job post:', error.message);
            throw new Error(`Error deleting job post: ${error.message}`);
        }
    },

    searchJobPosts: async (query) => {
        if (!query) throw new Error('Search query is required');
        const keyword = query.toLowerCase().trim();
        try {
            const jobPosts = await prisma.jobPost.findMany({
                where: {
                    OR: [
                        { title: { contains: keyword, mode: 'insensitive' } },
                        { description: { contains: keyword, mode: 'insensitive' } },
                    ],
                },
                include: { Company: true },
            });
            console.log('Search found:', jobPosts.length);
            return jobPosts;
        } catch (error) {
            console.error('Error searching job posts:', error.message);
            throw new Error(`Error searching job posts: ${error.message}`);
        }
    },
};

module.exports = { JobPostService };
