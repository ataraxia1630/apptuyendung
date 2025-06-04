const Joi = require('joi');

const JobPostSchema = Joi.object({
    companyId: Joi.string().required().messages({
        'any.required': 'Company ID is required',
        'string.base': 'Company ID must be a string',
        'string.empty': 'Company ID cannot be empty',
    }),
    jobCategory: Joi.string().required().messages({
        'any.required': 'Job Category ID is required',
        'string.base': 'Job Category ID must be a string',
        'string.empty': 'Job Category ID cannot be empty',
    }),
    jobType: Joi.string().required().messages({
        'any.required': 'Job Type ID is required',
        'string.base': 'Job Type ID must be a string',
        'string.empty': 'Job Type ID cannot be empty',
    }),
    title: Joi.string().max(255).required().messages({
        'any.required': 'Job title is required',
        'string.base': 'Title must be a string',
        'string.empty': 'Title cannot be empty',
        'string.max': 'Title must be at most 255 characters long',
    }),
    description: Joi.string().required().messages({
        'any.required': 'Description is required',
        'string.base': 'Description must be a string',
        'string.empty': 'Description cannot be empty',
    }),
    location: Joi.string().max(255).messages({
        'string.base': 'Location must be a string',
        'string.max': 'Location must be at most 255 characters long',
        'string.empty': 'Location cannot be empty',
    }),
    position: Joi.string().max(255).messages({
        'string.base': 'Position must be a string',
        'string.max': 'Position must be at most 255 characters long',
        'string.empty': 'Location cannot be empty',
    }),
    workingAddress: Joi.string().max(500).messages({
        'string.base': 'Working address must be a string',
        'string.max': 'Working address must be at most 500 characters long',
        'string.empty': 'Location cannot be empty',
    }),
    educationRequirement: Joi.string().max(1000).messages({
        'string.base': 'Education requirement must be a string',
        'string.max': 'Education requirement must be at most 1000 characters long',
        'string.empty': 'Location cannot be empty',
    }),
    skillRequirement: Joi.string().max(1000).messages({
        'string.base': 'Skill requirement must be a string',
        'string.max': 'Skill requirement must be at most 1000 characters long',
        'string.empty': 'Location cannot be empty',
    }),
    responsibility: Joi.string().max(2000).messages({
        'string.base': 'Responsibility must be a string',
        'string.max': 'Responsibility must be at most 2000 characters long',
        'string.empty': 'Location cannot be empty',
    }),
    salary_start: Joi.string().pattern(/^\d+$/).messages({
        'string.base': 'Salary start must be a string',
        'string.pattern.base': 'Salary start must be a number in string format',
        'string.empty': 'Location cannot be empty',
    }),
    salary_end: Joi.string().pattern(/^\d+$/).messages({
        'string.base': 'Salary end must be a string',
        'string.pattern.base': 'Salary end must be a number in string format',
        'string.empty': 'Location cannot be empty',
    }),
    currency: Joi.string().valid('USD', 'VND', 'EUR').messages({
        'string.base': 'Currency must be a string',
        'any.only': 'Currency must be one of: USD, VND, EUR',
        'string.empty': 'Location cannot be empty',
    }),
    status: Joi.string().valid('OPENING', 'CLOSED').messages({
        'string.base': 'Status must be a string',
        'any.only': 'Status must be either OPENING or CLOSED',
        'string.empty': 'Location cannot be empty',
    }),
    apply_until: Joi.string()
        .pattern(/^([0-2][0-9]|(3)[0-1])\-([0][1-9]|1[0-2])\-\d{4}$/)
        .required()
        .messages({
            'any.required': 'Apply until is required',
            'string.empty': 'Apply until cannot be empty',
            'string.pattern.base': 'Apply until must be in format dd/mm/yyyy',
        })
});

module.exports = {
    JobPostSchema,
};
