const prisma = require('../config/db/prismaClient');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

const SECRET_KEY = process.env.JWT_SECRET || 'secret';

const AuthService = {
  register: async (data) => {
    const { username, email, password, phoneNumber, role } = data;

    const existingUser = await prisma.user.findFirst({
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
      if (existingUser.email === email) throw new Error('Email already exists');
      if (existingUser.phoneNumber === phoneNumber)
        throw new Error('Phone number already exists');
    }

    const hashedPassword = await bcrypt.hash(password, 10);

    const { confirmPassword, ...userData } = data;

    const user = await prisma.user.create({
      data: {
        ...userData,
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
