/*
  Warnings:

  - You are about to drop the column `detail` on the `Post` table. All the data in the column will be lost.
  - You are about to drop the `Image` table. If the table is not empty, all the data it contains will be lost.
  - A unique constraint covering the columns `[taxcode]` on the table `Company` will be added. If there are existing duplicate values, this will fail.

*/
-- CreateEnum
CREATE TYPE "ContentType" AS ENUM ('TEXT', 'IMAGE');

-- DropForeignKey
ALTER TABLE "Image" DROP CONSTRAINT "Image_postId_fkey";

-- AlterTable
ALTER TABLE "JobPost" ALTER COLUMN "status" DROP NOT NULL;

-- AlterTable
ALTER TABLE "Post" DROP COLUMN "detail",
ALTER COLUMN "title" SET DATA TYPE TEXT;

-- DropTable
DROP TABLE "Image";

-- CreateTable
CREATE TABLE "PostContent" (
    "id" TEXT NOT NULL,
    "postId" TEXT NOT NULL,
    "type" "ContentType" NOT NULL,
    "value" TEXT NOT NULL,
    "order" INTEGER NOT NULL,

    CONSTRAINT "PostContent_pkey" PRIMARY KEY ("id")
);

-- CreateIndex
CREATE UNIQUE INDEX "Company_taxcode_key" ON "Company"("taxcode");

-- AddForeignKey
ALTER TABLE "PostContent" ADD CONSTRAINT "PostContent_postId_fkey" FOREIGN KEY ("postId") REFERENCES "Post"("id") ON DELETE RESTRICT ON UPDATE CASCADE;
