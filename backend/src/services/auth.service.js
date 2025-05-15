const prisma = require('../config/db/prismaClient');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const googleClient = require('../config/google/googleClient');

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

  login: async (username, password) => {
    let user;
    try {
      user = await prisma.user.findUnique({
        where: { username },
        include: {
          Applicant: {
            include: {
              Skill: true,
              Edu: true,
              Exp: true,
              InterestedField: true,
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

      if (!user) {
        user = await prisma.user.findUnique({
          where: { email: username },
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
  },

  loginGoogle: async (idToken, role = 'APPLICANT') => {
    try {
      if (!['APPLICANT', 'COMPANY'].includes(role)) {
        throw new Error('Invalid role');
      }

      const ticket = await googleClient.verifyIdToken({
        idToken,
        audience: process.env.GOOGLE_CLIENT_ID,
      });

      const payload = ticket.getPayload();

      const id = payload.sub;
      const email = payload.email;
      const name = payload.name;
      const phoneNumber = payload.phoneNumber || null;
      const avatar = payload.picture;

      const existingUser = await prisma.user.findUnique({
        where: { email },
      });

      if (existingUser) {
        const token = jwt.sign(
          { userId: existingUser.id, role: existingUser.role },
          SECRET_KEY,
          { expiresIn: '24h' }
        );
        return { token, user: existingUser };
      }

      const hashedPassword = bcrypt.hashSync(id, 10);

      const newUser = await prisma.user.create({
        data: {
          email,
          phoneNumber,
          username: email,
          password: hashedPassword,
          avatar,
          role,
          ...(role === 'APPLICANT'
            ? {
                Applicant: {
                  create: {
                    address: '',
                    firstName: name.split(' ')[0],
                    lastName: name.split(' ')[1] || '',
                  },
                },
              }
            : {}),

          ...(role === 'COMPANY'
            ? {
                Company: {
                  create: {
                    name: name,
                  },
                },
              }
            : {}),
        },
      });

      const token = jwt.sign(
        { userId: newUser.id, role: newUser.role },
        SECRET_KEY,
        { expiresIn: '24h' }
      );

      return { token, user: newUser };
    } catch (error) {
      throw new Error('Error logging into Google account: ' + error.message);
    }
  },
};

module.exports = { AuthService };
