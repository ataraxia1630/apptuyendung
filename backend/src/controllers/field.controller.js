const { FieldService } = require('../services/field.service');
const FieldController = {
  getAllFields: async (req, res) => {
    try {
      const fields = await FieldService.getAllFields();
      return res.status(200).json({ fields: fields });
    } catch (error) {
      return res.status(500).json({
        message: 'Error fetching fields',
        error: error.message || error,
      });
    }
  },

  getField: async (req, res) => {
    const { name } = req.params;
    if (!name) {
      return res.status(400).json({
        message: 'Field name is required',
      });
    }
    try {
      const field = await FieldService.getField(name);
      return res.status(200).json({ field: field });
    } catch (error) {
      return res.status(500).json({
        message: 'Error retrieving field',
        error: error.message || error,
      });
    }
  },

  createField: async (req, res) => {
    const { name } = req.body;
    if (!name) {
      return res.status(400).json({
        message: 'Field name is required',
      });
    }
    try {
      const field = await FieldService.createField(name);
      return res.status(201).json({ field: field });
    } catch (error) {
      return res.status(500).json({
        message: 'Error creating new field',
        error: error.message || error,
      });
    }
  },

  updateField: async (req, res) => {
    const { id } = req.params;
    if (!id) {
      return res.status(400).json({
        message: 'Field ID is required',
      });
    }
    const { name } = req.body;
    try {
      const field = await FieldService.updateField(id, name);
      return res.status(200).json({ field: field });
    } catch (error) {
      return res.status(500).json({
        message: 'Error updating field',
        error: error.message || error,
      });
    }
  },

  getAllInterestedFields: async (req, res) => {
    const { applicantId } = req.params;
    if (!applicantId) {
      return res.status(400).json({
        message: 'Applicant ID is required',
      });
    }
    try {
      const fields = await FieldService.getAllInterestedFields(applicantId);
      return res.status(200).json({ fields: fields });
    } catch (error) {
      return res.status(500).json({
        message: 'Error retrieving interested fields',
        error: error.message || error,
      });
    }
  },

  addInterestedFields: async (req, res) => {
    const { applicantId } = req.params;
    if (!applicantId) {
      return res.status(400).json({
        message: 'Applicant ID is required',
      });
    }
    const { fieldIds } = req.body;
    try {
      const fields = await FieldService.addInterestedFields(
        applicantId,
        fieldIds
      );
      return res.status(201).json({ fields: fields });
    } catch (error) {
      return res.status(500).json({
        message: 'Error adding interested fields',
        error: error.message || error,
      });
    }
  },

  removeAllInterestedFields: async (req, res) => {
    const { applicantId } = req.params;
    if (!applicantId) {
      return res.status(400).json({
        message: 'Applicant ID is required',
      });
    }
    try {
      await FieldService.removeAllInterestedFields(applicantId);
      return res.status(204).send();
    } catch (error) {
      return res.status(500).json({
        message: 'Error removing interested fields',
        error: error.message || error,
      });
    }
  },

  removeInterestedField: async (req, res) => {
    const { applicantId, fieldId } = req.params;
    if (!applicantId || !fieldId) {
      return res.status(400).json({
        message: 'Applicant ID and Field ID are required',
      });
    }
    try {
      await FieldService.removeInterestedField(applicantId, fieldId);
      return res.status(204).send();
    } catch (error) {
      return res.status(500).json({
        message: 'Error removing interested field',
        error: error.message || error,
      });
    }
  },
};

module.exports = { FieldController };
