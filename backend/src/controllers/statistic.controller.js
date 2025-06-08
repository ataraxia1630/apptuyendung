const { StatisticService } = require('../services/statistic.service');
const { getPagination, buildMeta } = require('../utils/paginate');

const StatisticController = {
    // GET /statistic/overview
    getOverview: async (req, res) => {
        try {
            const data = await StatisticService.getOverview();
            return res.status(200).json({ data });
        } catch (error) {
            return res
                .status(500)
                .json({ message: 'Error fetching overview', error: error.message });
        }
    },

    // GET /statistic/top-jobposts?page=&pageSize=
    getTopJobposts: async (req, res) => {
        const page = parseInt(req.query.page) || 1;
        const pageSize = parseInt(req.query.pageSize) || 10;
        const { skip, take } = getPagination(page, pageSize);

        try {
            const { items: topJobposts, total } = await StatisticService.getTopJobposts(skip, take);
            const meta = buildMeta(total, page, pageSize);
            return res.status(200).json({ data: topJobposts, meta });
        } catch (error) {
            return res
                .status(500)
                .json({ message: 'Error fetching top job posts', error: error.message });
        }
    },

    // GET /statistic/top-companies?page=&pageSize=
    getTopCompanies: async (req, res) => {
        const page = parseInt(req.query.page) || 1;
        const pageSize = parseInt(req.query.pageSize) || 10;
        const { skip, take } = getPagination(page, pageSize);

        try {
            const { items: topCompanies, total } = await StatisticService.getTopCompanies(skip, take);
            const meta = buildMeta(total, page, pageSize);
            return res.status(200).json({ data: topCompanies, meta });
        } catch (error) {
            return res
                .status(500)
                .json({ message: 'Error fetching top companies', error: error.message });
        }
    },

    // GET /statistic/monthly-growth
    getMonthlyGrowth: async (req, res) => {
        try {
            const { userGrowth, jobGrowth } = await StatisticService.getMonthlyGrowth();
            return res.status(200).json({ userGrowth, jobGrowth });
        } catch (error) {
            return res
                .status(500)
                .json({ message: 'Error fetching monthly growth', error: error.message });
        }
    },

    // GET /statistic/by-category?page=&pageSize=
    getByCategory: async (req, res) => {
        const page = parseInt(req.query.page) || 1;
        const pageSize = parseInt(req.query.pageSize) || 10;
        const { skip, take } = getPagination(page, pageSize);

        try {
            const { items: data, total } = await StatisticService.getByCategory(skip, take);
            const meta = buildMeta(total, page, pageSize);
            return res.status(200).json({ data, meta });
        } catch (error) {
            return res
                .status(500)
                .json({ message: 'Error fetching statistics by category', error: error.message });
        }
    },

    // GET /statistic/applications/summary/company/:companyId
    getApplicationSummaryForCompany: async (req, res) => {
        try {
            const { companyId } = req.params;
            const data = await StatisticService.getApplicationSummaryForCompany(companyId);
            return res.status(200).json({ data });
        } catch (error) {
            return res.status(500).json({
                message: 'Error fetching application summary for company',
                error: error.message
            });
        }
    },
};

module.exports = { StatisticController };
