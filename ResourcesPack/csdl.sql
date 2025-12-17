-- =====================
-- 1. THIẾT LẬP DATABASE
-- =====================
DROP DATABASE IF EXISTS QuanLyBanHang;
CREATE DATABASE QuanLyBanHang CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE QuanLyBanHang;

-- ====================
-- 2. TẠO CẤU TRÚC BẢNG
-- ====================
CREATE TABLE Suppliers (
    sup_ID INT PRIMARY KEY AUTO_INCREMENT,
    sup_name VARCHAR(100) NOT NULL,
    sup_address VARCHAR(255),
    sup_phone VARCHAR(20)
);

CREATE TABLE Customers (
    cus_ID INT PRIMARY KEY AUTO_INCREMENT,
    cus_name VARCHAR(100) NOT NULL,
    cus_address VARCHAR(255),
    cus_phone VARCHAR(20)
);

CREATE TABLE Staffs (
    sta_ID INT PRIMARY KEY AUTO_INCREMENT,
    sta_name VARCHAR(100) NOT NULL,
    sta_date_of_birth DATE NOT NULL,
    sta_phone VARCHAR(20) NOT NULL,
    sta_address VARCHAR(255) NOT NULL,
    sta_username VARCHAR(50) UNIQUE,
    sta_password VARCHAR(50),
    sta_role VARCHAR(20) DEFAULT 'Staff'
);

CREATE TABLE ProductTypes (
    type_ID INT PRIMARY KEY AUTO_INCREMENT,
    type_name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE Products (
    pro_ID INT PRIMARY KEY AUTO_INCREMENT,
    pro_name VARCHAR(100) NOT NULL,
    pro_price DECIMAL(18, 0) NOT NULL,
    pro_count INT DEFAULT 0,
    type_ID INT,
    sup_ID INT,
    FOREIGN KEY (type_ID) REFERENCES ProductTypes(type_ID),
    FOREIGN KEY (sup_ID) REFERENCES Suppliers(sup_ID)
);

CREATE TABLE Invoices (
    inv_ID INT PRIMARY KEY AUTO_INCREMENT,
    sta_ID INT,
    cus_ID INT,
    inv_price DECIMAL(18, 0) DEFAULT 0,
    inv_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sta_ID) REFERENCES Staffs(sta_ID),
    FOREIGN KEY (cus_ID) REFERENCES Customers(cus_ID)
);

CREATE TABLE Invoice_details (
    ind_ID INT PRIMARY KEY AUTO_INCREMENT,
    inv_ID INT,
    pro_ID INT,
    ind_count INT NOT NULL,
    unit_price DECIMAL(18, 0) DEFAULT 0,
    FOREIGN KEY (inv_ID) REFERENCES Invoices(inv_ID) ON DELETE CASCADE,
    FOREIGN KEY (pro_ID) REFERENCES Products(pro_ID)
);

-- ===============
-- 3. CHÈN DỮ LIỆU
-- ===============
SET FOREIGN_KEY_CHECKS = 0; -- Tắt kiểm tra khóa ngoại tạm thời để insert nhanh

-- -----------------
-- 3.1. NHÀ CUNG CẤP
-- -----------------
INSERT INTO Suppliers (sup_name, sup_address, sup_phone) VALUES
('Dell Việt Nam', 'Hà Nội', '1800545455'), ('Asus Corp', 'TP.HCM', '1900555581'), ('Samsung Vina', 'Bắc Ninh', '0988777666'), ('Apple Distributor', 'TP.HCM', '02833334444'), ('HP Việt Nam', 'Hà Nội', '18006688'),
('Lenovo Group', 'Đà Nẵng', '0236123456'), ('Sony Electronics', 'TP.HCM', '1800588885'), ('LG Việt Nam', 'Hà Nội', '18001503'), ('MSI Gaming', 'TP.HCM', '02877778888'), ('Gigabyte VN', 'Hà Nội', '02433332222'),
('Kingston Tech', 'TP.HCM', '02899990000'), ('Logitech VN', 'TP.HCM', '02811112222'), ('Intel VN', 'TP.HCM', '02855556666'), ('AMD VN', 'Hà Nội', '02488889999'), ('Western Digital', 'TP.HCM', '1800555555'),
('Seagate VN', 'Hà Nội', '1800888888'), ('TP-Link VN', 'TP.HCM', '02866667777'), ('Canon Marketing', 'TP.HCM', '02838200466'), ('FPT Trading', 'Hà Nội', '02473008888'), ('Digiworld', 'TP.HCM', '02839290059');

-- Chèn thêm 80 Nhà cung cấp theo mẫu
INSERT INTO Suppliers (sup_name, sup_address, sup_phone) VALUES
('Nhà Cung Cấp 21', 'Địa chỉ 21', '0900000021'), ('Nhà Cung Cấp 22', 'Địa chỉ 22', '0900000022'), ('Nhà Cung Cấp 23', 'Địa chỉ 23', '0900000023'), ('Nhà Cung Cấp 24', 'Địa chỉ 24', '0900000024'), ('Nhà Cung Cấp 25', 'Địa chỉ 25', '0900000025'),
('Nhà Cung Cấp 26', 'Địa chỉ 26', '0900000026'), ('Nhà Cung Cấp 27', 'Địa chỉ 27', '0900000027'), ('Nhà Cung Cấp 28', 'Địa chỉ 28', '0900000028'), ('Nhà Cung Cấp 29', 'Địa chỉ 29', '0900000029'), ('Nhà Cung Cấp 30', 'Địa chỉ 30', '0900000030'),
('Nhà Cung Cấp 31', 'Địa chỉ 31', '0900000031'), ('Nhà Cung Cấp 32', 'Địa chỉ 32', '0900000032'), ('Nhà Cung Cấp 33', 'Địa chỉ 33', '0900000033'), ('Nhà Cung Cấp 34', 'Địa chỉ 34', '0900000034'), ('Nhà Cung Cấp 35', 'Địa chỉ 35', '0900000035'),
('Nhà Cung Cấp 36', 'Địa chỉ 36', '0900000036'), ('Nhà Cung Cấp 37', 'Địa chỉ 37', '0900000037'), ('Nhà Cung Cấp 38', 'Địa chỉ 38', '0900000038'), ('Nhà Cung Cấp 39', 'Địa chỉ 39', '0900000039'), ('Nhà Cung Cấp 40', 'Địa chỉ 40', '0900000040'),
('Nhà Cung Cấp 41', 'Địa chỉ 41', '0900000041'), ('Nhà Cung Cấp 42', 'Địa chỉ 42', '0900000042'), ('Nhà Cung Cấp 43', 'Địa chỉ 43', '0900000043'), ('Nhà Cung Cấp 44', 'Địa chỉ 44', '0900000044'), ('Nhà Cung Cấp 45', 'Địa chỉ 45', '0900000045'),
('Nhà Cung Cấp 46', 'Địa chỉ 46', '0900000046'), ('Nhà Cung Cấp 47', 'Địa chỉ 47', '0900000047'), ('Nhà Cung Cấp 48', 'Địa chỉ 48', '0900000048'), ('Nhà Cung Cấp 49', 'Địa chỉ 49', '0900000049'), ('Nhà Cung Cấp 50', 'Địa chỉ 50', '0900000050'),
('Nhà Cung Cấp 51', 'Địa chỉ 51', '0900000051'), ('Nhà Cung Cấp 52', 'Địa chỉ 52', '0900000052'), ('Nhà Cung Cấp 53', 'Địa chỉ 53', '0900000053'), ('Nhà Cung Cấp 54', 'Địa chỉ 54', '0900000054'), ('Nhà Cung Cấp 55', 'Địa chỉ 55', '0900000055'),
('Nhà Cung Cấp 56', 'Địa chỉ 56', '0900000056'), ('Nhà Cung Cấp 57', 'Địa chỉ 57', '0900000057'), ('Nhà Cung Cấp 58', 'Địa chỉ 58', '0900000058'), ('Nhà Cung Cấp 59', 'Địa chỉ 59', '0900000059'), ('Nhà Cung Cấp 60', 'Địa chỉ 60', '0900000060'),
('Nhà Cung Cấp 61', 'Địa chỉ 61', '0900000061'), ('Nhà Cung Cấp 62', 'Địa chỉ 62', '0900000062'), ('Nhà Cung Cấp 63', 'Địa chỉ 63', '0900000063'), ('Nhà Cung Cấp 64', 'Địa chỉ 64', '0900000064'), ('Nhà Cung Cấp 65', 'Địa chỉ 65', '0900000065'),
('Nhà Cung Cấp 66', 'Địa chỉ 66', '0900000066'), ('Nhà Cung Cấp 67', 'Địa chỉ 67', '0900000067'), ('Nhà Cung Cấp 68', 'Địa chỉ 68', '0900000068'), ('Nhà Cung Cấp 69', 'Địa chỉ 69', '0900000069'), ('Nhà Cung Cấp 70', 'Địa chỉ 70', '0900000070'),
('Nhà Cung Cấp 71', 'Địa chỉ 71', '0900000071'), ('Nhà Cung Cấp 72', 'Địa chỉ 72', '0900000072'), ('Nhà Cung Cấp 73', 'Địa chỉ 73', '0900000073'), ('Nhà Cung Cấp 74', 'Địa chỉ 74', '0900000074'), ('Nhà Cung Cấp 75', 'Địa chỉ 75', '0900000075'),
('Nhà Cung Cấp 76', 'Địa chỉ 76', '0900000076'), ('Nhà Cung Cấp 77', 'Địa chỉ 77', '0900000077'), ('Nhà Cung Cấp 78', 'Địa chỉ 78', '0900000078'), ('Nhà Cung Cấp 79', 'Địa chỉ 79', '0900000079'), ('Nhà Cung Cấp 80', 'Địa chỉ 80', '0900000080'),
('Nhà Cung Cấp 81', 'Địa chỉ 81', '0900000081'), ('Nhà Cung Cấp 82', 'Địa chỉ 82', '0900000082'), ('Nhà Cung Cấp 83', 'Địa chỉ 83', '0900000083'), ('Nhà Cung Cấp 84', 'Địa chỉ 84', '0900000084'), ('Nhà Cung Cấp 85', 'Địa chỉ 85', '0900000085'),
('Nhà Cung Cấp 86', 'Địa chỉ 86', '0900000086'), ('Nhà Cung Cấp 87', 'Địa chỉ 87', '0900000087'), ('Nhà Cung Cấp 88', 'Địa chỉ 88', '0900000088'), ('Nhà Cung Cấp 89', 'Địa chỉ 89', '0900000089'), ('Nhà Cung Cấp 90', 'Địa chỉ 90', '0900000090'),
('Nhà Cung Cấp 91', 'Địa chỉ 91', '0900000091'), ('Nhà Cung Cấp 92', 'Địa chỉ 92', '0900000092'), ('Nhà Cung Cấp 93', 'Địa chỉ 93', '0900000093'), ('Nhà Cung Cấp 94', 'Địa chỉ 94', '0900000094'), ('Nhà Cung Cấp 95', 'Địa chỉ 95', '0900000095'),
('Nhà Cung Cấp 96', 'Địa chỉ 96', '0900000096'), ('Nhà Cung Cấp 97', 'Địa chỉ 97', '0900000097'), ('Nhà Cung Cấp 98', 'Địa chỉ 98', '0900000098'), ('Nhà Cung Cấp 99', 'Địa chỉ 99', '0900000099'), ('Nhà Cung Cấp 100', 'Địa chỉ 100', '0900000100');

-- ---------------
-- 3.2. KHÁCH HÀNG
-- ---------------
INSERT INTO Customers (cus_name, cus_address, cus_phone) VALUES
('Nguyễn Văn Khách 1', 'Ba Đình, Hà Nội', '0988111001'), ('Trần Thị Khách 2', 'Hoàn Kiếm, Hà Nội', '0988111002'), ('Lê Văn Khách 3', 'Đống Đa, Hà Nội', '0988111003'), ('Phạm Thị Khách 4', 'Cầu Giấy, Hà Nội', '0988111004'), ('Hoàng Văn Khách 5', 'Thanh Xuân, Hà Nội', '0988111005'),
('Vũ Thị Khách 6', 'Quận 1, TP.HCM', '0988111006'), ('Đặng Văn Khách 7', 'Quận 3, TP.HCM', '0988111007'), ('Bùi Thị Khách 8', 'Quận 5, TP.HCM', '0988111008'), ('Đỗ Văn Khách 9', 'Quận 7, TP.HCM', '0988111009'), ('Hồ Thị Khách 10', 'Thủ Đức, TP.HCM', '0988111010'),
('Ngô Văn Khách 11', 'Hải Châu, Đà Nẵng', '0988111011'), ('Dương Thị Khách 12', 'Sơn Trà, Đà Nẵng', '0988111012'), ('Lý Văn Khách 13', 'Ngũ Hành Sơn, Đà Nẵng', '0988111013'), ('Trương Thị Khách 14', 'Ninh Kiều, Cần Thơ', '0988111014'), ('Đinh Văn Khách 15', 'Hồng Bàng, Hải Phòng', '0988111015'),
('Lâm Thị Khách 16', 'Biên Hòa, Đồng Nai', '0988111016'), ('Mai Văn Khách 17', 'Thủ Dầu Một, Bình Dương', '0988111017'), ('Cao Thị Khách 18', 'Vũng Tàu', '0988111018'), ('Phan Văn Khách 19', 'Nha Trang, Khánh Hòa', '0988111019'), ('Hà Thị Khách 20', 'Buôn Ma Thuột, Đắk Lắk', '0988111020');

INSERT INTO Customers (cus_name, cus_address, cus_phone) VALUES
('Khách Hàng 21', 'Địa chỉ 21', '0911111021'), ('Khách Hàng 22', 'Địa chỉ 22', '0911111022'), ('Khách Hàng 23', 'Địa chỉ 23', '0911111023'), ('Khách Hàng 24', 'Địa chỉ 24', '0911111024'), ('Khách Hàng 25', 'Địa chỉ 25', '0911111025'),
('Khách Hàng 26', 'Địa chỉ 26', '0911111026'), ('Khách Hàng 27', 'Địa chỉ 27', '0911111027'), ('Khách Hàng 28', 'Địa chỉ 28', '0911111028'), ('Khách Hàng 29', 'Địa chỉ 29', '0911111029'), ('Khách Hàng 30', 'Địa chỉ 30', '0911111030'),
('Khách Hàng 31', 'Địa chỉ 31', '0911111031'), ('Khách Hàng 32', 'Địa chỉ 32', '0911111032'), ('Khách Hàng 33', 'Địa chỉ 33', '0911111033'), ('Khách Hàng 34', 'Địa chỉ 34', '0911111034'), ('Khách Hàng 35', 'Địa chỉ 35', '0911111035'),
('Khách Hàng 36', 'Địa chỉ 36', '0911111036'), ('Khách Hàng 37', 'Địa chỉ 37', '0911111037'), ('Khách Hàng 38', 'Địa chỉ 38', '0911111038'), ('Khách Hàng 39', 'Địa chỉ 39', '0911111039'), ('Khách Hàng 40', 'Địa chỉ 40', '0911111040'),
('Khách Hàng 41', 'Địa chỉ 41', '0911111041'), ('Khách Hàng 42', 'Địa chỉ 42', '0911111042'), ('Khách Hàng 43', 'Địa chỉ 43', '0911111043'), ('Khách Hàng 44', 'Địa chỉ 44', '0911111044'), ('Khách Hàng 45', 'Địa chỉ 45', '0911111045'),
('Khách Hàng 46', 'Địa chỉ 46', '0911111046'), ('Khách Hàng 47', 'Địa chỉ 47', '0911111047'), ('Khách Hàng 48', 'Địa chỉ 48', '0911111048'), ('Khách Hàng 49', 'Địa chỉ 49', '0911111049'), ('Khách Hàng 50', 'Địa chỉ 50', '0911111050'),
('Khách Hàng 51', 'Địa chỉ 51', '0911111051'), ('Khách Hàng 52', 'Địa chỉ 52', '0911111052'), ('Khách Hàng 53', 'Địa chỉ 53', '0911111053'), ('Khách Hàng 54', 'Địa chỉ 54', '0911111054'), ('Khách Hàng 55', 'Địa chỉ 55', '0911111055'),
('Khách Hàng 56', 'Địa chỉ 56', '0911111056'), ('Khách Hàng 57', 'Địa chỉ 57', '0911111057'), ('Khách Hàng 58', 'Địa chỉ 58', '0911111058'), ('Khách Hàng 59', 'Địa chỉ 59', '0911111059'), ('Khách Hàng 60', 'Địa chỉ 60', '0911111060'),
('Khách Hàng 61', 'Địa chỉ 61', '0911111061'), ('Khách Hàng 62', 'Địa chỉ 62', '0911111062'), ('Khách Hàng 63', 'Địa chỉ 63', '0911111063'), ('Khách Hàng 64', 'Địa chỉ 64', '0911111064'), ('Khách Hàng 65', 'Địa chỉ 65', '0911111065'),
('Khách Hàng 66', 'Địa chỉ 66', '0911111066'), ('Khách Hàng 67', 'Địa chỉ 67', '0911111067'), ('Khách Hàng 68', 'Địa chỉ 68', '0911111068'), ('Khách Hàng 69', 'Địa chỉ 69', '0911111069'), ('Khách Hàng 70', 'Địa chỉ 70', '0911111070'),
('Khách Hàng 71', 'Địa chỉ 71', '0911111071'), ('Khách Hàng 72', 'Địa chỉ 72', '0911111072'), ('Khách Hàng 73', 'Địa chỉ 73', '0911111073'), ('Khách Hàng 74', 'Địa chỉ 74', '0911111074'), ('Khách Hàng 75', 'Địa chỉ 75', '0911111075'),
('Khách Hàng 76', 'Địa chỉ 76', '0911111076'), ('Khách Hàng 77', 'Địa chỉ 77', '0911111077'), ('Khách Hàng 78', 'Địa chỉ 78', '0911111078'), ('Khách Hàng 79', 'Địa chỉ 79', '0911111079'), ('Khách Hàng 80', 'Địa chỉ 80', '0911111080'),
('Khách Hàng 81', 'Địa chỉ 81', '0911111081'), ('Khách Hàng 82', 'Địa chỉ 82', '0911111082'), ('Khách Hàng 83', 'Địa chỉ 83', '0911111083'), ('Khách Hàng 84', 'Địa chỉ 84', '0911111084'), ('Khách Hàng 85', 'Địa chỉ 85', '0911111085'),
('Khách Hàng 86', 'Địa chỉ 86', '0911111086'), ('Khách Hàng 87', 'Địa chỉ 87', '0911111087'), ('Khách Hàng 88', 'Địa chỉ 88', '0911111088'), ('Khách Hàng 89', 'Địa chỉ 89', '0911111089'), ('Khách Hàng 90', 'Địa chỉ 90', '0911111090'),
('Khách Hàng 91', 'Địa chỉ 91', '0911111091'), ('Khách Hàng 92', 'Địa chỉ 92', '0911111092'), ('Khách Hàng 93', 'Địa chỉ 93', '0911111093'), ('Khách Hàng 94', 'Địa chỉ 94', '0911111094'), ('Khách Hàng 95', 'Địa chỉ 95', '0911111095'),
('Khách Hàng 96', 'Địa chỉ 96', '0911111096'), ('Khách Hàng 97', 'Địa chỉ 97', '0911111097'), ('Khách Hàng 98', 'Địa chỉ 98', '0911111098'), ('Khách Hàng 99', 'Địa chỉ 99', '0911111099'), ('Khách Hàng 100', 'Địa chỉ 100', '0911111100');

-- --------------
-- 3.3. NHÂN VIÊN
-- --------------
INSERT INTO Staffs (sta_name, sta_date_of_birth, sta_phone, sta_address, sta_username, sta_password, sta_role) VALUES
('Nguyễn Quản Lý', '1990-01-01', '0901000000', 'Hà Nội', 'admin', '123', 'Admin'), ('Trần Văn A', '1995-02-15', '0901000001', 'Hà Nội', 'user1', '123', 'Staff'), ('Lê Thị B', '1996-03-20', '0901000002', 'TP.HCM', 'user2', '123', 'Staff'), ('Phạm Văn C', '1997-04-25', '0901000003', 'Đà Nẵng', 'user3', '123', 'Staff'), ('Hoàng Thị D', '1998-05-30', '0901000004', 'Cần Thơ', 'user4', '123', 'Staff'),
('Vũ Văn E', '1999-06-05', '0901000005', 'Hải Phòng', 'user5', '123', 'Staff'), ('Đặng Thị F', '2000-07-10', '0901000006', 'Hà Nội', 'user6', '123', 'Staff'), ('Bùi Văn G', '1991-08-15', '0901000007', 'TP.HCM', 'user7', '123', 'Staff'), ('Đỗ Thị H', '1992-09-20', '0901000008', 'Đà Nẵng', 'user8', '123', 'Staff'), ('Hồ Văn I', '1993-10-25', '0901000009', 'Nha Trang', 'user9', '123', 'Staff'),
('Ngô Thị K', '1994-11-30', '0901000010', 'Huế', 'user10', '123', 'Staff'), ('Dương Văn L', '1995-12-05', '0901000011', 'Vinh', 'user11', '123', 'Staff'), ('Lý Thị M', '1996-01-10', '0901000012', 'Hà Nội', 'user12', '123', 'Staff'), ('Trương Văn N', '1997-02-15', '0901000013', 'TP.HCM', 'user13', '123', 'Staff'), ('Đinh Thị O', '1998-03-20', '0901000014', 'Cần Thơ', 'user14', '123', 'Staff'),
('Lâm Văn P', '1999-04-25', '0901000015', 'Hải Dương', 'user15', '123', 'Staff'), ('Mai Thị Q', '2000-05-30', '0901000016', 'Thái Bình', 'user16', '123', 'Staff'), ('Cao Văn R', '1991-06-05', '0901000017', 'Nam Định', 'user17', '123', 'Staff'), ('Phan Thị S', '1992-07-10', '0901000018', 'Ninh Bình', 'user18', '123', 'Staff'), ('Hà Văn T', '1993-08-15', '0901000019', 'Thanh Hóa', 'user19', '123', 'Staff');

INSERT INTO Staffs (sta_name, sta_date_of_birth, sta_phone, sta_address, sta_username, sta_password, sta_role) VALUES
('Nhân Viên 21', '2000-01-01', '0900000021', 'TPHCM', 'user21', '123', 'Staff'), ('Nhân Viên 22', '2000-01-01', '0900000022', 'TPHCM', 'user22', '123', 'Staff'), ('Nhân Viên 23', '2000-01-01', '0900000023', 'TPHCM', 'user23', '123', 'Staff'), ('Nhân Viên 24', '2000-01-01', '0900000024', 'TPHCM', 'user24', '123', 'Staff'), ('Nhân Viên 25', '2000-01-01', '0900000025', 'TPHCM', 'user25', '123', 'Staff'),
('Nhân Viên 26', '2000-01-01', '0900000026', 'TPHCM', 'user26', '123', 'Staff'), ('Nhân Viên 27', '2000-01-01', '0900000027', 'TPHCM', 'user27', '123', 'Staff'), ('Nhân Viên 28', '2000-01-01', '0900000028', 'TPHCM', 'user28', '123', 'Staff'), ('Nhân Viên 29', '2000-01-01', '0900000029', 'TPHCM', 'user29', '123', 'Staff'), ('Nhân Viên 30', '2000-01-01', '0900000030', 'TPHCM', 'user30', '123', 'Staff'),
('Nhân Viên 31', '2000-01-01', '0900000031', 'TPHCM', 'user31', '123', 'Staff'), ('Nhân Viên 32', '2000-01-01', '0900000032', 'TPHCM', 'user32', '123', 'Staff'), ('Nhân Viên 33', '2000-01-01', '0900000033', 'TPHCM', 'user33', '123', 'Staff'), ('Nhân Viên 34', '2000-01-01', '0900000034', 'TPHCM', 'user34', '123', 'Staff'), ('Nhân Viên 35', '2000-01-01', '0900000035', 'TPHCM', 'user35', '123', 'Staff'),
('Nhân Viên 36', '2000-01-01', '0900000036', 'TPHCM', 'user36', '123', 'Staff'), ('Nhân Viên 37', '2000-01-01', '0900000037', 'TPHCM', 'user37', '123', 'Staff'), ('Nhân Viên 38', '2000-01-01', '0900000038', 'TPHCM', 'user38', '123', 'Staff'), ('Nhân Viên 39', '2000-01-01', '0900000039', 'TPHCM', 'user39', '123', 'Staff'), ('Nhân Viên 40', '2000-01-01', '0900000040', 'TPHCM', 'user40', '123', 'Staff'),
('Nhân Viên 41', '2000-01-01', '0900000041', 'TPHCM', 'user41', '123', 'Staff'), ('Nhân Viên 42', '2000-01-01', '0900000042', 'TPHCM', 'user42', '123', 'Staff'), ('Nhân Viên 43', '2000-01-01', '0900000043', 'TPHCM', 'user43', '123', 'Staff'), ('Nhân Viên 44', '2000-01-01', '0900000044', 'TPHCM', 'user44', '123', 'Staff'), ('Nhân Viên 45', '2000-01-01', '0900000045', 'TPHCM', 'user45', '123', 'Staff'),
('Nhân Viên 46', '2000-01-01', '0900000046', 'TPHCM', 'user46', '123', 'Staff'), ('Nhân Viên 47', '2000-01-01', '0900000047', 'TPHCM', 'user47', '123', 'Staff'), ('Nhân Viên 48', '2000-01-01', '0900000048', 'TPHCM', 'user48', '123', 'Staff'), ('Nhân Viên 49', '2000-01-01', '0900000049', 'TPHCM', 'user49', '123', 'Staff'), ('Nhân Viên 50', '2000-01-01', '0900000050', 'TPHCM', 'user50', '123', 'Staff'),
('Nhân Viên 51', '2000-01-01', '0900000051', 'TPHCM', 'user51', '123', 'Staff'), ('Nhân Viên 52', '2000-01-01', '0900000052', 'TPHCM', 'user52', '123', 'Staff'), ('Nhân Viên 53', '2000-01-01', '0900000053', 'TPHCM', 'user53', '123', 'Staff'), ('Nhân Viên 54', '2000-01-01', '0900000054', 'TPHCM', 'user54', '123', 'Staff'), ('Nhân Viên 55', '2000-01-01', '0900000055', 'TPHCM', 'user55', '123', 'Staff'),
('Nhân Viên 56', '2000-01-01', '0900000056', 'TPHCM', 'user56', '123', 'Staff'), ('Nhân Viên 57', '2000-01-01', '0900000057', 'TPHCM', 'user57', '123', 'Staff'), ('Nhân Viên 58', '2000-01-01', '0900000058', 'TPHCM', 'user58', '123', 'Staff'), ('Nhân Viên 59', '2000-01-01', '0900000059', 'TPHCM', 'user59', '123', 'Staff'), ('Nhân Viên 60', '2000-01-01', '0900000060', 'TPHCM', 'user60', '123', 'Staff'),
('Nhân Viên 61', '2000-01-01', '0900000061', 'TPHCM', 'user61', '123', 'Staff'), ('Nhân Viên 62', '2000-01-01', '0900000062', 'TPHCM', 'user62', '123', 'Staff'), ('Nhân Viên 63', '2000-01-01', '0900000063', 'TPHCM', 'user63', '123', 'Staff'), ('Nhân Viên 64', '2000-01-01', '0900000064', 'TPHCM', 'user64', '123', 'Staff'), ('Nhân Viên 65', '2000-01-01', '0900000065', 'TPHCM', 'user65', '123', 'Staff'),
('Nhân Viên 66', '2000-01-01', '0900000066', 'TPHCM', 'user66', '123', 'Staff'), ('Nhân Viên 67', '2000-01-01', '0900000067', 'TPHCM', 'user67', '123', 'Staff'), ('Nhân Viên 68', '2000-01-01', '0900000068', 'TPHCM', 'user68', '123', 'Staff'), ('Nhân Viên 69', '2000-01-01', '0900000069', 'TPHCM', 'user69', '123', 'Staff'), ('Nhân Viên 70', '2000-01-01', '0900000070', 'TPHCM', 'user70', '123', 'Staff'),
('Nhân Viên 71', '2000-01-01', '0900000071', 'TPHCM', 'user71', '123', 'Staff'), ('Nhân Viên 72', '2000-01-01', '0900000072', 'TPHCM', 'user72', '123', 'Staff'), ('Nhân Viên 73', '2000-01-01', '0900000073', 'TPHCM', 'user73', '123', 'Staff'), ('Nhân Viên 74', '2000-01-01', '0900000074', 'TPHCM', 'user74', '123', 'Staff'), ('Nhân Viên 75', '2000-01-01', '0900000075', 'TPHCM', 'user75', '123', 'Staff'),
('Nhân Viên 76', '2000-01-01', '0900000076', 'TPHCM', 'user76', '123', 'Staff'), ('Nhân Viên 77', '2000-01-01', '0900000077', 'TPHCM', 'user77', '123', 'Staff'), ('Nhân Viên 78', '2000-01-01', '0900000078', 'TPHCM', 'user78', '123', 'Staff'), ('Nhân Viên 79', '2000-01-01', '0900000079', 'TPHCM', 'user79', '123', 'Staff'), ('Nhân Viên 80', '2000-01-01', '0900000080', 'TPHCM', 'user80', '123', 'Staff'),
('Nhân Viên 81', '2000-01-01', '0900000081', 'TPHCM', 'user81', '123', 'Staff'), ('Nhân Viên 82', '2000-01-01', '0900000082', 'TPHCM', 'user82', '123', 'Staff'), ('Nhân Viên 83', '2000-01-01', '0900000083', 'TPHCM', 'user83', '123', 'Staff'), ('Nhân Viên 84', '2000-01-01', '0900000084', 'TPHCM', 'user84', '123', 'Staff'), ('Nhân Viên 85', '2000-01-01', '0900000085', 'TPHCM', 'user85', '123', 'Staff'),
('Nhân Viên 86', '2000-01-01', '0900000086', 'TPHCM', 'user86', '123', 'Staff'), ('Nhân Viên 87', '2000-01-01', '0900000087', 'TPHCM', 'user87', '123', 'Staff'), ('Nhân Viên 88', '2000-01-01', '0900000088', 'TPHCM', 'user88', '123', 'Staff'), ('Nhân Viên 89', '2000-01-01', '0900000089', 'TPHCM', 'user89', '123', 'Staff'), ('Nhân Viên 90', '2000-01-01', '0900000090', 'TPHCM', 'user90', '123', 'Staff'),
('Nhân Viên 91', '2000-01-01', '0900000091', 'TPHCM', 'user91', '123', 'Staff'), ('Nhân Viên 92', '2000-01-01', '0900000092', 'TPHCM', 'user92', '123', 'Staff'), ('Nhân Viên 93', '2000-01-01', '0900000093', 'TPHCM', 'user93', '123', 'Staff'), ('Nhân Viên 94', '2000-01-01', '0900000094', 'TPHCM', 'user94', '123', 'Staff'), ('Nhân Viên 95', '2000-01-01', '0900000095', 'TPHCM', 'user95', '123', 'Staff'),
('Nhân Viên 96', '2000-01-01', '0900000096', 'TPHCM', 'user96', '123', 'Staff'), ('Nhân Viên 97', '2000-01-01', '0900000097', 'TPHCM', 'user97', '123', 'Staff'), ('Nhân Viên 98', '2000-01-01', '0900000098', 'TPHCM', 'user98', '123', 'Staff'), ('Nhân Viên 99', '2000-01-01', '0900000099', 'TPHCM', 'user99', '123', 'Staff'), ('Nhân Viên 100', '2000-01-01', '0900000100', 'TPHCM', 'user100', '123', 'Staff');

-- ------------------
-- 3.4. LOẠI SẢN PHẨM
-- ------------------
INSERT INTO ProductTypes (type_name) VALUES
('Laptop Văn Phòng'), ('Laptop Gaming'), ('Macbook'), ('Điện thoại iPhone'), ('Điện thoại Android'), ('Máy tính bảng'), ('Đồng hồ thông minh'), ('Tai nghe'), ('Loa Bluetooth'), ('Bàn phím'),
('Chuột máy tính'), ('Màn hình'), ('Ram - Bộ nhớ'), ('Ổ cứng SSD'), ('VGA - Card màn hình'), ('Mainboard'), ('Case - Vỏ máy'), ('Nguồn máy tính'), ('Phần mềm'), ('Camera an ninh');

INSERT INTO ProductTypes (type_name) VALUES
('Loại Sản Phẩm 21'), ('Loại Sản Phẩm 22'), ('Loại Sản Phẩm 23'), ('Loại Sản Phẩm 24'), ('Loại Sản Phẩm 25'), ('Loại Sản Phẩm 26'), ('Loại Sản Phẩm 27'), ('Loại Sản Phẩm 28'), ('Loại Sản Phẩm 29'), ('Loại Sản Phẩm 30'),
('Loại Sản Phẩm 31'), ('Loại Sản Phẩm 32'), ('Loại Sản Phẩm 33'), ('Loại Sản Phẩm 34'), ('Loại Sản Phẩm 35'), ('Loại Sản Phẩm 36'), ('Loại Sản Phẩm 37'), ('Loại Sản Phẩm 38'), ('Loại Sản Phẩm 39'), ('Loại Sản Phẩm 40'),
('Loại Sản Phẩm 41'), ('Loại Sản Phẩm 42'), ('Loại Sản Phẩm 43'), ('Loại Sản Phẩm 44'), ('Loại Sản Phẩm 45'), ('Loại Sản Phẩm 46'), ('Loại Sản Phẩm 47'), ('Loại Sản Phẩm 48'), ('Loại Sản Phẩm 49'), ('Loại Sản Phẩm 50'),
('Loại Sản Phẩm 51'), ('Loại Sản Phẩm 52'), ('Loại Sản Phẩm 53'), ('Loại Sản Phẩm 54'), ('Loại Sản Phẩm 55'), ('Loại Sản Phẩm 56'), ('Loại Sản Phẩm 57'), ('Loại Sản Phẩm 58'), ('Loại Sản Phẩm 59'), ('Loại Sản Phẩm 60'),
('Loại Sản Phẩm 61'), ('Loại Sản Phẩm 62'), ('Loại Sản Phẩm 63'), ('Loại Sản Phẩm 64'), ('Loại Sản Phẩm 65'), ('Loại Sản Phẩm 66'), ('Loại Sản Phẩm 67'), ('Loại Sản Phẩm 68'), ('Loại Sản Phẩm 69'), ('Loại Sản Phẩm 70'),
('Loại Sản Phẩm 71'), ('Loại Sản Phẩm 72'), ('Loại Sản Phẩm 73'), ('Loại Sản Phẩm 74'), ('Loại Sản Phẩm 75'), ('Loại Sản Phẩm 76'), ('Loại Sản Phẩm 77'), ('Loại Sản Phẩm 78'), ('Loại Sản Phẩm 79'), ('Loại Sản Phẩm 80'),
('Loại Sản Phẩm 81'), ('Loại Sản Phẩm 82'), ('Loại Sản Phẩm 83'), ('Loại Sản Phẩm 84'), ('Loại Sản Phẩm 85'), ('Loại Sản Phẩm 86'), ('Loại Sản Phẩm 87'), ('Loại Sản Phẩm 88'), ('Loại Sản Phẩm 89'), ('Loại Sản Phẩm 90'),
('Loại Sản Phẩm 91'), ('Loại Sản Phẩm 92'), ('Loại Sản Phẩm 93'), ('Loại Sản Phẩm 94'), ('Loại Sản Phẩm 95'), ('Loại Sản Phẩm 96'), ('Loại Sản Phẩm 97'), ('Loại Sản Phẩm 98'), ('Loại Sản Phẩm 99'), ('Loại Sản Phẩm 100');

-- -------------
-- 3.5. SẢN PHẨM
-- -------------
-- 40 Dòng đầu: Dữ liệu thật
INSERT INTO Products (pro_name, pro_price, pro_count, type_ID, sup_ID) VALUES
('Laptop Dell XPS 13 Plus', 45000000, 10, 1, 1), ('Laptop Dell Inspiron 15', 15000000, 20, 1, 1), ('Laptop Asus Zenbook 14', 25000000, 15, 1, 2), ('Laptop Asus TUF Gaming', 22000000, 12, 2, 2), ('Laptop HP Pavilion', 18000000, 18, 1, 5),
('Laptop Lenovo ThinkPad X1', 35000000, 8, 1, 6), ('Laptop MSI Raider GE78', 55000000, 5, 2, 9), ('MacBook Air M2', 28000000, 25, 3, 4), ('MacBook Pro M3 Max', 60000000, 5, 3, 4), ('iPhone 15 Pro Max', 33000000, 30, 4, 4),
('iPhone 14 Plus', 20000000, 20, 4, 4), ('Samsung Galaxy S24 Ultra', 30000000, 22, 5, 3), ('Samsung Galaxy Z Fold 5', 35000000, 10, 5, 3), ('Xiaomi 14 Ultra', 25000000, 15, 5, 20), ('iPad Pro M2 11 inch', 20000000, 15, 6, 4),
('Samsung Galaxy Tab S9', 18000000, 12, 6, 3), ('Apple Watch Series 9', 10000000, 25, 7, 4), ('Samsung Galaxy Watch 6', 7000000, 30, 7, 3), ('Tai nghe AirPods Pro 2', 5500000, 50, 8, 4), ('Tai nghe Sony WH-1000XM5', 7500000, 20, 8, 7),
('Loa Marshall Stanmore III', 9000000, 10, 9, 20), ('Loa JBL Charge 5', 3500000, 25, 9, 20), ('Bàn phím cơ Keychron K2', 1800000, 30, 10, 20), ('Bàn phím Logitech MX Keys', 2500000, 20, 10, 12), ('Chuột Logitech MX Master 3S', 2200000, 40, 11, 12),
('Chuột Gaming Logitech G502', 1000000, 35, 11, 12), ('Màn hình Dell UltraSharp U2422H', 6000000, 15, 12, 1), ('Màn hình LG 27UP850 4K', 9000000, 10, 12, 8), ('RAM Kingston Fury 16GB', 1200000, 50, 13, 11), ('RAM Corsair Vengeance 32GB', 2500000, 30, 13, 19),
('SSD Samsung 980 Pro 1TB', 2800000, 40, 14, 3), ('SSD Western Digital Black 500GB', 1500000, 45, 14, 15), ('VGA RTX 4090 Gaming OC', 50000000, 3, 15, 10), ('VGA GTX 1660 Super', 5000000, 20, 15, 10), ('Mainboard Asus ROG Strix Z790', 9000000, 8, 16, 2),
('CPU Intel Core i9 14900K', 15000000, 10, 15, 13), ('CPU AMD Ryzen 9 7950X', 14000000, 10, 15, 14), ('Nguồn Corsair RM850x', 3000000, 20, 18, 19), ('Vỏ case NZXT H9 Flow', 4000000, 15, 17, 19), ('Camera Wifi Imou Ranger 2', 600000, 60, 20, 20);

-- 60 Dòng sau: Dữ liệu sinh
-- Quy tắc: Giá = ID * 100,000 (Để dễ tính toán khớp Invoices)
INSERT INTO Products (pro_name, pro_price, pro_count, type_ID, sup_ID) VALUES
('Sản Phẩm 41', 4100000, 100, 21, 1), ('Sản Phẩm 42', 4200000, 100, 22, 2), ('Sản Phẩm 43', 4300000, 100, 23, 3), ('Sản Phẩm 44', 4400000, 100, 24, 4), ('Sản Phẩm 45', 4500000, 100, 25, 5),
('Sản Phẩm 46', 4600000, 100, 26, 6), ('Sản Phẩm 47', 4700000, 100, 27, 7), ('Sản Phẩm 48', 4800000, 100, 28, 8), ('Sản Phẩm 49', 4900000, 100, 29, 9), ('Sản Phẩm 50', 5000000, 100, 30, 10),
('Sản Phẩm 51', 5100000, 100, 31, 11), ('Sản Phẩm 52', 5200000, 100, 32, 12), ('Sản Phẩm 53', 5300000, 100, 33, 13), ('Sản Phẩm 54', 5400000, 100, 34, 14), ('Sản Phẩm 55', 5500000, 100, 35, 15),
('Sản Phẩm 56', 5600000, 100, 36, 16), ('Sản Phẩm 57', 5700000, 100, 37, 17), ('Sản Phẩm 58', 5800000, 100, 38, 18), ('Sản Phẩm 59', 5900000, 100, 39, 19), ('Sản Phẩm 60', 6000000, 100, 40, 20),
('Sản Phẩm 61', 6100000, 100, 41, 1), ('Sản Phẩm 62', 6200000, 100, 42, 2), ('Sản Phẩm 63', 6300000, 100, 43, 3), ('Sản Phẩm 64', 6400000, 100, 44, 4), ('Sản Phẩm 65', 6500000, 100, 45, 5),
('Sản Phẩm 66', 6600000, 100, 46, 6), ('Sản Phẩm 67', 6700000, 100, 47, 7), ('Sản Phẩm 68', 6800000, 100, 48, 8), ('Sản Phẩm 69', 6900000, 100, 49, 9), ('Sản Phẩm 70', 7000000, 100, 50, 10),
('Sản Phẩm 71', 7100000, 100, 51, 11), ('Sản Phẩm 72', 7200000, 100, 52, 12), ('Sản Phẩm 73', 7300000, 100, 53, 13), ('Sản Phẩm 74', 7400000, 100, 54, 14), ('Sản Phẩm 75', 7500000, 100, 55, 15),
('Sản Phẩm 76', 7600000, 100, 56, 16), ('Sản Phẩm 77', 7700000, 100, 57, 17), ('Sản Phẩm 78', 7800000, 100, 58, 18), ('Sản Phẩm 79', 7900000, 100, 59, 19), ('Sản Phẩm 80', 8000000, 100, 60, 20),
('Sản Phẩm 81', 8100000, 100, 61, 1), ('Sản Phẩm 82', 8200000, 100, 62, 2), ('Sản Phẩm 83', 8300000, 100, 63, 3), ('Sản Phẩm 84', 8400000, 100, 64, 4), ('Sản Phẩm 85', 8500000, 100, 65, 5),
('Sản Phẩm 86', 8600000, 100, 66, 6), ('Sản Phẩm 87', 8700000, 100, 67, 7), ('Sản Phẩm 88', 8800000, 100, 68, 8), ('Sản Phẩm 89', 8900000, 100, 69, 9), ('Sản Phẩm 90', 9000000, 100, 70, 10),
('Sản Phẩm 91', 9100000, 100, 71, 11), ('Sản Phẩm 92', 9200000, 100, 72, 12), ('Sản Phẩm 93', 9300000, 100, 73, 13), ('Sản Phẩm 94', 9400000, 100, 74, 14), ('Sản Phẩm 95', 9500000, 100, 75, 15),
('Sản Phẩm 96', 9600000, 100, 76, 16), ('Sản Phẩm 97', 9700000, 100, 77, 17), ('Sản Phẩm 98', 9800000, 100, 78, 18), ('Sản Phẩm 99', 9900000, 100, 79, 19), ('Sản Phẩm 100', 10000000, 100, 80, 20);

-- ----------------------------------------------------------------------
-- 3.6. HÓA ĐƠN & CHI TIẾT
-- Logic: Mỗi Hóa đơn i (1-100) mua Sản phẩm i. Số lượng: i%2==0 ? 2 : 1.
-- ----------------------------------------------------------------------

-- A. HÓA ĐƠN (Header)
-- Dữ liệu được rải đều trong vòng 30 ngày gần nhất
INSERT INTO Invoices (sta_ID, cus_ID, inv_price, inv_date) VALUES
-- [Ngày hôm nay và hôm qua] (Mới nhất)
(1, 1, 45000000, NOW()),
(2, 5, 1200000, DATE_SUB(NOW(), INTERVAL 1 HOUR)),
(3, 8, 25000000, DATE_SUB(NOW(), INTERVAL 3 HOUR)),
(1, 12, 5500000, DATE_SUB(NOW(), INTERVAL 5 HOUR)),
(4, 3, 18500000, DATE_SUB(NOW(), INTERVAL 1 DAY)),
(2, 15, 900000, DATE_SUB(NOW(), INTERVAL 1 DAY)),
(5, 20, 32000000, DATE_SUB(NOW(), INTERVAL 1 DAY)),
(6, 22, 15000000, DATE_SUB(NOW(), INTERVAL 2 DAY)),
(7, 25, 450000, DATE_SUB(NOW(), INTERVAL 2 DAY)),
(1, 2, 60000000, DATE_SUB(NOW(), INTERVAL 2 DAY)),

-- [3 - 5 ngày trước]
(3, 4, 2200000, DATE_SUB(NOW(), INTERVAL 3 DAY)),
(8, 6, 8500000, DATE_SUB(NOW(), INTERVAL 3 DAY)),
(2, 9, 12500000, DATE_SUB(NOW(), INTERVAL 3 DAY)),
(9, 11, 3000000, DATE_SUB(NOW(), INTERVAL 4 DAY)),
(10, 14, 45000000, DATE_SUB(NOW(), INTERVAL 4 DAY)),
(4, 18, 500000, DATE_SUB(NOW(), INTERVAL 4 DAY)),
(5, 19, 7500000, DATE_SUB(NOW(), INTERVAL 5 DAY)),
(1, 30, 28000000, DATE_SUB(NOW(), INTERVAL 5 DAY)),
(2, 35, 1500000, DATE_SUB(NOW(), INTERVAL 5 DAY)),
(6, 38, 9000000, DATE_SUB(NOW(), INTERVAL 5 DAY)),

-- [6 - 10 ngày trước] (Tuần trước)
(7, 10, 12000000, DATE_SUB(NOW(), INTERVAL 6 DAY)),
(8, 12, 3500000, DATE_SUB(NOW(), INTERVAL 6 DAY)),
(3, 15, 21000000, DATE_SUB(NOW(), INTERVAL 7 DAY)),
(9, 16, 600000, DATE_SUB(NOW(), INTERVAL 7 DAY)),
(10, 18, 4500000, DATE_SUB(NOW(), INTERVAL 8 DAY)),
(1, 20, 18000000, DATE_SUB(NOW(), INTERVAL 8 DAY)),
(2, 21, 2500000, DATE_SUB(NOW(), INTERVAL 9 DAY)),
(4, 25, 30000000, DATE_SUB(NOW(), INTERVAL 9 DAY)),
(5, 28, 5500000, DATE_SUB(NOW(), INTERVAL 10 DAY)),
(6, 30, 9000000, DATE_SUB(NOW(), INTERVAL 10 DAY)),

-- [11 - 15 ngày trước]
(7, 33, 1500000, DATE_SUB(NOW(), INTERVAL 11 DAY)),
(8, 35, 40000000, DATE_SUB(NOW(), INTERVAL 11 DAY)),
(9, 40, 1200000, DATE_SUB(NOW(), INTERVAL 12 DAY)),
(1, 5, 8000000, DATE_SUB(NOW(), INTERVAL 12 DAY)),
(2, 7, 24000000, DATE_SUB(NOW(), INTERVAL 13 DAY)),
(3, 9, 3500000, DATE_SUB(NOW(), INTERVAL 13 DAY)),
(4, 11, 19000000, DATE_SUB(NOW(), INTERVAL 14 DAY)),
(10, 13, 6500000, DATE_SUB(NOW(), INTERVAL 14 DAY)),
(5, 14, 2000000, DATE_SUB(NOW(), INTERVAL 15 DAY)),
(6, 17, 15000000, DATE_SUB(NOW(), INTERVAL 15 DAY)),

-- [16 - 20 ngày trước]
(7, 22, 9000000, DATE_SUB(NOW(), INTERVAL 16 DAY)),
(8, 24, 4500000, DATE_SUB(NOW(), INTERVAL 16 DAY)),
(9, 26, 32000000, DATE_SUB(NOW(), INTERVAL 17 DAY)),
(1, 29, 2800000, DATE_SUB(NOW(), INTERVAL 17 DAY)),
(2, 31, 1000000, DATE_SUB(NOW(), INTERVAL 18 DAY)),
(3, 34, 5000000, DATE_SUB(NOW(), INTERVAL 18 DAY)),
(4, 37, 12000000, DATE_SUB(NOW(), INTERVAL 19 DAY)),
(5, 39, 8500000, DATE_SUB(NOW(), INTERVAL 19 DAY)),
(6, 1, 30000000, DATE_SUB(NOW(), INTERVAL 20 DAY)),
(7, 3, 2000000, DATE_SUB(NOW(), INTERVAL 20 DAY)),

-- [21 - 25 ngày trước]
(8, 6, 1500000, DATE_SUB(NOW(), INTERVAL 21 DAY)),
(9, 8, 45000000, DATE_SUB(NOW(), INTERVAL 21 DAY)),
(10, 10, 6000000, DATE_SUB(NOW(), INTERVAL 22 DAY)),
(1, 12, 2200000, DATE_SUB(NOW(), INTERVAL 22 DAY)),
(2, 15, 9500000, DATE_SUB(NOW(), INTERVAL 23 DAY)),
(3, 18, 18000000, DATE_SUB(NOW(), INTERVAL 23 DAY)),
(4, 20, 3500000, DATE_SUB(NOW(), INTERVAL 24 DAY)),
(5, 23, 1200000, DATE_SUB(NOW(), INTERVAL 24 DAY)),
(6, 25, 28000000, DATE_SUB(NOW(), INTERVAL 25 DAY)),
(7, 27, 4000000, DATE_SUB(NOW(), INTERVAL 25 DAY)),

-- [26 - 28 ngày trước]
(8, 30, 15000000, DATE_SUB(NOW(), INTERVAL 26 DAY)),
(9, 32, 500000, DATE_SUB(NOW(), INTERVAL 26 DAY)),
(10, 35, 7000000, DATE_SUB(NOW(), INTERVAL 26 DAY)),
(1, 38, 21000000, DATE_SUB(NOW(), INTERVAL 27 DAY)),
(2, 40, 9000000, DATE_SUB(NOW(), INTERVAL 27 DAY)),
(3, 2, 33000000, DATE_SUB(NOW(), INTERVAL 27 DAY)),
(4, 4, 2500000, DATE_SUB(NOW(), INTERVAL 28 DAY)),
(5, 7, 12000000, DATE_SUB(NOW(), INTERVAL 28 DAY)),
(6, 9, 4500000, DATE_SUB(NOW(), INTERVAL 28 DAY)),
(7, 11, 55000000, DATE_SUB(NOW(), INTERVAL 28 DAY)),

-- [29 - 30 ngày trước] (Cũ nhất trong tháng)
(8, 13, 1800000, DATE_SUB(NOW(), INTERVAL 29 DAY)),
(9, 16, 26000000, DATE_SUB(NOW(), INTERVAL 29 DAY)),
(10, 19, 3000000, DATE_SUB(NOW(), INTERVAL 29 DAY)),
(1, 21, 8000000, DATE_SUB(NOW(), INTERVAL 29 DAY)),
(2, 24, 15000000, DATE_SUB(NOW(), INTERVAL 30 DAY)),
(3, 26, 4000000, DATE_SUB(NOW(), INTERVAL 30 DAY)),
(4, 28, 9500000, DATE_SUB(NOW(), INTERVAL 30 DAY)),
(5, 31, 2200000, DATE_SUB(NOW(), INTERVAL 30 DAY)),
(6, 33, 10000000, DATE_SUB(NOW(), INTERVAL 30 DAY)),
(7, 36, 5000000, DATE_SUB(NOW(), INTERVAL 30 DAY)),

-- [Các ngày ngẫu nhiên trong tháng để lấp đầy 100]
(8, 39, 35000000, DATE_SUB(NOW(), INTERVAL 12 DAY)),
(9, 1, 1200000, DATE_SUB(NOW(), INTERVAL 5 DAY)),
(10, 5, 28000000, DATE_SUB(NOW(), INTERVAL 24 DAY)),
(1, 8, 4500000, DATE_SUB(NOW(), INTERVAL 2 DAY)),
(2, 12, 19000000, DATE_SUB(NOW(), INTERVAL 18 DAY)),
(3, 15, 6000000, DATE_SUB(NOW(), INTERVAL 9 DAY)),
(4, 20, 2500000, DATE_SUB(NOW(), INTERVAL 27 DAY)),
(5, 25, 15000000, DATE_SUB(NOW(), INTERVAL 14 DAY)),
(6, 30, 3000000, DATE_SUB(NOW(), INTERVAL 21 DAY)),
(7, 35, 8500000, DATE_SUB(NOW(), INTERVAL 6 DAY)),
(8, 40, 42000000, DATE_SUB(NOW(), INTERVAL 1 DAY)),
(9, 1, 1200000, DATE_SUB(NOW(), INTERVAL 29 DAY)),
(10, 5, 28000000, DATE_SUB(NOW(), INTERVAL 15 DAY)),
(1, 8, 4500000, DATE_SUB(NOW(), INTERVAL 8 DAY)),
(2, 12, 19000000, DATE_SUB(NOW(), INTERVAL 22 DAY)),
(3, 15, 6000000, DATE_SUB(NOW(), INTERVAL 4 DAY)),
(4, 20, 2500000, DATE_SUB(NOW(), INTERVAL 16 DAY)),
(5, 25, 15000000, DATE_SUB(NOW(), INTERVAL 28 DAY)),
(6, 30, 3000000, DATE_SUB(NOW(), INTERVAL 11 DAY)),
(7, 35, 8500000, DATE_SUB(NOW(), INTERVAL 3 DAY));

-- B. CHI TIẾT HÓA ĐƠN (Detail)
-- Logic: ID lẻ -> Mua 1 cái. ID chẵn -> Mua 2 cái.
-- Unit_Price: Copy chính xác từ Products.
INSERT INTO Invoice_details (inv_ID, pro_ID, ind_count, unit_price) VALUES
(1, 1, 1, 45000000), (2, 2, 2, 15000000), (3, 3, 1, 25000000), (4, 4, 2, 22000000), (5, 5, 1, 18000000),
(6, 6, 2, 35000000), (7, 7, 1, 55000000), (8, 8, 2, 28000000), (9, 9, 1, 60000000), (10, 10, 2, 33000000),
(11, 11, 1, 20000000), (12, 12, 2, 30000000), (13, 13, 1, 35000000), (14, 14, 2, 25000000), (15, 15, 1, 20000000),
(16, 16, 2, 18000000), (17, 17, 1, 10000000), (18, 18, 2, 7000000), (19, 19, 1, 5500000), (20, 20, 2, 7500000),
(21, 21, 1, 9000000), (22, 22, 2, 3500000), (23, 23, 1, 1800000), (24, 24, 2, 2500000), (25, 25, 1, 2200000),
(26, 26, 2, 1000000), (27, 27, 1, 6000000), (28, 28, 2, 9000000), (29, 29, 1, 1200000), (30, 30, 2, 2500000),
(31, 31, 1, 2800000), (32, 32, 2, 1500000), (33, 33, 1, 50000000), (34, 34, 2, 5000000), (35, 35, 1, 9000000),
(36, 36, 2, 15000000), (37, 37, 1, 4000000), (38, 38, 2, 3000000), (39, 39, 1, 4000000), (40, 40, 2, 600000),
(41, 41, 1, 4100000), (42, 42, 2, 4200000), (43, 43, 1, 4300000), (44, 44, 2, 4400000), (45, 45, 1, 4500000),
(46, 46, 2, 4600000), (47, 47, 1, 4700000), (48, 48, 2, 4800000), (49, 49, 1, 4900000), (50, 50, 2, 5000000),
(51, 51, 1, 5100000), (52, 52, 2, 5200000), (53, 53, 1, 5300000), (54, 54, 2, 5400000), (55, 55, 1, 5500000),
(56, 56, 2, 5600000), (57, 57, 1, 5700000), (58, 58, 2, 5800000), (59, 59, 1, 5900000), (60, 60, 2, 6000000),
(61, 61, 1, 6100000), (62, 62, 2, 6200000), (63, 63, 1, 6300000), (64, 64, 2, 6400000), (65, 65, 1, 6500000),
(66, 66, 2, 6600000), (67, 67, 1, 6700000), (68, 68, 2, 6800000), (69, 69, 1, 6900000), (70, 70, 2, 7000000),
(71, 71, 1, 7100000), (72, 72, 2, 7200000), (73, 73, 1, 7300000), (74, 74, 2, 7400000), (75, 75, 1, 7500000),
(76, 76, 2, 7600000), (77, 77, 1, 7700000), (78, 78, 2, 7800000), (79, 79, 1, 7900000), (80, 80, 2, 8000000),
(81, 81, 1, 8100000), (82, 82, 2, 8200000), (83, 83, 1, 8300000), (84, 84, 2, 8400000), (85, 85, 1, 8500000),
(86, 86, 2, 8600000), (87, 87, 1, 8700000), (88, 88, 2, 8800000), (89, 89, 1, 8900000), (90, 90, 2, 9000000),
(91, 91, 1, 9100000), (92, 92, 2, 9200000), (93, 93, 1, 9300000), (94, 94, 2, 9400000), (95, 95, 1, 9500000),
(96, 96, 2, 9600000), (97, 97, 1, 9700000), (98, 98, 2, 9800000), (99, 99, 1, 9900000), (100, 100, 2, 10000000);

SET FOREIGN_KEY_CHECKS = 1;