const { CompanyService } = require('../services/company.service');

const CompanyController = {
  getAllCompanies: async (req, res) => {
    try {
      const companies = await CompanyService.getAllCompanies();
      return res.status(200).json(companies);
    } catch (error) {
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  searchCompaniesByName: async (req, res) => {
    try {
      const { name } = req.params;
      const companies = await CompanyService.searchCompaniesByName(name);
      return res.status(200).json(companies);
    } catch (error) {
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  getCompanyById: async (req, res) => {
    try {
      const { id } = req.params;
      const company = await CompanyService.getCompanyById(id);
      if (!company) {
        return res.status(404).json({ message: 'Company not found' });
      }
      return res.status(200).json(company);
    } catch (error) {
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  updateCompany: async (req, res) => {
    try {
      const { id } = req.params;
      const company = await CompanyService.updateCompany(id, req.body);
      if (!company) {
        return res.status(404).json({ message: 'Company not found' });
      }
      return res.status(200).json(company);
    } catch (error) {
      return res.status(500).json({ message: 'Internal server error' });
    }
  },

  deleteCompany: async (req, res) => {
    try {
      const { id } = req.params;
      const company = await CompanyService.deleteCompany(id);
      if (!company) {
        return res.status(404).json({ message: 'Company not found' });
      }
      return res.status(200).json({ message: 'Company deleted successfully' });
    } catch (error) {
      return res.status(500).json({ message: 'Internal server error' });
    }
  },
};

module.exports = { CompanyController };
