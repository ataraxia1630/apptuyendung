require('dotenv').config();

module.exports = {
  client: 'pg',
  connection: process.env.DB_URL,
  pool: { min: 2, max: 10 },
  migrations: {
    directory: './migrations',
  },
  seeds: {
    directory: './seeds',
  },
};
