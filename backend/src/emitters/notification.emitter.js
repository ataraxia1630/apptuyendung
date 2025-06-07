const EventEmitter = require('events');
const { NotiEventHandler } = require('../socket/noti.eventHandler');

const NotiEmitter = new EventEmitter();

NotiEmitter.on('job.apply', async ({ userId, jobTitle }) => {
  await NotiEventHandler.notify(
    userId,
    'Có ứng viên vừa ứng tuyển!',
    `Có ứng viên mới ứng tuyển vào công việc: ${jobTitle}`,
    'both'
  );
});

NotiEmitter.on('jobApplied.success', async ({ userId, jobTitle }) => {
  await NotiEventHandler.notify(
    userId,
    'Ứng tuyển thành công!',
    `Chúc mừng bạn! Bạn đã ứng tuyển thành công vào công việc: ${jobTitle}`,
    'both'
  );
});

NotiEmitter.on('jobApplied.fail', async ({ userId, jobTitle }) => {
  await NotiEventHandler.notify(
    userId,
    'Ứng tuyển thất bại!',
    `Rất tiếc, bạn đã ứng tuyển thất bại vào công việc: ${jobTitle}`,
    'both'
  );
});

module.exports = NotiEmitter;
