const { ReviewService } = require('../services/review.service');
const { getPagination, buildMeta } = require('../utils/paginate');

const ReviewController = {
    createOrUpdate: async (req, res) => {
        const { reviewed_userId, detail, rating } = req.body;
        const userId = req.user?.userId;

        try {
            const review = await ReviewService.createOrUpdate({
                userId,
                reviewed_userId,
                detail,
                rating: parseInt(rating)
            });
            return res.status(200).json({ message: 'Review submitted', review });
        } catch (error) {
            return res.status(400).json({ message: error.message });
        }
    },

    getByUser: async (req, res) => {
        const reviewed_user_id = req.params.id;
        const page = parseInt(req.query.page) || 1;
        const pageSize = parseInt(req.query.pageSize) || 10;
        const { skip, take } = getPagination(page, pageSize);

        try {
            const { items, total } = await ReviewService.getByUser(reviewed_user_id, skip, take);
            const meta = buildMeta(total, page, pageSize);
            return res.status(200).json({ data: items, meta });
        } catch (error) {
            return res.status(500).json({ message: 'Error fetching reviews', error: error.message });
        }
    },

    getAverageRating: async (req, res) => {
        const reviewed_user_id = req.params.id;

        try {
            const avg = await ReviewService.getAverageRating(reviewed_user_id);
            return res.status(200).json({ average: avg });
        } catch (error) {
            return res.status(500).json({ message: 'Error calculating average', error: error.message });
        }
    }
};

module.exports = { ReviewController };
