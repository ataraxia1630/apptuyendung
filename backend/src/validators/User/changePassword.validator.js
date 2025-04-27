const Joi = require('joi');

const SettingSchema = Joi.object({
  email: Joi.string().email().optional().messages({
    'string.base': 'Email must be a string',
    'string.empty': 'Email cannot be empty',
    'string.email': 'Email must be a valid email address',
  }),
  username: Joi.string().min(6).max(30).optional().messages({
    'string.base': 'Username must be a string',
    'any.required': 'Username is required',
    'string.empty': 'Username cannot be empty',
    'string.min': 'Username must be at least 6 characters long',
    'string.max': 'Username must be at most 30 characters long',
  }),
  phoneNumber: Joi.string()
    .optional()
    .pattern(/^(0|\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-9]|9[0-9])[0-9]{7}$/)
    .messages({
      'string.base': 'Phone Number must be a string',
      'string.empty': 'Phone Number cannot be empty',
      'string.pattern.base': 'Phone Number must be a valid 10-digit number',
    }),
  oldPassword: Joi.string().min(8).max(30).optional().messages({
    'string.base': 'Old Password must be a string',
    'any.required': 'Old Password is required',
    'string.empty': 'Old Password cannot be empty',
    'string.min': 'Old Password must be at least 8 characters long',
    'string.max': 'Old Password must be at most 30 characters long',
  }),
  newPassword: Joi.string().min(8).max(30).optional().messages({
    'string.base': 'New Password must be a string',
    'any.required': 'New Password is required',
    'string.empty': 'New Password cannot be empty',
    'string.min': 'New Password must be at least 8 characters long',
    'string.max': 'New Password must be at most 30 characters long',
  }),
  confirmPassword: Joi.string()
    .valid(Joi.ref('newPassword'))
    .optional()
    .messages({
      'string.base': 'Confirm Password must be a string',
      'any.required': 'Confirm Password is required',
      'string.empty': 'Confirm Password cannot be empty',
      'any.only': 'Confirm Password must match New Password',
    }),
})
  .and('oldPassword', 'newPassword', 'confirmPassword') // Nếu một trường tồn tại, tất cả phải tồn tại
  .messages({
    'object.and':
      'If one of oldPassword, newPassword, or confirmPassword is provided, all three must be provided.',
  });

module.exports = { SettingSchema };
