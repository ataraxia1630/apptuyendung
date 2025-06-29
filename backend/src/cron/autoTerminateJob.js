// src/cron/autoTerminateJob.js
const prisma = require('../config/db/prismaClient');
const NotiEmitter = require('../emitters/notification.emitter');
const cron = require('node-cron');

const autoTerminateExpiredJobPosts = async () => {
    try {
        const now = new Date();

        const expiredJobs = await prisma.jobPost.findMany({
            where: {
                apply_until: { lt: now },
                status: { not: 'TERMINATED' },
            },
            select: {
                id: true,
                title: true,
                companyId: true,
                JobApplied: {
                    select: {
                        applicantId: true,
                    },
                },
            },
        });

        for (const job of expiredJobs) {
            await prisma.jobPost.update({
                where: { id: job.id },
                data: { status: 'TERMINATED' },
            });

            NotiEmitter.emit('job.expired', {
                userId: job.companyId,
                jobTitle: job.title,
            });

            for (const applied of job.JobApplied) {
                const user = await prisma.user.findFirst({
                    where: { applicantId: applied.applicantId },
                    select: { id: true },
                });

                if (user) {
                    NotiEmitter.emit('job.statusChanged', {
                        userId: user.id,
                        jobTitle: job.title
                    });
                }
            }
        }

        if (expiredJobs.length > 0) {
            console.log(`[CRON] Auto-terminated ${expiredJobs.length} expired job posts.`);
        }
    } catch (error) {
        console.error('[CRON] Failed to auto-terminate expired job posts:', error.message);
    }
};

const scheduleAutoTerminateJob = () => {
    cron.schedule('55 23 * * *', autoTerminateExpiredJobPosts, {
        timezone: 'Asia/Ho_Chi_Minh',
    });
};

module.exports = { scheduleAutoTerminateJob };
