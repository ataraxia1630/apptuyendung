const { supabase } = require('../config/db/supabase');
const { v4: uuidv4 } = require('uuid');

const PostImageHelper = {
    uploadImage: async (file, postId, userId) => {
        const fileExt = file.originalname.split('.').pop();
        const fileName = `${userId}-${postId}-${uuidv4()}.${fileExt}`;
        const filePath = `${fileName}`;

        try {
            const { data, error } = await supabase.storage
                .from('post-storage')
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

    getImageSignedUrl: async (filePath) => {
        try {
            const { data, error } = await supabase.storage
                .from('post-storage')
                .createSignedUrl(filePath, 60 * 60);
            if (error) {
                throw new Error('Generate signed URL failed: ' + error.message);
            }
            return data.signedUrl;
        } catch (error) {
            throw new Error('Generate signed URL failed: ' + error.message);
        }
    },

    deleteImage: async (filePath) => {
        try {
            const { error } = await supabase.storage
                .from('post-storage')
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

module.exports = { PostImageHelper };
