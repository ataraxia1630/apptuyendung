const { Router } = require('express');
const { JobTypeController } = require('../controllers/jobType.controller');
const { verifyToken } = require('../middlewares/auth.middleware');

const route = new Router();

route.get('/all', verifyToken, JobTypeController.getAll);

route.post('/', verifyToken, JobTypeController.createMany);

module.exports = route;
