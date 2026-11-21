CREATE TABLE IF NOT EXISTS `customer_registration_otp` (
  `email` varchar(255) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `password_hash` varchar(255) NOT NULL,
  `otp_hash` varchar(255) NOT NULL,
  `attempts` int NOT NULL DEFAULT 0,
  `expires_at` datetime NOT NULL,
  `resend_after` datetime NOT NULL,
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`email`),
  KEY `idx_customer_registration_otp_expires_at` (`expires_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
