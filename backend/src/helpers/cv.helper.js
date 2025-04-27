const { supabase } = require('../config/db/supabase');

const CVHelper = {
  uploadCV: async (file, applicantId) => {
    const fileExt = file.originalname.split('.').pop();
    const fileName = `${applicantId}-${Date.now()}.${fileExt}`;
    const filePath = `applicant-cv/${fileName}`;

    try {
      await supabase.storage.from('cv-storage').upload(filePath, file.buffer, {
        contentType: file.mimetype,
        upsert: true,
      });
      return filePath;
    } catch (error) {
      throw new Error('Upload failed (supabase): ' + error.message);
    }
  },

  getCVSignedUrl: async (filePath) => {
    try {
      console.log('Generating signed URL for file:', filePath);
      const { data, error } = await supabase.storage
        .from('cv-storage')
        .createSignedUrl(filePath, 60 * 60); // 1 hour expiration
      console.log('Signed URL:', data);
      if (error) {
        throw new Error('Generate signed URL failed: ' + error.message);
      }
      return data.signedUrl;
    } catch (error) {
      throw new Error('Generate signed URL failed: ' + error.message);
    }
  },
};

module.exports = { CVHelper };
