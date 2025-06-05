const prisma = require('../config/db/prismaClient');

/**
 * Helper: lấy mốc UTC 00:00:00 từ chuỗi 'YYYY-MM-DD'
 */
function parseDateKey(dateString) {
    const [year, month, day] = dateString.split('-').map(Number);
    // Dùng Date UTC để tránh lệch timezone
    return new Date(Date.UTC(year, month - 1, day, 0, 0, 0));
}

const DailyStatisticService = {
    /**
     * upsertFromReport({ date, userCount, jobPostCount, reportCount, applicationCount })
     * → Upsert (create if not exists / update nếu đã có) vào bảng DailyStatistic
     */
    upsertFromReport: async ({ date, userCount, jobPostCount, reportCount, applicationCount }) => {
        const dateKey = parseDateKey(date);
        return prisma.dailyStatistic.upsert({
            where: { date: dateKey },
            create: {
                date: dateKey,
                userCount,
                jobPostCount,
                reportCount,
                applicationCount
            },
            update: {
                userCount,
                jobPostCount,
                reportCount,
                applicationCount
            }
        });
    },

    /**
     * findAll({ start, end, skip, take })
     * - Nếu có start & end (chuỗi 'YYYY-MM-DD'), lọc date giữa start và end.
     * - skip/take để pagination.
     */
    findAll: async ({ start, end, skip = 0, take = 20 } = {}) => {
        const where = {};
        if (start && end) {
            const startDate = parseDateKey(start);
            const endDate = parseDateKey(end);
            // EndDate mốc 00:00 nên cộng thêm 23:59:59.999 vào
            endDate.setUTCHours(23, 59, 59, 999);
            where.date = { gte: startDate, lte: endDate };
        }
        const [items, total] = await Promise.all([
            prisma.dailyStatistic.findMany({
                where,
                skip,
                take,
                orderBy: { date: 'desc' }
            }),
            prisma.dailyStatistic.count({ where })
        ]);
        return { items, total };
    },

    /**
     * findByDate(dateString = 'YYYY-MM-DD')
     * → Tìm record có date = mốc 00:00 UTC của ngày đó
     */
    findByDate: async (dateString) => {
        const dateKey = parseDateKey(dateString);
        return prisma.dailyStatistic.findUnique({
            where: { date: dateKey }
        });
    }
};

module.exports = { DailyStatisticService };
