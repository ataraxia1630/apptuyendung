const authRoutes = require('./auth.route');
const applicantRoutes = require('./applicant.route');

const applicantSkillRoutes = require('./applicantSkill.route');

function route(app) {
  app.use('/api/auth', authRoutes);
  app.use('/api/users/applicant', applicantRoutes);

  app.use('/api/skill', applicantSkillRoutes);
}

module.exports = route;
