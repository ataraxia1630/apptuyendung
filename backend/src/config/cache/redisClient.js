const { createClient } = require('redis');

const redisClient = createClient({
  username: 'default',
  password: process.env.REDIS_PW,
  socket: {
    host: process.env.REDIS_HOST_URL,
    port: process.env.REDIS_PORT,
  },
});

redisClient.on('error', (err) => {
  console.error('❌ Redis Client Error:', err);
});

module.exports = redisClient;
