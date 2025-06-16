const prisma = require('../config/db/prismaClient');

const PostImageService = {
    saveImageContent: async (postId, filePath, order) => {
        return await prisma.postContent.create({
            data: {
                postId,
                value: filePath,
                order,
                type: 'IMAGE',
            },
        });
    },
};

module.exports = { PostImageService };
