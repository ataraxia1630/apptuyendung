const Joi = require('joi');

const ApplicantSchema = Joi.object({
  firstName: Joi.string().max(30).required().messages({
    'string.base': 'First Name must be a string',
    'any.required': 'First Name is required',
    'string.empty': 'First Name cannot be empty',
    'string.max': 'First Name must be at most 30 characters long',
  }),
  lastName: Joi.string().max(30).required().messages({
    'string.base': 'Last Name must be a string',
    'any.required': 'Last Name is required',
    'string.empty': 'Last Name cannot be empty',
    'string.max': 'Last Name must be at most 30 characters long',
  }),
  profileSummary: Joi.string().max(500).messages({
    'string.base': 'Profile Summary must be a string',
    'string.empty': 'Profile Summary cannot be empty',
    'string.max': 'Profile Summary must be at most 500 characters long',
  }),
  address: Joi.string().max(100).messages({
    'string.base': 'Address must be a string',
    'string.empty': 'Address cannot be empty',
    'string.max': 'Address must be at most 100 characters long',
  }),
});

module.exports = {
  ApplicantSchema,
};
