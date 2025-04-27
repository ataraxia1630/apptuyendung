const multer = require('multer');

const storage = multer.memoryStorage();

const upload = multer({ storage });

const uploadFile = upload.single('file');

module.exports = { uploadFile };
