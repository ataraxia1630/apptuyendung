-- DropForeignKey
ALTER TABLE "Skill" DROP CONSTRAINT "Skill_applicantId_fkey";

-- AddForeignKey
ALTER TABLE "Skill" ADD CONSTRAINT "Skill_applicantId_fkey" FOREIGN KEY ("applicantId") REFERENCES "Applicant"("id") ON DELETE CASCADE ON UPDATE CASCADE;
