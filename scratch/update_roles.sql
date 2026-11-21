USE tour_management;
UPDATE roles SET permissions = REPLACE(permissions, '"order-trash"', '"order-trash", "coupon-view", "coupon-create", "coupon-edit", "coupon-delete", "coupon-trash"') WHERE id = 1 AND permissions NOT LIKE '%coupon-view%';
UPDATE roles SET permissions = REPLACE(permissions, '"order-trash"', '"order-trash", "coupon-view", "coupon-create", "coupon-edit", "coupon-delete", "coupon-trash"') WHERE id = 4 AND permissions NOT LIKE '%coupon-view%';
SELECT id, name, permissions FROM roles WHERE id IN (1, 4);
