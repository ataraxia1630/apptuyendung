const { PrismaClient } = require('@prisma/client');
const prisma = new PrismaClient();

/**
 * @param {String} modelName - Tên model trong Prisma (JobPost, Post, v.v.)
 * @param {String} companyField - Tên trường chứa companyId trong model đó
 */
const checkCompanyOwnership = (modelName, companyField = 'companyId') => {
    return async (req, res, next) => {
        const recordId = req.params.id;
        const userId = req.user?.userId;

        try {
            const user = await prisma.user.findUnique({
                where: { id: userId },
                select: { companyId: true }
            });

            if (!user) {
                return res.status(404).json({ message: 'User not found' });
            }

            const record = await prisma[modelName].findUnique({
                where: { id: recordId },
                select: { [companyField]: true }
            });

            if (!record) {
                return res.status(404).json({ message: `${modelName} not found` });
            }

            if (record[companyField] !== user.companyId) {
                return res.status(403).json({ message: 'Forbidden: You do not own this resource' });
            }

            next();
        } catch (error) {
            console.error(`Error in checkCompanyOwnership for ${modelName}:`, error.message);
            return res.status(500).json({ message: 'Internal server error' });
        }
    };
};

module.exports = { checkCompanyOwnership };
