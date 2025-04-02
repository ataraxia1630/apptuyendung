const authRoutes = require('./auth.route');

function route(app) {
  app.use('api/auth', authRoutes);
}

module.exports = route;
