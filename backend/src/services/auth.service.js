const prisma = require('../config/db/prismaClient');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

const SECRET_KEY = process.env.JWT_SECRET || 'secret';

const AuthService = {
  register: async (data) => {
    const { username, email, password, phoneNumber, role } = data;
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
    } catch (error) {
      throw new Error(
        'Error looking up existing user (service): ' + error.message
      );
    }

    if (existingUser) {
      if (existingUser.username === username)
        throw new Error('Username already exists');
      if (existingUser.email === email) throw new Error('Email already exists');
      if (existingUser.phoneNumber === phoneNumber)
        throw new Error('Phone number already exists');
    }

    const hashedPassword = await bcrypt.hash(password, 10);

    const { confirmPassword, ...userData } = data;

    try {
      return await prisma.user.create({
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
    const token = jwt.sign({ userId: user.id }, SECRET_KEY, {
      expiresIn: '24h',
    });
    return { token, user };
  },

  logout: async (token) => {
    if (!token) {
      throw new Error('Missing token');
    }
  },
};

module.exports = { AuthService };
