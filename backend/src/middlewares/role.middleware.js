const requireRole = (...allowedRoles) => {
  return (req, res, next) => {
    if (req.user.role === 'ADMIN') {
      return next(); // Admins can access all routes
    }
    const userRole = req.user.role;
    if (!allowedRoles.includes(userRole)) {
      return res.status(403).json({ message: 'Forbidden' });
    }
    next();
  };
};

module.exports = { requireRole };
