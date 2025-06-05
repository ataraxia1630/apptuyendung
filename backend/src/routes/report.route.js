const express = require('express');
const router = express.Router();
const { ReportController } = require('../controllers/report.controller');
const { verifyToken, verifyAdmin } = require('../middlewares/auth.middleware');
const { cache } = require('../middlewares/cache.middleware');

router.post('/', verifyToken, cache, ReportController.createReport);
router.get('/', verifyToken, verifyAdmin, cache, ReportController.getAllReports);
router.patch('/:id/approve', verifyToken, verifyAdmin, ReportController.approveReport);
router.patch('/:id/reject', verifyToken, verifyAdmin, ReportController.rejectReport);

module.exports = router;
