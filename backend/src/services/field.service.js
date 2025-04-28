const prisma = require('../config/db/prismaClient');

const FieldService = {
  getAllFields: async () => {
    try {
      const fields = await prisma.field.findMany();
      return fields;
    } catch (error) {
      throw new Error('Error retrieving fields (service): ' + error.message);
    }
  },

  getFieldByName: async (fieldName) => {
    try {
      const field = await prisma.field.findUnique({
        where: { fieldName },
      });
      return field;
    } catch (error) {
      throw new Error('Error retrieving field (service): ' + error.message);
    }
  },

  createField: async (fieldName) => {
    try {
      const field = await prisma.field.create({
        data: { fieldName },
      });
      return field;
    } catch (error) {
      throw new Error('Error creating field (service): ' + error.message);
    }
  },

  updateField: async (id, fieldName) => {
    try {
      const field = await prisma.field.update({
        where: { id },
        data: { fieldName },
      });
      return field;
    } catch (error) {
      throw new Error('Error updating field (service): ' + error.message);
    }
  },

  getAllInterestedFields: async (applicantId) => {
    try {
      const fields = await prisma.interestedField.findMany({
        where: { applicantId },
        include: { field: true },
      });
      return fields.map((item) => item.field);
    } catch (error) {
      throw new Error(
        'Error retrieving interested fields (service): ' + error.message
      );
    }
  },

  addInterestedFields: async (applicantId, fieldIds) => {
    try {
      const interestedFields = await prisma.interestedField.createMany({
        data: fieldIds.map((fieldId) => ({ applicantId, fieldId })),
        skipDuplicates: true,
      });
      return interestedFields;
    } catch (error) {
      throw new Error(
        'Error adding interested fields (service): ' + error.message
      );
    }
  },

  removeAllInterestedFields: async (applicantId) => {
    try {
      await prisma.interestedField.deleteMany({
        where: { applicantId },
      });
    } catch (error) {
      throw new Error(
        'Error removing all interested fields (service): ' + error.message
      );
    }
  },

  removeInterestedField: async (applicantId, fieldId) => {
    try {
      await prisma.interestedField.deleteMany({
        where: { applicantId, fieldId },
      });
    } catch (error) {
      throw new Error(
        'Error removing interested field (service): ' + error.message
      );
    }
  },
};

module.exports = { FieldService };
