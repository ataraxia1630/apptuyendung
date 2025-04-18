const Joi = require('joi');

const UserSchema = Joi.object({
  email: Joi.string().email().optional().messages({
    'string.base': 'Email must be a string',
    'string.empty': 'Email cannot be empty',
    'string.email': 'Email must be a valid email address',
  }),
  phoneNumber: Joi.string()
    .optional()
    .pattern(/^(0|\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-9]|9[0-9])[0-9]{7}$/)
    .messages({
      'string.base': 'Phone Number must be a string',
      'string.empty': 'Phone Number cannot be empty',
      'string.pattern.base': 'Phone Number must be a valid 10-digit number',
    }),

  avatar: Joi.string().optional,
  background: Joi.string().optional,
});

module.exports = { UserSchema };
