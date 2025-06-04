const Joi = require('joi');

const JobPostUpdateSchema = Joi.object({
    title: Joi.alternatives().try(Joi.string().min(1), Joi.valid(null)),
    description: Joi.alternatives().try(Joi.string().min(1), Joi.valid(null)),
    location: Joi.alternatives().try(Joi.string().min(1), Joi.valid(null)),
    position: Joi.alternatives().try(Joi.string().min(1), Joi.valid(null)),
    workingAddress: Joi.alternatives().try(Joi.string().min(1), Joi.valid(null)),
    educationRequirement: Joi.alternatives().try(Joi.string().min(1), Joi.valid(null)),
    skillRequirement: Joi.alternatives().try(Joi.string().min(1), Joi.valid(null)),
    responsibility: Joi.alternatives().try(Joi.string().min(1), Joi.valid(null)),
    salary_start: Joi.alternatives().try(Joi.string().pattern(/^\d+$/), Joi.valid(null)),
    salary_end: Joi.alternatives().try(Joi.string().pattern(/^\d+$/), Joi.valid(null)),
    currency: Joi.alternatives().try(Joi.string().valid('USD', 'VND', 'EUR'), Joi.valid(null)),
    status: Joi.alternatives().try(Joi.string().valid('OPENING', 'CLOSED'), Joi.valid(null)),
    apply_until: Joi.alternatives().try(
        Joi.date(),
        Joi.string().pattern(/^([0-2][0-9]|(3)[0-1])\-([0][1-9]|1[0-2])\-\d{4}$/),
        Joi.valid(null)
    ),
    jobCategory: Joi.alternatives().try(Joi.string().min(1), Joi.valid(null)),
    jobType: Joi.alternatives().try(Joi.string().min(1), Joi.valid(null)),
});

module.exports = {
    JobPostUpdateSchema,
};
