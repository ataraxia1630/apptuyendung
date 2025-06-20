const express = require('express');
const router = express.Router();
const { ReviewController } = require('../controllers/review.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { cache } = require('../middlewares/cache.middleware');
const { validate } = require('../middlewares/validate.middleware');
const { ReviewSchema } = require('../validators/Review/review.validator');

// Tạo hoặc cập nhật review
router.post('/', verifyToken, validate(ReviewSchema), ReviewController.createOrUpdate);

// Lấy danh sách review của 1 user
router.get('/user/:id', verifyToken, cache, ReviewController.getByUser);

// Lấy điểm trung bình đánh giá của 1 user
router.get('/average/:id', verifyToken, cache, ReviewController.getAverageRating);

module.exports = router;
