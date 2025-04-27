const Joi = require('joi');

const UserSchema = Joi.object({
  avatar: Joi.string().optional,
  background: Joi.string().optional,
});

module.exports = { UserSchema };
