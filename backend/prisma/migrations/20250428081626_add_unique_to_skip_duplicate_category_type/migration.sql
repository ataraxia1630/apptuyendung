/*
  Warnings:

  - A unique constraint covering the columns `[name,fieldId]` on the table `JobCategory` will be added. If there are existing duplicate values, this will fail.
  - A unique constraint covering the columns `[name]` on the table `JobType` will be added. If there are existing duplicate values, this will fail.

*/
-- CreateIndex
CREATE UNIQUE INDEX "JobCategory_name_fieldId_key" ON "JobCategory"("name", "fieldId");

-- CreateIndex
CREATE UNIQUE INDEX "JobType_name_key" ON "JobType"("name");
