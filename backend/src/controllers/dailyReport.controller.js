const { DailyReportService } = require('../services/dailyReport.service');

const DailyReportController = {
  getDailyReport: async (req, res) => {
    try {
      const report = await DailyReportService.getDailyReport();
      return res.status(200).json({ report: report });
    } catch (error) {
      res.status(error.status || 500).json({
        message: 'Error calculating daily report',
        error: error.message,
      });
    }
  },
};

module.exports = { DailyReportController };
