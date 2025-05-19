const NotiEmitter = require('../emitters/notification.emitter');
const { NotificationService } = require('../services/notification.service');

NotiEmitter.on('job.applied', async ({ userId, jobId }) => {
  await NotificationService.notify({
    userId,
    title: 'Có ứng viên vừa ứng tuyển!',
    body: `Một ứng viên đã ứng tuyển vào công việc của bạn.`,
    type: 'job.applied',
  });
});
