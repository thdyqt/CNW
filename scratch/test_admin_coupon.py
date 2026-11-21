import urllib.request
import urllib.parse
import json
import http.cookiejar

BASE_URL = "http://localhost:8080/project"
TOKEN = "tok_adm_711659258d1b4648a261a43341d64bca"

headers = {
    "Cookie": f"token={TOKEN}"
}

def run_tests():
    print("=== TEST ADMIN COUPON CRUD ===")
    
    # 1. GET /admin/coupon/list
    req = urllib.request.Request(f"{BASE_URL}/admin/coupon/list", headers=headers)
    with urllib.request.urlopen(req) as resp:
        html = resp.read().decode("utf-8")
        assert "Quản lý mã giảm giá" in html
        assert "DUT2026" in html
        print("1. GET /admin/coupon/list: OK")

    # 2. GET /admin/coupon/create
    req = urllib.request.Request(f"{BASE_URL}/admin/coupon/create", headers=headers)
    with urllib.request.urlopen(req) as resp:
        html = resp.read().decode("utf-8")
        assert "Tạo mã giảm giá mới" in html
        print("2. GET /admin/coupon/create: OK")

    # 3. POST /admin/coupon/create
    post_data = urllib.parse.urlencode({
        "code": "TESTCOUPON20",
        "title": "Mã thử nghiệm giảm 20%",
        "type": "percentage",
        "value": "20",
        "minOrderValue": "1000000",
        "maxDiscountValue": "500000",
        "usageLimit": "50",
        "startDate": "2026-01-01",
        "endDate": "2026-12-31",
        "status": "active"
    }).encode("utf-8")
    req = urllib.request.Request(f"{BASE_URL}/admin/coupon/create", data=post_data, headers=headers)
    with urllib.request.urlopen(req) as resp:
        assert resp.status in (200, 302)
        print("3. POST /admin/coupon/create: OK")

    # Verify created
    req = urllib.request.Request(f"{BASE_URL}/admin/coupon/list?keyword=TESTCOUPON20", headers=headers)
    with urllib.request.urlopen(req) as resp:
        html = resp.read().decode("utf-8")
        assert "TESTCOUPON20" in html
        print("4. Verify created coupon in list: OK")

    # Get coupon ID from list HTML
    import re
    match = re.search(r'edit/(\d+)', html)
    assert match is not None
    coupon_id = match.group(1)
    print(f"Created coupon ID: {coupon_id}")

    # 5. GET /admin/coupon/edit/{id}
    req = urllib.request.Request(f"{BASE_URL}/admin/coupon/edit/{coupon_id}", headers=headers)
    with urllib.request.urlopen(req) as resp:
        html = resp.read().decode("utf-8")
        assert "TESTCOUPON20" in html
        print("5. GET /admin/coupon/edit: OK")

    # 6. POST /admin/coupon/edit/{id}
    update_data = urllib.parse.urlencode({
        "id": coupon_id,
        "code": "TESTCOUPON25",
        "title": "Mã thử nghiệm cập nhật 25%",
        "type": "percentage",
        "value": "25",
        "minOrderValue": "1500000",
        "maxDiscountValue": "800000",
        "usageLimit": "60",
        "startDate": "2026-02-01",
        "endDate": "2026-12-31",
        "status": "active"
    }).encode("utf-8")
    req = urllib.request.Request(f"{BASE_URL}/admin/coupon/edit/{coupon_id}", data=update_data, headers=headers)
    with urllib.request.urlopen(req) as resp:
        assert resp.status in (200, 302)
        print("6. POST /admin/coupon/edit: OK")

    # 7. PATCH /admin/coupon/change-status/inactive/{id}
    req = urllib.request.Request(f"{BASE_URL}/admin/coupon/change-status/inactive/{coupon_id}", method="PATCH", headers=headers)
    with urllib.request.urlopen(req) as resp:
        res = json.loads(resp.read().decode("utf-8"))
        assert res["code"] == "success"
        print("7. PATCH change-status to inactive: OK")

    # 8. PATCH /admin/coupon/delete/{id}
    req = urllib.request.Request(f"{BASE_URL}/admin/coupon/delete/{coupon_id}", method="PATCH", headers=headers)
    with urllib.request.urlopen(req) as resp:
        res = json.loads(resp.read().decode("utf-8"))
        assert res["code"] == "success"
        print("8. PATCH delete to trash: OK")

    # 9. GET /admin/coupon/trash
    req = urllib.request.Request(f"{BASE_URL}/admin/coupon/trash", headers=headers)
    with urllib.request.urlopen(req) as resp:
        html = resp.read().decode("utf-8")
        assert "TESTCOUPON25" in html
        print("9. GET /admin/coupon/trash: OK")

    # 10. PATCH /admin/coupon/undo/{id}
    req = urllib.request.Request(f"{BASE_URL}/admin/coupon/undo/{coupon_id}", method="PATCH", headers=headers)
    with urllib.request.urlopen(req) as resp:
        res = json.loads(resp.read().decode("utf-8"))
        assert res["code"] == "success"
        print("10. PATCH undo restore: OK")

    # 11. DELETE /admin/coupon/destroy/{id}
    req = urllib.request.Request(f"{BASE_URL}/admin/coupon/destroy/{coupon_id}", method="DELETE", headers=headers)
    with urllib.request.urlopen(req) as resp:
        res = json.loads(resp.read().decode("utf-8"))
        assert res["code"] == "success"
        print("11. DELETE destroy cleanup: OK")

    print("\n>>> ALL ADMIN COUPON TESTS PASSED!")

if __name__ == "__main__":
    run_tests()
