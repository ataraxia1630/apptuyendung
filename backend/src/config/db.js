const knex = require('knex');
const config = require('../../knexfile');

const db = knex(config);

db.raw('SELECT 1')
  .then(() => console.log('✅ Kết nối PostgreSQL thành công!'))
  .catch((err) => console.error('❌ Lỗi kết nối DB:', err));

module.exports = db;
