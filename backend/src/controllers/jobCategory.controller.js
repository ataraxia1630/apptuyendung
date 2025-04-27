const { JobCategoryService } = require('../services/jobType.service');

const JobCategoryController = {
  getAll: async (req, res) => {
    try {
      const categories = await JobCategoryService.getAll();
      return res.status(200).json(categories);
    } catch (error) {
      return res.status(500).json({
        message: 'Error fetching categories',
        error: error.message || error,
      });
    }
  },

  createMany: async (req, res) => {
    const { jobCategories } = req.body;
    try {
      const categories = await JobCategoryService.createMany(jobCategories);
      return res.status(201).json(categories);
    } catch (error) {
      return res.status(500).json({
        message: 'Error creating categories',
        error: error.message || error,
      });
    }
  },
};

module.exports = { JobCategoryController };
