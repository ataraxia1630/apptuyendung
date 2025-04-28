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

  getField: async (name) => {
    try {
      let field = await prisma.field.findFirst({
        where: { name },
      });
      if (!field) {
        field = await prisma.field.findFirst({
          where: { id: name },
        });
      }
      if (!field) {
        throw new Error('Field not found');
      }
      return field;
    } catch (error) {
      throw new Error('Error retrieving field (service): ' + error.message);
    }
  },

  createField: async (name) => {
    try {
      const existingField = await prisma.field.findFirst({
        where: { name },
      });
      if (existingField) {
        throw new Error('Field already exists');
      }
      const field = await prisma.field.create({
        data: { name },
      });
      return field;
    } catch (error) {
      throw new Error('Error creating field (service): ' + error.message);
    }
  },

  updateField: async (id, name) => {
    try {
      const existingField = await prisma.field.findFirst({
        where: { id },
      });
      if (!existingField) {
        throw new Error('Field not found');
      }
      const field = await prisma.field.update({
        where: { id },
        data: { name },
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
      await prisma.interestedField.deleteMany({
        where: { applicantId, fieldId: { notIn: fieldIds } },
      });

      await prisma.interestedField.createMany({
        data: fieldIds.map((fieldId) => ({ applicantId, fieldId })),
        skipDuplicates: true,
      });

      const interestedFields = await prisma.interestedField.findMany({
        where: { applicantId },
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
