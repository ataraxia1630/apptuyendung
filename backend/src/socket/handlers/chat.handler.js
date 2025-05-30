const { MessService } = require('../../services/message.service');

function chatHandler(socket, userSockets) {
  // Tham gia chat
  socket.on('joinChat', (chatId) => {
    socket.join(chatId);
    console.log(`🔸 ${socket.id} joined chat ${chatId}`);
  });

  // Rời đoạn chat
  socket.on('leaveChat', (chatId) => {
    socket.leave(chatId);
    console.log(`🔸 ${socket.id} left chat ${chatId}`);
  });

  // Gửi tin nhắn
  socket.on('sendMess', async ({ chatId, content }) => {
    try {
      const userId = socket.data.userId;
      const mess = await MessService.createNew(userId, chatId, content);
      socket.to(chatId).emit('newMess', { mess });
    } catch (error) {
      socket.emit('error', { message: error.message });
    }
  });

  // Xoá tin nhắn
  socket.on('deleteMess', async ({ messId }) => {
    try {
      const mess = await MessService.deleteMess(messId);
      socket.to(mess.conversationId).emit('messDeleted', { mess });
    } catch (error) {
      socket.emit('error', { message: error.message });
    }
  });

  // Sửa tin nhắn
  socket.on('editMess', async ({ messId, content }) => {
    try {
      const mess = await MessService.editMess(messId, content);
      socket.to(mess.conversationId).emit('messEdited', { mess });
    } catch (error) {
      socket.emit('error', { message: error.message });
    }
  });
}

module.exports = chatHandler;
