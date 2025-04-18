const { FieldService } = require('../services/field.service');
const FieldController = {
  getAllFields: async (req, res) => {
    try {
      const fields = await FieldService.getAllFields();
      return res.status(200).json(fields);
    } catch (error) {
      return res.status(500).json({
        message: 'Error fetching fields',
        error: error.message || error,
      });
    }
  },

  getFieldByName: async (req, res) => {
    const { fieldName } = req.params;
    try {
      const field = await FieldService.getFieldByName(fieldName);
      if (!field) {
        return res.status(404).json({ message: 'Field not found' });
      }
      return res.status(200).json(field);
    } catch (error) {
      return res.status(500).json({
        message: 'Error retrieving field',
        error: error.message || error,
      });
    }
  },

  createField: async (req, res) => {
    const { fieldName } = req.body;
    try {
      const field = await FieldService.createField(fieldName);
      return res.status(201).json(field);
    } catch (error) {
      return res.status(500).json({
        message: 'Error creating new field',
        error: error.message || error,
      });
    }
  },

  updateField: async (req, res) => {
    const { id } = req.params;
    const { fieldName } = req.body;
    try {
      const field = await FieldService.updateField(id, fieldName);
      if (!field) {
        return res.status(404).json({ message: 'Field not found' });
      }
      return res.status(200).json(field);
    } catch (error) {
      return res.status(500).json({
        message: 'Error updating field',
        error: error.message || error,
      });
    }
  },

  getAllInterestedFields: async (req, res) => {
    const { applicantId } = req.params;
    try {
      const fields = await FieldService.getAllInterestedFields(applicantId);
      return res.status(200).json(fields);
    } catch (error) {
      return res.status(500).json({
        message: 'Error retrieving interested fields',
        error: error.message || error,
      });
    }
  },

  addInterestedFields: async (req, res) => {
    const { applicantId } = req.params;
    const { fieldIds } = req.body;
    try {
      const fields = await FieldService.addInterestedFields(
        applicantId,
        fieldIds
      );
      return res.status(200).json(fields);
    } catch (error) {
      return res.status(500).json({
        message: 'Error adding interested fields',
        error: error.message || error,
      });
    }
  },

  removeAllInterestedFields: async (req, res) => {
    const { applicantId } = req.params;
    try {
      await FieldService.removeAllInterestedFields(applicantId);
      return res.status(200).json({ message: 'All interested fields removed' });
    } catch (error) {
      return res.status(500).json({
        message: 'Error removing interested fields',
        error: error.message || error,
      });
    }
  },

  removeInterestedField: async (req, res) => {
    const { applicantId, fieldId } = req.params;
    try {
      await FieldService.removeInterestedField(applicantId, fieldId);
      return res.status(200).json({ message: 'Interested field removed' });
    } catch (error) {
      return res.status(500).json({
        message: 'Error removing interested field',
        error: error.message || error,
      });
    }
  },
};

module.exports = { FieldController };
