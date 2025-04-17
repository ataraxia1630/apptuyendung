const Joi = require('joi');

const CompanySchema = Joi.object({
  name: Joi.string().max(255).required().messages({
    'string.base': 'Name must be a string',
    'any.required': 'Name is required',
    'string.empty': 'Name cannot be empty',
    'string.max': 'Name must be at most 255 characters long',
  }),
  description: Joi.string().max(1000).messages({
    'string.base': 'Description must be a string',
    'string.empty': 'Description cannot be empty',
    'string.max': 'Description must be at most 1000 characters long',
  }),
  taxcode: Joi.string().max(20).messages({
    'string.base': 'Tax Code must be a string',
    'string.empty': 'Tax Code cannot be empty',
    'string.max': 'Tax Code must be at most 20 characters long',
  }),
  establishedYear: Joi.number()
    .integer()
    .min(1800)
    .max(new Date().getFullYear())
    .messages({
      'number.base': 'Established Year must be a number',
      'number.integer': 'Established Year must be an integer',
      'number.min': 'Established Year must be at least 1800',
      'number.max': `Established Year must be at most ${new Date().getFullYear()}`,
    }),
});

module.exports = {
  CompanySchema,
};
