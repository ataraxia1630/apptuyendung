const { CompanyService } = require('../services/company.service');

const CompanyController = {
  getAllCompanies: async (req, res) => {
    try {
      const companies = await CompanyService.getAllCompanies();
      return res.status(200).json({ companies: companies });
    } catch (error) {
      return res.status(500).json({
        message: 'Error fetching companies',
        error: error.message || error,
      });
    }
  },

  searchCompaniesByName: async (req, res) => {
    try {
      const { name } = req.params;
      const companies = await CompanyService.searchCompaniesByName(name);
      return res.status(200).json({ companies: companies });
    } catch (error) {
      return res.status(500).json({
        message: 'Error searching companies',
        error: error.message || error,
      });
    }
  },

  getCompanyById: async (req, res) => {
    try {
      const { id } = req.params;
      const company = await CompanyService.getCompanyById(id);
      if (!company) {
        return res.status(404).json({ message: 'Company not found' });
      }
      return res.status(200).json({ company: company });
    } catch (error) {
      return res.status(500).json({
        message: 'Error fetching company',
        error: error.message || error,
      });
    }
  },

  updateCompany: async (req, res) => {
    try {
      console.log("co try");
      const { id } = req.params;
      const company = await CompanyService.updateCompany(id, req.body);
      if (!company) {
        console.log("not found");
        return res.status(404).json({ message: 'Company not found' });
      }
            console.log("co update");
      return res.status(200).json({ company: company });
    } catch (error) {
      console.log("co error", error);
      return res.status(500).json({
        message: 'Error updating company',
        error: error.message || error,
      });
    }
  },

  deleteCompany: async (req, res) => {
    try {
      const { id } = req.params;
      const company = await CompanyService.deleteCompany(id);
      if (!company) {
        return res.status(404).json({ message: 'Company not found' });
      }
      return res.status(204).send();
    } catch (error) {
      return res.status(500).json({
        message: 'Error deleting company',
        error: error.message || error,
      });
    }
  },
};

module.exports = { CompanyController };
