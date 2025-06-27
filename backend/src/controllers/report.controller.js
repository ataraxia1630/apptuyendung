const { ReportService } = require('../services/report.service');
const { getPagination, buildMeta } = require('../utils/paginate');
const validTypes = ['jobPost', 'post', 'user'];

const ReportController = {
    createReport: async (req, res) => {
        try {
            const { reason, jobPostId, postId, reportedUserId } = req.body;
            const userId = req.user.userId;

            if (!reason || (!jobPostId && !postId && !reportedUserId)) {
                return res.status(400).json({ message: 'Missing required fields' });
            }

            const report = await ReportService.createReport({
                reason,
                userId,
                jobPostId,
                postId,
                reportedUserId
            });

            return res.status(201).json({ message: 'Report submitted', report });
        } catch (error) {
            return res.status(500).json({ message: 'Failed to create report', error: error.message });
        }
    },

    getAllReports: async (req, res) => {
        const page = parseInt(req.query.page) || 1;
        const pageSize = parseInt(req.query.pageSize) || 10;
        const { skip, take } = getPagination(page, pageSize);

        try {
            const { reports, total } = await ReportService.getAllReports(skip, take);
            const meta = buildMeta(total, page, pageSize);
            return res.status(200).json({ reports, meta });
        } catch (error) {
            return res.status(500).json({ message: 'Failed to fetch reports', error: error.message });
        }
    },

    getReportsByType: async (req, res) => {
        const { type } = req.params;
        const page = parseInt(req.query.page) || 1;
        const pageSize = parseInt(req.query.pageSize) || 10;
        const { skip, take } = getPagination(page, pageSize);

        if (!validTypes.includes(type)) {
            return res.status(400).json({ message: 'Invalid report type' });
        }

        try {
            const { reports, total } = await ReportService.getReportsByType(type, skip, take);
            const meta = buildMeta(total, page, pageSize);
            return res.status(200).json({ reports, meta });
        } catch (error) {
            return res.status(500).json({ message: 'Failed to fetch reports by type', error: error.message });
        }
    },
    getReportById: async (req, res) => {
        const { id } = req.params;

        try {
            const report = await ReportService.getReportById(id);
            if (!report) {
                return res.status(404).json({ message: 'Report not found' });
            }

            return res.status(200).json({ report });
        } catch (error) {
            return res.status(500).json({ message: 'Failed to fetch report', error: error.message });
        }
    }


};

module.exports = { ReportController };
