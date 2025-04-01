const { PrismaClient } = require('@prisma/client');

const prisma = new PrismaClient();

async function testConnection() {
  try {
    await prisma.$connect();
    console.log('✅ Kết nối database thành công!');
  } catch (error) {
    console.error('❌ Lỗi kết nối database:', error);
  } finally {
    await prisma.$disconnect();
  }
}

testConnection();
