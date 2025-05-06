/*
  Warnings:

  - You are about to drop the column `cv_file` on the `Applicant` table. All the data in the column will be lost.
  - You are about to drop the column `applicationId` on the `JobApplied` table. All the data in the column will be lost.
  - You are about to drop the column `created_at` on the `JobApplied` table. All the data in the column will be lost.
  - You are about to drop the `Application` table. If the table is not empty, all the data it contains will be lost.

*/
-- DropForeignKey
ALTER TABLE "JobApplied" DROP CONSTRAINT "JobApplied_applicationId_fkey";

-- AlterTable
ALTER TABLE "Applicant" DROP COLUMN "cv_file";

-- AlterTable
ALTER TABLE "JobApplied" DROP COLUMN "applicationId",
DROP COLUMN "created_at",
ADD COLUMN     "apply_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN     "cvId" TEXT;

-- DropTable
DROP TABLE "Application";

-- CreateTable
CREATE TABLE "CV" (
    "id" TEXT NOT NULL,
    "applicantId" TEXT NOT NULL,
    "title" TEXT NOT NULL,
    "filePath" TEXT NOT NULL,
    "viewCount" SMALLINT NOT NULL,
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "CV_pkey" PRIMARY KEY ("id")
);

-- AddForeignKey
ALTER TABLE "JobApplied" ADD CONSTRAINT "JobApplied_cvId_fkey" FOREIGN KEY ("cvId") REFERENCES "CV"("id") ON DELETE SET NULL ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "CV" ADD CONSTRAINT "CV_applicantId_fkey" FOREIGN KEY ("applicantId") REFERENCES "Applicant"("id") ON DELETE RESTRICT ON UPDATE CASCADE;
