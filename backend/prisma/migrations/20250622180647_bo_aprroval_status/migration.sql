/*
  Warnings:

  - You are about to drop the column `approvalStatus` on the `JobPost` table. All the data in the column will be lost.
  - You are about to drop the column `approvalStatus` on the `Post` table. All the data in the column will be lost.
  - You are about to drop the column `status` on the `Report` table. All the data in the column will be lost.

*/
-- AlterTable
ALTER TABLE "JobPost" DROP COLUMN "approvalStatus";

-- AlterTable
ALTER TABLE "Post" DROP COLUMN "approvalStatus";

-- AlterTable
ALTER TABLE "Report" DROP COLUMN "status";

-- DropEnum
DROP TYPE "AdminApprovalStatus";

-- CreateTable
CREATE TABLE "ReportReply" (
    "id" TEXT NOT NULL,
    "reportId" TEXT NOT NULL,
    "adminId" TEXT NOT NULL,
    "message" VARCHAR(2000) NOT NULL,
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "ReportReply_pkey" PRIMARY KEY ("id")
);

-- CreateIndex
CREATE INDEX "ReportReply_reportId_idx" ON "ReportReply"("reportId");

-- CreateIndex
CREATE INDEX "ReportReply_adminId_idx" ON "ReportReply"("adminId");

-- AddForeignKey
ALTER TABLE "ReportReply" ADD CONSTRAINT "ReportReply_reportId_fkey" FOREIGN KEY ("reportId") REFERENCES "Report"("id") ON DELETE CASCADE ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "ReportReply" ADD CONSTRAINT "ReportReply_adminId_fkey" FOREIGN KEY ("adminId") REFERENCES "User"("id") ON DELETE CASCADE ON UPDATE CASCADE;
