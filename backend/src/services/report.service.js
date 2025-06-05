const prisma = require('../config/db/prismaClient');

const ReportService = {
    createReport: async ({ reporterId, reason, jobPostId, postId, reportedUserId }) => {
        return await prisma.report.create({
            data: {
                userId: reporterId,
                reason,
                jobPostId,
                postId,
                reportedUserId,
            },
        });
    },

    getAllReports: async () => {
        return await prisma.report.findMany({
            orderBy: { created_at: 'desc' },
            include: {
                jobPost: true,
                post: true,
                reportedUser: true,
                user: true,
            },
        });
    },

    getReportById: async (id) => {
        return await prisma.report.findUnique({
            where: { id },
        });
    },

    updateReportStatus: async (id, status) => {
        return await prisma.report.update({
            where: { id },
            data: {
                status,
            },
        });
    },
};

module.exports = { ReportService };