const authRoutes = require('./auth.route');
const applicantRoutes = require('./applicant.route');

const applicantSkillRoutes = require('./applicantSkill.route');
const eduRoutes = require('./applicantEdu.route');

function route(app) {
  app.use('/api/auth', authRoutes);
  app.use('/api/users/applicant', applicantRoutes);

  app.use('/api/skill', applicantSkillRoutes);
  app.use('/api/education', eduRoutes);
}

module.exports = route;
