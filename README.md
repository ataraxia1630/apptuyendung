# 🌟 WorkLeap – Ứng Dụng Tuyển Dụng & Tìm Việc Làm

**WorkLeap** là ứng dụng Android hỗ trợ kết nối giữa **ứng viên** và **nhà tuyển dụng** một cách nhanh chóng, hiệu quả. Giao diện đơn giản, dễ sử dụng, phù hợp cho cả người mới bắt đầu tiếp cận công nghệ.

---

## 🚀 Tính năng nổi bật

- ✅ Giao diện thân thiện, dễ dùng.
- ✅ Phân tách rõ ràng vai trò **Ứng viên** và **Nhà tuyển dụng**.
- ✅ Cho phép Quản trị viên quản lý tài khoản, bài đăng, công việc.
- ✅ Hỗ trợ **tải lên CV** dạng file PDF, xem và duyệt CV nhanh chóng.
- ✅ Hệ thống **thông báo thời gian thực** qua Firebase.
- ✅ **Chat trực tiếp** giữa ứng viên và nhà tuyển dụng.
- ✅ Kiến trúc hiện đại **MVVM**, dễ bảo trì và mở rộng.
- ✅ Kết nối mượt mà với backend sử dụng **Node.js**, **Supabase**, **Firebase**.
- ✅ Chạy ổn định trên thiết bị Android thực tế.

---

## 🛠️ Công nghệ sử dụng

- **Android Studio (Java)**
- **MVVM Architecture**
- **Retrofit (API client)**
- **Firebase** (Authentication, Notification, Chat)
- **Supabase** (Database & Auth)
- **Node.js + Express** (REST API)
- **Prisma** (ORM)

---

## ⚙️ Cách cài đặt & chạy dự án

### 📱Frontend (Android Studio)
1. Clone repo:
   Mở thư mục frontend trong Android Studio.

2. Sync Project:
Vào menu File → chọn Sync Project with Gradle Files.
Cấu hình Firebase (nếu cần):
Thêm file google-services.json vào thư mục app/.
Chạy ứng dụng kết nối thiết bị thật hoặc emulator.

💡 Lưu ý: Thiết bị cần có Internet và cấp đầy đủ quyền truy cập (File, Network,...).

### 📱 Backend (ExpressJS + Node.js)
1. Mở dự án trong VSCode, thêm file .env
2. Mở cửa sổ terminal, chạy các lệnh:
  cd backend
  npm install
  npm run dev
  npx prisma generate
