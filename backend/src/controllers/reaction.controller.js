const { ReactionService } = require('../services/reaction.service');

const ReactionController = {
    // Toggle reaction (thêm hoặc thay đổi reaction)
    toggleReaction: async (req, res) => {
        const { postId, reactionType } = req.body;
        const userId = req.user?.userId;

        if (!postId || !reactionType || !userId) {
            return res.status(400).json({ message: 'Missing required fields' });
        }

        try {
            const result = await ReactionService.toggleReaction({ postId, userId, reactionType });
            return res.status(result.removed ? 200 : 201).json(result);
        } catch (err) {
            return res.status(500).json({ message: err.message });
        }
    },

    // Xóa reaction
    removeReaction: async (req, res) => {
        const { postId } = req.params;
        const userId = req.user?.userId;

        if (!postId || !userId) {
            return res.status(400).json({ message: 'Missing postId or userId' });
        }

        try {
            const result = await ReactionService.removeReaction({ postId, userId });
            return res.status(200).json({ message: 'Reaction removed' });
        } catch (err) {
            return res.status(500).json({ message: err.message });
        }
    }
};

module.exports = { ReactionController };
