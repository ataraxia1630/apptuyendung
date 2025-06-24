const prisma = require('../config/db/prismaClient');

const ReportReplyService = {
    createReply: async ({ reportId, adminId, message }) => {
        return prisma.reportReply.create({
            data: { reportId, adminId, message },
        });
    },

    getRepliesByReportId: async (reportId) => {
        return prisma.reportReply.findMany({
            where: { reportId },
            orderBy: { created_at: 'asc' },
            include: {
                admin: {
                    select: { id: true, name: true, role: true },
                },
            },
        });
    },
};

module.exports = { ReportReplyService };
