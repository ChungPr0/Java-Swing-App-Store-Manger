-- 1. Tạo cơ sở dữ liệu
CREATE DATABASE QuanLyBanHang;
USE QuanLyBanHang;

-- 2. Tạo bảng NHÀ CUNG CẤP (Suppliers) trước vì Product tham chiếu đến nó
CREATE TABLE Suppliers (
    sup_ID INT PRIMARY KEY AUTO_INCREMENT,
    sup_name NVARCHAR(100) NOT NULL,
    sup_address NVARCHAR(255),
    sup_phone VARCHAR(20)
);

-- 3. Tạo bảng KHÁCH HÀNG (Customers)
CREATE TABLE Customers (
    cus_ID INT PRIMARY KEY AUTO_INCREMENT,
    cus_name NVARCHAR(100) NOT NULL,
    cus_address NVARCHAR(255),
    cus_phone VARCHAR(20)
);

-- 4. Tạo bảng NHÂN VIÊN (Staffs)
CREATE TABLE Staffs (
    sta_ID INT PRIMARY KEY AUTO_INCREMENT,
    sta_name NVARCHAR(100) NOT NULL,
    sta_date_of_birth DATE,
    sta_phone VARCHAR(20),
    sta_address NVARCHAR(255)
);

-- 5. Tạo bảng SẢN PHẨM (Products) - Tham chiếu đến Suppliers
CREATE TABLE Products (
    pro_ID INT PRIMARY KEY AUTO_INCREMENT,
    pro_name NVARCHAR(100) NOT NULL,
    pro_price DECIMAL(18, 2) NOT NULL, -- Dùng Decimal cho tiền tệ
    pro_count INT DEFAULT 0,
    pro_type INT NOT NULL,
    sup_ID INT,
    FOREIGN KEY (sup_ID) REFERENCES Suppliers(sup_ID)
);

-- 6. Tạo bảng HÓA ĐƠN (Invoices) - Tham chiếu Staffs và Customers
CREATE TABLE Invoices (
    inv_ID INT PRIMARY KEY AUTO_INCREMENT,
    sta_ID INT,
    cus_ID INT,
    inv_price DECIMAL(18, 2),
    FOREIGN KEY (sta_ID) REFERENCES Staffs(sta_ID),
    FOREIGN KEY (cus_ID) REFERENCES Customers(cus_ID)
);

-- 7. Tạo bảng CHI TIẾT HÓA ĐƠN (Invoice_details) - Tham chiếu Invoices và Products
CREATE TABLE Invoice_details (
    ind_ID INT PRIMARY KEY AUTO_INCREMENT,
    inv_ID INT,
    pro_ID INT,
    ind_count INT NOT NULL,
    FOREIGN KEY (inv_ID) REFERENCES Invoices(inv_ID),
    FOREIGN KEY (pro_ID) REFERENCES Products(pro_ID)
);

-- 8. Tạo bảng Accounts
CREATE TABLE Accounts (
    acc_ID INT PRIMARY KEY AUTO_INCREMENT,
    acc_name varchar(20),
    acc_pass varchar(255)
);

INSERT INTO Accounts (acc_name, acc_pass) 
VALUES ('admin', '12345678');

INSERT INTO Staffs (sta_name, sta_date_of_birth, sta_phone, sta_address)
VALUES 
INSERT INTO Staffs (sta_name, sta_date_of_birth, sta_phone, sta_address) VALUES
(N'Trần Thị Hoa', '1992-08-21', '0902345678', N'Hồ Chí Minh'),
(N'Lê Văn Bình', '1988-12-05', '0913456789', N'Đà Nẵng'),
(N'Phạm Thu Trang', '1995-03-18', '0924567890', N'Hải Phòng'),
(N'Hoàng Đức Minh', '1991-11-10', '0935678901', N'Cần Thơ'),
(N'Vũ Thị Hạnh', '1993-07-22', '0946789012', N'Huế'),
(N'Đỗ Ngọc Tuấn', '1989-02-14', '0957890123', N'Quảng Ninh'),
(N'Bùi Thị Mai', '1996-09-30', '0968901234', N'Nha Trang'),
(N'Nguyễn Anh Dũng', '1994-04-25', '0979012345', N'Buôn Ma Thuột'),
(N'Đặng Thị Ngọc', '1990-01-19', '0980123456', N'Vũng Tàu'),
(N'Phan Văn Khánh', '1987-06-11', '0901122334', N'Nam Định'),
(N'Dương Thị Kim', '1998-10-09', '0912233445', N'Thanh Hóa'),
(N'Tạ Minh Hoàng', '1993-12-28', '0923344556', N'Hải Dương'),
(N'Nguyễn Quỳnh Chi', '1997-07-07', '0934455667', N'Thái Bình'),
(N'Hồ Văn Phúc', '1992-03-03', '0945566778', N'Hà Tĩnh'),
(N'Võ Thị Loan', '1995-05-20', '0956677889', N'Quảng Nam'),
(N'Lý Thành Đạt', '1989-09-17', '0967788990', N'Lào Cai'),
(N'Cao Thị Yến', '1996-11-12', '0978899001', N'Phú Thọ'),
(N'Ngô Văn Tài', '1991-01-29', '0989900112', N'Gia Lai'),
(N'Đinh Thị Hòa', '1988-04-04', '0902211334', N'Kon Tum'),
(N'Phùng Minh Khang', '1993-06-26', '0913322445', N'Sóc Trăng'),
(N'Mai Thị Ngân', '1997-02-18', '0924433556', N'Trà Vinh'),
(N'Đoàn Văn Huy', '1990-08-14', '0935544667', N'Bạc Liêu'),
(N'Kiều Thị Phương', '1994-09-23', '0946655778', N'Long An'),
(N'Hoàng Gia Bảo', '1995-01-06', '0957766889', N'Quảng Bình'),
(N'Vũ Thị Thu', '1992-12-30', '0968877990', N'Hòa Bình'),
(N'Trịnh Văn Toàn', '1989-05-09', '0979988001', N'Lạng Sơn'),
(N'Chu Thị Nhung', '1998-07-15', '0980099112', N'Tuyên Quang'),
(N'Trần Minh Quân', '1991-03-27', '0903344556', N'Yên Bái'),
(N'Lã Thị Hương', '1996-10-08', '0914455667', N'Bắc Giang');