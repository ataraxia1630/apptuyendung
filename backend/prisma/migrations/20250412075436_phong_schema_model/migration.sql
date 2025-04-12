/*
  Warnings:

  - The values [HUHU,ANGRY] on the enum `ReactionType` will be removed. If these variants are still used in the database, this will fail.

*/
-- AlterEnum
BEGIN;
CREATE TYPE "ReactionType_new" AS ENUM ('LIKE', 'LOVE', 'IDEA', 'SAD', 'WOW');
ALTER TABLE "Reaction" ALTER COLUMN "reactionType" TYPE "ReactionType_new" USING ("reactionType"::text::"ReactionType_new");
ALTER TYPE "ReactionType" RENAME TO "ReactionType_old";
ALTER TYPE "ReactionType_new" RENAME TO "ReactionType";
DROP TYPE "ReactionType_old";
COMMIT;

-- DropForeignKey
ALTER TABLE "Comment" DROP CONSTRAINT "Comment_postId_fkey";

-- AddForeignKey
ALTER TABLE "Comment" ADD CONSTRAINT "Comment_postId_fkey" FOREIGN KEY ("postId") REFERENCES "Post"("id") ON DELETE RESTRICT ON UPDATE CASCADE;
