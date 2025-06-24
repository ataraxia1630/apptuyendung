const express = require('express');
const router = express.Router();
const { StatisticController } = require('../controllers/statistic.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { requireRole } = require('../middlewares/role.middleware');
const { cache } = require('../middlewares/cache.middleware');

router.get('/overview', cache, StatisticController.getOverview);
router.get('/top-jobposts', cache, StatisticController.getTopJobposts);
router.get('/top-companies', cache, StatisticController.getTopCompanies);
router.get('/monthly-growth', verifyToken, cache, StatisticController.getMonthlyGrowth);

router.get('/applications/summary/company/:companyId',
    verifyToken,
    requireRole('COMPANY'),
    cache,
    StatisticController.getApplicationSummaryForCompany
);

router.get(
    '/by-category',
    verifyToken,
    requireRole('ADMIN'),
    cache,
    StatisticController.getByCategory
);

router.get(
    '/by-field',
    verifyToken,
    requireRole('ADMIN'),
    cache,
    StatisticController.getByField
);


module.exports = router;
