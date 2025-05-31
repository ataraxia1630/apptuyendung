const { Server } = require('socket.io');
const chatHandler = require('./handlers/chat.handler');
const reactionHandler = require('./handlers/reaction.handler');
const commentHandler = require('./handlers/comment.handler');

let io;
const userSockets = new Map();

function initSocket(server) {
  io = new Server(server, {
    cors: {
      origin: '*',
    },
  });

  io.on('connection', (socket) => {
    console.log('🔌 New client connected', socket.id);
    socket.on('register', (userId) => {
      userSockets.set(userId, socket.id);
      socket.data.userId = userId;
      console.log(`✅ Registered user ${userId} with socket ${socket.id}`);
    });

    chatHandler(socket, userSockets);
    commentHandler(socket);
    reactionHandler(socket);
    //notiHandler(socket, userSockets);

    socket.on('disconnect', () => {
      for (const [userId, id] of userSockets.entries()) {
        if (id === socket.id) {
          userSockets.delete(userId);
          break;
        }
      }
      console.log(`❌ Client disconnected ${socket.id}`);
    });
  });
}

function sendToUser(userId, eventName, data) {
  const socketId = userSockets.get(userId);
  if (socketId && io) {
    io.to(socketId).emit(eventName, data);
  }
}

function sendToPostRoom(postId, event, payload) {
  io.to(`post:${postId}`).emit(event, payload);
}

function sendToChatRoom(chatId, eventName, data) {
  if (io) {
    io.to(chatId).emit(eventName, data);
  }
}

module.exports = {
  initSocket,
  sendToUser,
  sendToChatRoom,
  sendToPostRoom,
};
