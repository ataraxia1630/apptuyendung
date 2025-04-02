const authRouter = require('./auth');

function route(app) {
  app.use('/auth', authRouter);
}

module.exports = route;
