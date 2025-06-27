const express = require('express');
const router = express.Router();
const { ReactionController } = require('../controllers/reaction.controller');
const { verifyToken } = require('../middlewares/auth.middleware');

router.post('/toggle', verifyToken, ReactionController.toggleReaction);

module.exports = router;