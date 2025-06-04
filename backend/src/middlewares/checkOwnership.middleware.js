const { PrismaClient } = require('@prisma/client');
const prisma = new PrismaClient();

/**
 * Middleware kiểm tra quyền sở hữu tài nguyên
 * @param {String} modelName - Tên model trong Prisma (Post, Comment, Image, Reaction, JobPost, ...)
 * @param {String} ownerField - Trường để kiểm tra quyền sở hữu ('companyId' hoặc 'userId')
 */
const checkOwnership = (modelName, ownerField = 'companyId') => {
    return async (req, res, next) => {
        const recordId = req.params.id || req.params.postId;  // Hỗ trợ trường hợp postId
        const userId = req.user?.userId;
        const role = req.user?.role;

        try {
            if (!userId) {
                return res.status(401).json({ message: 'Unauthorized' });
            }

            if (role === 'ADMIN') {
                return next(); // ADMIN bypass
            }

            const user = await prisma.user.findUnique({
                where: { id: userId },
                select: {
                    id: true,
                    companyId: true,
                }
            });

            if (!user) {
                return res.status(404).json({ message: 'User not found' });
            }

            if (modelName === 'image') {
                // Nếu là model Image, kiểm tra quyền sở hữu qua postId
                const image = await prisma.image.findUnique({
                    where: { id: recordId },
                    select: { postId: true }
                });

                if (!image) {
                    return res.status(404).json({ message: 'Image not found' });
                }

                // Kiểm tra quyền sở hữu của postId
                const post = await prisma.post.findUnique({
                    where: { id: image.postId },
                    select: { userId: true }
                });

                if (post?.userId !== userId) {
                    return res.status(403).json({ message: 'Forbidden: You do not own this resource' });
                }
            } else {
                const record = await prisma[modelName].findUnique({
                    where: { id: recordId },
                    select: {
                        [ownerField]: true
                    }
                });

                if (!record) {
                    return res.status(404).json({ message: `${modelName} not found` });
                }
                if (record[ownerField] !== user[ownerField]) {

                    return res.status(403).json({ message: 'Forbidden: You do not own this resource' });
                }
            }

            next();
        } catch (error) {
            console.error(`❌ Ownership check failed (${modelName}):`, error);
            return res.status(500).json({ message: 'Internal server error' });
        }
    };
};

module.exports = { checkOwnership };
