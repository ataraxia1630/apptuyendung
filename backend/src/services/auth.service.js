const { prisma } = require('../config/db/prismaClient');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

const SECRET_KEY = process.env.JWT_SECRET || 'secret';

const AuthService = {
  register: async (username, password, role) => {
    const hashedPassword = await bcrypt.hash(password, 10);

    const user = await prisma.user.create({
      data: {
        username,
        password: hashedPassword,
        role,
        ...(role === 'APPLICANT' ? { Applicant: { create: {} } } : {}),
        ...(role === 'COMPANY' ? { Company: { create: {} } } : {}),
      },
    });
    return user;
  },

  login: async (username, password) => {
    const user = await prisma.user.findUnique({
      where: { username },
    });
    if (!user) {
      throw new Error('User not found');
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

  logout: async (token) => {},
};

module.exports = { AuthService };
