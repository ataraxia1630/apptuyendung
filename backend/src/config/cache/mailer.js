const nodemailer = require('nodemailer');
require('dotenv').config();

const transporter = nodemailer.createTransport({
  service: 'gmail',
  auth: {
    user: process.env.EMAIL_USER,
    pass: process.env.EMAIL_PASS,
  },
});

async function sendOTPEmail(to, otp) {
  await transporter.sendMail({
    from: `"My App" <${process.env.EMAIL_USER}>`,
    to,
    subject: 'Mã OTP xác thực',
    text: `Mã OTP của bạn là: ${otp}`,
  });
}

module.exports = { sendOTPEmail };
