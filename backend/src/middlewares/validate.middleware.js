// validateMiddleware.js
const Joi = require('joi');

const validate = (schema, options = {}) => {
  return (req, res, next) => {
    const validationOptions = {
      abortEarly: false,
      stripUnknown: true,
      ...options,
    };

    const { error, value } = schema.validate(req.body, validationOptions);

    if (error) {
      const messages = error.details.map((err) => err.message);
      return res.status(400).json({ errors: messages });
    }

    req.body = value; // Dữ liệu đã được validate + clean
    next();
  };
};

module.exports = { validate };
