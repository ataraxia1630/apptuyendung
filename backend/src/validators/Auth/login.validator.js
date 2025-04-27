const Joi = require('joi');

const LoginSchema = Joi.object({
  username: Joi.string().min(6).max(30).messages({
    'string.base': 'Username must be a string',
    'string.empty': 'Username cannot be empty',
    'string.min': 'Username must be at least 6 characters long',
    'string.max': 'Username must be at most 30 characters long',
  }),
  email: Joi.string().email().messages({
    'string.base': 'Email must be a string',
    'string.empty': 'Email cannot be empty',
    'string.email': 'Email must be a valid email address',
  }),
  password: Joi.string().min(8).max(30).required().messages({
    'string.base': 'Password must be a string',
    'any.required': 'Password is required',
    'string.empty': 'Password cannot be empty',
    'string.min': 'Password must be at least 8 characters long',
    'string.max': 'Password must be at most 30 characters long',
  }),
})
  .or('username', 'email') // Ít nhất một trong hai phải tồn tại
  .nand('username', 'email') // Không cho phép cả hai cùng tồn tại
  .messages({
    'object.missing': 'Either username or email must be provided.',
    'object.nand': 'Only one of username or email can be provided, not both.',
  });

module.exports = { LoginSchema };
