const { JobTypeService } = require('../services/jobType.service');

const JobTypeController = {
  getAll: async (req, res) => {
    try {
      const types = await JobTypeService.getAll();
      return res.status(200).json(types);
    } catch (error) {
      return res.status(500).json({
        message: 'Error fetching types',
        error: error.message || error,
      });
    }
  },

  createMany: async (req, res) => {
    const { jobTypes } = req.body;
    try {
      const types = await JobTypeService.createMany(jobTypes);
      return res.status(201).json(types);
    } catch (error) {
      return res.status(500).json({
        message: 'Error creating types',
        error: error.message || error,
      });
    }
  },
};

module.exports = { JobTypeController };
