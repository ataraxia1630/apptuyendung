const isValidEmail = (email) => {
  const regex = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;
  return regex.test(email);
};

const isValidPhoneNumber = (phoneNumber) => {
  const regex = /^(0|\+84)(3[2-9]|5[6|8|9]|7[0|6-9]|8[1-9]|9[0-9])[0-9]{7}$/;
  return regex.test(phone);
};

module.exports = {
  isValidEmail,
  isValidPhoneNumber,
};
