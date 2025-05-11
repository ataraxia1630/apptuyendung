const Joi = require('joi');

const ApplySchema = Joi.object({
  jobpostId: Joi.string().required().messages({
    'string.base': 'Job post ID must be a string',
    'any.required': 'jobpostId is required',
    'string.empty': 'Job post ID cannot be empty',
  }),
  applicantId: Joi.string().required().messages({
    'string.base': 'Applicant ID must be a string',
    'any.required': 'applicantId is required',
    'string.empty': 'Applicant ID cannot be empty',
  }),
  cvId: Joi.string().required().messages({
    'string.base': 'CV ID must be a string',
    'any.required': 'cvId is required',
    'string.empty': 'CV ID cannot be empty',
  }),
});

module.exports = { ApplySchema };
