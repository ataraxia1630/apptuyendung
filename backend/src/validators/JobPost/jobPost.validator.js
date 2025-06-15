const Joi = require('joi');

const JobPostSchema = Joi.object({
    companyId: Joi.string().required().messages({
        'any.required': 'Company ID is required',
        'string.base': 'Company ID must be a string',
        'string.empty': 'Company ID cannot be empty',
    }),
    jobCategoryId: Joi.string().optional().allow(null, '').messages({
        'string.base': 'Job Category ID must be a string',
    }),
    jobTypeId: Joi.string().required().messages({
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
    description: Joi.string().max(1000).allow(null, '').messages({
        'string.base': 'Description must be a string',
        'string.max': 'Description must be at most 1000 characters long',
    }),
    location: Joi.string().max(255).messages({
        'string.base': 'Location must be a string',
        'string.max': 'Location must be at most 255 characters long',
    }),
    position: Joi.string().max(255).messages({
        'string.base': 'Position must be a string',
        'string.max': 'Position must be at most 255 characters long',
    }),
    workingAddress: Joi.string().allow(null, '').messages({
        'string.base': 'Working address must be a string',
    }),
    educationRequirement: Joi.string().max(1000).messages({
        'string.base': 'Education requirement must be a string',
        'string.max': 'Education requirement must be at most 1000 characters long',
    }),
    skillRequirement: Joi.string().max(1000).messages({
        'string.base': 'Skill requirement must be a string',
        'string.max': 'Skill requirement must be at most 1000 characters long',
    }),
    responsibility: Joi.string().max(2000).messages({
        'string.base': 'Responsibility must be a string',
        'string.max': 'Responsibility must be at most 2000 characters long',
    }),
    salary_start: Joi.number().precision(2).messages({
        'number.base': 'Salary start must be a number',
    }),
    salary_end: Joi.number().precision(2).messages({
        'number.base': 'Salary end must be a number',
    }),
    currency: Joi.string().valid('USD', 'VND', 'EUR').messages({
        'string.base': 'Currency must be a string',
        'any.only': 'Currency must be one of: USD, VND, EUR',
    }),
    apply_until: Joi.string()
        .pattern(/^([0-2][0-9]|(3)[0-1])\-([0][1-9]|1[0-2])\-\d{4}$/)
        .required()
        .messages({
            'any.required': 'Apply until is required',
            'string.empty': 'Apply until cannot be empty',
            'string.pattern.base': 'Apply until must be in format dd-mm-yyyy',
        }),
});

module.exports = {
    JobPostSchema,
};
