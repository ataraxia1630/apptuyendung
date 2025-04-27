const Joi = require('joi');

const ExpSchema = Joi.object({
  companyName: Joi.string().required().messages({
    'string.empty': 'Company cannot be empty',
    'any.required': 'companyName is required',
  }),
  companyLink: Joi.string().uri().optional().messages({
    'string.uri': 'Company link must be a valid URL',
  }),
  position: Joi.string().required().max(255).messages({
    'string.empty': 'Position cannot be empty',
    'string.base': 'Position must be a string',
    'any.required': 'position is required',
    'string.max': 'Position must be less than 255 characters',
  }),
  work_start: Joi.date().optional().messages({
    'date.base': 'Start date must be a valid date',
  }),
  work_end: Joi.date().optional().greater(Joi.ref('work_start')).messages({
    'date.base': 'End date must be a valid date',
    'date.greater': 'End date must be greater than start date',
  }),
  jobResponsibility: Joi.string().required().messages({
    'string.empty': 'Job responsibility cannot be empty',
    'any.required': 'jobResponsibility is required',
  }),
  moreInfo: Joi.string().optional().messages({
    'string.base': 'More info must be a string',
  }),
});

module.exports = {
  ExpSchema,
};
