const { jwt } = require('jsonwebtoken');

const SECRET_KEY = process.env.JWT_SECRET || 'secret';

export const authMiddleware = (req, res, next) => {
  const token = req.headers['Authorization']?.split(' ')[1];
  if (!token) {
    return res.status(401).json({ message: 'Unauthorized' });
  }

  try {
    jwt.verify(token, SECRET_KEY, (err, decoded) => {
      if (err) {
        return res.status(401).json({ message: 'Invalid token' });
      }
      req.user = decoded;
      next();
    });
  } catch (error) {
    res.status(401).json({ message: 'Invalid token' });
  }
};
