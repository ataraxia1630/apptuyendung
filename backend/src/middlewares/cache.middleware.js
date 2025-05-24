const redisClient = require('../config/cache/redisClient');

const cache = async (req, res, next) => {
  const key = req.originalUrl;

  try {
    const cachedData = await redisClient.get(key);
    if (cachedData) {
      console.log('⚡ Dữ liệu lấy từ cache');
      return res.json(JSON.parse(cachedData));
    }
  } catch (error) {
    console.error('❌ Lỗi Redis:', error);
  }

  res.sendResponse = res.json;
  res.json = async (body) => {
    if (res.statusCode === 200)
      await redisClient.set(key, JSON.stringify(body), { EX: 120 });
    res.sendResponse(body);
  };

  next();
};

module.exports = { cache };
