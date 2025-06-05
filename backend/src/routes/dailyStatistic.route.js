const express = require('express');
const router = express.Router();
const { requireRole } = require('../middlewares/role.middleware');
const { DailyStatisticController } = require('../controllers/dailyStatistic.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { cache } = require('../middlewares/cache.middleware');

router.post('/', verifyToken, requireRole('ADMIN'), DailyStatisticController.upsertFromReport);

/**
 * 2. GET /daily-statistics
 *    → Lấy danh sách tất cả record, có phân trang & lọc theo khoảng ngày (?start=&end=&page=&pageSize=).
 */
router.get('/', cache, DailyStatisticController.getAll);

/**
 * 3. GET /daily-statistics/:date
 *    → Lấy record của ngày cụ thể (date = 'YYYY-MM-DD').
 *    → Nếu không tồn tại, trả về 404.
 */
router.get('/:date', cache, DailyStatisticController.getByDate);

module.exports = router;
