const prisma = require('../config/db/prismaClient');

const autoTerminateExpiredJobPosts = async () => {
    try {
        const now = new Date();

        // Cập nhật tất cả jobPost đã quá hạn apply_until thành TERMINATED
        const result = await prisma.jobPost.updateMany({
            where: {
                apply_until: { lt: now },
                status: { not: 'TERMINATED' }
            },
            data: {
                status: 'TERMINATED'
            }
        });

        if (result.count > 0) {
            console.log(`[CRON] Auto-terminated ${result.count} expired job posts.`);
        }
    } catch (error) {
        console.error('[CRON] Failed to auto-terminate expired job posts:', error.message);
    }
};

const scheduleAutoTerminateJob = () => {
    const cron = require('node-cron');

    // Chạy mỗi ngày lúc 0h05 sáng
    cron.schedule('*/10 * * * *', autoTerminateExpiredJobPosts, {
        timezone: 'Asia/Ho_Chi_Minh',
    });

    console.log('[CRON] Auto-terminate job scheduled at 00:05 AM daily.');
};

module.exports = {
    scheduleAutoTerminateJob,
};
