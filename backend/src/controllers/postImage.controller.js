const { PostImageService } = require('../services/postImage.service');
const { PostImageHelper } = require('../helpers/postImage.helper');

const PostImageController = {
    uploadImage: async (req, res) => {
        const userId = req.user.userId;
        const postId = req.body.postId;
        const order = parseInt(req.body.order) || 0;

        if (!req.file) {
            return res.status(400).json({ message: 'No file uploaded' });
        }

        try {
            const filePath = await PostImageHelper.uploadImage(req.file, postId, userId);
            const postContent = await PostImageService.saveImageContent(postId, filePath, order);

            return res.status(201).json({
                message: 'Image uploaded successfully',
                postContent,
            });
        } catch (error) {
            return res.status(500).json({
                message: 'Upload failed',
                error: error.message,
            });
        }
    },

    getImageURL: async (req, res) => {
        try {
            const filePath = req.params.filePath;
            const signedUrl = await PostImageHelper.getImageSignedUrl(filePath);

            return res.status(200).json({
                message: 'Signed URL generated successfully',
                url: signedUrl,
            });
        } catch (error) {
            return res.status(500).json({
                message: 'Get signed URL failed',
                error: error.message,
            });
        }
    },
    deleteImage: async (req, res) => {
        const filePath = req.params.filePath;

        try {
            const success = await PostImageHelper.deleteImage(filePath);
            if (!success) {
                return res.status(500).json({ message: 'Delete failed' });
            }

            return res.status(200).json({ message: 'Image deleted successfully' });
        } catch (error) {
            return res.status(500).json({
                message: 'Delete image failed',
                error: error.message,
            });
        }
    },
};

module.exports = { PostImageController };
