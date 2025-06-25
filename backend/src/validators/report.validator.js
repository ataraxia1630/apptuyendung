const Joi = require('joi');

const ReportSchema = Joi.object({
    reason: Joi.string().max(1000).required().messages({
        'any.required': 'Reason is required',
        'string.base': 'Reason must be a string',
        'string.empty': 'Reason cannot be empty',
        'string.max': 'Reason must be at most 1000 characters',
    }),
    jobPostId: Joi.string().uuid().optional().messages({
        'string.base': 'Job Post ID must be a string',
        'string.guid': 'Job Post ID must be a valid UUID',
    }),
    postId: Joi.string().uuid().optional().messages({
        'string.base': 'Post ID must be a string',
        'string.guid': 'Post ID must be a valid UUID',
    }),
    reportedUserId: Joi.string().uuid().optional().messages({
        'string.base': 'Reported User ID must be a string',
        'string.guid': 'Reported User ID must be a valid UUID',
    }),
})
    .or('jobPostId', 'postId', 'reportedUserId')
    .messages({
        'object.missing': 'At least one of jobPostId, postId, or reportedUserId is required',
    });

module.exports = {
    ReportSchema,
};
