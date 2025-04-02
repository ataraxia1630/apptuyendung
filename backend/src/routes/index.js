const authRoutes = require('./auth');

function route(app) {
  app.use('api/auth', authRoutes);
}

module.exports = route;
