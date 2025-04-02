/*
  Warnings:

  - You are about to drop the column `phoneNumber` on the `Applicant` table. All the data in the column will be lost.
  - A unique constraint covering the columns `[phoneNumber]` on the table `User` will be added. If there are existing duplicate values, this will fail.
  - Added the required column `phoneNumber` to the `User` table without a default value. This is not possible if the table is not empty.

*/
-- DropIndex
DROP INDEX "Applicant_phoneNumber_key";

-- AlterTable
ALTER TABLE "Applicant" DROP COLUMN "phoneNumber";

-- AlterTable
ALTER TABLE "Company" ALTER COLUMN "description" DROP NOT NULL,
ALTER COLUMN "taxcode" DROP NOT NULL,
ALTER COLUMN "establishedYear" DROP NOT NULL;

-- AlterTable
ALTER TABLE "User" ADD COLUMN     "phoneNumber" VARCHAR(15) NOT NULL;

-- CreateIndex
CREATE UNIQUE INDEX "User_phoneNumber_key" ON "User"("phoneNumber");
