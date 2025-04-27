const Joi = require('joi');
const { achievement } = require('../../config/db/prismaClient');
const ApplicantEduSchema = new Joi.object({
  eduId: Joi.string().required().messages({
    'any.required': 'eduId is required',
    'string.base': 'Education ID must be a string',
    'string.empty': 'Education ID cannot be empty',
  }),
  edu_start: Joi.date().messages({
    'date.base': 'Start date must be a valid date',
    'date.empty': 'Start date cannot be empty',
  }),
  edu_end: Joi.date().messages({
    'date.base': 'End date must be a valid date',
    'date.empty': 'End date cannot be empty',
  }),
  major: Joi.string().required().max(255).messages({
    'any.required': 'major is required',
    'string.base': 'Major must be a string',
    'string.empty': 'Major cannot be empty',
    'string.max': 'Major must be at most 255 characters long',
  }),
  eduLevel: Joi.string()
    .required()
    .valid('BACHELOR', 'MASTER', 'DOCTOR')
    .messages({
      'any.required': 'eduLevel is required',
      'string.base': 'Education level must be a string',
      'string.empty': 'Education level cannot be empty',
      'any.only':
        'Education level must be one of the following: BACHELOR, MASTER, DOCTOR',
    }),
  moreInfo: Joi.string().max(1000).messages({
    'string.base': 'More info must be a string',
    'string.empty': 'More info cannot be empty',
    'string.max': 'More info must be at most 1000 characters long',
  }),
  achievement: Joi.array(),
});

module.exports = {
  ApplicantEduSchema,
};
