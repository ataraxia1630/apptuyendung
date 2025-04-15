const Joi = require('joi');

const UserSchema = Joi.object({
  username: Joi.string().min(6).max(30).required().message({
    'string.base': 'Username must be a string',
    'any.required': 'Username is required',
    'string.empty': 'Username cannot be empty',
    'string.min': 'Username must be at least 6 characters long',
    'string.max': 'Username must be at most 30 characters long',
  }),
  email: Joi.string().email().required().message({
    'string.base': 'Email must be a string',
    'any.required': 'Email is required',
    'string.empty': 'Email cannot be empty',
    'string.email': 'Email must be a valid email address',
  }),
  password: Joi.string().min(8).max(30).required().message({
    'string.base': 'Password must be a string',
    'any.required': 'Password is required',
    'string.empty': 'Password cannot be empty',
    'string.min': 'Password must be at least 8 characters long',
    'string.max': 'Password must be at most 30 characters long',
  }),
  confirmPassword: Joi.string().valid(Joi.ref('password')).required().message({
    'string.base': 'Confirm Password must be a string',
    'any.required': 'Confirm Password is required',
    'string.empty': 'Confirm Password cannot be empty',
    'any.only': 'Confirm Password must match Password',
  }),
  phoneNumber: Joi.string()
    .pattern(/^(0|\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-9]|9[0-9])[0-9]{7}$/)
    .message({
      'string.base': 'Phone Number must be a string',
      'string.empty': 'Phone Number cannot be empty',
      'string.pattern.base': 'Phone Number must be a valid 10-digit number',
    }),
  avatar: Joi.string(),
  background: Joi.string(),
  role: Joi.string().valid('APPLICANT', 'COMPANY').required().message({
    'string.base': 'Role must be a string',
    'any.required': 'Role is required',
    'string.empty': 'Role cannot be empty',
    'any.only': 'Role must be either APPLICANT or COMPANY',
  }),
});

module.exports = { UserSchema };
