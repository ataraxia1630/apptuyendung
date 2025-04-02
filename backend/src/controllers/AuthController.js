const { prismaClient } = require('../../prisma/prismaClient');
const { hashSync } = require('bcrypt');

class AuthController {
  // [GET] /auth
  index(req, res) {}

  // [GET] /auth/signup
  async signup(req, res) {
    const [username, password] = req.body;
    //ktra co trong db ko => true: bao loi
    //false: them vo db, chuyen trang

    return res.json();
  }
  login(req, res) {}
}

module.exports = new AuthController();
