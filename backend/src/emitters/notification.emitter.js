const EventEmitter = require('events');
const { NotiEventHandler } = require('../socket/noti.event');

const NotiEmitter = new EventEmitter();

NotiEmitter.on('job.applied', async ({ userId, jobTitle }) => {
  await NotiEventHandler.notify({
    userId,
    title: 'Có ứng viên vừa ứng tuyển!',
    message: `Có ứng viên mới ứng tuyển vào công việc: ${jobTitle}`,
    type: 'both',
  });
});

module.exports = new NotiEmitter();
