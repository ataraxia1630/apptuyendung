function reactionHandler(socket) {
    socket.on('joinPost', (postId) => {
        if (postId) {
            socket.join(`post:${postId}`);
        }
    });

    socket.on('leavePost', (postId) => {
        if (postId) {
            socket.leave(`post:${postId}`);
        }
    });
}

module.exports = reactionHandler;
