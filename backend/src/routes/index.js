const authRoutes = require('./auth.route');
const applicantRoutes = require('./applicant.route');

const applicantSkillRoutes = require('./applicantSkill.route');
const eduRoutes = require('./education.route');

const fieldRoutes = require('./field.route');

function route(app) {
  app.use('/api/auth', authRoutes);
  app.use('/api/users/applicant', applicantRoutes);

  app.use('/api/skill', applicantSkillRoutes);
  app.use('/api/education', eduRoutes);

  app.use('/api/field', fieldRoutes);
}

module.exports = route;
