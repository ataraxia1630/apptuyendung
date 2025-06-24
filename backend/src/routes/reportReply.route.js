const express = require('express');
const router = express.Router();
const { ReportReplyController } = require('../controllers/reportReply.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { requireRole } = require('../middlewares/role.middleware');

// ADMIN gửi phản hồi cho một report
router.post('/', verifyToken, requireRole('ADMIN'), ReportReplyController.createReply);

// Lấy tất cả phản hồi theo reportId (Admin hoặc User có thể xem)
router.get('/report/:reportId', verifyToken, ReportReplyController.getRepliesByReport);

module.exports = router;
