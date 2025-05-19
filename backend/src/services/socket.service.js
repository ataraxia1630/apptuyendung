let io;
const userSockets = new Map();

const SocketService = {
  init: (_io) => {
    io = _io;
    io.on('connection', (socket) => {
      const userId = socket.handshake.query.userId;
      userSockets.set(userId, socket.id);

      socket.on('disconnect', () => {
        userSockets.delete(userId);
      });
    });
  },

  emitToUser: (userId, event, data) => {
    const socketId = userSockets.get(userId);
    if (socketId) {
      io.to(socketId).emit(event, data);
    }
  },

  emitToAll: (event, data) => {
    io.emit(event, data);
  },
};

module.exports = { SocketService };
