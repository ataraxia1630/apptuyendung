const { Router } = require('express');
const { JobTypeController } = require('../controllers/jobType.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { cache } = require('../middlewares/cache.middleware');

const route = new Router();

route.get('/all', verifyToken, cache, JobTypeController.getAll);

route.post('/', verifyToken, JobTypeController.createMany);

module.exports = route;
