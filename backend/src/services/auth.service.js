const prisma = require('../config/db/prismaClient');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

const SECRET_KEY = process.env.JWT_SECRET || 'secret';

const AuthService = {
  register: async (username, password, email, role) => {
    console.log('Registering user:', username, email, role, prisma.user);
    // Validate input
    if (!username || !password || !email || !role) {
      throw new Error('All fields are required');
    }

    if (!['APPLICANT', 'COMPANY'].includes(role)) {
      throw new Error('Invalid role');
    }

    // Check if username already exists
    const existingUser = await prisma.user.findUnique({
      where: { username },
    });
    if (existingUser) {
      throw new Error('Username already exists');
    }

    // Check if email already exists
    const existingEmail = await prisma.user.findUnique({
      where: { email },
    });
    if (existingEmail) {
      throw new Error('Email already exists');
    }

    // Hash the password
    const hashedPassword = await bcrypt.hash(password, 10);

    const user = await prisma.user.create({
      data: {
        username,
        password: hashedPassword,
        email,
        role,
        ...(role === 'APPLICANT' ? { Applicant: { create: {} } } : {}),
        ...(role === 'COMPANY' ? { Company: { create: {} } } : {}),
      },
    });
    return user;
  },

  login: async (username, password) => {
    if (!username || !password) {
      throw new Error('Missing fields');
    }
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

  logout: async (token) => {
    if (!token) {
      throw new Error('Missing token');
    }
  },
};

module.exports = { AuthService };
