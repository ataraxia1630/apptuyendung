const authRoutes = require('./auth.route');
const userRoutes = require('./user.route');
const applicantRoutes = require('./applicant.route');
const companyRoutes = require('./company.route');

const applicantSkillRoutes = require('./applicantSkill.route');
const eduRoutes = require('./education.route');
const expRoutes = require('./exp.route');

const fieldRoutes = require('./field.route');

function route(app) {
  app.use('/api/auth', authRoutes);
  app.use('/api/users/applicant', applicantRoutes);
  app.use('/api/users/company', companyRoutes);

  app.use('/api/users', userRoutes);

  app.use('/api/skill', applicantSkillRoutes);
  app.use('/api/education', eduRoutes);
  app.use('/api/experience', expRoutes);

  app.use('/api/field', fieldRoutes);
}

module.exports = route;
