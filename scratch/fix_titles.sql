USE tour_management;
SET NAMES utf8mb4;
UPDATE coupons SET title = 'Mã Giảm Giá Khai Xuân 2026 (Giảm 10%)' WHERE id = 1;
UPDATE coupons SET title = 'Ưu Đãi Đặc Biệt Giảm 500.000đ' WHERE id = 2;
UPDATE coupons SET title = 'Tri Ân Khách Hàng VIP (Giảm 15%)' WHERE id = 3;
SELECT id, code, title FROM coupons;
