require('dotenv').config();
const app = require('./app');
const { initSocket } = require('./socket');
const { scheduleAutoTerminateJob } = require('./cron/autoTerminateJob');
const server = require('http').createServer(app);

initSocket(server);
scheduleAutoTerminateJob();

const PORT = process.env.PORT || 5000;

server.listen(PORT, () => console.log(`Server running on port ${PORT}`));
