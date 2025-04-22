/*
  Warnings:

  - You are about to drop the column `companyId` on the `Experience` table. All the data in the column will be lost.
  - You are about to alter the column `jobResponsibility` on the `Experience` table. The data in that column could be lost. The data in that column will be cast from `Text` to `VarChar(1000)`.
  - Added the required column `companyName` to the `Experience` table without a default value. This is not possible if the table is not empty.
  - Made the column `jobResponsibility` on table `Experience` required. This step will fail if there are existing NULL values in that column.

*/
-- AlterTable
ALTER TABLE "Experience" DROP COLUMN "companyId",
ADD COLUMN     "companyName" TEXT NOT NULL,
ALTER COLUMN "work_start" DROP NOT NULL,
ALTER COLUMN "work_end" DROP NOT NULL,
ALTER COLUMN "jobResponsibility" SET NOT NULL,
ALTER COLUMN "jobResponsibility" SET DATA TYPE VARCHAR(1000);
