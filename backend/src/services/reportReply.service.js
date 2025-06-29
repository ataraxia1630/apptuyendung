const prisma = require('../config/db/prismaClient');

const ReportReplyService = {
    createReply: async ({ reportId, adminId, message }) => {
        const reply = await prisma.reportReply.create({
            data: { reportId, adminId, message },
        });

        // 2. Cập nhật report thành đã phản hồi
        await prisma.report.update({
            where: { id: reportId },
            data: { status: true },
        });

        return reply;
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
