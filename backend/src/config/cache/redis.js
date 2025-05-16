const redisClient = require('./redisClient');

async function connect() {
  try {
    await redisClient.connect();
    console.log('✅ Redis client connected!');
  } catch (error) {
    console.error('Error connecting to Redis:', error);
  }
}

module.exports = { connect };
