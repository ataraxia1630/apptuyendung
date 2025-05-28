const { supabase } = require('../config/db/supabase');

const AvatarHelper = {
  uploadAvatar: async (file, userId) => {
    const fileExt = file.originalname.split('.').pop();
    const fileName = `${userId}-${Date.now()}.${fileExt}`;
    const filePath = `${fileName}`;

    try {
      const { data, error } = await supabase.storage
        .from('avatar-storage')
        .upload(filePath, file.buffer, {
          contentType: file.mimetype,
          upsert: true,
        });

      if (error) {
        throw new Error('Upload failed: ' + error.message);
      }
      return filePath;
    } catch (error) {
      throw new Error('Upload failed (supabase): ' + error.message);
    }
  },

  getAvatarSignedUrl: async (filePath) => {
    try {
      console.log('Generating signed URL for file:', filePath);
      const { data, error } = await supabase.storage
        .from('avatar-storage')
        .createSignedUrl(filePath, 60 * 60);
      console.log('Signed URL:', data);
      if (error) {
        throw new Error('Generate signed URL failed: ' + error.message);
      }
      return data.signedUrl;
    } catch (error) {
      throw new Error('Generate signed URL failed: ' + error.message);
    }
  },

  deleteAvatar: async (filePath) => {
    try {
      const { error } = await supabase.storage
        .from('avatar-storage')
        .remove([filePath]);
      if (error) {
        throw new Error('Delete file failed: ' + error.message);
      }
      return true;
    } catch (error) {
      throw new Error('Delete file failed: ' + error.message);
    }
  },
};

module.exports = { AvatarHelper };
