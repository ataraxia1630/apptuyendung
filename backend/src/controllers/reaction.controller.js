const { sendToPostRoom } = require('../socket');
const { ReactionService } = require('../services/reaction.service');

const ReactionController = {
    toggleReaction: async (req, res) => {
        const { postId, reactionType } = req.body;
        const userId = req.user?.userId;

        if (!postId || !reactionType || !userId) {
            return res.status(400).json({ message: 'Missing required fields' });
        }

        try {
            const result = await ReactionService.toggleReaction({ postId, userId, reactionType });

            // Emit sau khi xử lý thành công
            sendToPostRoom(postId, 'reaction.changed', {
                postId,
                userId,
                reactionType,
                removed: result.removed
            });

            return res.status(result.removed ? 200 : 201).json(result);
        } catch (err) {
            return res.status(500).json({ message: err.message });
        }
    },


};

module.exports = { ReactionController };
