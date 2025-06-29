const express = require('express');
const router = express.Router();
const { ReportController } = require('../controllers/report.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { cache } = require('../middlewares/cache.middleware');
const { requireRole } = require('../middlewares/role.middleware');
const { validate } = require('../middlewares/validate.middleware');
const { ReportSchema } = require('../validators/report.validator');

router.post('/', verifyToken, validate(ReportSchema), ReportController.createReport);
router.get('/', verifyToken, requireRole('ADMIN'), ReportController.getAllReports);
router.get('/unsolved', verifyToken, requireRole('ADMIN'), ReportController.getAllUnsolvedReports);
router.get('/type/:type', verifyToken, requireRole('ADMIN'), cache, ReportController.getReportsByType);
router.get('/:id', verifyToken, requireRole('ADMIN'), cache, ReportController.getReportById);


module.exports = router;
