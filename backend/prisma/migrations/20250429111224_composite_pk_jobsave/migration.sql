/*
  Warnings:

  - The primary key for the `JobSaved` table will be changed. If it partially fails, the table could be left without primary key constraint.
  - You are about to drop the column `id` on the `JobSaved` table. All the data in the column will be lost.

*/
-- AlterTable
ALTER TABLE "JobSaved" DROP CONSTRAINT "JobSaved_pkey",
DROP COLUMN "id",
ADD CONSTRAINT "JobSaved_pkey" PRIMARY KEY ("jobpostId", "applicantId");
