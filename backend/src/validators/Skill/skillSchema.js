const Joi = require('joi');
const SkillSchema = Joi.object({
  skillName: Joi.string().required().min(3).max(255).messages({
    'string.base': 'Skill name must be a string',
    'string.empty': 'Skill name cannot be empty',
    'string.min': 'Skill name must be at least 3 characters long',
    'string.max': 'Skill name must be at most 255 characters long',
  }),
  certificate: Joi.string().required().messages({
    'string.base': 'Certificate must be a string',
    'string.empty': 'Certificate cannot be empty',
  }),
  expertiseLevel: Joi.string()
    .valid('BEGINNER', 'INTERMEDIATE', 'PROFESSIONAL')
    .messages({
      'string.base': 'Expertise level must be a string',
      'any.only':
        'Expertise level must be one of the following: BEGINNER, INTERMEDIATE, PROFESSIONAL',
    }),
  yearOfExp: Joi.number().integer().min(1).max(100).messages({
    'number.base': 'Year of experience must be a number',
    'number.integer': 'Year of experience must be an integer',
    'number.min':
      'Year of experience cannot be less than 1, u should use monthOfExp instead',
    'number.max': 'Year of experience cannot exceed 100',
  }),
  monthOfExp: Joi.number().integer().min(1).max(11).messages({
    'number.base': 'Month of experience must be a number',
    'number.integer': 'Month of experience must be an integer',
    'number.min': 'Month of experience cannot be less than 1',
    'number.max':
      'Month of experience cannot exceed 11, u should use yearOfExp instead',
  }),
});

module.exports = { SkillSchema };
