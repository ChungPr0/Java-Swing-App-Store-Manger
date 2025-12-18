# 🛒 PHẦN MỀM QUẢN LÝ BÁN HÀNG (POS JAVA SWING)

![Java](https://img.shields.io/badge/Language-Java_17+-orange?style=for-the-badge&logo=java)
![Database](https://img.shields.io/badge/Database-MySQL-blue?style=for-the-badge&logo=mysql)
![Version](https://img.shields.io/badge/Version-1.5.0-green?style=for-the-badge)

> **Giải pháp quản lý bán hàng toàn diện, đơn giản và hiệu quả dành cho cửa hàng vừa và nhỏ.**

---

## 📑 MỤC LỤC
1. [Giới thiệu](#-giới-thiệu)
2. [Tính năng nổi bật](#-tính-năng-nổi-bật)
3. [Cài đặt môi trường (SQL)](#-cài-đặt-môi-trường-sql)
4. [Cấu hình hệ thống](#-cấu-hình-hệ-thống-quan-trọng)
5. [Hướng dẫn chạy phần mềm](#-hướng-dẫn-chạy-phần-mềm)
6. [Hướng dẫn sử dụng chi tiết](#-hướng-dẫn-sử-dụng-chi-tiết)

---

## 📖 GIỚI THIỆU

Phần mềm **Quản Lý Bán Hàng** được xây dựng trên nền tảng **Java Swing** kết hợp với cơ sở dữ liệu **MySQL**. Hệ thống tập trung vào tính chính xác trong giao dịch, quản lý chặt chẽ tồn kho và cung cấp trải nghiệm bán hàng nhanh chóng.

Phần mềm hoạt động tốt trên Windows, macOS và Linux (yêu cầu cài đặt Java).

---

## 🌟 TÍNH NĂNG NỔI BẬT

| Chức năng                 | Mô tả chi tiết                                                                                                        |
|:--------------------------|:----------------------------------------------------------------------------------------------------------------------|
| **📦 Quản lý Kho Hàng**   | Tự động trừ kho khi bán. **Chặn bán quá số lượng tồn**. Hiển thị tồn kho thực tế ngay khi chọn sản phẩm.              |
| **🧾 Hóa đơn thông minh** | Tạo hóa đơn nhanh, hỗ trợ tìm kiếm khách hàng/sản phẩm. Tự động tính tổng tiền. **Hoàn kho tự động** khi xóa hóa đơn. |
| **⚙️ Cấu hình động**      | Sử dụng file `config.properties` bên ngoài. Dễ dàng đổi mật khẩu/địa chỉ Database mà không cần sửa code.              |
| **🛡️ Bảo mật & An toàn** | Cơ chế **Transaction** (Rollback) đảm bảo dữ liệu không bị lỗi nửa vời. Ngăn chặn SQL Injection.                      |
| **👥 Quản lý Đối tượng**  | Quản lý thông tin Khách hàng, Nhân viên. Phân quyền **Admin** (Toàn quyền) và **Staff** (Hạn chế).                    |

---

## 🛠 CÀI ĐẶT MÔI TRƯỜNG (SQL)

Để phần mềm chạy được, bạn cần tạo Cơ sở dữ liệu. Hãy làm theo các bước sau:

### Bước 1: Mở trình quản lý MySQL
Sử dụng **phpMyAdmin**, **MySQL Workbench**, hoặc **HeidiSQL**.

### Bước 2: Chạy Script tạo Database
Để chạy dự án, bạn cần tạo Database bằng Script sau:

1. **[Bấm vào đây để xem file csdl.sql](https://github.com/ChungPr0/Java-Swing-App-Store-Manger/blob/master/ResourcesPack/csdl.sql)**
2. Copy toàn bộ nội dung.
3. Mở MySQL Workbench -> Paste vào và bấm nút ⚡ (Execute).

---

## ⚙️ CẤU HÌNH HỆ THỐNG (QUAN TRỌNG)

Phần mềm hoạt động dựa trên file cấu hình bên ngoài. Điều này giúp bạn dễ dàng thay đổi thông tin kết nối mà không cần can thiệp vào mã nguồn.

**Bước 1:** Tạo một file mới tên là `config.properties`.
**Bước 2:** Mở bằng Notepad (hoặc trình soạn thảo bất kỳ) và dán nội dung sau:

```properties
# --- CẤU HÌNH KẾT NỐI MYSQL ---
# Đường dẫn kết nối (bao gồm fix lỗi font tiếng Việt)
db.url=jdbc:mysql://localhost:3306/QuanLyBanHang?useSSL=false&useUnicode=true&characterEncoding=UTF-8

# Tài khoản đăng nhập MySQL
db.username=root

# Mật khẩu MySQL (Điền ngay sau dấu bằng nếu có)
db.password=
```

---

## 🚀 HƯỚNG DẪN CHẠY PHẦN MỀM

Sau khi Build dự án ra file `.jar` (ví dụ `StoreManager.jar`), bạn cần đảm bảo cấu trúc thư mục đúng như sau:

### 1. Cấu trúc thư mục chuẩn
```text
D:\PhanMemBanHang\
   ├── ☕ StoreManager.jar    (File phần mềm chính)
   └── 📄 config.properties   (File cấu hình đã tạo ở trên)
```

### 2. Cách chạy phần mềm
Bạn có thể chọn 1 trong 2 cách sau:

* **Cách 1 (Nhanh):** Double click trực tiếp vào file `StoreManager.jar`.
* **Cách 2 (Khuyên dùng):** Chạy bằng dòng lệnh để xem thông báo lỗi (nếu có).
    1.  Tại thư mục chứa file, gõ `cmd` vào thanh địa chỉ -> Nhấn **Enter**.
    2.  Gõ lệnh sau:
    ```bash
    java -jar StoreManager.jar
    ```

---

## 📘 HƯỚNG DẪN SỬ DỤNG CHI TIẾT

### 🔐 1. Đăng Nhập Hệ Thống
Khởi động phần mềm và sử dụng các tài khoản mặc định sau (hoặc tài khoản trong Database của bạn):

| Vai trò   | Tên đăng nhập | Mật khẩu | Quyền hạn                             |
|:----------|:--------------|:---------|:--------------------------------------|
| **Admin** | `admin`       | `123`    | Toàn quyền (Xóa hóa đơn, Sửa dữ liệu) |
| **Staff** | `user1`       | `123`    | Bán hàng, Tra cứu (Không được xóa)    |

### 🛒 2. Quy Trình Bán Hàng (Tạo Hóa Đơn)
Để thực hiện một giao dịch bán hàng, hãy làm theo các bước:

1.  **Vào tab Hóa Đơn** → Bấm nút <kbd>Tạo mới</kbd>.
2.  **Chọn Khách hàng:**
    * Chọn từ danh sách xổ xuống.
    * *Mẹo:* Nếu là khách mới, bấm nút **"Thêm"** nhỏ bên cạnh để tạo nhanh hồ sơ.
3.  **Thêm Sản phẩm vào giỏ:**
    * Bấm nút <kbd>THÊM</kbd> (nằm dưới bảng danh sách sản phẩm).
    * Một cửa sổ hiện ra: Chọn sản phẩm (Hệ thống sẽ hiển thị **Tồn kho hiện tại**).
    * Nhập số lượng mua → Bấm **"Xác nhận"**.
    * *Lưu ý:* Hệ thống sẽ chặn nếu bạn nhập quá số lượng tồn kho.
4.  **Chỉnh sửa giỏ hàng (Nếu cần):**
    * Chọn dòng sản phẩm trong bảng.
    * Bấm <kbd>SỬA</kbd> để đổi số lượng hoặc <kbd>XÓA</kbd> để bỏ món.
5.  **Thanh toán:**
    * Kiểm tra lại **Tổng tiền**.
    * Bấm nút <kbd>Lưu hóa đơn</kbd> để hoàn tất.
    * 👉 *Lúc này số lượng hàng trong kho mới chính thức bị trừ.*

### 🧾 3. Quản lý Hóa đơn & Hoàn trả
* **Xem chi tiết:** Chọn một hóa đơn trong danh sách bên trái, thông tin chi tiết sẽ hiện bên phải.
* **Xóa hóa đơn (Chỉ Admin):**
    * Bấm nút <kbd>Xóa Hóa Đơn</kbd>.
    * Hệ thống sẽ hỏi xác nhận và **tự động hoàn trả (cộng lại)** số lượng sản phẩm về kho.
* **In ấn:** Bấm nút <kbd>In Hóa Đơn</kbd> để xem trước phiếu in (View).

### 👥 4. Quản lý Khách hàng
* Truy cập tab **Khách hàng**.
* **Tìm kiếm:** Nhập Tên hoặc Số điện thoại vào ô tìm kiếm → Nhấn Enter.
* **Sắp xếp:** Bấm nút **Sắp xếp** để đổi kiểu xem (Tên A-Z, Khách mới/cũ).
* **Kiểm soát lỗi:** Ô nhập Số điện thoại được tích hợp bộ lọc, chỉ cho phép nhập số.

---

<div style="text-align: center;">
<b>© 2025 Copyright by Chung. All rights reserved.</b><br>
<i>Designed with using Java Swing & MySQL.</i>
</div>