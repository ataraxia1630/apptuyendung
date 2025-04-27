/*
  Warnings:

  - A unique constraint covering the columns `[uniName]` on the table `Education` will be added. If there are existing duplicate values, this will fail.

*/
-- CreateIndex
CREATE UNIQUE INDEX "Education_uniName_key" ON "Education"("uniName");
