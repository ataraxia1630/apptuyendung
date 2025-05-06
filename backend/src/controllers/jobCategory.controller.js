const { JobCategoryService } = require('../services/jobCategory.service');
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
      if (!jobCategories || jobCategories.length === 0) {
        return res.status(400).json({
          message: 'No job categories provided',
        });
      }
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
