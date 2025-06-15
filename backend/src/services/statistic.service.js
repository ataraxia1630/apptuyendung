const prisma = require('../config/db/prismaClient');

const StatisticService = {
    getOverview: async () => {
        const [userCount, jobPostCount, reportCount, applicationCount] = await Promise.all([
            prisma.user.count(),
            prisma.jobPost.count(),
            prisma.report.count(),
            prisma.jobApplied.count()
        ]);
        return { userCount, jobPostCount, reportCount, applicationCount };
    },

    getTopJobposts: async () => {
        // Nhóm các jobApplied theo jobpostId, đếm số ứng tuyển, sắp xếp giảm dần, lấy 10 đầu
        const results = await prisma.jobApplied.groupBy({
            by: ['jobpostId'],
            _count: { jobpostId: true },
            orderBy: { _count: { jobpostId: 'desc' } },
            take: 10
        });

        // Lấy chi tiết JobPost kèm số lượng ứng tuyển
        const topJobposts = await Promise.all(
            results.map(async (r) => {
                const job = await prisma.jobPost.findUnique({ where: { id: r.jobpostId } });
                return { job, applicationCount: r._count.jobpostId };
            })
        );
        return topJobposts;
    },

    getTopCompanies: async () => {
        // 1. Lấy tất cả JobApplied kèm theo jobPostId và companyId (nối qua jobPost)
        const jobApplications = await prisma.jobApplied.findMany({
            select: {
                jobpostId: true,
                JobPost: {
                    select: { companyId: true }
                }
            }
        });

        // 2. Tính tổng số ứng tuyển theo companyId (dùng object đếm)
        const companyCounts = {};
        for (const app of jobApplications) {
            const cid = app.jobPost.companyId;
            if (!companyCounts[cid]) companyCounts[cid] = 0;
            companyCounts[cid]++;
        }

        // 3. Sắp xếp và lấy top 10
        const sorted = Object.entries(companyCounts)
            .sort((a, b) => b[1] - a[1])
            .slice(0, 10);

        // 4. Trả về thông tin company + số lượng
        const topCompanies = await Promise.all(
            sorted.map(async ([companyId, count]) => {
                const company = await prisma.company.findUnique({ where: { id: companyId } });
                return { company, applicationCount: count };
            })
        );

        return topCompanies;
    },

    getMonthlyGrowth: async () => {
        // Thống kê số User mới theo tháng trong 6 tháng gần nhất
        const userGrowth = await prisma.$queryRaw`
            SELECT to_char(date_trunc('month', created_at), 'YYYY-MM') AS month, COUNT(*) AS count
            FROM "User"
            WHERE created_at >= date_trunc('month', NOW()) - interval '5 months'
            GROUP BY month
            ORDER BY month;
        `;

        // Thống kê số JobPost mới theo tháng trong 6 tháng gần nhất
        const jobGrowth = await prisma.$queryRaw`
            SELECT to_char(date_trunc('month', created_at), 'YYYY-MM') AS month, COUNT(*) AS count
            FROM "JobPost"
            WHERE created_at >= date_trunc('month', NOW()) - interval '5 months'
            GROUP BY month
            ORDER BY month;
        `;

        return { userGrowth, jobGrowth };
    },

    getByCategory: async () => {
        // Nhóm JobPost theo jobCategoryId, đếm số bài mỗi category
        const results = await prisma.jobPost.groupBy({
            by: ['jobCategoryId'],
            _count: { id: true }
        });

        // Lấy tên category kèm số lượng
        const data = await Promise.all(
            results.map(async (r) => {
                const category = await prisma.jobCategory.findUnique({ where: { id: r.jobCategoryId } });
                return {
                    category: category ? category.name : 'Unknown',
                    count: r._count.id
                };
            })
        );
        return data;
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
