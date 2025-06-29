const Joi = require('joi');

const JobPostUpdateSchema = Joi.object({
    title: Joi.string().max(255).allow(null, '').messages({
        'string.base': 'Title must be a string',
        'string.max': 'Title must be at most 255 characters long',
    }),
    description: Joi.string().max(1000).allow(null, '').messages({
        'string.base': 'Description must be a string',
        'string.max': 'Description must be at most 1000 characters long',
    }),
    location: Joi.string().max(255).allow(null, '').messages({
        'string.base': 'Location must be a string',
        'string.max': 'Location must be at most 255 characters long',
    }),
    position: Joi.string().max(255).allow(null, '').messages({
        'string.base': 'Position must be a string',
        'string.max': 'Position must be at most 255 characters long',
    }),
    workingAddress: Joi.string().allow(null, '').messages({
        'string.base': 'Working address must be a string',
    }),
    educationRequirement: Joi.string().max(1000).allow(null, '').messages({
        'string.base': 'Education requirement must be a string',
        'string.max': 'Education requirement must be at most 1000 characters long',
    }),
    skillRequirement: Joi.string().max(1000).allow(null, '').messages({
        'string.base': 'Skill requirement must be a string',
        'string.max': 'Skill requirement must be at most 1000 characters long',
    }),
    responsibility: Joi.string().max(2000).allow(null, '').messages({
        'string.base': 'Responsibility must be a string',
        'string.max': 'Responsibility must be at most 2000 characters long',
    }),
    salary_start: Joi.number().precision(2).allow(null).messages({
        'number.base': 'Salary start must be a number',
    }),
    salary_end: Joi.number().precision(2).allow(null).messages({
        'number.base': 'Salary end must be a number',
    }),
    currency: Joi.string().valid('USD', 'VND', 'EUR').allow(null).messages({
        'string.base': 'Currency must be a string',
        'any.only': 'Currency must be one of: USD, VND, EUR',
    }),
    apply_until: Joi.string()
        .allow(null, '')
        .pattern(/^([0-2][0-9]|(3)[0-1])\-([0][1-9]|1[0-2])\-\d{4}$/)
        .custom((value, helpers) => {
            if (!value) return value; // cho phép null hoặc ''

            const [day, month, year] = value.split('-');
            const applyDate = new Date(`${year}-${month}-${day}T00:00:00Z`);
            const today = new Date();
            today.setHours(0, 0, 0, 0);

            if (applyDate < today) {
                return helpers.message('Apply until must be today or a future date');
            }

            return value;
        })
        .messages({
            'string.pattern.base': 'Apply until must be in format dd-mm-yyyy',
        }),

    jobCategory: Joi.string().allow(null, '').messages({
        'string.base': 'Job Category ID must be a string',
    }),
    jobType: Joi.string().allow(null, '').messages({
        'string.base': 'Job Type ID must be a string',
    }),
});

module.exports = {
    JobPostUpdateSchema,
};
