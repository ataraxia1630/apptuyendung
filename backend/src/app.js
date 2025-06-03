const express = require('express');
const db = require('./config/db/db');
const redis = require('./config/cache/redis');
const route = require('./routes');
const cors = require('cors');
//connect to DB
db.connect();

//connect to Redis
redis.connect();

const app = express();

app.use(express.json());

route(app);

module.exports = app;
