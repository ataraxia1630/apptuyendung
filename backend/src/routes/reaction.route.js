const express = require('express');
const router = express.Router();
const { ReactionController } = require('../controllers/reaction.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { checkOwnership } = require('../middlewares/checkOwnership.middleware');

router.post('/toggle', verifyToken, ReactionController.toggleReaction);
router.delete('/:postId', verifyToken, checkOwnership('Reaction', 'userId'), ReactionController.removeReaction);

module.exports = router;
