const Joi = require('joi');
const EduSchema = Joi.object({
  uniName: Joi.string().max(255).required().messages({
    'string.base': 'University name must be a string',
    'any.required': 'University name is required',
    'string.empty': 'University name cannot be empty',
    'string.max': 'University name must be at most 255 characters long',
  }),
  description: Joi.string().max(1000).messages({
    'string.base': 'Description must be a string',
    'string.empty': 'Description cannot be empty',
    'string.max': 'Description must be at most 1000 characters long',
  }),
  uniLink: Joi.string().uri().messages({
    'string.base': 'University link must be a string',
    'string.empty': 'University link cannot be empty',
    'string.uri': 'University link must be a valid URL',
  }),
  address: Joi.string().required().max(255).messages({
    'string.base': 'Address must be a string',
    'string.empty': 'Address cannot be empty',
    'string.max': 'Address must be at most 255 characters long',
  }),
});

module.exports = { EduSchema };
