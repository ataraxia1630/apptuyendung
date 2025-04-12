const RegisterValidator = () => {
  return {
    firstName: {
      type: 'string',
      minLength: 2,
      maxLength: 50,
      required: true,
    },
    lastName: {
      type: 'string',
      minLength: 2,
      maxLength: 50,
      required: true,
    },
    email: {
      type: 'string',
      format: 'email',
      required: true,
    },
    password: {
      type: 'string',
      minLength: 8,
      maxLength: 100,
      required: true,
    },
    confirmPassword: {
      type: 'string',
      minLength: 8,
      maxLength: 100,
      required: true,
    },
  };
};

module.exports = { RegisterValidator };
