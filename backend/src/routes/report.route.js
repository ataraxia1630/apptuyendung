const express = require('express');
const router = express.Router();
const { ReportController } = require('../controllers/report.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { cache } = require('../middlewares/cache.middleware');
const { requireRole } = require('../middlewares/role.middleware');
const { validate } = require('../middlewares/validate.middleware');
const { ReportSchema } = require('../validators/report.validator');

router.post('/', verifyToken, validate(ReportSchema), ReportController.createReport);
router.get('/', verifyToken, cache, ReportController.getAllReports);
router.patch('/:id/approve', verifyToken, requireRole('ADMIN'), ReportController.approveReport);
router.patch('/:id/reject', verifyToken, requireRole('ADMIN'), ReportController.rejectReport);

module.exports = router;
