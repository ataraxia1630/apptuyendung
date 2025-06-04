-- CreateEnum
CREATE TYPE "AdminApprovalStatus" AS ENUM ('PENDING', 'APPROVED', 'REJECTED');

-- AlterTable
ALTER TABLE "JobPost" ADD COLUMN     "approvalStatus" "AdminApprovalStatus" NOT NULL DEFAULT 'PENDING';

-- AlterTable
ALTER TABLE "Post" ADD COLUMN     "approvalStatus" "AdminApprovalStatus" NOT NULL DEFAULT 'PENDING';
