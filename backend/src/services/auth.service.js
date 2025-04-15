const prisma = require('../config/db/prismaClient');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const { isValidEmail, isValidPhoneNumber } = require('../utils/validators');

const SECRET_KEY = process.env.JWT_SECRET || 'secret';

const AuthService = {
  register: async (username, password, email, phoneNumber, role) => {
    const existingUser = await prisma.user.findUnique({
      where: { username },
    });

    if (existingUser) {
      throw new Error('Username already exists');
    }

    const existingEmail = await prisma.user.findUnique({
      where: { email },
    });
    if (existingEmail) {
      throw new Error('Email already exists');
    }

    const existingPhoneNumber = await prisma.user.findUnique({
      where: { phoneNumber },
    });
    if (existingPhoneNumber) {
      throw new Error('Phone number already exists');
    }

    const hashedPassword = await bcrypt.hash(password, 10);

    const user = await prisma.user.create({
      data: {
        username,
        password: hashedPassword,
        email,
        phoneNumber,
        role,
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
    return user;
  },

  login: async (username, email, password) => {
    if (!username && !email) {
      throw new Error('Missing fields');
    }
    let user;
    if (username) {
      user = await prisma.user.findUnique({
        where: { username },
      });
    }
    if (!user && email) {
      user = await prisma.user.findUnique({
        where: { email },
      });
    }
    if (!user) {
      throw new Error('Invalid credentials'); // Generic error message
    }
    const isPasswordValid = await bcrypt.compare(password, user.password);
    if (!isPasswordValid) {
      throw new Error('Invalid password');
    }
    const token = jwt.sign({ userId: user.id }, SECRET_KEY, {
      expiresIn: '24h',
    });
    return { token, role: user.role };
  },

  logout: async (token) => {
    if (!token) {
      throw new Error('Missing token');
    }
  },
};

module.exports = { AuthService };
