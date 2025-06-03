function commentHandler(socket) {
    socket.on('joinPostRoom', (postId) => {
        if (postId) {
            socket.join(`post:${postId}`);
        }
    });

    socket.on('leavePostRoom', (postId) => {
        if (postId) {
            socket.leave(`post:${postId}`);
        }
    });
}

module.exports = commentHandler;
