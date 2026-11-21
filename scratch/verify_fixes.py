import urllib.request
import json
import time

time.sleep(2)

print("=== 1. VERIFY CART PAGE TITLE ===")
try:
    with urllib.request.urlopen("http://localhost:8080/project/cart") as r:
        html = r.read().decode("utf-8")
        if "Giỏ hàng - DUT Travel" in html:
            print(">>> Cart Page Title PASSED: 'Giỏ hàng - DUT Travel' found!")
        else:
            print(">>> Cart Page Title FAILED! 'DUT Travel' not in title.")
        if "28Tech" in html:
            print(">>> WARNING: '28Tech' still found in cart HTML!")
        else:
            print(">>> '28Tech' completely eliminated from cart HTML!")
except Exception as e:
    print("Error fetching /cart:", e)

print("\n=== 2. VERIFY CART DETAIL API CODE FIELD ===")
try:
    req = urllib.request.Request(
        "http://localhost:8080/project/cart/detail",
        data=json.dumps([{"tourId": 1, "quantityAdult": 1, "checked": True}]).encode("utf-8"),
        headers={"Content-Type": "application/json"}
    )
    with urllib.request.urlopen(req) as r:
        data = json.loads(r.read().decode("utf-8"))
        print("Cart API response code:", data.get("code"))
        first_item = data.get("cart", [])[0] if data.get("cart") else {}
        print("First item tourId:", first_item.get("tourId"))
        print("First item tour code:", first_item.get("code"))
        if first_item.get("code") and "123456789" not in first_item.get("code"):
            print(">>> Cart API code field PASSED: Dynamic code present:", first_item.get("code"))
        else:
            print(">>> Cart API code field FAILED!")
except Exception as e:
    print("Error calling /cart/detail:", e)

print("\n=== 3. VERIFY SOLD OUT (HẾT CHỖ) UX ===")
# Find a tour with stock = 0 or temporarily set tour 2 to stock 0
import subprocess
try:
    cmd_stock0 = 'c:\\xampp\\mysql\\bin\\mysql.exe -u root -P 3307 -e "UPDATE tour_management.tours SET stock_adult=0, stock_children=0, stock_baby=0 WHERE id=2;"'
    subprocess.check_call(cmd_stock0, shell=True)

    # Check tour 2 detail page
    with urllib.request.urlopen("http://localhost:8080/project/tour/detail/combo-da-nang-2024-da-nang-hoi-an-ba-na-hill") as r:
        html2 = r.read().decode("utf-8")
        if "Tour này hiện đã hết chỗ" in html2 and "ĐÃ HẾT CHỖ" in html2:
            print(">>> Tour Detail Sold Out Warning & Disabled Button PASSED!")
        else:
            print(">>> Tour Detail Sold Out FAILED!")

    # Check tour 2 in homepage or category page
    with urllib.request.urlopen("http://localhost:8080/project/category/tour-trong-nuoc") as r:
        html_cat = r.read().decode("utf-8")
        if "inner-soldout-badge" in html_cat and "Hết chỗ" in html_cat:
            print(">>> Product Card Sold Out Badge PASSED!")
        else:
            print(">>> Product Card Sold Out Badge FAILED!")

    # Reset tour 2 stock back to 9, 8, 11
    cmd_reset = 'c:\\xampp\\mysql\\bin\\mysql.exe -u root -P 3307 -e "UPDATE tour_management.tours SET stock_adult=9, stock_children=8, stock_baby=11 WHERE id=2;"'
    subprocess.check_call(cmd_reset, shell=True)
    print(">>> Tour 2 stock reset back to normal (9, 8, 11).")

except Exception as e:
    print("Error in sold out test:", e)

print("\nALL VERIFICATIONS COMPLETE!")
