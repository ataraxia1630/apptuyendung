const { Router } = require('express');
const { UserController } = require('../controllers/user.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { validate } = require('../middlewares/validate.middleware');
const { userSchema } = require('../validators/User/updateUser.validator');
const {
  ChangePasswordSchema,
} = require('../validators/User/changePassword.validator');

const route = Router();

// lấy tất cả người dùng
route.get('/all', verifyToken, UserController.getAllUsers);

// search người dùng theo tên
route.get('/search/:name', verifyToken, UserController.searchUsersByName);

// lấy 1 người dùng theo id
route.get('/:id', verifyToken, UserController.getUserById);

// cập nhật người dùng
route.put('/:id', verifyToken, validate(userSchema), UserController.updateUser);
// xóa người dùng
route.delete('/:id', verifyToken, UserController.deleteUser);
// đổi mật khẩu người dùng
route.put(
  '/change-password/:id',
  verifyToken,
  validate(ChangePasswordSchema),
  UserController.changePassword
);

module.exports = route;
