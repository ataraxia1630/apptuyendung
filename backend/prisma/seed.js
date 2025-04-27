const { PrismaClient } = require('@prisma/client');
const fs = require('fs');
const prisma = new PrismaClient();

async function main() {
  await prisma.education.deleteMany();
  console.log('🧹 Đã xoá toàn bộ dữ liệu education cũ');

  const universities = JSON.parse(
    fs.readFileSync('./prisma/data/universities.json', 'utf8')
  );

  for (const uni of universities) {
    await prisma.education.upsert({
      where: { uniName: uni.uniName },
      update: {},
      create: {
        uniName: uni.uniName,
        description: uni.description,
        uniLink: uni.uniLink,
        address: uni.address,
      },
    });
  }

  console.log('✅ Seed thành công!');
}

main()
  .catch((e) => {
    console.error(e);
    process.exit(1);
  })
  .finally(async () => {
    await prisma.$disconnect();
  });
