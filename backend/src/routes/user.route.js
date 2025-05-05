const { Router } = require('express');
const { UserController } = require('../controllers/user.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { cache } = require('../middlewares/cache.middleware');
const { validate } = require('../middlewares/validate.middleware');
const { userSchema } = require('../validators/User/updateUser.validator');
const {
  SettingSchema,
} = require('../validators/User/changePassword.validator');

const route = Router();

// lấy tất cả người dùng
route.get('/all', verifyToken, cache, UserController.getAllUsers);

// search người dùng theo tên
route.get(
  '/search/:name',
  verifyToken,
  cache,
  UserController.searchUsersByName
);

// lấy 1 người dùng theo id
route.get('/:id', verifyToken, cache, UserController.getUserById);

// cập nhật người dùng
route.put('/:id', verifyToken, validate(userSchema), UserController.updateUser);
// xóa người dùng
route.delete('/:id', verifyToken, UserController.deleteUser);
// đổi mật khẩu người dùng
route.put(
  '/setting/:id',
  verifyToken,
  validate(SettingSchema),
  UserController.changeSetting
);

module.exports = route;
