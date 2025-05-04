const ImageService = {
    addImageToPost: async ({ postId, url, type, size }) => {
        if (!postId || !url || !type || !size) {
            throw new Error('Missing required fields');
        }
        return await prisma.image.create({ data: { postId, url, type, size } });
    },

    getImagesByPost: async (postId) => {
        return await prisma.image.findMany({ where: { postId } });
    },

    deleteImageById: async (id) => {
        if (!id) throw new Error('Image ID is required');
        return await prisma.image.delete({ where: { id } });
    },

    deleteImagesByPost: async (postId) => {
        if (!postId) throw new Error('Post ID is required');
        return await prisma.image.deleteMany({ where: { postId } });
    }

};

module.exports = { ImageService };
