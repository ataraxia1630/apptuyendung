/*
  Warnings:

  - A unique constraint covering the columns `[user_id]` on the table `user_token` will be added. If there are existing duplicate values, this will fail.

*/
-- DropIndex
DROP INDEX "user_token_fcm_token_key";

-- CreateIndex
CREATE UNIQUE INDEX "user_token_user_id_key" ON "user_token"("user_id");
