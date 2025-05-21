const { Server } = require('socket.io');

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
    // const userId = socket.handshake.query.userId;
    // if (userId) {
    //   userSocketMap.set(userId, socket.id);
    // }

    socket.on('register', (userId) => {
      userSockets.set(userId, socket.id);
      console.log(`✅ Registered user ${userId} with socket ${socket.id}`);
    });

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

module.exports = {
  initSocket,
  sendToUser,
};
