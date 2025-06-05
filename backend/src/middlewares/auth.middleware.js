const jwt = require('jsonwebtoken');
const redisClient = require('../config/cache/redisClient');

const SECRET_KEY = process.env.JWT_SECRET || 'secret';

const verifyToken = async (req, res, next) => {
  const token = req.headers['authorization']?.split(' ')[1];
  if (!token) {
    return res.status(401).json({ message: 'Unauthorized' });
  }

  try {
    const isBlacklisted = await redisClient.get(`blacklist:${token}`);
    if (isBlacklisted) {
      return res.status(403).json({ message: 'Token has been logged out' });
    }
  } catch (error) {
    console.error('Error checking token blacklist:', error);
    return res.status(500).json({ message: 'Internal server error' });
  }

  jwt.verify(token, SECRET_KEY, (err, decoded) => {
    if (err) {
      return res.status(401).json({ message: 'Invalid token' });
    }
    req.user = decoded;
    console.log('Middleware: verifyToken called');
    next();
  });
};

module.exports = { verifyToken };
