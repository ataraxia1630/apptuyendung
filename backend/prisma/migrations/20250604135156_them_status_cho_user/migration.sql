-- CreateEnum
CREATE TYPE "UserAccountStatus" AS ENUM ('ACTIVE', 'LOCKED', 'BANNED');

-- AlterTable
ALTER TABLE "User" ADD COLUMN     "status" "UserAccountStatus" NOT NULL DEFAULT 'ACTIVE';
