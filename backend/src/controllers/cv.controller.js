const { CVService } = require('../services/cv.service');

const CVController = {
  uploadCV: async (req, res) => {
    const { applicantId } = req.params;
    if (!applicantId) {
      return res.status(400).json({ message: 'Applicant ID is required' });
    }
    if (!req.file) {
      return res.status(400).json({ message: 'No file uploaded' });
    }
    console.log('File:', req.file);
    try {
      const cv = await CVService.uploadCV(applicantId, req.file, req.body);
      return res.status(201).json({ message: 'CV uploaded successfully', cv });
    } catch (error) {
      console.error('Error uploading CV:', error);
      return res
        .status(500)
        .json({ message: 'Internal server error', error: error.message });
    }
  },

  downloadCV: async (req, res) => {
    const { id } = req.params;
    if (!id) {
      return res.status(400).json({ message: 'CV ID is required' });
    }

    try {
      const signedUrl = await CVService.downloadCV(id);
      return res
        .status(200)
        .json({ message: 'CV fetched successfully', signedUrl });
    } catch (error) {
      console.error('Error fetching CV:', error);
      return res
        .status(500)
        .json({ message: 'Internal server error', error: error.message });
    }
  },

  getAllCV: async (req, res) => {
    const { applicantId } = req.params;
    if (!applicantId) {
      return res.status(400).json({ message: 'Applicant ID is required' });
    }

    try {
      const cvs = await CVService.getAllCV(applicantId);
      return res.status(200).json({ message: 'CVs fetched successfully', cvs });
    } catch (error) {
      console.error('Error fetching CVs:', error);
      return res
        .status(500)
        .json({ message: 'Internal server error', error: error.message });
    }
  },

  deleteAllCV: async (req, res) => {
    const { applicantId } = req.params;
    if (!applicantId) {
      return res.status(400).json({ message: 'Applicant ID is required' });
    }

    try {
      await CVService.deleteAllCV(applicantId);
      return res.status(204).json({ message: 'All CVs deleted successfully' });
    } catch (error) {
      console.error('Error deleting all CVs:', error);
      return res
        .status(500)
        .json({ message: 'Internal server error', error: error.message });
    }
  },

  getCV: async (req, res) => {
    const { id } = req.params;
    if (!id) {
      return res.status(400).json({ message: 'CV ID is required' });
    }

    try {
      const cv = await CVService.getCV(id);
      if (!cv) {
        return res.status(404).json({ message: 'CV not found' });
      }
      return res.status(200).json({ message: 'CV fetched successfully', cv });
    } catch (error) {
      console.error('Error fetching CV:', error);
      return res
        .status(500)
        .json({ message: 'Internal server error', error: error.message });
    }
  },

  updateCV: async (req, res) => {
    const { id } = req.params;
    if (!id) {
      return res.status(400).json({ message: 'CV ID is required' });
    }

    try {
      const cv = await CVService.updateCV(id, req.body);
      if (!cv) {
        return res.status(404).json({ message: 'CV not found' });
      }
      return res.status(200).json({ message: 'CV updated successfully', cv });
    } catch (error) {
      console.error('Error updating CV:', error);
      return res
        .status(500)
        .json({ message: 'Internal server error', error: error.message });
    }
  },

  deleteCV: async (req, res) => {
    const { id } = req.params;
    if (!id) {
      return res.status(400).json({ message: 'CV ID is required' });
    }

    try {
      await CVService.deleteCV(id);
      return res.status(204).json({ message: 'CV deleted successfully' });
    } catch (error) {
      console.error('Error deleting CV:', error);
      return res
        .status(500)
        .json({ message: 'Internal server error', error: error.message });
    }
  },
};

module.exports = { CVController };
