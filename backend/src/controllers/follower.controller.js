const { FollowerService } = require('../services/follower.service');
const { sendToUser } = require('../socket');

const FollowerController = {
    toggleFollow: async (req, res) => {
        const followId = req.user?.userId;
        const { followedId } = req.params;

        if (!followId || !followedId || followId === followedId) {
            return res.status(400).json({ message: 'Invalid userId or cannot follow yourself' });
        }

        try {
            const result = await FollowerService.toggleFollow({ followId, followedId });
            sendToUser(followedId, 'follower.changed', {
                followerId: followId,
                followedId,
                removed: result.removed
            });
            return res.status(result.removed ? 200 : 201).json(result);
        } catch (err) {
            return res.status(500).json({ message: err.message });
        }
    },

    getFollowers: async (req, res) => {
        const { userId } = req.params;

        try {
            const followers = await FollowerService.getFollowers(userId);
            return res.status(200).json(followers);
        } catch (err) {
            return res.status(500).json({ message: err.message });
        }
    },

    getFollowing: async (req, res) => {
        const { userId } = req.params;

        try {
            const following = await FollowerService.getFollowing(userId);
            return res.status(200).json(following);
        } catch (err) {
            return res.status(500).json({ message: err.message });
        }
    }
};

module.exports = { FollowerController };
