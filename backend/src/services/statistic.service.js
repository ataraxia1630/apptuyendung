const prisma = require('../config/db/prismaClient');

const StatisticService = {
    getOverview: async () => {
        const [userCount, jobPostCount, companyCount, applicationCount] = await Promise.all([
            prisma.user.count(),
            prisma.jobPost.count(),
            prisma.company.count(),
            prisma.jobApplied.count()
        ]);
        return { userCount, jobPostCount, companyCount, applicationCount };
    },

    getTopJobposts: async (skip, take) => {
        // Nhóm jobApplied theo jobpostId và đếm
        const allResults = await prisma.jobApplied.groupBy({
            by: ['jobpostId'],
            _count: { jobpostId: true },
            orderBy: { _count: { jobpostId: 'desc' } }
        });

        // Cắt phân trang
        const paginated = allResults.slice(skip, skip + take);

        // Lấy chi tiết JobPost
        const items = await Promise.all(
            paginated.map(async (r) => {
                const job = await prisma.jobPost.findUnique({ where: { id: r.jobpostId } });
                return { job, applicationCount: r._count.jobpostId };
            })
        );

        // Trả về đúng format controller mong muốn
        return {
            items,
            total: allResults.length
        };
    },


    getTopCompanies: async (skip, take) => {
        const jobApplications = await prisma.jobApplied.findMany({
            select: {
                JobPost: {
                    select: { companyId: true }
                }
            }
        });

        const companyCounts = {};
        for (const app of jobApplications) {
            const cid = app.JobPost?.companyId;
            if (cid) {
                companyCounts[cid] = (companyCounts[cid] || 0) + 1;
            }
        }

        const sorted = Object.entries(companyCounts)
            .sort((a, b) => b[1] - a[1])
            .slice(0, 10);

        const companyIds = sorted.map(([id]) => id);

        const companies = await prisma.company.findMany({
            where: { id: { in: companyIds } }
        });

        const topCompanies = sorted.map(([id, count]) => {
            const company = companies.find(c => c.id === id);
            return { company, applicationCount: count };
        });

        const paginated = topCompanies.slice(skip, skip + take);

        return {
            items: paginated,
            total: topCompanies.length // ← bạn quên dòng này là sẽ lỗi totalPages
        };
    },


    getMonthlyGrowth: async () => {
        const startDate = new Date();
        startDate.setMonth(startDate.getMonth() - 5);
        startDate.setDate(1);

        const users = await prisma.user.findMany({
            where: { created_at: { gte: startDate } },
            select: { created_at: true }
        });

        const jobs = await prisma.jobPost.findMany({
            where: { created_at: { gte: startDate } },
            select: { created_at: true }
        });

        const formatMonth = (date) => {
            const d = new Date(date);
            return `${d.getFullYear()}-${String(d.getMonth() + 1).padStart(2, '0')}`;
        };

        const groupByMonth = (items) => {
            const counts = {};
            for (const item of items) {
                const month = formatMonth(item.created_at);
                counts[month] = (counts[month] || 0) + 1;
            }
            return Object.entries(counts)
                .sort(([a], [b]) => a.localeCompare(b))
                .map(([month, count]) => ({ month, count }));
        };

        return {
            userGrowth: groupByMonth(users),
            jobGrowth: groupByMonth(jobs)
        };
    },

    getByCategory: async (skip, take) => {
        const results = await prisma.jobPost.groupBy({
            by: ['jobCategoryId'],
            _count: { id: true }
        });

        const data = await Promise.all(
            results.map(async (r) => {
                const category = await prisma.jobCategory.findUnique({ where: { id: r.jobCategoryId } });
                return {
                    category: category?.name || 'Unknown',
                    count: r._count.id
                };
            })
        );

        // Thêm paginate ở đây
        const paginated = data.slice(skip, skip + take);

        return {
            items: paginated,
            total: data.length // ← bạn quên dòng này là sẽ lỗi totalPages
        };
    },


    getApplicationSummaryForCompany: async (companyId) => {
        // Đếm tổng số đơn ứng tuyển thuộc về các JobPost của company đó
        const total = await prisma.jobApplied.count({
            where: { jobPost: { companyId } }
        });
        return { companyId, totalApplications: total };
    }
};

module.exports = { StatisticService };
