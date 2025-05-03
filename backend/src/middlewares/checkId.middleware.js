const { PrismaClient } = require('@prisma/client');
const prisma = new PrismaClient();

const checkCompanyOwnsJobPost = async (req, res, next) => {
    const jobPostId = req.params.id;
    const userId = req.user?.userId;  // Lấy userId từ token

    try {
        const user = await prisma.user.findUnique({
            where: {
                id: userId,  // Tìm người dùng theo userId
            },
            select: {
                companyId: true,  // Chỉ lấy companyId của người dùng
            }
        });

        if (!user) {
            return res.status(404).json({ message: 'User not found' });
        }

        // Kiểm tra quyền sở hữu job post
        if (user.companyId !== req.params.companyId) {
            return res.status(403).json({ message: 'Forbidden: You do not own this job post' });
        }

        next();  // Nếu không có lỗi, tiếp tục với các middleware tiếp theo
    } catch (error) {
        console.error('Error in checkCompanyOwnsJobPost:', error.message);
        return res.status(500).json({ message: 'Internal server error' });
    }
};


module.exports = { checkCompanyOwnsJobPost };
