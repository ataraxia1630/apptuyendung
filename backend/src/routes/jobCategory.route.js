const { Router } = require('express');
const {
  JobCategoryController,
} = require('../controllers/jobCategory.controller');
const { verifyToken } = require('../middlewares/auth.middleware');

const route = new Router();

route.get('/all', verifyToken, JobCategoryController.getAll);

route.post('/', verifyToken, JobCategoryController.createMany);

module.exports = route;
