import urllib.request
import urllib.parse
import json
import http.cookiejar

BASE_URL = "http://localhost:8080/project"

def test_apply_coupon():
    print("=== 1. TEST /cart/apply-coupon API ===")
    
    # 1. Invalid coupon
    req = urllib.request.Request(
        f"{BASE_URL}/cart/apply-coupon",
        data=json.dumps({"couponCode": "NONEXISTENT", "subTotal": 5000000}).encode("utf-8"),
        headers={"Content-Type": "application/json"}
    )
    with urllib.request.urlopen(req) as resp:
        res = json.loads(resp.read().decode("utf-8"))
        print("Invalid Coupon Result:", res)
        assert res["code"] == "error"
        assert "không tồn tại" in res["message"]

    # 2. Min order value not met (DUT2026 requires min 2,000,000)
    req = urllib.request.Request(
        f"{BASE_URL}/cart/apply-coupon",
        data=json.dumps({"couponCode": "DUT2026", "subTotal": 1000000}).encode("utf-8"),
        headers={"Content-Type": "application/json"}
    )
    with urllib.request.urlopen(req) as resp:
        res = json.loads(resp.read().decode("utf-8"))
        print("Min order not met Result:", res)
        assert res["code"] == "error"
        assert "tối thiểu" in res["message"]

    # 3. Valid percentage coupon DUT2026 (10%, max discount 1,000,000)
    req = urllib.request.Request(
        f"{BASE_URL}/cart/apply-coupon",
        data=json.dumps({"couponCode": "DUT2026", "subTotal": 5000000}).encode("utf-8"),
        headers={"Content-Type": "application/json"}
    )
    with urllib.request.urlopen(req) as resp:
        res = json.loads(resp.read().decode("utf-8"))
        print("Valid DUT2026 Result:", res)
        assert res["code"] == "success"
        assert res["discount"] == 500000 # 10% of 5M
        assert res["total"] == 4500000

    # 4. Valid fixed coupon DUT500K (500k, min order 3,000,000)
    req = urllib.request.Request(
        f"{BASE_URL}/cart/apply-coupon",
        data=json.dumps({"couponCode": "DUT500K", "subTotal": 4000000}).encode("utf-8"),
        headers={"Content-Type": "application/json"}
    )
    with urllib.request.urlopen(req) as resp:
        res = json.loads(resp.read().decode("utf-8"))
        print("Valid DUT500K Result:", res)
        assert res["code"] == "success"
        assert res["discount"] == 500000
        assert res["total"] == 3500000

    print(">>> APPLY COUPON API TESTS PASSED!\n")

def test_order_creation_with_coupon():
    print("=== 2. TEST ORDER CREATION WITH COUPON ===")
    
    order_payload = {
        "fullName": "Nguyễn Văn Test Coupon",
        "phone": "0987654321",
        "note": "Test đơn hàng áp dụng mã giảm giá DUT2026",
        "paymentMethod": "money",
        "couponCode": "DUT2026",
        "items": [
            {
                "tourId": 1,
                "quantityAdult": 2,
                "quantityChildren": 0,
                "quantityBaby": 0,
                "locationFrom": 1
            }
        ]
    }
    
    req = urllib.request.Request(
        f"{BASE_URL}/order/create",
        data=json.dumps(order_payload).encode("utf-8"),
        headers={"Content-Type": "application/json"}
    )
    with urllib.request.urlopen(req) as resp:
        res = json.loads(resp.read().decode("utf-8"))
        print("Order creation result:", res)
        assert res["code"] == "success"
        order_code = res["orderCode"]
        print(f"Order created with code: {order_code}")
        
    # Check success page
    success_url = f"{BASE_URL}/order/success?orderCode={order_code}&phone=0987654321"
    with urllib.request.urlopen(success_url) as resp:
        html = resp.read().decode("utf-8")
        assert "DUT2026" in html
        assert "Mã giảm giá áp dụng" in html
        print(">>> Success page displays coupon code successfully!")

    print(">>> ORDER CREATION WITH COUPON PASSED!\n")

if __name__ == "__main__":
    test_apply_coupon()
    test_order_creation_with_coupon()
