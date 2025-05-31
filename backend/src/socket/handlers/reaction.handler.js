function reactionHandler(socket) {
    socket.on('joinPost', (postId) => {
        if (postId) {
            socket.join(`post:${postId}`);
            console.log(`✅ Socket ${socket.id} joined post room: post:${postId}`);
        }
    });

    socket.on('leavePost', (postId) => {
        if (postId) {
            socket.leave(`post:${postId}`);
            console.log(`🚪 Socket ${socket.id} left post room: post:${postId}`);
        }
    });
}

module.exports = reactionHandler;
