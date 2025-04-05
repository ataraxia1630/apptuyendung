const authRoutes = require('./auth.route');
const applicantRoutes = require('./applicant.route');

function route(app) {
  app.use('/api/auth', authRoutes);
  app.use('/api/users', applicantRoutes);
}

module.exports = route;
