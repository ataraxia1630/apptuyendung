const { Router } = require('express');
const { CompanyController } = require('../controllers/company.controller');
const { verifyToken } = require('../middlewares/auth.middleware');
const { validate } = require('../middlewares/validate.middleware');
const {
  CompanySchema,
} = require('../validators/Company/updateCompany.validator');
const route = Router();

// lấy tất cả công ty
route.get('/all', verifyToken, CompanyController.getAllCompanies);

// tìm kiếm công ty theo tên
route.get(
  '/search/:name',
  verifyToken,
  CompanyController.searchCompaniesByName
);
// lấy 1 công ty theo id
route.get('/:id', verifyToken, CompanyController.getCompanyById);
// cập nhật công ty
route.put(
  '/:id',
  verifyToken,
  validate(CompanySchema),
  CompanyController.updateCompany
);
// xóa công ty
route.delete('/:id', verifyToken, CompanyController.deleteCompany);

module.exports = route;
