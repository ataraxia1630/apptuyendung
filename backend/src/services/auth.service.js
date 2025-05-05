const prisma = require('../config/db/prismaClient');
const redisClient = require('../config/cache/redisClient');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

const { sendOTPEmail } = require('../config/cache/mailer');

const SECRET_KEY = process.env.JWT_SECRET || 'secret';

const AuthService = {
  sendOtp: async (email) => {
    const otp = Math.floor(100000 + Math.random() * 900000);
    try {
      await redisClient.setEx(`otp:${email}`, 300, otp.toString());
      await sendOTPEmail(email, otp);
      return otp;
    } catch (error) {
      throw new Error('Error sending OTP (service): ' + error.message);
    }
  },

  resendOtp: async (email) => {
    const otp = Math.floor(100000 + Math.random() * 900000);
    try {
      await redisClient.del(`otp:${email}`);
      await redisClient.setEx(`otp:${email}`, 300, otp.toString());
      await sendOTPEmail(email, otp.toString());
      return otp;
    } catch (error) {
      throw new Error('Error resending OTP (service): ' + error.message);
    }
  },

  verifyOtp: async (email, otp) => {
    try {
      const savedOtp = await redisClient.get(`otp:${email}`);
      console.log('Saved OTP:', savedOtp);
      console.log('Provided OTP:', otp);
      if (otp === savedOtp) {
        await redisClient.del(`otp:${email}`);
        await redisClient.setEx(`verified:${email}`, 300, true.toString());
        return;
      } else throw new Error('Invalid or expired OTP');
    } catch (error) {
      throw new Error('Error verifying OTP (service): ' + error.message);
    }
  },

  checkExistUser: async (data) => {
    const { username, email, phoneNumber } = data;
    let existingUser;
    try {
      existingUser = await prisma.user.findFirst({
        where: {
          OR: [
            { username },
            { email },
            ...(phoneNumber ? [{ phoneNumber }] : []),
          ],
        },
      });

      if (existingUser) {
        if (existingUser.username === username)
          throw new Error('Username already exists');
        if (existingUser.email === email)
          throw new Error('Email already exists');
        if (existingUser.phoneNumber === phoneNumber)
          throw new Error('Phone number already exists');
      }
    } catch (error) {
      throw new Error(
        'Error looking up existing user (service): ' + error.message
      );
    }
  },

  register: async (data) => {
    const { email, password, role } = data;
    const hashedPassword = await bcrypt.hash(password, 10);

    const isVerified = await redisClient.get(`verified:${email}`);
    if (email && !isVerified) {
      throw new Error('Email is not verified');
    }

    try {
      return await prisma.user.create({
        data: {
          ...data,
          password: hashedPassword,
          ...(role === 'APPLICANT'
            ? {
                Applicant: {
                  create: {
                    address: '',
                    firstName: '',
                    lastName: '',
                  },
                },
              }
            : {}),
          ...(role === 'COMPANY'
            ? {
                Company: {
                  create: {
                    name: '',
                  },
                },
              }
            : {}),
        },
      });
    } catch (error) {
      throw new Error('Error creating user (service): ' + error.message);
    }
  },

  login: async (username, email, password) => {
    let user;
    try {
      if (username) {
        user = await prisma.user.findUnique({
          where: { username },
          include: {
            Applicant: {
              include: {
                Skill: true,
                Edu: true,
                Exp: true,
                InterestedField: true,
                JobSaved: true,
                JobApplied: true,
                CV: true,
              },
            },
            Company: {
              include: {
                Post: true,
                JobPost: true,
              },
            },
          },
        });
      }
      if (!user && email) {
        user = await prisma.user.findUnique({
          where: { email },
          include: {
            Applicant: {
              include: {
                Skill: true,
                Edu: true,
                Exp: true,
                InterestedField: true,
                JobSaved: true,
                JobApplied: true,
                CV: true,
              },
            },
            Company: {
              include: {
                Post: true,
                JobPost: true,
              },
            },
          },
        });
      }
    } catch (error) {
      throw new Error('Error looking up user (service): ' + error.message);
    }

    if (!user) {
      throw new Error('Invalid credentials');
    }
    const isPasswordValid = await bcrypt.compare(password, user.password);
    if (!isPasswordValid) {
      throw new Error('Invalid password');
    }
    const token = jwt.sign({ userId: user.id, role: user.role }, SECRET_KEY, {
      expiresIn: '24h',
    });
    return { token, user };
  },

  logout: async (token) => {
    if (!token) {
      throw new Error('Missing token');
    }
    try {
      const decoded = jwt.verify(token, SECRET_KEY);
      const now = Math.floor(Date.now() / 1000);
      const ttl = decoded.exp - now;

      if (ttl > 0) {
        await redisClient.setEx(`blacklist:${token}`, ttl, 'true');
      }
    } catch (error) {
      throw new Error('Invalid token or already expired');
    }
  },
};

module.exports = { AuthService };
