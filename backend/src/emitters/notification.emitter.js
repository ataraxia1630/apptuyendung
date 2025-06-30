const EventEmitter = require('events');
const { NotiEventHandler } = require('../socket/noti.eventHandler');
const prisma = require('../config/db/prismaClient');

const NotiEmitter = new EventEmitter();

// Khi có ứng viên ứng tuyển
NotiEmitter.on('job.apply', async ({ userId, jobTitle }) => {
  const message = `Có ứng viên mới ứng tuyển vào công việc: ${jobTitle}`;
  await NotiEventHandler.notify(
    userId,
    'Có ứng viên vừa ứng tuyển!',
    message,
    'both'
  );
});

// Ứng tuyển thành công
NotiEmitter.on('jobApplied.success', async ({ userId, jobTitle }) => {
  const message = `Chúc mừng bạn! Bạn đã ứng tuyển thành công vào công việc: ${jobTitle}`;
  await NotiEventHandler.notify(
    userId,
    'Ứng tuyển thành công!',
    message,
    'both'
  );
});

// Ứng tuyển thất bại
NotiEmitter.on('jobApplied.fail', async ({ userId, jobTitle }) => {
  const message = `Rất tiếc, bạn đã ứng tuyển thất bại vào công việc: ${jobTitle}`;
  await NotiEventHandler.notify(
    userId,
    'Ứng tuyển thất bại!',
    message,
    'both'
  );
});

// Tin nhắn mới
NotiEmitter.on('mess.new', async ({ userId }) => {
  const message = 'Kiểm tra hộp thư ngay ✉️';
  await NotiEventHandler.notify(
    userId,
    'Bạn có tin nhắn mới!',
    message,
    'fcm'
  );
});

// Công việc hết hạn (cho công ty)
NotiEmitter.on('job.expired', async ({ userId, jobTitle }) => {
  const title = 'Công việc đã hết hạn';
  const message = `Công việc "${jobTitle}" bạn đăng đã hết hạn.`;

  await NotiEventHandler.notify(
    userId,
    title,
    message,
    'both'
  );
});

// Công việc ứng tuyển hết hạn (cho ứng viên)
NotiEmitter.on('job.statusChanged', async ({ userId, jobTitle }) => {
  const title = 'Công việc đã hết hạn';
  const message = `Công việc "${jobTitle}" bạn ứng tuyển đã hết hạn và ngừng tuyển.`;

  await NotiEventHandler.notify(
    userId,
    title,
    message,
    'both'
  );
});

// Công việc bị xoá
NotiEmitter.on('job.deleted', async ({ userId, jobTitle }) => {
  const title = 'Công việc đã bị xoá';
  const message = `Công việc "${jobTitle}" bạn đã ứng tuyển đã bị xoá.`;

  await NotiEventHandler.notify(
    userId,
    title,
    message,
    'both'
  );
});

// Công việc được cập nhật
NotiEmitter.on('job.updated', async ({ userId, jobTitle }) => {
  const title = 'Công việc vừa được cập nhật';
  const message = `Công việc "${jobTitle}" bạn ứng tuyển đã thay đổi thông tin.`;

  await NotiEventHandler.notify(
    userId,
    title,
    message,
    'both'
  );
});

// Admin cập nhật trạng thái bài đăng
NotiEmitter.on('job.adminUpdatedStatusforCompany', async ({ userId, jobTitle, status }) => {
  let title = '';
  let message = '';

  if (status === 'OPENING') {
    title = 'Bài đăng đã được mở lại';
    message = `Bài đăng "${jobTitle}" đã được quản trị viên cho phép mở lại.`;
  } else if (status === 'TERMINATED') {
    title = 'Bài đăng đã bị tạm ngưng';
    message = `Bài đăng "${jobTitle}" đã bị quản trị viên tạm ngưng.`;
  } else if (status === 'CANCELLED') {
    title = 'Bài đăng đã bị huỷ';
    message = `Bài đăng "${jobTitle}" đã bị quản trị viên huỷ.`;
  }

  if (title && message) {
    await NotiEventHandler.notify(
      userId,
      title,
      message,
      'both'
    );
  }
});

NotiEmitter.on('job.adminUpdatedStatusforUser', async ({ userId, jobTitle, status }) => {
  let title = '';
  let message = '';
  if (status === 'CANCELLED') {
    title = 'Bài đăng bạn ứng tuyển đã bị huỷ';
    message = `Bài đăng "${jobTitle}" đã bị quản trị viên huỷ.`;
  } else if (status === 'OPENING') {
    title = 'Bài đăng bạn ứng tuyển đã được mở lại';
    message = `Bài đăng "${jobTitle}" đã được quản trị viên cho phép mở lại.`;
  } else if (status === 'TERMINATED') {
    title = 'Bài đăng bạn ứng tuyển đã bị tạm ngưng';
    message = `Bài đăng "${jobTitle}" đã bị quản trị viên tạm ngưng.`;
  }

  if (title && message) {
    await NotiEventHandler.notify(
      userId,
      title,
      message,
      'both'
    );
  }
});

// Phản ứng mới lên bài viết
NotiEmitter.on('reaction.post', async ({ userId, fromUserId, postId, reactionType }) => {
  const title = 'Có phản ứng mới trên bài viết của bạn';
  const fromUser = await prisma.user.findUnique({
    where: { id: fromUserId },
    select: { username: true }
  });
  const message = `${fromUser?.username || 'Ai đó'} vừa thả cảm xúc vào bài viết của bạn.`;

  await NotiEventHandler.notify(
    userId,
    title,
    message,
    'both'
  );
});

// Bình luận trên post
NotiEmitter.on('comment.onPost', async ({ userId, fromUserId, postId, commentId }) => {
  if (userId === fromUserId) return;

  // 1. Truy vấn thông tin người bình luận
  const fromUser = await prisma.user.findUnique({
    where: { id: fromUserId },
    select: { username: true }
  });

  // 2. Tạo message
  const message = `${fromUser?.username || 'Ai đó'} vừa bình luận vào bài viết của bạn.`;

  // 3. Gửi thông báo
  await NotiEventHandler.notify(
    userId,
    'Bài viết của bạn có bình luận mới',
    message,
    'both'
  );
});


// Phản hồi vào bình luận
NotiEmitter.on('comment.onReply', async ({ userId, fromUserId, postId, commentId }) => {
  if (userId === fromUserId) return;
  // 1. Truy vấn thông tin người bình luận
  const fromUser = await prisma.user.findUnique({
    where: { id: fromUserId },
    select: { username: true }
  });

  // 2. Tạo message
  const message = `${fromUser?.username || 'Ai đó'} vừa trả lời bình luận của bạn.`;
  await NotiEventHandler.notify(
    userId,
    'Phản hồi bình luận',
    message,
    'both'
  );
});

NotiEmitter.on('report.replied', async ({ userId, reportId }) => {
  const title = 'Báo cáo đã được phản hồi';
  const message = 'Báo cáo của bạn đã được quản trị viên xem xét và phản hồi.';
  await NotiEventHandler.notify(
    userId,
    title,
    message,
    'both'
  );
});

module.exports = NotiEmitter;
