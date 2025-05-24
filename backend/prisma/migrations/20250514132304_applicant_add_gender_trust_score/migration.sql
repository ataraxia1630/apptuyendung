-- CreateEnum
CREATE TYPE "GenderType" AS ENUM ('MALE', 'FEMALE');

-- AlterTable
ALTER TABLE "Applicant" ADD COLUMN     "gender" "GenderType",
ADD COLUMN     "trustScore" DOUBLE PRECISION NOT NULL DEFAULT 0;
