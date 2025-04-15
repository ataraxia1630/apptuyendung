const Joi = require('joi');

function validate(schema) {
  return (req, res, next) => {
    const { error } = schema.validate(req.body, { abortEarly: false });

    // Nếu có lỗi, trả về danh sách lỗi
    if (error) {
      const errorMessages = error.details.map((err) => err.message);
      return res.status(400).json({ errors: errorMessages });
    }

    // Nếu không có lỗi, tiếp tục request
    next();
  };
}

module.exports = { validate };
