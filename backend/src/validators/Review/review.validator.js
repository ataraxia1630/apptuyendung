const Joi = require('joi');

const ReviewSchema = Joi.object({
    reviewed_userId: Joi.string().required().messages({
        'any.required': 'Reviewed user ID is required',
    }),
    detail: Joi.string().max(1000).required().messages({
        'any.required': 'Review detail is required',
        'string.max': 'Detail can be up to 1000 characters'
    }),
    rating: Joi.number().integer().min(1).max(5).required().messages({
        'any.required': 'Rating is required',
        'number.base': 'Rating must be a number',
        'number.min': 'Rating must be at least 1',
        'number.max': 'Rating must be at most 5'
    }),
});

module.exports = {
    ReviewSchema
};
