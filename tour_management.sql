-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: Nov 21, 2026 at 08:30 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tour_management`
--

-- --------------------------------------------------------
CREATE DATABASE tour_management;
USE tour_management;
--
-- Table structure for table `accounts_admin`
--

CREATE TABLE `accounts_admin` (
  `id` int(11) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `position` varchar(100) DEFAULT NULL,
  `status` varchar(50) DEFAULT 'active',
  `token` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT '/admin/assets/images/AvatarIcon.jpg',
  `slug` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT 'Phan Thanh Duy',
  `updated_by` varchar(255) DEFAULT 'Phan Thanh Duy',
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted` tinyint(1) DEFAULT 0,
  `deleted_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `accounts_admin`
--

INSERT INTO `accounts_admin` (`id`, `full_name`, `email`, `phone`, `role_id`, `position`, `status`, `token`, `password`, `avatar`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`, `deleted_by`, `deleted_at`) VALUES
(1, 'sfasfsafsadf', 'ptd.ntn2901@gmail.com', '0905383131', 2, 'sfsfsfs', 'inactive', NULL, '$2b$10$40NbjwPNmNem1ZCfHGxYQeb/rE7MkzEMpSCaxnqlQSwnX13CJ.wmW', '', 'sfasfsafsadf', 'Tài khoản đã xóa', 'Phan Thanh Duy', '2026-10-10 08:13:38', '2026-11-16 16:47:47', 1, NULL, '2026-11-16 16:47:47'),
(2, 'Phan Thanh Duy', 'ptd.ntn2907@gmail.com', '0905383132', 1, 'Giám đốc', 'active', 'tok_adm_711659258d1b4648a261a43341d64bca', '$2a$10$BPR5fSr4sUmMAzv0Zh.5SekiUFFlPqXI8/Vj6uxBLEU4E2viG/aLS', 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1794818559/fflxl80totrsobaikn3q.jpg', 'phan-thanh-duy', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-10-10 09:33:53', '2026-11-19 20:25:24', 0, NULL, NULL),
(3, 'Nguyễn Hoàng Hiếu', 'nhhieu@gmail.com', '0901234567', 6, 'Trợ lý', 'active', NULL, '$2a$10$HfZ.o3irLH1ntBLMFZVe0.UFFS1QUU9Mi3Ub8n3zpiRQTGlacyj1q', 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1795050860/mxbb2g1urvouabiimmoq.jpg', NULL, 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-15 21:49:03', '2026-11-19 08:20:44', 0, NULL, NULL),
(11, 'Phan Thanh Duy', 'ptd.ntn2902@gmail.com', '0905383131', 3, 'Thư ký', 'active', NULL, '$2a$10$7eHUn2qeQxcgFOHODVLgQeIvLr9uXdvh/wOFBKjAAKTDQEkH3UsMW', '', NULL, 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-18 21:01:46', '2026-11-18 21:20:43', 0, NULL, NULL),
(100, 'Trần Hoài An', 'an@gmail.com', '0912345678', 1, 'Phó giám đốc', 'active', NULL, '$2a$10$OTcLk7AHIY2k/EMx4rLsPutkDVFEr9IKMDojnRLCnjup4k68Gh9Yy', '', NULL, 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-21 14:28:58', '2026-11-21 14:28:58', 0, NULL, NULL),
(101, 'Phan Xuân Mạnh', 'manh@gmail.com', '0912345678', 1, 'Phó giám đốc', 'active', NULL, '$2a$10$xPtE1uiax.biH/MTgN8OC.NuEc4p/KkrjMO9uybaPlEO4TCcyKd6a', '', NULL, 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-21 14:29:25', '2026-11-21 14:29:25', 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `position` int(11) DEFAULT 0,
  `avatar` varchar(255) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `status` varchar(50) DEFAULT 'active',
  `slug` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT 'Phan Thanh Duy',
  `updated_by` varchar(255) DEFAULT 'Phan Thanh Duy',
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted` tinyint(1) DEFAULT 0,
  `deleted_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`, `parent_id`, `position`, `avatar`, `description`, `status`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`, `deleted_by`, `deleted_at`) VALUES
(1, 'Tour Trong Nước', NULL, 1, '', '<p>Tour Trong Nước 34 tỉnh th&agrave;nh</p>', 'active', 'tour-trong-nuoc', 'Tài khoản đã xóa', 'Phan Thanh Duy', '2026-09-29 07:24:40', '2026-11-15 21:50:52', 0, NULL, NULL),
(2, 'Tour Miền Bắc', 1, 2, '', '', 'active', 'tour-mien-bac', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-09-29 07:36:12', '2026-10-01 14:10:49', 0, NULL, NULL),
(3, 'Tour Miền Nam', 1, 3, 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1794818897/apzrucliu1iyy9arr5ix.jpg', '', 'active', 'tour-mien-nam', 'Tài khoản đã xóa', 'Phan Thanh Duy', '2026-09-29 07:36:52', '2026-11-16 15:48:18', 0, NULL, NULL),
(4, 'Tour Miền Trung', 1, 4, '', '', 'active', 'tour-mien-trung', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-09-29 07:37:23', '2026-09-29 07:37:23', 0, NULL, NULL),
(5, 'Tour Xuyên Việt', 1, 5, '', '', 'active', 'tour-xuyen-viet', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-09-29 07:37:37', '2026-09-29 07:37:37', 0, NULL, NULL),
(6, 'Tour Nước Ngoài', NULL, 6, '', '<p>Tour Nước Ngo&agrave;i</p>', 'active', 'tour-nuoc-ngoai', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-09-29 07:38:16', '2026-09-29 07:38:16', 0, NULL, NULL),
(7, 'Tour Châu Á', 6, 7, '', '', 'active', 'tour-chau-a', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-09-29 07:38:42', '2026-10-01 14:10:49', 0, NULL, NULL),
(8, 'Tour Châu Âu', 6, 8, '', '', 'active', 'tour-chau-au', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-09-29 07:39:22', '2026-10-01 14:10:49', 0, NULL, NULL),
(9, 'Tour Châu Mỹ', 6, 9, '', '', 'active', 'tour-chau-my', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-09-29 07:39:48', '2026-10-01 14:10:49', 0, NULL, NULL),
(10, 'Tour Châu Úc', 6, 10, '', '', 'active', 'tour-chau-uc', 'Tài khoản đã xóa', 'Phan Thanh Duy', '2026-09-29 07:40:01', '2026-10-11 10:38:53', 0, NULL, NULL),
(12, 'werwedwdew', NULL, 12, '', '<p>ewd</p>', 'active', 'werwedwdew', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-10-01 14:11:07', '2026-11-16 16:48:37', 1, 'Phan Thanh Duy', '2026-11-16 16:48:37'),
(13, 'sadad', NULL, 13, '', '', 'active', 'sadad', 'Tài khoản đã xóa', 'Tài khoản đã xóa', '2026-10-02 06:14:08', '2026-11-16 16:48:39', 1, 'Phan Thanh Duy', '2026-11-16 16:48:39');

-- --------------------------------------------------------

--
-- Table structure for table `cities`
--

CREATE TABLE `cities` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `cities`
--

INSERT INTO `cities` (`id`, `name`) VALUES
(1, 'Quảng Ninh'),
(2, 'Cà Mau'),
(3, 'Đắk Lắk'),
(4, 'Hà Tĩnh'),
(5, 'Lâm Đồng'),
(6, 'Lạng Sơn'),
(7, 'Sơn La'),
(8, 'Tuyên Quang'),
(9, 'Hà Nội'),
(10, 'Hồ Chí Minh'),
(11, 'Đà Nẵng'),
(12, 'Cao Bằng'),
(13, 'Đồng Tháp'),
(14, 'Hưng Yên'),
(15, 'Phú Thọ'),
(16, 'Quảng Ngãi'),
(17, 'Vĩnh Long'),
(18, 'Hải Phòng'),
(19, 'Huế'),
(20, 'Bắc Ninh'),
(21, 'An Giang'),
(22, 'Điện Biên'),
(23, 'Ninh Bình'),
(24, 'Tây Ninh'),
(25, 'Thái Nguyên'),
(26, 'Cần Thơ'),
(27, 'Đồng Nai'),
(28, 'Gia Lai'),
(29, 'Khánh Hòa'),
(30, 'Lai Châu'),
(31, 'Lào Cai'),
(32, 'Nghệ An'),
(33, 'Quảng Trị'),
(34, 'Thanh Hóa');

-- --------------------------------------------------------

--
-- Table structure for table `contacts`
--

CREATE TABLE `contacts` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `message` text DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted` tinyint(1) DEFAULT 0,
  `deleted_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `contacts`
--

INSERT INTO `contacts` (`id`, `email`, `name`, `phone`, `subject`, `message`, `created_at`, `updated_at`, `deleted`, `deleted_by`, `deleted_at`) VALUES
(1, 'ptd.ntn2907@gmail.com', NULL, NULL, NULL, NULL, '2026-10-14 08:30:49', '2026-10-14 08:30:49', 0, NULL, NULL),
(2, 'ptd.ntn2902@gmail.com', NULL, NULL, NULL, NULL, '2026-10-14 09:16:08', '2026-10-14 09:16:08', 0, NULL, NULL),
(3, 'ptd.ntn2901@gmail.com', NULL, NULL, NULL, NULL, '2026-10-14 09:18:42', '2026-11-17 14:40:32', 0, NULL, NULL),
(4, 'đa@gmail.com', NULL, NULL, NULL, NULL, '2026-10-14 09:22:04', '2026-10-14 09:56:39', 1, NULL, NULL),
(5, 'ptd.ntn29012@gmail.com', NULL, NULL, NULL, NULL, '2026-11-19 20:25:05', '2026-11-19 20:25:05', 0, NULL, NULL),
(8, 'ptd.ntn265907@gmail.com', NULL, NULL, NULL, NULL, '2026-11-19 20:38:44', '2026-11-19 20:38:44', 0, NULL, NULL),
(10, 'ptd.ntn2907@gmail.com', 'Phan Thanh Duy', '0905383132', 'Tư vấn tour trong nước', 'csfsfsf', '2026-11-19 21:24:42', '2026-11-19 21:24:42', 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `coupons`
--

CREATE TABLE `coupons` (
  `id` int(11) NOT NULL,
  `code` varchar(50) NOT NULL,
  `title` varchar(255) NOT NULL,
  `type` varchar(20) NOT NULL DEFAULT 'percentage',
  `value` double NOT NULL DEFAULT 0,
  `min_order_value` double DEFAULT 0,
  `max_discount_value` double DEFAULT 0,
  `usage_limit` int(11) DEFAULT 0,
  `used_count` int(11) DEFAULT 0,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `status` varchar(20) DEFAULT 'active',
  `created_by` varchar(255) DEFAULT 'Phan Thanh Duy',
  `updated_by` varchar(255) DEFAULT 'Phan Thanh Duy',
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted` tinyint(1) DEFAULT 0,
  `deleted_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `coupons`
--

INSERT INTO `coupons` (`id`, `code`, `title`, `type`, `value`, `min_order_value`, `max_discount_value`, `usage_limit`, `used_count`, `start_date`, `end_date`, `status`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`, `deleted_by`, `deleted_at`) VALUES
(1, 'DUT2026', 'Mã Giảm Giá Khai Xuân 2026 (Giảm 10%)', 'percentage', 10, 0, 1000000, 100, 2, '2026-01-01', '2026-12-31', 'active', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-20 16:23:49', '2026-11-20 20:42:00', 0, NULL, NULL),
(2, 'DUT500K', 'Ưu Đãi Đặc Biệt Giảm 500.000đ', 'fixed', 500000, 3000000, 500000, 50, 0, '2026-01-01', '2026-12-31', 'active', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-20 16:23:49', '2026-11-20 16:42:32', 0, NULL, NULL),
(3, 'VIPDUT', 'Tri Ân Khách Hàng VIP (Giảm 15%)', 'percentage', 15, 5000000, 2000000, 30, 0, '2026-01-01', '2026-12-31', 'active', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-20 16:23:49', '2026-11-20 16:42:32', 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `forgot_password`
--

CREATE TABLE `forgot_password` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `otp` varchar(20) NOT NULL,
  `expire_at` datetime DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `news`
--

CREATE TABLE `news` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `slug` varchar(255) NOT NULL,
  `category` varchar(100) NOT NULL DEFAULT 'C?m nang du l?ch',
  `avatar` varchar(500) NOT NULL,
  `description` text DEFAULT NULL,
  `content` longtext DEFAULT NULL,
  `author` varchar(100) DEFAULT 'DUT Travel Editor',
  `views` int(11) DEFAULT 0,
  `featured` tinyint(1) DEFAULT 0,
  `status` varchar(20) DEFAULT 'active',
  `deleted` tinyint(1) DEFAULT 0,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `news`
--

INSERT INTO `news` (`id`, `title`, `slug`, `category`, `avatar`, `description`, `content`, `author`, `views`, `featured`, `status`, `deleted`, `created_at`, `updated_at`, `deleted_by`, `deleted_at`) VALUES
(1, 'Top 5 điểm check in độc đáo tại Singapore không thể bỏ lỡ', 'top-5-diem-check-in-doc-dao-tai-singapore', 'Điểm đến hot', '/public/assets/images/Blog-2.jpg', 'Nếu bạn nghĩ du lịch Singapore rất nhàm chán vì chỉ mãi quanh quẩn trong những khối bê tông vô cảm thì bạn “chắc chưa?”. Tuy đảo quốc Sư Tử không phải là một quốc gia rộng lớn nhưng các địa điểm du lịch tại Singapore chưa từng đánh mất sức hút đối với cộng đồng xê dịch quốc tế.', '<p>Singapore tuy l&agrave; một quốc gia c&oacute; diện t&iacute;ch nhỏ nhưng lu&ocirc;n nằm trong top những điểm đến hấp dẫn bậc nhất thế giới nhờ sự kết hợp h&agrave;i h&ograve;a giữa kiến tr&uacute;c tương lai, thi&ecirc;n nhi&ecirc;n xanh m&aacute;t v&agrave; văn h&oacute;a đa sắc tộc.</p>\r\n<h3>1. Gardens by the Bay - Khu vườn si&ecirc;u thực</h3>\r\n<p>Đ&acirc;y l&agrave; biểu tượng số một của Singapore hiện đại với c&aacute;c si&ecirc;u c&acirc;y Supertree Grove khổng lồ ph&aacute;t s&aacute;ng rực rỡ v&agrave;o ban đ&ecirc;m c&ugrave;ng hai nh&agrave; k&iacute;nh Cloud Forest v&agrave; Flower Dome sở hữu th&aacute;c nước trong nh&agrave; cao nhất thế giới.</p>\r\n<h3>2. Marina Bay Sands &amp; SkyPark Observation Deck</h3>\r\n<p>Đứng từ đ&agrave;i quan s&aacute;t tầng 57, bạn c&oacute; thể ph&oacute;ng tầm mắt bao trọn vịnh Marina thơ mộng, ngắm ho&agrave;ng h&ocirc;n bu&ocirc;ng xuống th&agrave;nh phố v&agrave; chi&ecirc;m ngưỡng m&agrave;n tr&igrave;nh diễn &aacute;nh s&aacute;ng Spectra show sống động.</p>\r\n<h3>3. Đảo Sentosa &amp; B&atilde;i biển Siloso</h3>\r\n<p>Sentosa l&agrave; thi&ecirc;n đường giải tr&iacute; đỉnh cao với Universal Studios Singapore, thủy cung S.E.A. Aquarium kỳ vĩ v&agrave; những b&atilde;i biển c&aacute;t trắng mịn m&agrave;ng th&iacute;ch hợp cho những ai t&igrave;m kiếm sự thư gi&atilde;n.</p>\r\n<h3>4. Khu phố cổ Haji Lane &amp; Kampong Glam</h3>\r\n<p>Khu phố nghệ thuật rực rỡ với c&aacute;c bức tranh b&iacute;ch họa graffiti đầy m&agrave;u sắc, những tiệm c&agrave; ph&ecirc; indie phong c&aacute;ch v&agrave; cửa h&agrave;ng thời trang thiết kế độc lạ đậm chất nghệ thuật đường phố.</p>\r\n<h3>5. Th&aacute;c nước Jewel Changi</h3>\r\n<p>Được mệnh danh l&agrave; th&aacute;c nước trong nh&agrave; lớn nhất h&agrave;nh tinh, Rain Vortex tại Jewel Changi sẽ khiến bất kỳ du kh&aacute;ch n&agrave;o đặt ch&acirc;n đến Singapore cũng phải trầm trồ ngay từ khoảnh khắc đầu ti&ecirc;n.</p>', 'Nguyễn Minh Anh', 1544, 1, 'active', 0, '2026-11-19 21:27:30', '2026-11-19 22:16:32', NULL, NULL),
(2, 'Homestay cổ tích giữa Đà Lạt mộng mơ', 'homestay-co-tich-giua-da-lat-mong-mo', 'Nghi duong', '/public/assets/images/Blog-1.jpg', 'Homestay dep nhat Da Lat', 'Noi dung', 'Phan Thanh Duy', 890, 0, 'active', 0, '2026-11-19 21:27:30', '2026-11-19 22:16:32', NULL, NULL),
(3, 'Đặc sản không thể bỏ qua khi đến với Đà Nẵng – Hội An', 'dac-san-khong-the-bo-qua-khi-den-voi-da-nang-hoi-an', 'Ẩm thực', '/public/assets/images/Blog-3.jpg', 'Từ Mì Quảng đậm đà, Bánh tráng cuốn thịt heo trứ danh đến Cao Lầu phố Hội và Bánh mì Phượng vang danh thế giới. Bỏ túi ngay danh sách địa chỉ ăn ngon chuẩn vị miền Trung.', '<p>Ẩm thực xứ Quảng mang hương vị đậm đà, mộc mạc nhưng khó quên. Mỗi món ăn đều là sự kết tinh của nguyên liệu tươi ngon từ vùng đồng bằng và biển cả miền Trung.</p><h3>1. Mì Quảng ếch, tôm thịt</h3><p>Sợi mì vàng óng dai mềm, nước dùng đậm vị sền sệt ăn kèm bánh tráng nướng giòn rụm và đĩa rau sống búp chuối tươi non.</p><h3>2. Bánh tráng cuốn thịt heo hai đầu da</h3><p>Từng lát thịt heo luộc khéo léo với hai đầu mỡ trong veo, cuốn kèm rau thơm, dưa chuột và chấm mắm nêm đậm đà thơm nức mũi.</p><h3>3. Cao Lầu phố cổ Hội An</h3><p>Món ăn mang đậm dấu ấn giao thoa văn hóa, sợi mì làm từ tro củi Cù Lao Chàm giòn dai độc nhất vô nhị, ăn cùng thịt xá xíu thơm lừng.</p>', 'Lê Thu Trang', 1210, 0, 'active', 0, '2026-11-19 21:27:30', '2026-11-19 21:27:30', NULL, NULL),
(4, 'Đảo Phú Quý Bình Thuận hot trend năm 2026', 'dao-phu-quy-binh-thuan-hot-trend-nam-2026', 'Khám phá', '/public/assets/images/Blog-4.jpg', 'Hòn đảo hoang sơ giữa đại dương xanh ngắt với bờ biển dốc đá tuyệt đẹp, hồ bơi tự nhiên và những góc sống ảo triệu view khiến mọi tín đồ du lịch say đắm.', '<p>Đảo Phú Quý - viên ngọc thô giữa biển Đông đang trở thành điểm đến được giới trẻ săn đón nhiều nhất trong năm 2026 nhờ vẻ đẹp hoang sơ và làn nước biển trong vắt nhìn thấy đáy.</p><h3>Những tọa độ check-in đẹp mê mẩn</h3><p>Dốc Phượt uốn lượn ven biển, Cột cờ chủ quyền, Vịnh Triều Dương với hàng dương xanh mát và Đấu trường La Mã (Hồ cá Làng Dương) đều là những điểm chụp ảnh không góc chết.</p><h3>Lưu ý khi khám phá Phú Quý</h3><p>Nên chọn đi vào mùa biển êm từ tháng 2 đến tháng 6 để tránh say sóng khi di chuyển tàu cao tốc. Hãy thuê một chiếc xe máy để tự do lượn quanh đảo và cảm nhận nhịp sống thân thiện của người dân bản địa.</p>', 'Phạm Quốc Huy', 961, 0, 'active', 0, '2026-11-19 21:27:30', '2026-11-19 22:16:49', NULL, NULL),
(5, 'Trekking cắm trại tại Đà Lạt - Cảm nhận từng bước chân', 'trekking-cam-trai-tai-da-lat-cam-nhan-tung-buoc-chan', 'Trekking', '/public/assets/images/Blog-5.jpg', 'Hành trình chinh phục đỉnh Langbiang hùng vĩ hay săn mây đỉnh Hòn Bồ. Chia sẻ trọn bộ kinh nghiệm chuẩn bị thể lực, dụng cụ và cắm trại qua đêm giữa thiên nhiên trong lành.', '<p>Trekking kết hợp cắm trại tại Đà Lạt là trải nghiệm tuyệt vời cho những ai muốn rời xa ồn ào khói bụi đô thị để hòa mình vào núi rừng hoang sơ và lắng nghe tiếng gió thông reo.</p><h3>Chuẩn bị hành trang cần thiết</h3><ul><li>Lều cắm trại 2 lớp chống thấm sương đêm và túi ngủ giữ nhiệt chuẩn dưới 10 độ C.</li><li>Giày leo núi có độ bám tốt, vớ len ấm và áo khoác gió cản nhiệt.</li><li>Đèn pin đội đầu, bình nước giữ nhiệt và dụng cụ nấu nướng dã ngoại gọn nhẹ.</li></ul><p>Cảm giác quây quần bên đốm lửa trại bập bùng, cùng nướng thịt và ngắm dải ngân hà lấp lánh sẽ là kỷ niệm thanh xuân khó phai mờ.</p>', 'Đặng Hải Đăng', 745, 0, 'active', 0, '2026-11-19 21:27:30', '2026-11-19 21:27:30', NULL, NULL),
(6, 'Kinh nghiệm du lịch Phú Quốc từ A đến Z cho người đi lần đầu', 'kinh-nghiem-du-lich-phu-quoc-tu-a-den-z-cho-nguoi-di-lan-dau', 'Cẩm nang du lịch', 'https://images.unsplash.com/photo-1540555700478-4be289fbecef?auto=format&fit=crop&w=800&q=80', 'Trọn bộ bí kíp khám phá đảo Ngọc Phú Quốc: Thời điểm đi đẹp nhất, phương tiện di chuyển, cách săn vé máy bay giá rẻ và những trải nghiệm ngắm hoàng hôn lãng mạn nhất.', '<p>Phú Quốc được mệnh danh là đảo Ngọc với những bãi cát trắng trải dài, nước biển màu ngọc bích và hoàng hôn rực rỡ nhất Việt Nam.</p><h3>Thời điểm vàng để du lịch Phú Quốc</h3><p>Thời gian đẹp nhất là từ tháng 10 đến tháng 4 năm sau khi mùa mưa đã dứt, thời tiết khô ráo, nắng vàng ươm và biển cực kỳ êm ả.</p><h3>Những trải nghiệm bắt buộc phải thử</h3><p>Trải nghiệm cáp treo vượt biển Hòn Thơm dài nhất thế giới, lặn ngắm san hô tại quần đảo An Thới, thưởng thức hải sản tươi rói tại làng chài Hàm Ninh và ngắm hoàng hôn đỏ rực tại Sunset Sanato.</p>', 'DUT Travel Team', 2133, 0, 'active', 0, '2026-11-19 21:27:30', '2026-11-19 22:12:25', NULL, NULL),
(7, 'Khám phá Vịnh Hạ Long: Trải nghiệm nghỉ dưỡng du thuyền 5 sao', 'kham-pha-vinh-ha-long-trai-nghiem-nghi-duong-du-thuyen-5-sao', 'Nghỉ dưỡng', 'https://images.unsplash.com/photo-1528127269322-539801943592?auto=format&fit=crop&w=800&q=80', 'Chiêm ngưỡng kỳ quan thiên nhiên thế giới trên những chiếc du thuyền sang trọng chuẩn quốc tế, chèo kayak qua hang Luồn và thưởng thức bữa tối lãng mạn giữa vịnh biển.', '<p>Vịnh Hạ Long - kỳ quan thiên nhiên thế giới được UNESCO công nhận là niềm tự hào của du lịch Việt Nam. Trải nghiệm ngủ đêm trên du thuyền 5 sao sẽ mang đến cho bạn góc nhìn hoàn toàn mới lạ.</p><h3>Lịch trình 2 ngày 1 đêm trên du thuyền</h3><p>Ngày 1: Đón khách tại cảng quốc tế Tuần Châu, thưởng thức bữa trưa buffet hải sản trên vịnh, chèo kayak khám phá hang Sửng Sốt hoặc tắm biển tại đảo Ti Tốp.</p><p>Ngày 2: Tập Thái Cực Quyền đón bình minh trên sundeck, thăm hang Luồn và thưởng thức brunch buffet trước khi du thuyền cập bến trở về.</p>', 'DUT Travel Editor', 1350, 0, 'inactive', 0, '2026-11-19 21:27:30', '2026-11-19 22:06:20', NULL, NULL),
(8, '10 bí quyết vàng chuẩn bị hành lý khi đi tour du lịch quốc tế', 'bi-quyet-chuan-bi-hanh-ly-khi-di-tour-quoc-te', 'Mẹo hay', 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=800&q=80', 'Những lưu ý về quy định hành lý hàng không, đổi tiền tệ, SIM quốc tế và các giấy tờ cần thiết giúp chuyến xuất ngoại của bạn luôn suôn sẻ và tiện lợi nhất.', '<p>Chuẩn bị h&agrave;nh l&yacute; chu đ&aacute;o l&agrave; ch&igrave;a kh&oacute;a v&agrave;ng cho một chuyến đi tour nước ngo&agrave;i trọn vẹn v&agrave; an t&acirc;m.</p>\r\n<h3>1. Giấy tờ t&ugrave;y th&acirc;n</h3>\r\n<p>Hộ chiếu phải c&ograve;n hạn &iacute;t nhất 6 th&aacute;ng kể từ ng&agrave;y khởi h&agrave;nh. Lu&ocirc;n chụp ảnh v&agrave; lưu bản sao hộ chiếu, visa v&agrave; bảo hiểm du lịch l&ecirc;n điện thoại hoặc đ&aacute;m m&acirc;y ph&ograve;ng trường hợp thất lạc.</p>\r\n<h3>2. Tiền mặt &amp; Thẻ thanh to&aacute;n quốc tế</h3>\r\n<p>N&ecirc;n đổi sẵn một khoản tiền mặt nội tệ của nước sở tại v&agrave; mang theo &iacute;t nhất 2 loại thẻ t&iacute;n dụng/ghi nợ quốc tế (Visa/Mastercard) đ&atilde; mở kh&oacute;a thanh to&aacute;n quốc tế.</p>\r\n<h3>3. Kết nối internet xuy&ecirc;n bi&ecirc;n giới</h3>\r\n<p>Mua eSIM hoặc SIM du lịch quốc tế trước khi bay gi&uacute;p bạn kết nối mạng ngay khi m&aacute;y bay hạ c&aacute;nh để li&ecirc;n lạc với trưởng đo&agrave;n v&agrave; người th&acirc;n dễ d&agrave;ng.</p>', 'Nguyễn Minh Anh', 680, 0, 'inactive', 0, '2026-11-19 21:27:30', '2026-11-19 22:12:25', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `code` varchar(100) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `note` text DEFAULT NULL,
  `items` longtext DEFAULT NULL,
  `sub_total` double DEFAULT 0,
  `total` double DEFAULT 0,
  `coupon_code` varchar(50) DEFAULT NULL,
  `discount` double DEFAULT 0,
  `payment_method` varchar(50) DEFAULT 'money',
  `payment_status` varchar(50) DEFAULT 'unpaid',
  `status` varchar(50) DEFAULT 'initial',
  `updated_by` varchar(255) DEFAULT 'Phan Thanh Duy',
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted` tinyint(1) DEFAULT 0,
  `deleted_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `code`, `full_name`, `phone`, `email`, `note`, `items`, `sub_total`, `total`, `coupon_code`, `discount`, `payment_method`, `payment_status`, `status`, `updated_by`, `created_at`, `updated_at`, `deleted`, `deleted_by`, `deleted_at`) VALUES
(1, 'OD1171439027', 'Phan Thanh Duy', '0905383132', NULL, '', '[{\"tourId\": \"6acb69adc47144e89567b1ab\", \"quantityAdult\": 1, \"quantityChildren\": 1, \"quantityBaby\": 0, \"locationFrom\": \"6abf5222262841365017fd09\", \"priceNewAdult\": 2190000, \"priceNewChildren\": 1490000, \"priceNewBaby\": 1090000, \"departureDate\": \"2026-10-23 00:00:00\"}, {\"tourId\": \"6acb6a3ac47144e89567b1ac\", \"quantityAdult\": 1, \"quantityChildren\": 3, \"quantityBaby\": 0, \"locationFrom\": \"6abf5222262841365017fd03\", \"priceNewAdult\": 2290000, \"priceNewChildren\": 1790000, \"priceNewBaby\": 1490000, \"departureDate\": \"2026-10-26 00:00:00\"}]', 16900000, 16900000, NULL, 0, 'money', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-10-24 10:29:24', '2026-10-24 10:29:24', 0, NULL, NULL),
(2, 'OD5160646152', 'Phan Thanh Duy', '0982434442', NULL, '', '[{\"tourId\": \"6ad4a73c1dc6114b7491c17e\", \"quantityAdult\": 1, \"quantityChildren\": 0, \"quantityBaby\": 0, \"locationFrom\": \"6abf5222262841365017fd00\", \"priceNewAdult\": 2590000, \"priceNewChildren\": 2090000, \"priceNewBaby\": 1390000, \"departureDate\": \"2026-10-24 00:00:00\"}]', 2590000, 2590000, NULL, 0, 'money', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-10-24 10:30:36', '2026-10-24 10:30:36', 0, NULL, NULL),
(3, 'OD9599556564', 'Phan Thanh Duy', '0905383132', NULL, '', '[{\"tourId\": \"6acb69adc47144e89567b1ab\", \"quantityAdult\": 1, \"quantityChildren\": 0, \"quantityBaby\": 0, \"locationFrom\": \"6abf5222262841365017fd09\", \"priceNewAdult\": 2190000, \"priceNewChildren\": 1490000, \"priceNewBaby\": 1090000, \"departureDate\": \"2026-10-23 00:00:00\"}]', 2190000, 2190000, NULL, 0, 'money', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-10-24 10:31:26', '2026-10-24 10:31:26', 0, NULL, NULL),
(4, 'OD5326858323', 'Phan Thanh Duy', '0905383132', NULL, '123456', '[{\"tourId\": \"6acb6a3ac47144e89567b1ac\", \"quantityAdult\": 1, \"quantityChildren\": 0, \"quantityBaby\": 0, \"locationFrom\": \"6abf5222262841365017fd03\", \"priceNewAdult\": 2290000, \"priceNewChildren\": 1790000, \"priceNewBaby\": 1490000, \"departureDate\": \"2026-10-26 00:00:00\"}, {\"tourId\": \"6acb69adc47144e89567b1ab\", \"quantityAdult\": 1, \"quantityChildren\": 0, \"quantityBaby\": 0, \"locationFrom\": \"6abf5222262841365017fd09\", \"priceNewAdult\": 2190000, \"priceNewChildren\": 1490000, \"priceNewBaby\": 1090000, \"departureDate\": \"2026-10-23 00:00:00\"}]', 4480000, 4480000, NULL, 0, 'money', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-10-24 10:39:01', '2026-10-24 10:39:01', 0, NULL, NULL),
(5, 'OD0092881905', 'Phan Thanh Duy', '0905383132', NULL, '1234566', '[{\"tourId\": \"6acb69adc47144e89567b1ab\", \"quantityAdult\": 1, \"quantityChildren\": 1, \"quantityBaby\": 0, \"locationFrom\": \"6abf5222262841365017fd09\", \"priceNewAdult\": 2190000, \"priceNewChildren\": 1490000, \"priceNewBaby\": 1090000, \"departureDate\": \"2026-10-23 00:00:00\"}, {\"tourId\": \"6ad4a73c1dc6114b7491c17e\", \"quantityAdult\": 1, \"quantityChildren\": 0, \"quantityBaby\": 0, \"locationFrom\": \"6abf5222262841365017fd00\", \"priceNewAdult\": 2590000, \"priceNewChildren\": 2090000, \"priceNewBaby\": 1390000, \"departureDate\": \"2026-10-24 00:00:00\"}]', 7360000, 7360000, NULL, 0, 'money', 'paid', 'done', 'Phan Thanh Duy', '2026-10-24 13:30:49', '2026-10-25 13:35:02', 0, NULL, NULL),
(6, 'OD6918513040', 'Phan Thanh Duy', '0905383132', NULL, 'abc', '[{\"tourId\": \"6acb69adc47144e89567b1ab\", \"quantityAdult\": 0, \"quantityChildren\": 1, \"quantityBaby\": 1, \"locationFrom\": \"6abf5222262841365017fd09\", \"priceNewAdult\": 2190000, \"priceNewChildren\": 1490000, \"priceNewBaby\": 1090000, \"departureDate\": \"2026-10-23 00:00:00\"}]', 2580000, 2580000, NULL, 0, 'bank', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-10-28 10:20:52', '2026-11-17 14:14:12', 0, NULL, NULL),
(7, 'OD4261640964', 'Phan Thanh Duy', '0905383132', NULL, '21', '[{\"tourId\": \"6acb6a3ac47144e89567b1ac\", \"quantityAdult\": 1, \"quantityChildren\": 0, \"quantityBaby\": 0, \"locationFrom\": \"6abf5222262841365017fd03\", \"priceNewAdult\": 2290000, \"priceNewChildren\": 1790000, \"priceNewBaby\": 1490000, \"departureDate\": \"2026-10-26 00:00:00\"}]', 2290000, 2290000, NULL, 0, 'bank', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-10-28 10:25:23', '2026-11-17 14:27:04', 0, NULL, NULL),
(8, 'OD2154875255', 'Phan Thanh Duy', '0905383132', NULL, '', '[{\"tourId\": \"6acb6a3ac47144e89567b1ac\", \"quantityAdult\": 1, \"quantityChildren\": 0, \"quantityBaby\": 0, \"locationFrom\": \"6abf5222262841365017fd03\", \"priceNewAdult\": 2290000, \"priceNewChildren\": 1790000, \"priceNewBaby\": 1490000, \"departureDate\": \"2026-10-26 00:00:00\"}]', 2290000, 2290000, NULL, 0, 'bank', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-10-28 10:30:01', '2026-11-17 14:27:04', 0, NULL, NULL),
(9, 'OD7279759730', 'Phan Thanh Duy', '0905383132', NULL, '123456', '[{\"tourId\": \"6ad4a73c1dc6114b7491c17e\", \"quantityAdult\": 1, \"quantityChildren\": 2, \"quantityBaby\": 0, \"locationFrom\": \"6abf5222262841365017fd00\", \"priceNewAdult\": 2590000, \"priceNewChildren\": 2090000, \"priceNewBaby\": 1390000, \"departureDate\": \"2026-10-24 00:00:00\"}]', 9550000, 9550000, NULL, 0, 'vnpay', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-10-28 13:16:27', '2026-10-28 13:16:27', 0, NULL, NULL),
(10, 'OD0564158484', 'Phan Thanh Duy', '0905383132', NULL, '', '[{\"tourId\": \"6ad4a73c1dc6114b7491c17e\", \"quantityAdult\": 1, \"quantityChildren\": 0, \"quantityBaby\": 2, \"locationFrom\": \"6abf5222262841365017fd00\", \"priceNewAdult\": 2590000, \"priceNewChildren\": 2090000, \"priceNewBaby\": 1390000, \"departureDate\": \"2026-10-24 00:00:00\"}]', 2590000, 2590000, NULL, 0, 'vnpay', 'paid', 'initial', 'Phan Thanh Duy', '2026-10-28 13:40:46', '2026-11-19 22:27:48', 0, NULL, NULL),
(11, 'OD7155723840', 'Phan Thanh Duy', '0905383132', NULL, '123456', '[{\"tourId\": \"6acb69adc47144e89567b1ab\", \"quantityAdult\": 1, \"quantityChildren\": 1, \"quantityBaby\": 1, \"locationFrom\": \"6abf5222262841365017fd09\", \"priceNewAdult\": 2190000, \"priceNewChildren\": 1490000, \"priceNewBaby\": 1090000, \"departureDate\": \"2026-10-23 00:00:00\"}, {\"tourId\": \"6acb6a3ac47144e89567b1ac\", \"quantityAdult\": 1, \"quantityChildren\": 2, \"quantityBaby\": 0, \"locationFrom\": \"6abf5222262841365017fd03\", \"priceNewAdult\": 2290000, \"priceNewChildren\": 1790000, \"priceNewBaby\": 1490000, \"departureDate\": \"2026-10-26 00:00:00\"}]', 10640000, 10640000, NULL, 0, 'vnpay', 'paid', 'initial', 'Phan Thanh Duy', '2026-10-28 14:06:45', '2026-11-17 14:27:46', 1, 'Phan Thanh Duy', '2026-11-17 14:27:46'),
(12, 'OD1432691978', 'Phan Thanh Duy', '0905383132', NULL, '', '[{\"id\":0,\"orderId\":0,\"tourId\":2,\"name\":\"Combo Đà Nẵng 2024: ĐÀ NẴNG – HỘI AN – BÀ NÀ HILL\",\"slug\":\"combo-da-nang-2024-da-nang-hoi-an-ba-na-hill\",\"avatar\":\"https://res.cloudinary.com/r5hwa2cj/image/upload/v1791715898/adwwpovg9aj8np43mryj.jpg\",\"quantityAdult\":1,\"quantityChildren\":2,\"quantityBaby\":0,\"priceNewAdult\":2290000.0,\"priceNewChildren\":1790000.0,\"priceNewBaby\":1490000.0,\"locationFrom\":1,\"locationFromName\":\"Quảng Ninh\",\"departureDate\":null,\"departureDateFormatted\":\"20/09/2026\",\"priceNewBabyFormatted\":\"1.490.000\",\"priceNewChildrenFormatted\":\"1.790.000\",\"priceNewAdultFormatted\":\"2.290.000\"}]', 5870000, 5870000, NULL, 0, 'money', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-11-19 08:34:26', '2026-11-19 08:34:26', 0, NULL, NULL),
(13, 'OD1563840675', 'Phan Thanh Duy', '0905383132', NULL, '123456', '[{\"id\":0,\"orderId\":0,\"tourId\":12,\"name\":\"Thái Lan: Bangkok – Thành Phố Biển Pattaya – Đảo San Hô Coral (5N4Đ)\",\"slug\":\"thai-lan-bangkok-pattaya-dao-coral-5n4d\",\"avatar\":\"https://images.unsplash.com/photo-1508009603885-50cf7c579365?auto=format&fit=crop&w=800&q=80\",\"quantityAdult\":1,\"quantityChildren\":1,\"quantityBaby\":2,\"priceNewAdult\":6490000.0,\"priceNewChildren\":5190000.0,\"priceNewBaby\":2000000.0,\"locationFrom\":1,\"locationFromName\":\"Quảng Ninh\",\"departureDate\":null,\"departureDateFormatted\":\"20/09/2026\",\"priceNewChildrenFormatted\":\"5.190.000\",\"priceNewAdultFormatted\":\"6.490.000\",\"priceNewBabyFormatted\":\"2.000.000\"}]', 15680000, 15680000, NULL, 0, 'vnpay', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-11-19 22:41:20', '2026-11-19 22:41:20', 0, NULL, NULL),
(14, 'OD1089711899', 'Phan Thanh Duy', '0905383132', NULL, 'ewdw', '[{\"id\":0,\"orderId\":0,\"tourId\":16,\"name\":\"Châu Âu Tuyệt Mỹ: Pháp – Thụy Sĩ – Ý – Thành Vatican (10N9Đ)\",\"slug\":\"chau-au-phap-thuy-si-y-vatican-10n9d\",\"avatar\":\"https://images.unsplash.com/photo-1502602898657-3e91760cbb34?auto=format&fit=crop&w=800&q=80\",\"quantityAdult\":1,\"quantityChildren\":0,\"quantityBaby\":1,\"priceNewAdult\":7.99E7,\"priceNewChildren\":6.39E7,\"priceNewBaby\":2.5E7,\"locationFrom\":1,\"locationFromName\":\"Quảng Ninh\",\"departureDate\":null,\"departureDateFormatted\":\"20/09/2026\",\"priceNewChildrenFormatted\":\"63.900.000\",\"priceNewAdultFormatted\":\"79.900.000\",\"priceNewBabyFormatted\":\"25.000.000\"}]', 104900000, 104900000, NULL, 0, 'vnpay', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-11-19 22:42:55', '2026-11-19 22:42:55', 0, NULL, NULL),
(15, 'OD1211703189', 'Phan Thanh Duy', '0901234567', NULL, '', '[{\"id\":0,\"orderId\":0,\"tourId\":17,\"name\":\"Bờ Tây Nước Mỹ: Los Angeles – Las Vegas – San Francisco (8N7Đ)\",\"slug\":\"bo-tay-hoa-ky-los-angeles-las-vegas-san-francisco-8n7d\",\"avatar\":\"https://images.unsplash.com/photo-1501594907352-04cda38ebc29?auto=format&fit=crop&w=800&q=80\",\"quantityAdult\":1,\"quantityChildren\":0,\"quantityBaby\":0,\"priceNewAdult\":8.89E7,\"priceNewChildren\":7.1E7,\"priceNewBaby\":2.8E7,\"locationFrom\":1,\"locationFromName\":\"Quảng Ninh\",\"departureDate\":null,\"departureDateFormatted\":\"20/09/2026\",\"priceNewChildrenFormatted\":\"71.000.000\",\"priceNewAdultFormatted\":\"88.900.000\",\"priceNewBabyFormatted\":\"28.000.000\"}]', 88900000, 88900000, NULL, 0, 'money', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-11-19 22:45:25', '2026-11-19 22:45:25', 0, NULL, NULL),
(16, 'OD1272909344', 'Phan Thanh Duy', '0905383132', NULL, '', '[{\"id\":0,\"orderId\":0,\"tourId\":17,\"name\":\"Bờ Tây Nước Mỹ: Los Angeles – Las Vegas – San Francisco (8N7Đ)\",\"slug\":\"bo-tay-hoa-ky-los-angeles-las-vegas-san-francisco-8n7d\",\"avatar\":\"https://images.unsplash.com/photo-1501594907352-04cda38ebc29?auto=format&fit=crop&w=800&q=80\",\"quantityAdult\":1,\"quantityChildren\":0,\"quantityBaby\":0,\"priceNewAdult\":8.89E7,\"priceNewChildren\":7.1E7,\"priceNewBaby\":2.8E7,\"locationFrom\":1,\"locationFromName\":\"Quảng Ninh\",\"departureDate\":null,\"departureDateFormatted\":\"20/09/2026\",\"priceNewChildrenFormatted\":\"71.000.000\",\"priceNewAdultFormatted\":\"88.900.000\",\"priceNewBabyFormatted\":\"28.000.000\"}]', 88900000, 88900000, NULL, 0, 'money', 'paid', 'done', 'Phan Thanh Duy', '2026-11-19 22:45:46', '2026-11-19 22:46:15', 0, NULL, NULL),
(20, 'OD1039460021', 'Phan Thanh Duy', '0905383132', 'ptd.ntn2907@gmail.com', 'Test gửi email xác nhận kèm lịch trình tour', '[{\"id\":0,\"orderId\":0,\"tourId\":1,\"name\":\"Tour 2026 Phú Quốc – Thiên Đường Đảo Ngọc (3N2D)\",\"slug\":\"tour-2026-phu-quoc-thien-duong-dao-ngoc-3n2d\",\"avatar\":\"https://res.cloudinary.com/r5hwa2cj/image/upload/v1791715757/t6r3pcaljnvzmjd1q1gf.jpg\",\"quantityAdult\":1,\"quantityChildren\":0,\"quantityBaby\":0,\"priceNewAdult\":2190000.0,\"priceNewChildren\":1490000.0,\"priceNewBaby\":1090000.0,\"locationFrom\":1,\"locationFromName\":\"Quảng Ninh\",\"departureDate\":null,\"departureDateFormatted\":\"20/09/2026\",\"priceNewChildrenFormatted\":\"1.490.000\",\"priceNewBabyFormatted\":\"1.090.000\",\"priceNewAdultFormatted\":\"2.190.000\"}]', 2190000, 1971000, 'DUT2026', 219000, 'bank', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-11-20 20:38:13', '2026-11-20 20:38:13', 0, NULL, NULL),
(21, 'OD1064057703', 'Phan Thanh Duy', '0905383132', 'ptd.ntn2907@gmail.com', '!!!', '[{\"id\":0,\"orderId\":0,\"tourId\":1,\"name\":\"Tour 2026 Phú Quốc – Thiên Đường Đảo Ngọc (3N2D)\",\"slug\":\"tour-2026-phu-quoc-thien-duong-dao-ngoc-3n2d\",\"avatar\":\"https://res.cloudinary.com/r5hwa2cj/image/upload/v1791715757/t6r3pcaljnvzmjd1q1gf.jpg\",\"quantityAdult\":1,\"quantityChildren\":0,\"quantityBaby\":0,\"priceNewAdult\":2190000.0,\"priceNewChildren\":1490000.0,\"priceNewBaby\":1090000.0,\"locationFrom\":1,\"locationFromName\":\"Quảng Ninh\",\"departureDate\":null,\"departureDateFormatted\":\"20/09/2026\",\"priceNewChildrenFormatted\":\"1.490.000\",\"priceNewBabyFormatted\":\"1.090.000\",\"priceNewAdultFormatted\":\"2.190.000\"},{\"id\":0,\"orderId\":0,\"tourId\":16,\"name\":\"Châu Âu Tuyệt Mỹ: Pháp – Thụy Sĩ – Ý – Thành Vatican (10N9Đ)\",\"slug\":\"chau-au-phap-thuy-si-y-vatican-10n9d\",\"avatar\":\"https://images.unsplash.com/photo-1502602898657-3e91760cbb34?auto=format&fit=crop&w=800&q=80\",\"quantityAdult\":1,\"quantityChildren\":0,\"quantityBaby\":0,\"priceNewAdult\":7.99E7,\"priceNewChildren\":6.39E7,\"priceNewBaby\":2.5E7,\"locationFrom\":9,\"locationFromName\":\"Hà Nội\",\"departureDate\":null,\"departureDateFormatted\":\"20/09/2026\",\"priceNewChildrenFormatted\":\"63.900.000\",\"priceNewBabyFormatted\":\"25.000.000\",\"priceNewAdultFormatted\":\"79.900.000\"}]', 82090000, 81090000, 'DUT2026', 1000000, 'money', 'unpaid', 'initial', 'Phan Thanh Duy', '2026-11-20 20:42:00', '2026-11-20 20:42:00', 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `order_items`
--

CREATE TABLE `order_items` (
  `id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `tour_id` int(11) NOT NULL,
  `quantity_adult` int(11) DEFAULT 0,
  `quantity_children` int(11) DEFAULT 0,
  `quantity_baby` int(11) DEFAULT 0,
  `price_new_adult` double DEFAULT 0,
  `price_new_children` double DEFAULT 0,
  `price_new_baby` double DEFAULT 0,
  `location_from` int(11) DEFAULT 1,
  `departure_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `order_items`
--

INSERT INTO `order_items` (`id`, `order_id`, `tour_id`, `quantity_adult`, `quantity_children`, `quantity_baby`, `price_new_adult`, `price_new_children`, `price_new_baby`, `location_from`, `departure_date`) VALUES
(1, 1, 1, 1, 0, 0, 3890000, 2700000, 800000, 1, '2026-09-20'),
(2, 12, 2, 1, 2, 0, 2290000, 1790000, 1490000, 1, NULL),
(3, 13, 12, 1, 1, 2, 6490000, 5190000, 2000000, 1, NULL),
(4, 14, 16, 1, 0, 1, 79900000, 63900000, 25000000, 1, NULL),
(5, 15, 17, 1, 0, 0, 88900000, 71000000, 28000000, 1, NULL),
(6, 16, 17, 1, 0, 0, 88900000, 71000000, 28000000, 1, NULL),
(10, 20, 1, 1, 0, 0, 2190000, 1490000, 1090000, 1, NULL),
(11, 21, 1, 1, 0, 0, 2190000, 1490000, 1090000, 1, NULL),
(12, 21, 16, 1, 0, 0, 79900000, 63900000, 25000000, 9, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `permissions` text DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT 'Phan Thanh Duy',
  `updated_by` varchar(255) DEFAULT 'Phan Thanh Duy',
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted` tinyint(1) DEFAULT 0,
  `deleted_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`, `description`, `permissions`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`, `deleted_by`, `deleted_at`) VALUES
(1, 'Quản trị viên', 'Có đầy đủ toàn bộ quyền trong hệ thống.', '[\"dashboard-view\", \"category-view\", \"category-create\", \"category-edit\", \"category-delete\", \"category-trash\", \"tour-view\", \"tour-create\", \"tour-edit\", \"tour-delete\", \"tour-trash\", \"order-view\", \"order-edit\", \"order-delete\", \"order-trash\", \"coupon-view\", \"coupon-create\", \"coupon-edit\", \"coupon-delete\", \"coupon-trash\", \"user-view\", \"user-edit\", \"user-delete\", \"contact-view\", \"contact-delete\", \"contact-trash\", \"news-view\", \"news-create\", \"news-edit\", \"news-delete\", \"news-trash\", \"setting-view\", \"setting-edit\", \"role-view\", \"role-create\", \"role-edit\", \"role-delete\", \"role-trash\", \"account-view\", \"account-create\", \"account-edit\", \"account-delete\"]', 'quan-tri-vien', 'Tài khoản đã xóa', 'Phan Thanh Duy', '2026-10-05 09:09:29', '2026-11-20 16:28:41', 0, NULL, NULL),
(2, 'Quản lý danh mục', 'Có đầy đủ các quyền liên quan tới danh mục tour.', '[\"dashboard-view\", \"category-view\", \"category-create\", \"category-edit\", \"category-delete\", \"category-trash\"]', 'quan-ly-danh-muc', 'Tài khoản đã xóa', 'Phan Thanh Duy', '2026-10-05 11:41:58', '2026-11-18 21:11:56', 0, NULL, NULL),
(3, 'Quản lý tour', 'Có đầy đủ các quyền liên quan tới tour du lịch.', '[\"dashboard-view\", \"tour-view\", \"tour-create\", \"tour-edit\", \"tour-delete\", \"tour-trash\"]', 'quan-ly-tour', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-10-11 08:26:50', '2026-11-18 21:11:56', 0, NULL, NULL),
(4, 'Quản lý đơn hàng', 'Có đầy đủ các quyền liên quan tới quản lý đơn hàng.', '[\"dashboard-view\", \"order-view\", \"order-edit\", \"order-delete\", \"order-trash\", \"coupon-view\", \"coupon-create\", \"coupon-edit\", \"coupon-delete\", \"coupon-trash\"]', 'role-4', 'System', 'System', '2026-11-18 21:11:56', '2026-11-20 16:28:41', 0, NULL, NULL),
(5, 'Quản lý người dùng & liên hệ', 'Có đầy đủ các quyền liên quan tới tài khoản người dùng và liên hệ khách hàng.', '[\"dashboard-view\", \"user-view\", \"user-edit\", \"user-delete\", \"contact-view\", \"contact-delete\", \"contact-trash\"]', 'role-5', 'System', 'System', '2026-11-18 21:11:56', '2026-11-18 21:11:56', 0, NULL, NULL),
(6, 'Nhân viên kinh doanh & CSKH', 'Xem danh mục, xem tour, xem đơn hàng và xử lý thông tin liên hệ.', '[\"dashboard-view\", \"category-view\", \"tour-view\", \"order-view\", \"contact-view\", \"contact-delete\", \"contact-trash\"]', 'role-6', 'System', 'System', '2026-11-18 21:11:56', '2026-11-18 21:11:56', 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `setting_website_info`
--

CREATE TABLE `setting_website_info` (
  `id` int(11) NOT NULL,
  `website_name` varchar(255) DEFAULT '28Tech Travel',
  `phone` varchar(50) DEFAULT '0123 456 789',
  `email` varchar(100) DEFAULT 'contact@28tech.com',
  `address` varchar(255) DEFAULT 'Tòa nhà FPT, Cầu Giấy, Hà Nội',
  `logo` varchar(255) DEFAULT '/assets/images/Logo.svg',
  `favicon` varchar(255) DEFAULT '/assets/images/Logo.svg',
  `facebook` varchar(255) DEFAULT '',
  `instagram` varchar(255) DEFAULT '',
  `whatsapp` varchar(255) DEFAULT '',
  `youtube` varchar(255) DEFAULT '',
  `copyright` varchar(255) DEFAULT '© 2026 DUTTech. All rights reserved.',
  `github` varchar(255) DEFAULT 'https://github.com'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `setting_website_info`
--

INSERT INTO `setting_website_info` (`id`, `website_name`, `phone`, `email`, `address`, `logo`, `favicon`, `facebook`, `instagram`, `whatsapp`, `youtube`, `copyright`, `github`) VALUES
(1, 'DUT Travel Official', '0905383132', 'ptd.ntn2907@gmail.com', '54 Nguyễn Lương Bằng, Phường Liên Chiểu, TP Đà Nẵng', 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1794754962/lktalvhmsmnlphwwdoxb.png', 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1794754964/u110xuapfe66oeoon0ft.png', 'https://facebook.com/thdyqt', 'https://instagram.com/thdyqt', 'https://github.com/thdyqt/CNW', 'https://youtube.com/@thdyqt', '© 2026 DUTTech Travel. All rights reserved.', 'https://github.com/thdyqt/CNW');

-- --------------------------------------------------------

--
-- Table structure for table `tours`
--

CREATE TABLE `tours` (
  `id` int(11) NOT NULL,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `position` int(11) DEFAULT 0,
  `status` varchar(50) DEFAULT 'active',
  `avatar` varchar(255) DEFAULT NULL,
  `images` text DEFAULT NULL,
  `price_adult` double DEFAULT 0,
  `price_children` double DEFAULT 0,
  `price_baby` double DEFAULT 0,
  `price_new_adult` double DEFAULT 0,
  `price_new_children` double DEFAULT 0,
  `price_new_baby` double DEFAULT 0,
  `stock_adult` int(11) DEFAULT 0,
  `stock_children` int(11) DEFAULT 0,
  `stock_baby` int(11) DEFAULT 0,
  `locations` text DEFAULT NULL,
  `time` varchar(100) DEFAULT NULL,
  `vehicle` varchar(100) DEFAULT NULL,
  `departure_date` date DEFAULT NULL,
  `information` longtext DEFAULT NULL,
  `schedules` longtext DEFAULT NULL,
  `slug` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT 'Phan Thanh Duy',
  `updated_by` varchar(255) DEFAULT 'Phan Thanh Duy',
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted` tinyint(1) DEFAULT 0,
  `deleted_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `tours`
--

INSERT INTO `tours` (`id`, `code`, `name`, `category_id`, `position`, `status`, `avatar`, `images`, `price_adult`, `price_children`, `price_baby`, `price_new_adult`, `price_new_children`, `price_new_baby`, `stock_adult`, `stock_children`, `stock_baby`, `locations`, `time`, `vehicle`, `departure_date`, `information`, `schedules`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`, `deleted_by`, `deleted_at`) VALUES
(1, 'DUT00001', 'Tour 2026 Phú Quốc – Thiên Đường Đảo Ngọc (3N2D)', 3, 4, 'active', 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1791715757/t6r3pcaljnvzmjd1q1gf.jpg', '', 10650000, 6650000, 2850000, 2190000, 1490000, 1090000, 7, 6, 8, '10', '3 ngày 2 đêm', '', '2026-10-23', '', '[{\"title\": \"\", \"description\": \"\"}]', 'tour-2026-phu-quoc-thien-duong-dao-ngoc-3n2d', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-10-11 10:49:17', '2026-11-20 20:42:00', 0, NULL, NULL),
(2, 'DUT00002', 'Combo Đà Nẵng 2024: ĐÀ NẴNG – HỘI AN – BÀ NÀ HILL', 4, 5, 'active', 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1791715898/adwwpovg9aj8np43mryj.jpg', 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1791715898/adwwpovg9aj8np43mryj.jpg', 12650000, 5650000, 3450000, 2290000, 1790000, 1490000, 9, 8, 11, '11', '2 ngày 1 đêm', 'Xích lô, xe ô tô', '2026-10-26', '', '[{\"title\":\"\",\"description\":\"\"}]', 'combo-da-nang-2024-da-nang-hoi-an-ba-na-hill', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-10-11 10:51:38', '2026-11-20 16:19:08', 0, NULL, NULL),
(3, 'DUT00003', 'Hà Nội – Lào Cai – SaPa 4N3Đ', 2, 1, 'active', 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1792321337/qcgcbnlpiykpgss7ippa.jpg', 'https://res.cloudinary.com/r5hwa2cj/image/upload/v1792321340/ibmmpammcp14vrq6vems.jpg', 13650000, 10650000, 7650000, 2590000, 2090000, 1390000, 10, 11, 13, '9,31', '4 ngày 3 đêm', 'Máy bay', '2026-12-23', '<p>adsfsfsf</p>\r\n<p><img src=\"https://res.cloudinary.com/r5hwa2cj/image/upload/v1792324456/glydrlmwoalprfe46ise.jpg\" alt=\"\" width=\"960\" height=\"591\"></p>', '[{\"title\":\"sfsf\",\"description\":\"<p>fsfsdfsdfad</p>\"},{\"title\":\"sdfs\",\"description\":\"<p>dfsfsfsfss</p>\\n<p><img src=\\\"https://res.cloudinary.com/r5hwa2cj/image/upload/v1792324418/ix1e5kgjxfll2dhnovgc.jpg\\\" alt=\\\"\\\" width=\\\"667\\\" height=\\\"518\\\"></p>\"}]', 'ha-noi-lao-cai-sapa-4n3d-ng-V30IH5', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-10-18 11:02:20', '2026-11-20 16:18:53', 0, NULL, NULL),
(4, 'DUT00004', 'Hà Giang – Cao Nguyên Đá Đồng Văn – Cột Cờ Lũng Cú – Mã Pí Lèng (3N2Đ)', 2, 2, 'active', 'https://images.unsplash.com/photo-1528127269322-539801943592?auto=format&fit=crop&w=800&q=80', 'https://images.unsplash.com/photo-1528127269322-539801943592?auto=format&fit=crop&w=800&q=80,https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=800&q=80', 4500000, 3150000, 1350000, 3490000, 2490000, 990000, 15, 8, 5, '9,31', '3 ngày 2 đêm', 'Xe ô tô du lịch cao cấp', '2026-11-28', '<p>Hành trình khám phá vẻ đẹp hùng vĩ của vùng đất địa đầu Tổ quốc. Quý khách sẽ được chinh phục đèo Mã Pí Lèng hiểm trở, ngắm dòng sông Nho Quế xanh ngắt và chạm tay vào lá cờ thiêng liêng tại Cột cờ Lũng Cú.</p>', '[{\"title\": \"Ngày 1: Hà Nội – TP. Hà Giang – Quản Bạ – Yên Minh\", \"description\": \"<p>Xe đón quý khách tại điểm hẹn khởi hành đi Hà Giang. Dừng chân ngắm Núi Đôi Quản Bạ, Cổng Trời Quản Bạ. Nghỉ đêm tại Yên Minh.</p>\"}, {\"title\": \"Ngày 2: Yên Minh – Lũng Cú – Đèo Mã Pí Lèng – Du thuyền Sông Nho Quế\", \"description\": \"<p>Thăm Cột Cờ Lũng Cú, dinh thự Vua Mèo Vương Chính Đức. Buổi chiều chinh phục đèo Mã Pí Lèng và đi thuyền kayak trên sông Nho Quế.</p>\"}, {\"title\": \"Ngày 3: Đồng Văn – Cổng Trời – Hà Nội\", \"description\": \"<p>Khám phá chợ phiên Đồng Văn, mua sắm đặc sản bánh tam giác mạch, thịt trâu gác bếp và trở về Hà Nội.</p>\"}]', 'ha-giang-dong-van-lung-cu-ma-pi-leng-3n2d', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-18 21:28:08', '2026-11-20 16:18:53', 0, NULL, NULL),
(5, 'DUT00005', 'Hạ Long – Vịnh Lan Hạ – Nghỉ Dưỡng Du Thuyền 5 Sao (2N1Đ)', 2, 3, 'active', 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=800&q=80', 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=800&q=80', 3800000, 2660000, 1140000, 2890000, 1990000, 800000, 20, 10, 6, '1,9', '2 ngày 1 đêm', 'Du thuyền 5 sao, Xe Limousine', '2026-12-05', '<p>Trải nghiệm kỳ nghỉ đẳng cấp trên vịnh Hạ Long – Di sản thiên nhiên thế giới. Tận hưởng không gian sang trọng trên du thuyền 5 sao, chèo thuyền kayak khám phá hang động và thưởng thức ẩm thực hải sản phong phú.</p>', '[{\"title\": \"Ngày 1: Hà Nội – Tuần Châu – Vịnh Hạ Long – Chèo Kayak\", \"description\": \"<p>Đón khách tại Hà Nội, làm thủ tục lên du thuyền tại bến Tuần Châu. Thưởng thức bữa trưa buffet và chèo kayak qua hang Sáng Tối.</p>\"}, {\"title\": \"Ngày 2: Tập Thái Cực Quyền – Hang Sửng Sốt – Cảng Tuần Châu – Hà Nội\", \"description\": \"<p>Đón bình minh trên biển, tham quan hang Sửng Sốt – hang động lớn và đẹp nhất vịnh. Làm thủ tục trả phòng và về Hà Nội.</p>\"}]', 'ha-long-lan-ha-du-thuyen-5-sao-2n1d', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-18 21:28:08', '2026-11-20 16:18:53', 0, NULL, NULL),
(6, 'DUT00006', 'Ninh Bình: Danh Thắng Tràng An – Chùa Bái Đính – Tuyệt Tình Cốc (1N)', 2, 4, 'active', 'https://images.unsplash.com/photo-1579273166627-897c9b83b8b1?auto=format&fit=crop&w=800&q=80', 'https://images.unsplash.com/photo-1579273166627-897c9b83b8b1?auto=format&fit=crop&w=800&q=80', 1600000, 1120000, 480000, 1190000, 850000, 350000, 30, 15, 8, '9,23', '1 ngày', 'Xe Limousine đón tận nơi', '2026-11-22', '<p>Kh&aacute;m ph&aacute; v&ugrave;ng đất cố đ&ocirc; Hoa Lư với quần thể danh thắng Tr&agrave;ng An được UNESCO c&ocirc;ng nhận l&agrave; di sản thế giới k&eacute;p, viếng thăm ng&ocirc;i ch&ugrave;a B&aacute;i Đ&iacute;nh kỷ lục Đ&ocirc;ng Nam &Aacute;.</p>', '[{\"title\":\"Sáng: Hà Nội – Chùa Bái Đính – Thưởng thức Cơm Cháy Dê Núi\",\"description\":\"<p>Khởi h&agrave;nh từ H&agrave; Nội, viếng ch&ugrave;a B&aacute;i Đ&iacute;nh chi&ecirc;m b&aacute;i tượng Phật bằng đồng lớn nhất Đ&ocirc;ng Nam &Aacute;. Ăn trưa đặc sản d&ecirc; n&uacute;i Ninh B&igrave;nh.</p>\"},{\"title\":\"Chiều: Ngồi thuyền khám phá Tràng An – Hang Múa – Về lại Hà Nội\",\"description\":\"<p>Ngồi đ&ograve; xu&ocirc;i d&ograve;ng S&agrave;o Kh&ecirc; len lỏi qua c&aacute;c hang động kỳ b&iacute;. Chinh phục đỉnh Ngọa Long ngắm to&agrave;n cảnh Tam Cốc từ tr&ecirc;n cao.</p>\"}]', 'ninh-binh-trang-an-bai-dinh-1-ngay', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-18 21:28:08', '2026-11-20 16:18:53', 0, NULL, NULL),
(7, 'DUT00007', 'Di Sản Miền Trung: Đà Nẵng – Cố Đô Huế – Phố Cổ Hội An (4N3Đ)', 4, 2, 'active', 'https://images.unsplash.com/photo-1559592413-7cec4d0cae2b?auto=format&fit=crop&w=800&q=80', 'https://images.unsplash.com/photo-1559592413-7cec4d0cae2b?auto=format&fit=crop&w=800&q=80', 5200000, 3640000, 1560000, 3990000, 2790000, 1200000, 25, 12, 6, '11,19', '4 ngày 3 đêm', 'Máy bay, xe du lịch đời mới', '2026-11-26', '<p>Cung đường di sản miền Trung đưa bạn qua những thắng cảnh đẹp nhất: cây cầu Vàng trên đỉnh Bà Nà, vẻ đẹp lắng đọng thời gian của Hội An và nét cổ kính thâm trầm của cố đô Huế.</p>', '[{\"title\": \"Ngày 1: Đón khách – Bán Đảo Sơn Trà – Biển Mỹ Khê – Đà Nẵng về đêm\", \"description\": \"<p>Đón quý khách tại sân bay Đà Nẵng, viếng chùa Linh Ứng ngắm tượng Phật Bà 67m, thưởng thức bữa tối và ngắm Cầu Rồng phun lửa.</p>\"}, {\"title\": \"Ngày 2: Bà Nà Hills – Cầu Vàng Bàn Tay – Phố Cổ Hội An\", \"description\": \"<p>Lên đỉnh Bà Nà chiêm ngưỡng Cầu Vàng, Làng Pháp. Chiều di chuyển về Hội An đi bộ ngắm phố cổ lung linh đèn lồng và thả đèn hoa đăng.</p>\"}, {\"title\": \"Ngày 3: Đà Nẵng – Đèo Hải Vân – Cố Đô Huế – Đại Nội – Chùa Thiên Mụ\", \"description\": \"<p>Vượt đèo Hải Vân đến Huế, tham quan Đại Nội Hoàng Thành, chùa Thiên Mụ cổ kính soi bóng dòng sông Hương.</p>\"}, {\"title\": \"Ngày 4: Lăng Khải Định – Mua sắm đặc sản mè xửng, nón bài thơ – Tiễn đoàn\", \"description\": \"<p>Khám phá kiến trúc đỉnh cao Lăng Khải Định, mua sắm đặc sản xứ Huế và tiễn đoàn ra sân bay.</p>\"}]', 'di-san-mien-trung-da-nang-hue-hoi-an-4n3d', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-18 21:28:08', '2026-11-20 16:18:53', 0, NULL, NULL),
(8, 'DUT00008', 'Nha Trang Biển Xanh: Du Thuyền 3 Đảo – Hòn Tằm – VinWonders (3N2Đ)', 4, 3, 'active', 'https://images.unsplash.com/photo-1540555700478-4be289fbecef?auto=format&fit=crop&w=800&q=80', 'https://images.unsplash.com/photo-1540555700478-4be289fbecef?auto=format&fit=crop&w=800&q=80', 4600000, 3220000, 1380000, 3290000, 2300000, 990000, 20, 10, 5, '29', '3 ngày 2 đêm', 'Máy bay, cano cao tốc', '2026-12-01', '<p>Thư giãn tại thiên đường biển nhiệt đới Nha Trang với bãi cát dài trắng mịn, làn nước trong vắt, tắm bùn khoáng nóng Hòn Tằm và vui chơi bất tận tại công viên giải trí VinWonders.</p>', '[{\"title\": \"Ngày 1: Đón sân bay Cam Ranh – Check-in Nha Trang – Tháp Bà Ponagar\", \"description\": \"<p>Xe đón khách tại sân bay Cam Ranh, nhận phòng khách sạn. Tham quan cụm tháp Chăm Ponagar cổ kính và tắm bùn khoáng nóng thư giãn.</p>\"}, {\"title\": \"Ngày 2: Du ngoạn Vịnh Nha Trang – Lặn ngắm San hô – Đảo Hòn Tằm\", \"description\": \"<p>Cano đưa đoàn đến vịnh san hô ngắm sinh vật biển, thưởng thức tiệc bar nổi trên biển và tắm khoáng thảo dược Hòn Tằm.</p>\"}, {\"title\": \"Ngày 3: Oanh tạc VinWonders Đảo Hòn Tre – Mua sắm đặc sản – Tiễn sân bay\", \"description\": \"<p>Đi cáp treo vượt biển sang VinWonders trải nghiệm các trò chơi cảm giác mạnh, show diễn triệu đô Tata Show trước khi ra sân bay.</p>\"}]', 'nha-trang-bien-xanh-hon-tam-vinwonders-3n2d', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-18 21:28:08', '2026-11-20 16:18:53', 0, NULL, NULL),
(9, 'DUT00009', 'Đà Lạt Mộng Mơ: Săn Mây Cầu Đất – Thác Datanla – Đỉnh Langbiang (3N2Đ)', 3, 2, 'active', 'https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=800&q=80', 'https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=800&q=80', 3500000, 2450000, 1050000, 2690000, 1890000, 790000, 18, 8, 4, '5,10', '3 ngày 2 đêm', 'Xe Limousine phòng nằm VIP', '2026-11-27', '<p>Trốn nắng phố thị về với xứ sở ngàn hoa Đà Lạt, tận hưởng không khí se lạnh trong lành, đón bình minh săn biển mây bồng bềnh và thưởng thức ly cà phê ấm nóng giữa đồi thông thơ mộng.</p>', '[{\"title\": \"Ngày 1: Khởi hành – Đèo Chuối – Đà Lạt check-in Quảng Trường Lâm Viên\", \"description\": \"<p>Xe Limousine đưa đoàn tới Đà Lạt, dạo bước quanh Hồ Xuân Hương và chụp ảnh kỷ niệm tại Quảng trường Lâm Viên hoa dã quỳ khổng lồ.</p>\"}, {\"title\": \"Ngày 2: Săn Mây Cầu Đất – Đồi Chè Xanh – Trải nghiệm máng trượt Datanla\", \"description\": \"<p>Thức dậy sớm đón biển mây tại Cầu Đất, thử thách cảm giác mạnh cùng hệ thống máng trượt dài nhất Đông Nam Á tại thác Datanla.</p>\"}, {\"title\": \"Ngày 3: Chinh phục đỉnh Langbiang huyền thoại – Chợ Đà Lạt – Về lại TP.HCM\", \"description\": \"<p>Đi xe jeep lên đỉnh radar ngắm toàn cảnh hồ Đan Kia, mua sắm atiso, dâu tây tươi và khởi hành về lại TP.HCM.</p>\"}]', 'da-lat-mong-mo-san-may-langbiang-3n2d', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-18 21:28:08', '2026-11-20 16:18:53', 0, NULL, NULL),
(10, 'DUT00010', 'Miền Tây Sông Nước: Mỹ Tho – Bến Tre – Chợ Nổi Cái Răng Cần Thơ (2N1Đ)', 3, 3, 'active', 'https://images.unsplash.com/photo-1596422846543-75c6fc197f07?auto=format&fit=crop&w=800&q=80', 'https://images.unsplash.com/photo-1596422846543-75c6fc197f07?auto=format&fit=crop&w=800&q=80', 2500000, 1750000, 750000, 1890000, 1300000, 600000, 25, 12, 8, '10,26', '2 ngày 1 đêm', 'Xe du lịch đời mới, thuyền ba lá', '2026-11-29', '<p>Trải nghiệm nét sinh hoạt mộc mạc đậm tình đất phương Nam: chèo thuyền ba lá dưới rặng dừa nước, thưởng thức kẹo dừa Bến Tre nóng hổi, nghe đờn ca tài tử và ghé thăm chợ nổi Cái Răng nhộn nhịp.</p>', '[{\"title\": \"Ngày 1: Sài Gòn – Chùa Vĩnh Tràng – Bến Tre Miệt Vườn – Cần Thơ Tây Đô\", \"description\": \"<p>Khởi hành từ Sài Gòn đi Tiền Giang, thăm cồn Lân, cồn Thới Sơn, ăn trái cây miệt vườn và đến Cần Thơ dạo bến Ninh Kiều về đêm.</p>\"}, {\"title\": \"Ngày 2: Chợ Nổi Cái Răng – Lò Hủ Tiếu Truyền Thống – Về lại TP.HCM\", \"description\": \"<p>Đi thuyền từ sớm khám phá chợ nổi Cái Răng, thưởng thức tô hủ tiếu nóng hổi ngay trên ghe và trở về Sài Gòn vào chiều muộn.</p>\"}]', 'mien-tay-song-nuoc-my-tho-can-tho-2n1d', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-18 21:28:08', '2026-11-20 16:18:53', 0, NULL, NULL),
(11, 'DUT00011', 'Hành Trình Xuyên Việt: Hà Nội – Hạ Long – Đà Nẵng – Sài Gòn (9N8Đ)', 5, 1, 'active', 'https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?auto=format&fit=crop&w=800&q=80', 'https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?auto=format&fit=crop&w=800&q=80', 15500000, 10850000, 4650000, 11890000, 8300000, 3500000, 15, 6, 4, '9,1,11,10', '9 ngày 8 đêm', 'Máy bay khứ hồi, xe du lịch hạng sang', '2026-12-10', '<p>Hành trình trọn vẹn dải đất hình chữ S từ Thủ đô ngàn năm văn hiến đến kỳ quan Vịnh Hạ Long, di sản miền Trung và trung tâm kinh tế năng động bậc nhất phương Nam.</p>', '[{\"title\": \"Chặng 1 (Ngày 1-3): Hà Nội – Vịnh Hạ Long – Chùa Một Cột – Hồ Hoàn Kiếm\", \"description\": \"<p>Khám phá thủ đô Hà Nội cổ kính, ngủ đêm du thuyền ngắm hoàng hôn vịnh Hạ Long tráng lệ.</p>\"}, {\"title\": \"Chặng 2 (Ngày 4-6): Bay vào Đà Nẵng – Cầu Vàng Bà Nà – Phố Cổ Hội An\", \"description\": \"<p>Bay từ Nội Bài vào Đà Nẵng, tận hưởng không khí nghỉ dưỡng biển Mỹ Khê và phố cổ lung linh.</p>\"}, {\"title\": \"Chặng 3 (Ngày 7-9): Sài Gòn Hoa Lệ – Dinh Độc Lập – Địa Đạo Củ Chi – Tiễn đoàn\", \"description\": \"<p>Bay vào TP.HCM, tham quan các công trình biểu tượng lịch sử và thưởng thức ẩm thực Sài Gòn.</p>\"}]', 'hanh-trinh-xuyen-viet-bac-trung-nam-9n8d', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-18 21:28:08', '2026-11-20 16:18:53', 0, NULL, NULL),
(12, 'DUT00012', 'Thái Lan: Bangkok – Thành Phố Biển Pattaya – Đảo San Hô Coral (5N4Đ)', 7, 1, 'active', 'https://images.unsplash.com/photo-1508009603885-50cf7c579365?auto=format&fit=crop&w=800&q=80', 'https://images.unsplash.com/photo-1508009603885-50cf7c579365?auto=format&fit=crop&w=800&q=80', 8200000, 6560000, 2460000, 6490000, 5190000, 2000000, 35, 15, 10, '9,10', '5 ngày 4 đêm', 'Hàng không khứ hồi, xe du lịch VIP máy lạnh', '2026-11-25', '<p>Khám phá xứ sở Chùa Vàng với cung điện Hoàng Gia lộng lẫy, dạo thuyền trên sông Chaophraya huyền bí, đảo san hô Coral trong vắt và thưởng thức show diễn Alcazar Cabaret hoành tráng.</p>', '[{\"title\": \"Ngày 1: Hà Nội / TP.HCM – Bangkok – Thành Phố Biển Pattaya\", \"description\": \"<p>Đáp chuyến bay xuống sân bay Suvarnabhumi, xe đưa đoàn đến Pattaya tham quan chợ nổi 4 miền và thưởng thức ẩm thực Thái.</p>\"}, {\"title\": \"Ngày 2: Đảo San Hô Coral – Trân Bảo Phật Sơn – Alcazar Cabaret Show\", \"description\": \"<p>Cano đưa đoàn ra đảo Coral lặn biển, viếng tượng Phật Thích Ca dát vàng 999 khắc nổi trên vách núi và xem show nghệ thuật đặc sắc.</p>\"}, {\"title\": \"Ngày 3: Pattaya – Muang Boran (Thành Phố Cổ Đại) – Bangkok\", \"description\": \"<p>Khởi hành về lại Bangkok, tham quan quần thể bảo tàng ngoài trời lớn nhất thế giới Muang Boran.</p>\"}, {\"title\": \"Ngày 4: Chùa Thuyền Wat Yannawa – Dạo thuyền Sông Chaophraya – Mua sắm IconSiam\", \"description\": \"<p>Đi du thuyền ngắm cá nổi trên sông Chaophraya, viếng chùa Phật Vàng linh thiêng và tự do mua sắm tại trung tâm IconSiam.</p>\"}, {\"title\": \"Ngày 5: Viếng Phật Bốn Mặt – Sân bay Suvarnabhumi – Về Việt Nam\", \"description\": \"<p>Chiêm bái đền Erawan linh thiêng cầu may mắn, HDV hỗ trợ thủ tục xuất cảnh về lại Việt Nam.</p>\"}]', 'thai-lan-bangkok-pattaya-dao-coral-5n4d', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-18 21:28:08', '2026-11-20 16:18:53', 0, NULL, NULL),
(13, 'DUT00013', 'Hàn Quốc Mùa Thu Lãng Mạn: Seoul – Đảo Nami – Lotte World (5N4Đ)', 7, 2, 'active', 'https://images.unsplash.com/photo-1538485399081-7191377e8241?auto=format&fit=crop&w=800&q=80', 'https://images.unsplash.com/photo-1538485399081-7191377e8241?auto=format&fit=crop&w=800&q=80', 18500000, 14800000, 5550000, 14990000, 11990000, 4500000, 25, 10, 6, '9,10', '5 ngày 4 đêm', 'Hàng không quốc gia Vietnam Airlines, xe du lịch đời mới', '2026-12-02', '<p>Đắm chìm vào khung cảnh lãng mạn như phim truyền hình trên đảo Nami rợp bóng lá ngân hạnh vàng rực, check-in Cung điện Gyeongbokgung cổ kính trong trang phục Hanbok truyền thống.</p>', '[{\"title\": \"Ngày 1: Việt Nam – Bay thẳng Seoul – Check-in Khách sạn\", \"description\": \"<p>Đoàn tập trung tại sân bay làm thủ tục bay đi Incheon (Hàn Quốc), thưởng thức bữa sáng nhẹ và nhận phòng nghỉ ngơi.</p>\"}, {\"title\": \"Ngày 2: Đảo Nami – Tháp Namsan Tình Yêu – Suối Cheonggyecheon\", \"description\": \"<p>Ghé thăm đảo Nami nổi tiếng với bộ phim \'Bản tình ca mùa đông\', ngắm ổ khóa tình yêu trên tháp Namsan.</p>\"}, {\"title\": \"Ngày 3: Cung Điện Gyeongbokgung – Nhà Xanh Tổng Thống – Công Viên Lotte World\", \"description\": \"<p>Mặc trang phục Hanbok chụp ảnh tại cung điện hoàng gia Gyeongbokgung, oanh tạc công viên giải trí trong nhà Lotte World.</p>\"}, {\"title\": \"Ngày 4: Làng Cổ Bukchon Hanok – Mua sắm Mỹ phẩm & Nhân sâm – Chợ Myeongdong\", \"description\": \"<p>Khám phá làng cổ trăm năm tuổi Bukchon Hanok, thỏa sức mua sắm tại thiên đường ẩm thực đường phố Myeongdong.</p>\"}, {\"title\": \"Ngày 5: Seoul – Sân bay Incheon – Về lại Việt Nam\", \"description\": \"<p>Tự do mua sắm đặc sản bánh kẹo Hàn Quốc tại siêu thị miễn thuế và bay về Việt Nam an toàn.</p>\"}]', 'han-quoc-seoul-dao-nami-lotte-world-5n4d', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-18 21:28:08', '2026-11-20 16:18:53', 0, NULL, NULL),
(14, 'DUT00014', 'Nhật Bản Cung Đường Vàng: Tokyo – Núi Phú Sĩ – Cố Đô Kyoto – Osaka (6N5Đ)', 7, 3, 'active', 'https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?auto=format&fit=crop&w=800&q=80', 'https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?auto=format&fit=crop&w=800&q=80', 34000000, 27200000, 10200000, 28990000, 23000000, 8000000, 20, 8, 4, '9,10', '6 ngày 5 đêm', 'Hàng không 5 sao ANA, Tàu Shinkansen', '2026-12-15', '<p>Hành trình khám phá trọn vẹn xứ sở mặt trời mọc: ngắm núi Phú Sĩ tuyết phủ quanh năm, trải nghiệm tắm suối nước nóng Onsen truyền thống, dạo bước qua ngàn cổng Torii đỏ thắm tại Fushimi Inari Kyoto.</p>', '[{\"title\": \"Ngày 1: Việt Nam – Tokyo – Chùa Cổ Asakusa Kannon – Tháp Tokyo Skytree\", \"description\": \"<p>Bay đến Tokyo, viếng ngôi chùa cổ nhất Asakusa Kannon và ngắm toàn cảnh thủ đô hiện đại từ tháp truyền hình Skytree.</p>\"}, {\"title\": \"Ngày 2: Núi Phú Sĩ Trạm 5 – Làng Cổ Oshino Hakkai – Tắm Onsen truyền thống\", \"description\": \"<p>Chinh phục trạm số 5 núi Phú Sĩ thiêng liêng, thưởng thức bữa tối kaiseki truyền thống và tắm khoáng Onsen.</p>\"}, {\"title\": \"Ngày 3: Trải nghiệm tàu siêu tốc Shinkansen – Cố đô Kyoto – Rừng Trúc Arashiyama\", \"description\": \"<p>Trải nghiệm tàu Shinkansen đạt vận tốc 300km/h, tản bộ qua rừng trúc xanh mướt Arashiyama và chùa Vàng Kinkaku-ji.</p>\"}, {\"title\": \"Ngày 4: Đền Ngàn Cổng Fushimi Inari – Lâu Đài Osaka – Mua sắm Shinsaibashi\", \"description\": \"<p>Check-in đường hầm cổng Torii đỏ rực rỡ, thăm lâu đài cổ kính Osaka và thiên đường shopping Shinsaibashi.</p>\"}, {\"title\": \"Ngày 5: Osaka – Tự do khám phá ẩm thực đường phố Dotonbori\", \"description\": \"<p>Thưởng thức đặc sản takoyaki, okonomiyaki thơm lừng và bò Wagyu trứ danh.</p>\"}, {\"title\": \"Ngày 6: Sân bay Quốc tế Kansai – Bay về Việt Nam\", \"description\": \"<p>HDV hỗ trợ hoàn thuế mua sắm và làm thủ tục đáp chuyến bay về lại Việt Nam.</p>\"}]', 'nhat-ban-tokyo-nui-phu-si-kyoto-osaka-6n5d', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-18 21:28:08', '2026-11-20 16:18:53', 0, NULL, NULL),
(15, 'DUT00015', 'Singapore – Malaysia: 1 Hành Trình 2 Quốc Gia Hiện Đại (5N4Đ)', 7, 4, 'active', 'https://images.unsplash.com/photo-1525625293386-3f8f99389edd?auto=format&fit=crop&w=800&q=80', 'https://images.unsplash.com/photo-1525625293386-3f8f99389edd?auto=format&fit=crop&w=800&q=80', 12500000, 10000000, 3750000, 9890000, 7890000, 3000000, 30, 15, 8, '9,10', '5 ngày 4 đêm', 'Hàng không quốc tế Vietnam Airlines, xe du lịch đời mới', '2026-12-08', '<p>Hành trình liên tuyến kết hợp hai quốc gia Đông Nam Á hiện đại và đa văn hóa: Gardens by the Bay siêu cây năng lượng, tháp đôi Petronas Kuala Lumpur và cao nguyên Genting quanh năm sương mờ.</p>', '[{\"title\": \"Ngày 1: Việt Nam – Singapore – Công viên Sư Tử Biển Merlion – Marina Bay\", \"description\": \"<p>Bay đến sân bay Changi đạt chuẩn 5 sao thế giới, chụp ảnh cùng biểu tượng Sư tử biển Merlion và xem show nhạc nước Spectra Light.</p>\"}, {\"title\": \"Ngày 2: Kỳ quan Gardens by the Bay – Đảo Sentosa – Cửa khẩu sang Malacca\", \"description\": \"<p>Chiêm ngưỡng thảm thực vật kỳ vĩ tại Flower Dome & Cloud Forest, sau đó di chuyển qua cửa khẩu sang thành phố cổ Malacca (Malaysia).</p>\"}, {\"title\": \"Ngày 3: Malacca – Thủ đô Kuala Lumpur – Quảng Trường Độc Lập – Tháp Đôi Petronas\", \"description\": \"<p>Khám phá di sản kiến trúc Bồ Đào Nha và Hà Lan tại Malacca, chiêm ngưỡng tháp đôi Petronas rực rỡ khi về đêm.</p>\"}, {\"title\": \"Ngày 4: Động Batu Huyền Bí – Cao nguyên giải trí Genting Highlands\", \"description\": \"<p>Chinh phục 272 bậc thang rực rỡ sắc màu tại động Hindu Batu, đi cáp treo lên cao nguyên Genting tận hưởng thời tiết ôn đới.</p>\"}, {\"title\": \"Ngày 5: Kuala Lumpur – Mua sắm Socola Beryls – Sân bay KLIA – Về Việt Nam\", \"description\": \"<p>Mua sắm đặc sản socola sầu riêng Beryls hảo hạng và bay về điểm đón ban đầu tại Việt Nam.</p>\"}]', 'singapore-malaysia-2-quoc-gia-5n4d', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-18 21:28:08', '2026-11-20 16:18:53', 0, NULL, NULL),
(16, 'DUT00016', 'Châu Âu Tuyệt Mỹ: Pháp – Thụy Sĩ – Ý – Thành Vatican (10N9Đ)', 8, 1, 'active', 'https://images.unsplash.com/photo-1502602898657-3e91760cbb34?auto=format&fit=crop&w=800&q=80', 'https://images.unsplash.com/photo-1502602898657-3e91760cbb34?auto=format&fit=crop&w=800&q=80', 89000000, 71200000, 26700000, 79900000, 63900000, 25000000, 14, 5, 2, '9,10', '10 ngày 9 đêm', 'Hàng không 5 sao Qatar Airways, Xe du lịch châu Âu sang trọng', '2026-12-20', '<p>Hành trình mơ ước của mọi tín đồ du lịch: ngắm tháp Eiffel hoa lệ bên dòng sông Seine, ngắm đỉnh núi tuyết Titlis hùng vĩ tại Thụy Sĩ, đi thuyền gondola lãng mạn tại Venice và chạm tay vào Đấu trường La Mã Colosseum cổ kính.</p>', '[{\"title\": \"Ngày 1-3: Paris Kinh Đô Ánh Sáng – Tháp Eiffel – Bảo Tàng Louvre – Du thuyền Sông Seine\", \"description\": \"<p>Đáp chuyến bay đến Paris, tham quan bảo tàng Louvre chiêm ngưỡng nàng Mona Lisa, đi thuyền sông Seine thơ mộng.</p>\"}, {\"title\": \"Ngày 4-5: Thụy Sĩ Thiên Đường – Thị Trấn Engelberg – Đỉnh Núi Tuyết Titlis\", \"description\": \"<p>Đi cáp treo xoay 360 độ lên đỉnh Titlis tuyết trắng vĩnh cửu, tản bộ qua cây cầu gỗ cổ Chapelle nổi tiếng tại Lucerne.</p>\"}, {\"title\": \"Ngày 6-7: Venice Thành Phố Kênh Đào – Tháp Nghiêng Pisa Kỳ Quan\", \"description\": \"<p>Ngồi thuyền Gondola len lỏi qua các con kênh tại Venice, chụp ảnh cùng tháp nghiêng Pisa độc nhất vô nhị thế giới.</p>\"}, {\"title\": \"Ngày 8-10: Rome Thành Phố Vĩnh Hằng – Đấu Trường Colosseum – Vatican – Về Việt Nam\", \"description\": \"<p>Khám phá đấu trường Colosseum, đài phun nước Trevi và thánh đường thánh Peter tại Vatican trước khi bay về nước.</p>\"}]', 'chau-au-phap-thuy-si-y-vatican-10n9d', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-18 21:28:08', '2026-11-20 20:42:00', 0, NULL, NULL),
(17, 'DUT00017', 'Bờ Tây Nước Mỹ: Los Angeles – Las Vegas – San Francisco (8N7Đ)', 9, 1, 'active', 'https://images.unsplash.com/photo-1501594907352-04cda38ebc29?auto=format&fit=crop&w=800&q=80', 'https://images.unsplash.com/photo-1501594907352-04cda38ebc29?auto=format&fit=crop&w=800&q=80', 98000000, 78400000, 29400000, 88900000, 71000000, 28000000, 15, 6, 2, '9,10', '8 ngày 7 đêm', 'Hàng không Eva Air, xe du lịch chuẩn Hoa Kỳ', '2026-12-22', '<p>Khám phá giấc mơ Mỹ với kinh đô điện ảnh Hollywood tại Los Angeles, thành phố không ngủ Las Vegas với các sòng bài casino sa hoa và ngắm cầu Cổng Vàng sừng sững tại San Francisco.</p>', '[{\"title\": \"Ngày 1-2: Việt Nam – Los Angeles – Đại Lộ Danh Vọng Hollywood – Phim Trường Universal\", \"description\": \"<p>Bay qua bờ Tây nước Mỹ, dạo bước trên Đại lộ danh vọng Hollywood Walk of Fame và vui chơi tại Universal Studios.</p>\"}, {\"title\": \"Ngày 3-4: Xuyên sa mạc Nevada – Thủ phủ giải trí Las Vegas – Thủy điện Hoover Dam\", \"description\": \"<p>Di chuyển đến Las Vegas, ngắm show nhạc nước khách sạn Bellagio và tham quan kỳ quan thủy điện Hoover Dam đồ sộ.</p>\"}, {\"title\": \"Ngày 5-6: Bay tới San Francisco – Cầu Cổng Vàng Golden Gate – Bến Cảng Fisherman\'s Wharf\", \"description\": \"<p>Ngắm cây cầu Cổng Vàng biểu tượng của nước Mỹ, đi xe điện cáp treo cổ và thưởng thức súp cua biển tại bến tàu Pier 39.</p>\"}, {\"title\": \"Ngày 7-8: Mua sắm trung tâm Outlet – Sân bay quốc tế SFO – Về lại Việt Nam\", \"description\": \"<p>Thỏa sức săn hàng hiệu giảm giá tại Premium Outlets và đáp chuyến bay về lại Việt Nam.</p>\"}]', 'bo-tay-hoa-ky-los-angeles-las-vegas-san-francisco-8n7d', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-18 21:28:08', '2026-11-20 16:18:53', 0, NULL, NULL),
(18, 'DUT00018', 'Xứ Sở Chuột Túi: Sydney – Blue Mountains – Thủ Đô Văn Hóa Melbourne (7N6Đ)', 10, 1, 'active', 'https://images.unsplash.com/photo-1506973035872-a4ec16b8e8d9?auto=format&fit=crop&w=800&q=80', 'https://images.unsplash.com/photo-1506973035872-a4ec16b8e8d9?auto=format&fit=crop&w=800&q=80', 62000000, 49600000, 18600000, 54900000, 43900000, 18000000, 16, 6, 3, '9,10', '7 ngày 6 đêm', 'Hàng không quốc gia Vietnam Airlines bay thẳng', '2026-12-18', '<p>Khám phá nước Úc xinh đẹp với nhà hát Con Sò Sydney Opera House lừng danh thế giới, dãy núi xanh huyền thoại Blue Mountains, vườn thú hoang dã ôm gấu Koala và cho chuột túi Kangaroo ăn.</p>', '[{\"title\": \"Ngày 1-2: Việt Nam – Sydney – Nhà Hát Con Sò Opera House – Cầu Cảng Sydney\", \"description\": \"<p>Bay thẳng đến Sydney, chụp ảnh bên nhà hát Opera House và du thuyền ngắm hoàng hôn vịnh cảng Sydney tuyệt đẹp.</p>\"}, {\"title\": \"Ngày 3: Dãy Núi Xanh Blue Mountains – Vườn thú hoang dã Koala Park\", \"description\": \"<p>Chiêm ngưỡng mỏm đá Ba Chị Em huyền thoại, đi tàu kéo ngắm vực sâu và chụp ảnh cùng những chú gấu Koala dễ thương.</p>\"}, {\"title\": \"Ngày 4-5: Bay sang Melbourne – Quảng Trường Federation – Phố Nghệ Thuật Graffiti Hosier\", \"description\": \"<p>Bay đến Melbourne, tản bộ qua con đường tranh tường Hosier Lane và ngắm ga xe lửa cổ kính Flinders Street.</p>\"}, {\"title\": \"Ngày 6-7: Thung lũng rượu vang Yarra Valley – Sân bay Melbourne – Về Việt Nam\", \"description\": \"<p>Thưởng thức rượu vang hảo hạng tại thung lũng Yarra và đáp chuyến bay thẳng về lại Việt Nam.</p>\"}]', 'xu-so-chuot-tui-sydney-melbourne-7n6d', 'Phan Thanh Duy', 'Phan Thanh Duy', '2026-11-18 21:28:08', '2026-11-20 16:18:53', 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT '/admin/assets/images/AvatarIcon2.jpg',
  `status` varchar(50) DEFAULT 'active',
  `token_user` varchar(255) DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT 'Khách hàng',
  `updated_by` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT current_timestamp(),
  `updated_at` datetime DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted` tinyint(1) DEFAULT 0,
  `deleted_by` varchar(255) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `full_name`, `email`, `password`, `phone`, `address`, `avatar`, `status`, `token_user`, `slug`, `created_by`, `updated_by`, `created_at`, `updated_at`, `deleted`, `deleted_by`, `deleted_at`) VALUES
(1, 'Lê Văn A', 'levana@gmail.com', '/BSYl5XFk9ubLi5YubPxa5LYIGY5FF0RD1zgf1V1bq', '01234567890', 'Số 123, đường ABC,...', '/admin/assets/images/AvatarIcon2.jpg', 'active', 'tok_user_001_levana', 'le-van-a', 'Khách hàng', 'Test Admin', '2026-10-15 08:30:00', '2026-11-19 08:38:27', 1, 'Phan Thanh Duy', '2026-11-19 08:38:27'),
(2, 'Lê Văn B', 'levanb@gmail.com', '/BSYl5XFk9ubLi5YubPxa5LYIGY5FF0RD1zgf1V1bq', '01234567891', 'Số 124, đường ABC,...', '/admin/assets/images/AvatarIcon2.jpg', 'inactive', 'tok_user_002_levanb', 'le-van-b', 'Khách hàng', NULL, '2026-10-16 09:15:00', '2026-11-17 15:08:24', 0, NULL, NULL),
(3, 'Trần Thị Mai', 'maitt@gmail.com', '/BSYl5XFk9ubLi5YubPxa5LYIGY5FF0RD1zgf1V1bq', '0912345678', 'Số 45 Lê Duẩn, Đà Nẵng', '/admin/assets/images/AvatarIcon2.jpg', 'active', 'tok_user_003_maitt', 'tran-thi-mai', 'Khách hàng', NULL, '2026-10-18 10:20:00', '2026-11-17 15:08:24', 0, NULL, NULL),
(4, 'Nguyễn Văn Cường', 'cuongnv@gmail.com', '/BSYl5XFk9ubLi5YubPxa5LYIGY5FF0RD1zgf1V1bq', '0988776655', 'Số 12 Nguyễn Huệ, Quận 1, TP.HCM', '/admin/assets/images/AvatarIcon2.jpg', 'active', 'tok_user_004_cuongnv', 'nguyen-van-cuong', 'Khách hàng', NULL, '2026-10-20 14:05:00', '2026-11-17 15:08:24', 0, NULL, NULL),
(5, 'Phạm Quỳnh Anh', 'anhpq@gmail.com', '/BSYl5XFk9ubLi5YubPxa5LYIGY5FF0RD1zgf1V1bq', '0905123456', 'Số 78 Trần Phú, Hà Nội', '/admin/assets/images/AvatarIcon2.jpg', 'inactive', 'tok_user_005_anhpq', 'pham-quynh-anh', 'Khách hàng', NULL, '2026-10-22 16:40:00', '2026-11-17 15:08:24', 0, NULL, NULL),
(6, 'Hoàng Minh Tuấn', 'tuanhm@gmail.com', '/BSYl5XFk9ubLi5YubPxa5LYIGY5FF0RD1zgf1V1bq', '0933221100', 'Số 200 Phan Châu Trinh, Đà Nẵng', '/admin/assets/images/AvatarIcon2.jpg', 'active', 'tok_user_006_tuanhm', 'hoang-minh-tuan', 'Khách hàng', NULL, '2026-10-25 11:10:00', '2026-11-17 15:08:24', 0, NULL, NULL),
(7, 'Đỗ Mỹ Linh', 'linhdm@gmail.com', '/BSYl5XFk9ubLi5YubPxa5LYIGY5FF0RD1zgf1V1bq', '0977889900', 'Số 89 Nguyễn Tri Phương, Đà Nẵng', '/admin/assets/images/AvatarIcon2.jpg', 'active', 'tok_user_007_linhdm', 'do-my-linh', 'Khách hàng', NULL, '2026-10-27 15:30:00', '2026-11-17 15:08:24', 0, NULL, NULL),
(8, 'Vũ Đức Thịnh', 'thinhvd@gmail.com', '/BSYl5XFk9ubLi5YubPxa5LYIGY5FF0RD1zgf1V1bq', '0911223344', 'Số 56 Cầu Giấy, Hà Nội', '/admin/assets/images/AvatarIcon2.jpg', 'active', 'tok_user_008_thinhvd', 'vu-duc-thinh', 'Khách hàng', NULL, '2026-10-29 09:45:00', '2026-11-17 15:08:24', 0, NULL, NULL),
(9, 'Đặng Thu Thảo', 'thaodt@gmail.com', '/BSYl5XFk9ubLi5YubPxa5LYIGY5FF0RD1zgf1V1bq', '0944556677', 'Số 15 Võ Văn Tần, Quận 3, TP.HCM', '/admin/assets/images/AvatarIcon2.jpg', 'active', 'tok_user_009_thaodt', 'dang-thu-thao', 'Khách hàng', NULL, '2026-10-30 17:00:00', '2026-11-17 15:08:24', 0, NULL, NULL),
(10, 'Bùi Quốc Anh', 'anhbq@gmail.com', '/BSYl5XFk9ubLi5YubPxa5LYIGY5FF0RD1zgf1V1bq', '0966778899', 'Số 99 Hùng Vương, Nha Trang', '/admin/assets/images/AvatarIcon2.jpg', 'inactive', 'tok_user_010_anhbq', 'bui-quoc-anh', 'Khách hàng', NULL, '2026-11-02 13:25:00', '2026-11-17 15:08:24', 0, NULL, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts_admin`
--
ALTER TABLE `accounts_admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `slug` (`slug`);

--
-- Indexes for table `cities`
--
ALTER TABLE `cities`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `contacts`
--
ALTER TABLE `contacts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `coupons`
--
ALTER TABLE `coupons`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code` (`code`);

--
-- Indexes for table `forgot_password`
--
ALTER TABLE `forgot_password`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `news`
--
ALTER TABLE `news`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `slug` (`slug`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_order` (`order_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `setting_website_info`
--
ALTER TABLE `setting_website_info`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tours`
--
ALTER TABLE `tours`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `slug` (`slug`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts_admin`
--
ALTER TABLE `accounts_admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `cities`
--
ALTER TABLE `cities`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `contacts`
--
ALTER TABLE `contacts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `coupons`
--
ALTER TABLE `coupons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `forgot_password`
--
ALTER TABLE `forgot_password`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `news`
--
ALTER TABLE `news`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `setting_website_info`
--
ALTER TABLE `setting_website_info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `tours`
--
ALTER TABLE `tours`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `fk_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
