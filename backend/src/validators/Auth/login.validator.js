const Joi = require('joi');

const LoginSchema = Joi.object({
  username: Joi.string().min(6).max(300).messages({
    'string.base': 'Username must be a string',
    'string.empty': 'Username cannot be empty',
    'string.min': 'Username must be at least 6 characters long',
    'string.max': 'Username must be at most 300 characters long',
  }),
  password: Joi.string().min(8).max(30).required().messages({
    'string.base': 'Password must be a string',
    'any.required': 'Password is required',
    'string.empty': 'Password cannot be empty',
    'string.min': 'Password must be at least 8 characters long',
    'string.max': 'Password must be at most 30 characters long',
  }),
});

module.exports = { LoginSchema };
