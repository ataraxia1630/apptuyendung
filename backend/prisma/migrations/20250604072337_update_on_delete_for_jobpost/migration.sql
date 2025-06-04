-- DropForeignKey
ALTER TABLE "JobPost" DROP CONSTRAINT "JobPost_companyId_fkey";

-- DropForeignKey
ALTER TABLE "JobPost" DROP CONSTRAINT "JobPost_jobTypeId_fkey";

-- AddForeignKey
ALTER TABLE "JobPost" ADD CONSTRAINT "JobPost_companyId_fkey" FOREIGN KEY ("companyId") REFERENCES "Company"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "JobPost" ADD CONSTRAINT "JobPost_jobTypeId_fkey" FOREIGN KEY ("jobTypeId") REFERENCES "JobType"("id") ON DELETE CASCADE ON UPDATE CASCADE;
