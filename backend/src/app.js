const express = require('express');
const db = require('./config/db/db');
const route = require('./routes');

//connect to DB
db.connect();

const app = express();
app.use(express.json());

route(app);

module.exports = app;
