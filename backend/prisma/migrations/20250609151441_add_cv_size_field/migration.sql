/*
  Warnings:

  - Added the required column `size` to the `CV` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE "CV" ADD COLUMN     "size" INTEGER NOT NULL;
