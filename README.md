# 🛒 PHẦN MỀM QUẢN LÝ BÁN HÀNG (POS JAVA SWING)

![Java](https://img.shields.io/badge/Language-Java_17+-orange?style=for-the-badge&logo=java)
![Database](https://img.shields.io/badge/Database-SQLite-blue?style=for-the-badge&logo=sqlite)
![Version](https://img.shields.io/badge/Version-2.0.0-green?style=for-the-badge)

> **Giải pháp quản lý bán hàng toàn diện, gọn nhẹ (Portable) dành cho cửa hàng vừa và nhỏ.**

---

## 📑 MỤC LỤC
1. [Giới thiệu](#-giới-thiệu)
2. [Tính năng nổi bật](#-tính-năng-nổi-bật)
3. [Chuẩn bị Database](#-chuẩn-bị-database-sqlite)
4. [Hướng dẫn chạy phần mềm](#-hướng-dẫn-chạy-phần-mềm)
5. [Hướng dẫn sử dụng chi tiết](#-hướng-dẫn-sử-dụng-chi-tiết)

---

## 📖 GIỚI THIỆU

Phần mềm **Quản Lý Bán Hàng** được xây dựng trên nền tảng **Java Swing** kết hợp với cơ sở dữ liệu **SQLite**. Điểm mạnh của phiên bản này là sự **Gọn nhẹ (Portable)**: Không cần cài đặt server database phức tạp, chỉ cần tải về là chạy ngay (Plug & Play).

Phần mềm hoạt động tốt trên Windows, macOS và Linux.

---

## 🌟 TÍNH NĂNG NỔI BẬT

| Chức năng                 | Mô tả chi tiết                                                                        |
|:--------------------------|:--------------------------------------------------------------------------------------|
| **📊 Thống kê Đa dạng**   | Xem báo cáo theo **Ngày, Tuần, Tháng, Quý, Năm**. Biểu đồ trực quan.                  |
| **📦 Quản lý Kho Hàng**   | Tự động trừ kho ngay khi giao dịch thành công. **Chặn bán quá số lượng tồn**.         |
| **💾 Database Nhúng**     | Sử dụng **SQLite**. Dữ liệu nằm gọn trong 1 file `.db`. Dễ dàng sao chép, backup.     |
| **🧾 Hóa đơn thông minh** | Tạo hóa đơn siêu tốc. **Hoàn kho tự động** nếu xóa hóa đơn. In hóa đơn chuyên nghiệp. |
| **🛡️ Bảo mật & An toàn** | Ứng dụng cơ chế **Transaction** đảm bảo toàn vẹn dữ liệu.                             |
| **👥 Phân quyền**         | **Admin** (Toàn quyền) và **Staff** (Hạn chế quyền xóa/sửa sâu).                      |

---

## 🛠 CHUẨN BỊ DATABASE (SQLITE)

Vì sử dụng SQLite, bạn **KHÔNG CẦN** cài đặt MySQL Server hay XAMPP.

### Cách tạo file dữ liệu:
1. Phần mềm cần một file database tên là `storedatabase.db` nằm cùng thư mục với file chạy.
2. Nếu bạn chưa có file này, hãy dùng công cụ **DB Browser for SQLite** để chạy file script tạo bảng.
   - File script nằm tại: `ResourcesPack/csdl.sql`
   - Import file sql này vào DB Browser để tạo ra file `.db`.

---

## 🚀 HƯỚNG DẪN CHẠY PHẦN MỀM

### 1. Cấu trúc thư mục chuẩn
```text
Java-Swing-App-Store-Manager/
├── ☕ StoreManager.jar      (File phần mềm chính)
└── 🗄️ storedatabase.db     (File dữ liệu SQLite)
```

### 2. Cách khởi động
* **Cách 1 (Nhanh):** Double click vào file `.jar`.
* **Cách 2 (Khuyên dùng khi lỗi):** Mở CMD tại thư mục đó và gõ:
```bash
java -jar StoreManager.jar
```

---

## 📘 HƯỚNG DẪN SỬ DỤNG CHI TIẾT

### 🔐 1. Đăng Nhập Hệ Thống
Tài khoản mặc định (nếu sử dụng file DB mẫu):

|  Vai trò  | Tài khoản | Mật khẩu | Quyền hạn                                                      |
|:---------:|:---------:|:--------:|:---------------------------------------------------------------|
| **Admin** |  `admin`  |  `123`   | Toàn quyền hệ thống (Thêm/Sửa/Xóa nhân viên, Xem báo cáo...)   |
| **Staff** |  `user1`  |  `123`   | Chỉ bán hàng, Xem danh sách, Không được xóa dữ liệu quan trọng |

### 🛒 2. Quy Trình Bán Hàng
1. **Vào tab Hóa Đơn** → Bấm <kbd>Tạo mới</kbd>.
2. **Chọn Khách hàng:** Chọn từ danh sách hoặc bấm **(+)** để thêm nhanh.
3. **Thêm Sản phẩm:**
   * Bấm <kbd>THÊM</kbd>.
   * Chọn sản phẩm (Hệ thống hiện tồn kho từ file SQLite tức thời).
   * Nhập số lượng → **Xác nhận**.
4. **Thanh toán:**
   * Kiểm tra tổng tiền.
   * Bấm <kbd>Lưu hóa đơn</kbd>.

### 🧾 3. Quản lý Hóa đơn
* **Xem chi tiết:** Click vào hóa đơn bên trái.
* **Xóa hóa đơn (Admin):** Bấm <kbd>Xóa</kbd> → Số lượng hàng sẽ tự động được cộng lại vào kho.
* **Xuất Excel:** Bấm nút <kbd>Xuất Excel</kbd> để tải về báo cáo doanh thu.

### 👥 4. Quản lý Khách hàng
* Tìm kiếm theo Tên hoặc Số điện thoại.
* Sắp xếp danh sách A-Z hoặc theo mức độ chi tiêu.

---

<div style="text-align: center;">
<b>© 2025 Copyright by Chung. All rights reserved.</b><br>
<i>Designed with ❤️ using Java Swing & SQLite.</i>
</div>
