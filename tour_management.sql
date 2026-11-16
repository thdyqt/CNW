SET FOREIGN_KEY_CHECKS = 0;
-- ========================================================
-- Cơ sở dữ liệu: tour_management (Migrated from MongoDB)
-- Đồ án môn học: Công nghệ Web (JSP / Servlet MVC)
-- ========================================================

CREATE DATABASE IF NOT EXISTS `tour_management` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `tour_management`;

SET NAMES utf8mb4;

-- Table: roles
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL,
  `description` TEXT DEFAULT NULL,
  `permissions` TEXT DEFAULT NULL,
  `slug` VARCHAR(255) DEFAULT NULL,
  `created_by` VARCHAR(255) DEFAULT 'Phan Thanh Duy',
  `updated_by` VARCHAR(255) DEFAULT 'Phan Thanh Duy',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT(1) DEFAULT 0,
  `deleted_by` VARCHAR(255) DEFAULT NULL,
  `deleted_at` DATETIME DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
INSERT INTO `roles` (`id`, `name`, `description`, `permissions`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (1, 'Quản trị viên', 'Có đầy đủ quyền.', '["dashboard-view", "category-view", "category-create", "category-edit", "category-delete", "tour-view", "tour-create", "tour-edit", "tour-delete", "tour-trash"]', 'quan-tri-vien', 'Tài khoản đã xóa', 'Phan Thanh Duy', '2026-10-05 09:09:29', '2026-10-11 08:26:21', 0);
INSERT INTO `roles` (`id`, `name`, `description`, `permissions`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (2, 'Quản lý danh mục', 'Có đầy đủ các quyền liên quan tới danh mục.', '["category-view", "category-create", "category-edit", "category-delete"]', 'quan-ly-danh-muc', 'Tài khoản đã xóa', 'Phan Thanh Duy', '2026-10-05 11:41:58', '2026-10-11 08:27:44', 0);
INSERT INTO `roles` (`id`, `name`, `description`, `permissions`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (3, 'Quản lý tour', 'Có đầy đủ các quyền liên quan tới tour.', '["tour-view", "tour-create", "tour-edit", "tour-delete", "tour-trash"]', 'quan-ly-tour', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-10-11 08:26:50', '2026-10-11 08:26:50', 0);

-- Table: accounts_admin
DROP TABLE IF EXISTS `accounts_admin`;
CREATE TABLE `accounts_admin` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `full_name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `phone` VARCHAR(50) DEFAULT NULL,
  `role_id` INT DEFAULT NULL,
  `position` VARCHAR(100) DEFAULT NULL,
  `status` VARCHAR(50) DEFAULT 'active',
  `password` VARCHAR(255) DEFAULT NULL,
  `avatar` VARCHAR(255) DEFAULT '/admin/assets/images/AvatarIcon.jpg',
  `slug` VARCHAR(255) DEFAULT NULL,
  `created_by` VARCHAR(255) DEFAULT 'Phan Thanh Duy',
  `updated_by` VARCHAR(255) DEFAULT 'Phan Thanh Duy',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT(1) DEFAULT 0,
  `deleted_by` VARCHAR(255) DEFAULT NULL,
  `deleted_at` DATETIME DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
INSERT INTO `accounts_admin` (`id`, `full_name`, `email`, `phone`, `role_id`, `position`, `status`, `password`, `avatar`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (1, 'sfasfsafsadf', 'ptd.ntn2901@gmail.com', '0905383131', 2, 'sfsfsfs', 'inactive', '$2b$10$40NbjwPNmNem1ZCfHGxYQeb/rE7MkzEMpSCaxnqlQSwnX13CJ.wmW', '', 'sfasfsafsadf', 'Tài khoản đã xóa', 'Phan Thanh Duy', '2026-10-10 08:13:38', '2026-10-10 11:35:26', 1);
INSERT INTO `accounts_admin` (`id`, `full_name`, `email`, `phone`, `role_id`, `position`, `status`, `password`, `avatar`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (2, 'Phan Thanh Duy', 'ptd.ntn2907@gmail.com', '0905383132', 1, 'Giám đốc', 'active', '$2b$10$L.lunX/U7GYRqq0IxM2XJe4EGq0vrN9mieATbSmnWOAeAK7yDbaHC', 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1791638065/hdjdks9vbgdmjveyu4kq.jpg', 'phan-thanh-duy', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-10-10 09:33:53', '2026-10-11 08:33:38', 0);

-- Table: setting_website_info
DROP TABLE IF EXISTS `setting_website_info`;
CREATE TABLE `setting_website_info` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `website_name` VARCHAR(255) DEFAULT '28Tech Travel',
  `phone` VARCHAR(50) DEFAULT '0123 456 789',
  `email` VARCHAR(100) DEFAULT 'contact@28tech.com',
  `address` VARCHAR(255) DEFAULT 'Tòa nhà FPT, Cầu Giấy, Hà Nội',
  `logo` VARCHAR(255) DEFAULT '/assets/images/Logo.svg',
  `favicon` VARCHAR(255) DEFAULT '/assets/images/Logo.svg'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
INSERT INTO `setting_website_info` (`id`, `website_name`, `phone`, `email`, `address`, `logo`, `favicon`) VALUES (1, '28Tour', '0905383132', 'ptd.ntn2907@gmail.com', '54 Nguyễn Lương Bằng, Phường Liên Chiểu, Thành phố Đà Nẵng', 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1794056899/uwbzngtlgkyzbsjfvqwp.png', 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1794056900/eo5m48bwr7lm86rbdgvt.png');

-- Table: forgot_password
DROP TABLE IF EXISTS `forgot_password`;
CREATE TABLE `forgot_password` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `email` VARCHAR(255) NOT NULL,
  `otp` VARCHAR(20) NOT NULL,
  `expire_at` DATETIME DEFAULT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Table: categories
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL,
  `parent_id` INT DEFAULT NULL,
  `position` INT DEFAULT 0,
  `avatar` VARCHAR(255) DEFAULT NULL,
  `description` TEXT DEFAULT NULL,
  `status` VARCHAR(50) DEFAULT 'active',
  `slug` VARCHAR(255) UNIQUE NOT NULL,
  `created_by` VARCHAR(255) DEFAULT 'Phan Thanh Duy',
  `updated_by` VARCHAR(255) DEFAULT 'Phan Thanh Duy',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT(1) DEFAULT 0,
  `deleted_by` VARCHAR(255) DEFAULT NULL,
  `deleted_at` DATETIME DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
INSERT INTO `categories` (`id`, `name`, `parent_id`, `position`, `avatar`, `description`, `status`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (1, 'Tour Trong Nước', NULL, 1, 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1790766997/fefivrng8zekkpqhzbod.jpg', '<p>Tour Trong Nước 34 tỉnh th&agrave;nh</p>', 'active', 'tour-trong-nuoc', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-09-29 07:24:40', '2026-09-30 11:16:34', 0);
INSERT INTO `categories` (`id`, `name`, `parent_id`, `position`, `avatar`, `description`, `status`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (2, 'Tour Miền Bắc', 1, 2, '', '', 'active', 'tour-mien-bac', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-09-29 07:36:12', '2026-10-01 14:10:49', 0);
INSERT INTO `categories` (`id`, `name`, `parent_id`, `position`, `avatar`, `description`, `status`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (3, 'Tour Miền Nam', 1, 3, '', '', 'active', 'tour-mien-nam', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-09-29 07:36:52', '2026-09-29 07:36:52', 0);
INSERT INTO `categories` (`id`, `name`, `parent_id`, `position`, `avatar`, `description`, `status`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (4, 'Tour Miền Trung', 1, 4, '', '', 'active', 'tour-mien-trung', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-09-29 07:37:23', '2026-09-29 07:37:23', 0);
INSERT INTO `categories` (`id`, `name`, `parent_id`, `position`, `avatar`, `description`, `status`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (5, 'Tour Xuyên Việt', 1, 5, '', '', 'active', 'tour-xuyen-viet', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-09-29 07:37:37', '2026-09-29 07:37:37', 0);
INSERT INTO `categories` (`id`, `name`, `parent_id`, `position`, `avatar`, `description`, `status`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (6, 'Tour Nước Ngoài', NULL, 6, '', '<p>Tour Nước Ngo&agrave;i</p>', 'active', 'tour-nuoc-ngoai', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-09-29 07:38:16', '2026-09-29 07:38:16', 0);
INSERT INTO `categories` (`id`, `name`, `parent_id`, `position`, `avatar`, `description`, `status`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (7, 'Tour Châu Á', 6, 7, '', '', 'active', 'tour-chau-a', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-09-29 07:38:42', '2026-10-01 14:10:49', 0);
INSERT INTO `categories` (`id`, `name`, `parent_id`, `position`, `avatar`, `description`, `status`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (8, 'Tour Châu Âu', 6, 8, '', '', 'active', 'tour-chau-au', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-09-29 07:39:22', '2026-10-01 14:10:49', 0);
INSERT INTO `categories` (`id`, `name`, `parent_id`, `position`, `avatar`, `description`, `status`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (9, 'Tour Châu Mỹ', 6, 9, '', '', 'active', 'tour-chau-my', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-09-29 07:39:48', '2026-10-01 14:10:49', 0);
INSERT INTO `categories` (`id`, `name`, `parent_id`, `position`, `avatar`, `description`, `status`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (10, 'Tour Châu Úc', 6, 10, '', '', 'active', 'tour-chau-uc', 'Tài khoản đã xóa', 'Phan Thanh Duy', '2026-09-29 07:40:01', '2026-10-11 10:38:53', 0);
INSERT INTO `categories` (`id`, `name`, `parent_id`, `position`, `avatar`, `description`, `status`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (11, 'DM1', 1, 11, 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1790777380/dkeypbstbji7wgavu3wx.png', '<p>sfsdfs</p>', 'inactive', 'dm1', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-09-30 14:09:38', '2026-09-30 14:21:19', 1);
INSERT INTO `categories` (`id`, `name`, `parent_id`, `position`, `avatar`, `description`, `status`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (12, 'werwedwdew', NULL, 12, '', '<p>ewd</p>', 'active', 'werwedwdew', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-10-01 14:11:07', '2026-10-01 14:11:23', 1);
INSERT INTO `categories` (`id`, `name`, `parent_id`, `position`, `avatar`, `description`, `status`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (13, 'sadad', NULL, 13, '', '', 'active', 'sadad', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-10-02 06:14:08', '2026-10-02 06:26:11', 1);

-- Table: tours
DROP TABLE IF EXISTS `tours`;
CREATE TABLE `tours` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `code` VARCHAR(50) DEFAULT NULL,
  `name` VARCHAR(255) NOT NULL,
  `category_id` INT DEFAULT NULL,
  `position` INT DEFAULT 0,
  `status` VARCHAR(50) DEFAULT 'active',
  `avatar` VARCHAR(255) DEFAULT NULL,
  `images` TEXT DEFAULT NULL,
  `price_adult` DOUBLE DEFAULT 0,
  `price_children` DOUBLE DEFAULT 0,
  `price_baby` DOUBLE DEFAULT 0,
  `price_new_adult` DOUBLE DEFAULT 0,
  `price_new_children` DOUBLE DEFAULT 0,
  `price_new_baby` DOUBLE DEFAULT 0,
  `stock_adult` INT DEFAULT 0,
  `stock_children` INT DEFAULT 0,
  `stock_baby` INT DEFAULT 0,
  `locations` TEXT DEFAULT NULL,
  `time` VARCHAR(100) DEFAULT NULL,
  `vehicle` VARCHAR(100) DEFAULT NULL,
  `departure_date` DATE DEFAULT NULL,
  `information` LONGTEXT DEFAULT NULL,
  `schedules` LONGTEXT DEFAULT NULL,
  `slug` VARCHAR(255) UNIQUE NOT NULL,
  `created_by` VARCHAR(255) DEFAULT 'Phan Thanh Duy',
  `updated_by` VARCHAR(255) DEFAULT 'Phan Thanh Duy',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT(1) DEFAULT 0,
  `deleted_by` VARCHAR(255) DEFAULT NULL,
  `deleted_at` DATETIME DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
INSERT INTO `tours` (`id`, `code`, `name`, `category_id`, `position`, `status`, `avatar`, `images`, `price_adult`, `price_children`, `price_baby`, `price_new_adult`, `price_new_children`, `price_new_baby`, `stock_adult`, `stock_children`, `stock_baby`, `locations`, `time`, `vehicle`, `departure_date`, `information`, `schedules`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (1, '28T00001', 'Tour 2026 Phú Quốc – Thiên Đường Đảo Ngọc (3N2D)', 3, 4, 'active', 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1791715757/t6r3pcaljnvzmjd1q1gf.jpg', '', 10650000, 6650000, 2850000, 2190000, 1490000, 1090000, 9, 6, 8, '6abf5222262841365017fd09', '3 ngày 2 đêm', '', '2026-10-23', '', '[{"title": "", "description": ""}]', 'tour-2026-phu-quoc-thien-duong-dao-ngoc-3n2d', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-10-11 10:49:17', '2026-10-28 14:06:45', 0);
INSERT INTO `tours` (`id`, `code`, `name`, `category_id`, `position`, `status`, `avatar`, `images`, `price_adult`, `price_children`, `price_baby`, `price_new_adult`, `price_new_children`, `price_new_baby`, `stock_adult`, `stock_children`, `stock_baby`, `locations`, `time`, `vehicle`, `departure_date`, `information`, `schedules`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (2, '28T00002', 'Combo Đà Nẵng 2024: ĐÀ NẴNG – HỘI AN – BÀ NÀ HILL', 4, 5, 'active', 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1791715898/adwwpovg9aj8np43mryj.jpg', '', 12650000, 5650000, 3450000, 2290000, 1790000, 1490000, 9, 8, 11, '6abf5222262841365017fd03', '2 ngày 1 đêm', 'Xích lô, xe ô tô', '2026-10-26', '', '[{"title": "", "description": ""}]', 'combo-da-nang-2024-da-nang-hoi-an-ba-na-hill', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-10-11 10:51:38', '2026-10-28 14:06:45', 0);
INSERT INTO `tours` (`id`, `code`, `name`, `category_id`, `position`, `status`, `avatar`, `images`, `price_adult`, `price_children`, `price_baby`, `price_new_adult`, `price_new_children`, `price_new_baby`, `stock_adult`, `stock_children`, `stock_baby`, `locations`, `time`, `vehicle`, `departure_date`, `information`, `schedules`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (3, '28T00003', 'Hà Nội – Lào Cai – SaPa 4N3Đ', 2, 1, 'active', 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1792321337/qcgcbnlpiykpgss7ippa.jpg', 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1792321340/h9bus7086s2m7pze3gkk.jpg,https://res.cloudinary.com/r5hwa2cj/image/upload/v1792321340/ibmmpammcp14vrq6vems.jpg', 13650000, 10650000, 7650000, 2590000, 2090000, 1390000, 0, 3, 3, '6abf5222262841365017fd00,6abf5222262841365017fd16', '4 ngày 3 đêm', 'Máy bay', '2026-10-24', '<p>adsfsfsf</p>

<p><img src="https://res.cloudinary.com/r5hwa2cj/image/upload/v1792324456/glydrlmwoalprfe46ise.jpg" alt="" width="960" height="591"></p>', '[{"title": "sfsf", "description": "<p>fsfsdfsdf</p>"}, {"title": "sdfs", "description": "<p>dfsfsfsf</p>\\n<p><img src=\\"https://res.cloudinary.com/r5hwa2cj/image/upload/v1792324418/ix1e5kgjxfll2dhnovgc.jpg\\" alt=\\"\\" width=\\"667\\" height=\\"518\\"></p>"}]', 'ha-noi-lao-cai-sapa-4n3d-ng-V30IH5', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-10-18 11:02:20', '2026-10-28 13:40:46', 0);

-- Table: cities
DROP TABLE IF EXISTS `cities`;
CREATE TABLE `cities` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
INSERT INTO `cities` (`id`, `name`) VALUES (1, 'Quảng Ninh');
INSERT INTO `cities` (`id`, `name`) VALUES (2, 'Cà Mau');
INSERT INTO `cities` (`id`, `name`) VALUES (3, 'Đắk Lắk');
INSERT INTO `cities` (`id`, `name`) VALUES (4, 'Hà Tĩnh');
INSERT INTO `cities` (`id`, `name`) VALUES (5, 'Lâm Đồng');
INSERT INTO `cities` (`id`, `name`) VALUES (6, 'Lạng Sơn');
INSERT INTO `cities` (`id`, `name`) VALUES (7, 'Sơn La');
INSERT INTO `cities` (`id`, `name`) VALUES (8, 'Tuyên Quang');
INSERT INTO `cities` (`id`, `name`) VALUES (9, 'Hà Nội');
INSERT INTO `cities` (`id`, `name`) VALUES (10, 'Hồ Chí Minh');
INSERT INTO `cities` (`id`, `name`) VALUES (11, 'Đà Nẵng');
INSERT INTO `cities` (`id`, `name`) VALUES (12, 'Cao Bằng');
INSERT INTO `cities` (`id`, `name`) VALUES (13, 'Đồng Tháp');
INSERT INTO `cities` (`id`, `name`) VALUES (14, 'Hưng Yên');
INSERT INTO `cities` (`id`, `name`) VALUES (15, 'Phú Thọ');
INSERT INTO `cities` (`id`, `name`) VALUES (16, 'Quảng Ngãi');
INSERT INTO `cities` (`id`, `name`) VALUES (17, 'Vĩnh Long');
INSERT INTO `cities` (`id`, `name`) VALUES (18, 'Hải Phòng');
INSERT INTO `cities` (`id`, `name`) VALUES (19, 'Huế');
INSERT INTO `cities` (`id`, `name`) VALUES (20, 'Bắc Ninh');
INSERT INTO `cities` (`id`, `name`) VALUES (21, 'An Giang');
INSERT INTO `cities` (`id`, `name`) VALUES (22, 'Điện Biên');
INSERT INTO `cities` (`id`, `name`) VALUES (23, 'Ninh Bình');
INSERT INTO `cities` (`id`, `name`) VALUES (24, 'Tây Ninh');
INSERT INTO `cities` (`id`, `name`) VALUES (25, 'Thái Nguyên');
INSERT INTO `cities` (`id`, `name`) VALUES (26, 'Cần Thơ');
INSERT INTO `cities` (`id`, `name`) VALUES (27, 'Đồng Nai');
INSERT INTO `cities` (`id`, `name`) VALUES (28, 'Gia Lai');
INSERT INTO `cities` (`id`, `name`) VALUES (29, 'Khánh Hòa');
INSERT INTO `cities` (`id`, `name`) VALUES (30, 'Lai Châu');
INSERT INTO `cities` (`id`, `name`) VALUES (31, 'Lào Cai');
INSERT INTO `cities` (`id`, `name`) VALUES (32, 'Nghệ An');
INSERT INTO `cities` (`id`, `name`) VALUES (33, 'Quảng Trị');
INSERT INTO `cities` (`id`, `name`) VALUES (34, 'Thanh Hóa');

-- Table: contacts
DROP TABLE IF EXISTS `contacts`;
CREATE TABLE `contacts` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `email` VARCHAR(255) NOT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT(1) DEFAULT 0,
  `deleted_by` VARCHAR(255) DEFAULT NULL,
  `deleted_at` DATETIME DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
INSERT INTO `contacts` (`id`, `email`, `created_at`, `updated_at`, `deleted`) VALUES (1, 'ptd.ntn2907@gmail.com', '2026-10-14 08:30:49', '2026-10-14 08:30:49', 0);
INSERT INTO `contacts` (`id`, `email`, `created_at`, `updated_at`, `deleted`) VALUES (2, 'ptd.ntn2902@gmail.com', '2026-10-14 09:16:08', '2026-10-14 09:16:08', 0);
INSERT INTO `contacts` (`id`, `email`, `created_at`, `updated_at`, `deleted`) VALUES (3, 'ptd.ntn2901@gmail.com', '2026-10-14 09:18:42', '2026-10-14 09:18:42', 0);
INSERT INTO `contacts` (`id`, `email`, `created_at`, `updated_at`, `deleted`) VALUES (4, 'đa@gmail.com', '2026-10-14 09:22:04', '2026-10-14 09:56:39', 1);

-- Table: orders
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `code` VARCHAR(100) DEFAULT NULL,
  `full_name` VARCHAR(255) DEFAULT NULL,
  `phone` VARCHAR(50) DEFAULT NULL,
  `note` TEXT DEFAULT NULL,
  `items` LONGTEXT DEFAULT NULL,
  `sub_total` DOUBLE DEFAULT 0,
  `total` DOUBLE DEFAULT 0,
  `payment_method` VARCHAR(50) DEFAULT 'money',
  `payment_status` VARCHAR(50) DEFAULT 'unpaid',
  `status` VARCHAR(50) DEFAULT 'initial',
  `updated_by` VARCHAR(255) DEFAULT 'Phan Thanh Duy',
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` TINYINT(1) DEFAULT 0,
  `deleted_by` VARCHAR(255) DEFAULT NULL,
  `deleted_at` DATETIME DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
INSERT INTO `orders` (`id`, `code`, `full_name`, `phone`, `note`, `items`, `sub_total`, `total`, `payment_method`, `payment_status`, `status`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (1, 'OD1171439027', 'Phan Thanh Duy', '0905383132', '', '[{"tourId": "6acb69adc47144e89567b1ab", "quantityAdult": 1, "quantityChildren": 1, "quantityBaby": 0, "locationFrom": "6abf5222262841365017fd09", "priceNewAdult": 2190000, "priceNewChildren": 1490000, "priceNewBaby": 1090000, "departureDate": "2026-10-23 00:00:00"}, {"tourId": "6acb6a3ac47144e89567b1ac", "quantityAdult": 1, "quantityChildren": 3, "quantityBaby": 0, "locationFrom": "6abf5222262841365017fd03", "priceNewAdult": 2290000, "priceNewChildren": 1790000, "priceNewBaby": 1490000, "departureDate": "2026-10-26 00:00:00"}]', 16900000, 16900000, 'money', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-10-24 10:29:24', '2026-10-24 10:29:24', 0);
INSERT INTO `orders` (`id`, `code`, `full_name`, `phone`, `note`, `items`, `sub_total`, `total`, `payment_method`, `payment_status`, `status`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (2, 'OD5160646152', 'Phan Thanh Duy', '0982434442', '', '[{"tourId": "6ad4a73c1dc6114b7491c17e", "quantityAdult": 1, "quantityChildren": 0, "quantityBaby": 0, "locationFrom": "6abf5222262841365017fd00", "priceNewAdult": 2590000, "priceNewChildren": 2090000, "priceNewBaby": 1390000, "departureDate": "2026-10-24 00:00:00"}]', 2590000, 2590000, 'money', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-10-24 10:30:36', '2026-10-24 10:30:36', 0);
INSERT INTO `orders` (`id`, `code`, `full_name`, `phone`, `note`, `items`, `sub_total`, `total`, `payment_method`, `payment_status`, `status`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (3, 'OD9599556564', 'Phan Thanh Duy', '0905383132', '', '[{"tourId": "6acb69adc47144e89567b1ab", "quantityAdult": 1, "quantityChildren": 0, "quantityBaby": 0, "locationFrom": "6abf5222262841365017fd09", "priceNewAdult": 2190000, "priceNewChildren": 1490000, "priceNewBaby": 1090000, "departureDate": "2026-10-23 00:00:00"}]', 2190000, 2190000, 'money', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-10-24 10:31:26', '2026-10-24 10:31:26', 0);
INSERT INTO `orders` (`id`, `code`, `full_name`, `phone`, `note`, `items`, `sub_total`, `total`, `payment_method`, `payment_status`, `status`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (4, 'OD5326858323', 'Phan Thanh Duy', '0905383132', '123456', '[{"tourId": "6acb6a3ac47144e89567b1ac", "quantityAdult": 1, "quantityChildren": 0, "quantityBaby": 0, "locationFrom": "6abf5222262841365017fd03", "priceNewAdult": 2290000, "priceNewChildren": 1790000, "priceNewBaby": 1490000, "departureDate": "2026-10-26 00:00:00"}, {"tourId": "6acb69adc47144e89567b1ab", "quantityAdult": 1, "quantityChildren": 0, "quantityBaby": 0, "locationFrom": "6abf5222262841365017fd09", "priceNewAdult": 2190000, "priceNewChildren": 1490000, "priceNewBaby": 1090000, "departureDate": "2026-10-23 00:00:00"}]', 4480000, 4480000, 'money', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-10-24 10:39:01', '2026-10-24 10:39:01', 0);
INSERT INTO `orders` (`id`, `code`, `full_name`, `phone`, `note`, `items`, `sub_total`, `total`, `payment_method`, `payment_status`, `status`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (5, 'OD0092881905', 'Phan Thanh Duy', '0905383132', '1234566', '[{"tourId": "6acb69adc47144e89567b1ab", "quantityAdult": 1, "quantityChildren": 1, "quantityBaby": 0, "locationFrom": "6abf5222262841365017fd09", "priceNewAdult": 2190000, "priceNewChildren": 1490000, "priceNewBaby": 1090000, "departureDate": "2026-10-23 00:00:00"}, {"tourId": "6ad4a73c1dc6114b7491c17e", "quantityAdult": 1, "quantityChildren": 0, "quantityBaby": 0, "locationFrom": "6abf5222262841365017fd00", "priceNewAdult": 2590000, "priceNewChildren": 2090000, "priceNewBaby": 1390000, "departureDate": "2026-10-24 00:00:00"}]', 7360000, 7360000, 'money', 'paid', 'done', 'Phan Thanh Duy', '2026-10-24 13:30:49', '2026-10-25 13:35:02', 0);
INSERT INTO `orders` (`id`, `code`, `full_name`, `phone`, `note`, `items`, `sub_total`, `total`, `payment_method`, `payment_status`, `status`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (6, 'OD6918513040', 'Phan Thanh Duy', '0905383132', 'abc', '[{"tourId": "6acb69adc47144e89567b1ab", "quantityAdult": 0, "quantityChildren": 1, "quantityBaby": 1, "locationFrom": "6abf5222262841365017fd09", "priceNewAdult": 2190000, "priceNewChildren": 1490000, "priceNewBaby": 1090000, "departureDate": "2026-10-23 00:00:00"}]', 2580000, 2580000, 'zalopay', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-10-28 10:20:52', '2026-10-28 10:20:52', 0);
INSERT INTO `orders` (`id`, `code`, `full_name`, `phone`, `note`, `items`, `sub_total`, `total`, `payment_method`, `payment_status`, `status`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (7, 'OD4261640964', 'Phan Thanh Duy', '0905383132', '21', '[{"tourId": "6acb6a3ac47144e89567b1ac", "quantityAdult": 1, "quantityChildren": 0, "quantityBaby": 0, "locationFrom": "6abf5222262841365017fd03", "priceNewAdult": 2290000, "priceNewChildren": 1790000, "priceNewBaby": 1490000, "departureDate": "2026-10-26 00:00:00"}]', 2290000, 2290000, 'zalopay', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-10-28 10:25:23', '2026-10-28 10:25:23', 0);
INSERT INTO `orders` (`id`, `code`, `full_name`, `phone`, `note`, `items`, `sub_total`, `total`, `payment_method`, `payment_status`, `status`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (8, 'OD2154875255', 'Phan Thanh Duy', '0905383132', '', '[{"tourId": "6acb6a3ac47144e89567b1ac", "quantityAdult": 1, "quantityChildren": 0, "quantityBaby": 0, "locationFrom": "6abf5222262841365017fd03", "priceNewAdult": 2290000, "priceNewChildren": 1790000, "priceNewBaby": 1490000, "departureDate": "2026-10-26 00:00:00"}]', 2290000, 2290000, 'zalopay', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-10-28 10:30:01', '2026-10-28 10:30:01', 0);
INSERT INTO `orders` (`id`, `code`, `full_name`, `phone`, `note`, `items`, `sub_total`, `total`, `payment_method`, `payment_status`, `status`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (9, 'OD7279759730', 'Phan Thanh Duy', '0905383132', '123456', '[{"tourId": "6ad4a73c1dc6114b7491c17e", "quantityAdult": 1, "quantityChildren": 2, "quantityBaby": 0, "locationFrom": "6abf5222262841365017fd00", "priceNewAdult": 2590000, "priceNewChildren": 2090000, "priceNewBaby": 1390000, "departureDate": "2026-10-24 00:00:00"}]', 9550000, 9550000, 'vnpay', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-10-28 13:16:27', '2026-10-28 13:16:27', 0);
INSERT INTO `orders` (`id`, `code`, `full_name`, `phone`, `note`, `items`, `sub_total`, `total`, `payment_method`, `payment_status`, `status`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (10, 'OD0564158484', 'Phan Thanh Duy', '0905383132', '', '[{"tourId": "6ad4a73c1dc6114b7491c17e", "quantityAdult": 1, "quantityChildren": 0, "quantityBaby": 2, "locationFrom": "6abf5222262841365017fd00", "priceNewAdult": 2590000, "priceNewChildren": 2090000, "priceNewBaby": 1390000, "departureDate": "2026-10-24 00:00:00"}]', 2590000, 2590000, 'vnpay', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-10-28 13:40:46', '2026-10-28 13:40:46', 0);
INSERT INTO `orders` (`id`, `code`, `full_name`, `phone`, `note`, `items`, `sub_total`, `total`, `payment_method`, `payment_status`, `status`, `updated_by`, `created_at`, `updated_at`, `deleted`) VALUES (11, 'OD7155723840', 'Phan Thanh Duy', '0905383132', '123456', '[{"tourId": "6acb69adc47144e89567b1ab", "quantityAdult": 1, "quantityChildren": 1, "quantityBaby": 1, "locationFrom": "6abf5222262841365017fd09", "priceNewAdult": 2190000, "priceNewChildren": 1490000, "priceNewBaby": 1090000, "departureDate": "2026-10-23 00:00:00"}, {"tourId": "6acb6a3ac47144e89567b1ac", "quantityAdult": 1, "quantityChildren": 2, "quantityBaby": 0, "locationFrom": "6abf5222262841365017fd03", "priceNewAdult": 2290000, "priceNewChildren": 1790000, "priceNewBaby": 1490000, "departureDate": "2026-10-26 00:00:00"}]', 10640000, 10640000, 'vnpay', 'paid', 'initial', 'Phan Thanh Duy', '2026-10-28 14:06:45', '2026-10-28 14:10:12', 0);

SET FOREIGN_KEY_CHECKS = 1;
