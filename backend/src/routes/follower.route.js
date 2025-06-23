const express = require('express');
const router = express.Router();
const { FollowerController } = require('../controllers/follower.controller');
const { verifyToken } = require('../middlewares/auth.middleware');

router.post('/toggle/:followedId', verifyToken, FollowerController.toggleFollow);
router.get('/list/following/:userId', FollowerController.getFollowing);
router.get('/list/followers/:userId', FollowerController.getFollowers);

module.exports = router;
