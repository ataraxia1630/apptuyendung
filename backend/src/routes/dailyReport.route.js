const { Router } = require('express');
const {
  DailyReportController,
} = require('../controllers/dailyReport.controller');
const { verifyToken } = require('../middlewares/auth.middleware');

const route = Router();

route.get('/', DailyReportController.getDailyReport);

module.exports = route;
