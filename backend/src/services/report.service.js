const prisma = require('../config/db/prismaClient');

const ReportService = {
    createReport: async ({ userId, reason, jobPostId, postId, reportedUserId }) => {
        return await prisma.report.create({
            data: {
                userId,
                reason,
                jobPostId,
                postId,
                reportedUserId,
            },
        });
    },

    getAllReports: async (skip, take) => {
        const [reports, total] = await Promise.all([
            prisma.report.findMany({
                skip,
                take,
                orderBy: { created_at: 'desc' },
            }),
            prisma.report.count(),
        ]);

        return { reports, total };
    },
    getAllUnsolvedReports: async (skip, take) => {
        const [reports, total] = await Promise.all([
            prisma.report.findMany({
                where: { status: false },
                skip,
                take,
                orderBy: { created_at: 'desc' },
            }),
            prisma.report.count({
                where: { status: false },
            }),
        ]);

        return { reports, total };
    },

    getReportById: async (id) => {
        return await prisma.report.findUnique({
            where: { id },
        });
    },

    getReportsByType: async (type, skip, take) => {
        let whereClause = {};

        if (type === 'jobPost') {
            whereClause.jobPostId = { not: null };
        } else if (type === 'post') {
            whereClause.postId = { not: null };
        } else if (type === 'user') {
            whereClause.reportedUserId = { not: null };
        }

        const [reports, total] = await Promise.all([
            prisma.report.findMany({
                where: whereClause,
                skip,
                take,
                orderBy: { created_at: 'desc' },
            }),
            prisma.report.count({ where: whereClause }),
        ]);

        return { reports, total };
    },
};

module.exports = { ReportService };
