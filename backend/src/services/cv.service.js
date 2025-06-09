const prisma = require('../config/db/prismaClient');
const { CVHelper } = require('../helpers/cv.helper');

const CVService = {
  uploadCV: async (applicantId, file, data) => {
    try {
      const { filePath, size } = await CVHelper.uploadCV(file, applicantId);
      console.log('File uploaded to Supabase:', filePath);
      const cv = await prisma.cV.create({
        data: { ...data, filePath, size, applicantId, viewCount: 0 },
      });
      return cv;
    } catch (error) {
      console.error('Error uploading file to Supabase:', error);
      throw new Error('File upload failed');
    }
  },

  getCV: async (id) => {
    try {
      const cv = await prisma.cV.findUnique({
        where: { id },
      });
      if (!cv) {
        throw new Error('CV not found');
      }
      return cv;
    } catch (error) {
      console.error('Error fetching CV:', error);
      throw new Error('CV fetch failed');
    }
  },

  downloadCV: async (id) => {
    try {
      const cv = await prisma.cV.findUnique({
        where: { id },
      });
      if (!cv) {
        throw new Error('CV not found');
      }
      const signedUrl = await CVHelper.getCVSignedUrl(cv.filePath);
      return signedUrl;
    } catch (error) {
      console.error('Error fetching CV:', error);
      throw new Error('CV fetch failed');
    }
  },

  getAllCV: async (applicantId) => {
    try {
      const cvs = await prisma.cV.findMany({
        where: { applicantId },
      });
      return cvs;
    } catch (error) {
      console.error('Error fetching CVs:', error);
      throw new Error('CV fetch failed');
    }
  },

  deleteAllCV: async (applicantId) => {
    try {
      const files = await prisma.cV.findMany({
        where: { applicantId },
        select: { filePath: true },
      });

      for (const file of files) {
        await CVHelper.deleteCV(file.filePath);
      }

      await prisma.cV.deleteMany({
        where: { applicantId },
      });
    } catch (error) {
      console.error('Error deleting CVs:', error);
      throw new Error('CV delete failed');
    }
  },

  updateCV: async (id, data) => {
    try {
      const cv = await prisma.cV.update({
        where: { id },
        data,
      });
      return cv;
    } catch (error) {
      console.error('Error updating CV:', error);
      throw new Error('CV update failed');
    }
  },

  deleteCV: async (id) => {
    try {
      const cv = await prisma.cV.findUnique({
        where: { id },
      });

      if (!cv) {
        throw new Error('CV not found');
      }

      await CVHelper.deleteCV(cv.filePath);

      await prisma.cV.delete({
        where: { id },
      });
    } catch (error) {
      console.error('Error deleting CV:', error);
      throw new Error('CV delete failed');
    }
  },
};

module.exports = { CVService };
