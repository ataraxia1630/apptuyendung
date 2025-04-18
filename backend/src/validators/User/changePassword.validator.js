const Joi = require('joi');

const ChangePasswordSchema = Joi.object({
  oldPassword: Joi.string().min(8).max(30).required().messages({
    'string.base': 'Old Password must be a string',
    'any.required': 'Old Password is required',
    'string.empty': 'Old Password cannot be empty',
    'string.min': 'Old Password must be at least 8 characters long',
    'string.max': 'Old Password must be at most 30 characters long',
  }),
  newPassword: Joi.string().min(8).max(30).required().messages({
    'string.base': 'New Password must be a string',
    'any.required': 'New Password is required',
    'string.empty': 'New Password cannot be empty',
    'string.min': 'New Password must be at least 8 characters long',
    'string.max': 'New Password must be at most 30 characters long',
  }),
  confirmPassword: Joi.string()
    .valid(Joi.ref('newPassword'))
    .required()
    .messages({
      'string.base': 'Confirm Password must be a string',
      'any.required': 'Confirm Password is required',
      'string.empty': 'Confirm Password cannot be empty',
      'any.only': 'Confirm Password must match New Password',
    }),
});

module.exports = { ChangePasswordSchema };
