const authRoutes = require('./auth.route');
const userRoutes = require('./user.route');
const applicantRoutes = require('./applicant.route');
const companyRoutes = require('./company.route');

const applicantSkillRoutes = require('./applicantSkill.route');
const eduRoutes = require('./education.route');
const expRoutes = require('./exp.route');

const fieldRoutes = require('./field.route');
const jobPostRoutes = require('./jobPost.route');
const jobTypeRoutes = require('./jobType.route');
const jobCategoryRoutes = require('./jobCategory.route');

const cvRoutes = require('./cv.route');
const avatarRoutes = require('./avatar.route');

const jobSavedRoutes = require('./jobSaved.route');
const PostRoutes = require('./post.route');
const reactionRoutes = require('./reaction.route');
const commentRoutes = require('./comment.route');
const jobAppliedRoutes = require('./jobApplied.route');

const chatRoutes = require('./chat.route');
const messRoutes = require('./message.route');

const dailyReportRoutes = require('./dailyReport.route');
const dailyStatisticRoutes = require('./dailyStatistic.route');
const statisticRoutes = require('./statistic.route');
const reportRoutes = require('./report.route');
const postImageRoute = require('./postImage.route')

function route(app) {
  app.use('/api/auth', authRoutes);
  app.use('/api/users/applicant', applicantRoutes);
  app.use('/api/users/company', companyRoutes);

  app.use('/api/users', userRoutes);

  app.use('/api/skill', applicantSkillRoutes);
  app.use('/api/education', eduRoutes);
  app.use('/api/experience', expRoutes);

  app.use('/api/job-posts', jobPostRoutes);
  app.use('/api/fields', fieldRoutes);
  app.use('/api/types', jobTypeRoutes);
  app.use('/api/category', jobCategoryRoutes);

  app.use('/api/cv', cvRoutes);
  app.use('/api/avatar', avatarRoutes);

  app.use('/api/save', jobSavedRoutes);
  app.use('/api/posts', PostRoutes);
  app.use('/api/posts/images', postImageRoute);
  app.use('/api/reactions', reactionRoutes);
  app.use('/api/comments', commentRoutes);
  app.use('/api/apply', jobAppliedRoutes);

  app.use('/api/chat', chatRoutes);
  app.use('/api/mess', messRoutes);

  app.use('/api/dailyReport', dailyReportRoutes);
  app.use('/api/dailyStatistic', dailyStatisticRoutes);
  app.use('/api/statistic', statisticRoutes)
  app.use('/api/reports', reportRoutes);
}

module.exports = route;
