const { DailyStatisticService } = require('../services/dailyStatistic.service');
const { getPagination, buildMeta } = require('../utils/paginate');

const DailyStatisticController = {
    /**
     * POST /daily-statistics
     * Body: {
     *   date: 'YYYY-MM-DD',
     *   userCount: number,
     *   jobPostCount: number,
     *   reportCount: number,
     *   applicationCount: number
     * }
     */
    upsertFromReport: async (req, res) => {
        try {
            const { date, userCount, jobPostCount, reportCount, applicationCount } = req.body;
            // Kiểm tra payload
            if (!date || userCount == null || jobPostCount == null || reportCount == null || applicationCount == null) {
                return res.status(400).json({ message: 'Missing required fields' });
            }
            const stat = await DailyStatisticService.upsertFromReport({
                date,
                userCount,
                jobPostCount,
                reportCount,
                applicationCount
            });
            return res.status(200).json({ message: `Upserted statistic for ${date}`, data: stat });
        } catch (error) {
            return res.status(500).json({
                message: 'Error upserting daily statistic',
                error: error.message
            });
        }
    },

    /**
     * GET /daily-statistics
     * Query: ?start=YYYY-MM-DD&end=YYYY-MM-DD&page=<n>&pageSize=<m>
     */
    getAll: async (req, res) => {
        try {
            const { start, end, page = 1, pageSize = 20 } = req.query;
            const { skip, take } = getPagination(parseInt(page), parseInt(pageSize));
            const { items, total } = await DailyStatisticService.findAll({
                start,
                end,
                skip,
                take
            });
            const meta = buildMeta(total, parseInt(page), parseInt(pageSize));
            return res.status(200).json({ data: items, meta });
        } catch (error) {
            return res.status(500).json({
                message: 'Error fetching daily statistics',
                error: error.message
            });
        }
    },

    /**
     * GET /daily-statistics/:date
     */
    getByDate: async (req, res) => {
        try {
            const { date } = req.params; // 'YYYY-MM-DD'
            const stat = await DailyStatisticService.findByDate(date);
            if (!stat) {
                return res.status(404).json({ message: `No statistic found for date ${date}` });
            }
            return res.status(200).json({ data: stat });
        } catch (error) {
            return res.status(500).json({
                message: 'Error fetching daily statistic by date',
                error: error.message
            });
        }
    }
};

module.exports = { DailyStatisticController };
