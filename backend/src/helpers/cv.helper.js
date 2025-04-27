const { supabase } = require('../config/db/supabase');

const CVHelper = {
  uploadCV: async (file, applicantId) => {
    const fileExt = file.name.split('.').pop();
    const fileName = `${applicantId}-${Date.now()}.${fileExt}`;
    const filePath = `applicant-cv/${fileName}`;

    try {
      await supabase.storage.from('cv-storage').upload(filePath, file);
      return filePath;
    } catch (error) {
      throw new Error('Upload failed (supabase): ' + error.message);
    }
  },

  getCVSignedUrl: async (filePath) => {
    try {
      const { data } = await supabase.storage
        .from('cv-storage')
        .createSignedUrl(filePath, 60 * 60); // URL có hiệu lực 1 giờ

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
