const { ReportReplyService } = require('../services/reportReply.service');

const ReportReplyController = {
    createReply: async (req, res) => {
        const { reportId, message } = req.body;
        const adminId = req.user.id;

        try {
            const reply = await ReportReplyService.createReply({ reportId, adminId, message });
            res.status(201).json({ reply });
        } catch (error) {
            res.status(500).json({ message: 'Error creating reply', error: error.message });
        }
    },

    getRepliesByReport: async (req, res) => {
        const { reportId } = req.params;

        try {
            const replies = await ReportReplyService.getRepliesByReportId(reportId);
            res.status(200).json({ replies });
        } catch (error) {
            res.status(500).json({ message: 'Error fetching replies', error: error.message });
        }
    },
};

module.exports = { ReportReplyController };
