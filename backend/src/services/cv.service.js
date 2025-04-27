const prisma = require('../config/db/prismaClient');
const { CVHelper } = require('../helpers/cv.helper');
const { get } = require('../routes/auth.route');

const CVService = {
  uploadCV: async (applicantId, file, data) => {
    try {
      const filePath = await CVHelper.uploadCV(file, applicantId);
      const cv = await prisma.cv.create({
        data: {
          applicantId,
          filePath,
          ...data,
          viewCount: 0,
        },
      });
      return cv;
    } catch (error) {
      console.error('Error uploading file to Supabase:', error);
      throw new Error('File upload failed');
    }
  },

  getCV: async (id) => {
    try {
      const cv = await prisma.cv.findUnique({
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
      const cv = await getCV(id);
      const signedUrl = await CVHelper.getCVSignedUrl(cv.filePath);
      return signedUrl;
    } catch (error) {
      console.error('Error fetching CV:', error);
      throw new Error('CV fetch failed');
    }
  },

  getAllCV: async (applicantId) => {
    try {
      const cvs = await prisma.cv.findMany({
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
      await prisma.cv.deleteMany({
        where: { applicantId },
      });
    } catch (error) {
      console.error('Error deleting CVs:', error);
      throw new Error('CV delete failed');
    }
  },

  updateCV: async (id, data) => {
    try {
      const cv = await prisma.cv.update({
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
      await prisma.cv.delete({
        where: { id },
      });
    } catch (error) {
      console.error('Error deleting CV:', error);
      throw new Error('CV delete failed');
    }
  },
};

module.exports = { CVService };
