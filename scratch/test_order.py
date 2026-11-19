import urllib.request
import json
import subprocess
import time
import concurrent.futures

BASE_URL = "http://localhost:8080/project/order/create"

def query_tour(tour_id):
    cmd = f'c:\\xampp\\mysql\\bin\\mysql.exe -u root -P 3307 -e "SELECT id, stock_adult, stock_children, stock_baby FROM tour_management.tours WHERE id={tour_id};"'
    res = subprocess.check_output(cmd, shell=True).decode('utf-8', errors='ignore')
    lines = [line.strip().split('\t') for line in res.strip().split('\n') if line.strip()]
    if len(lines) >= 2:
        return {
            'id': int(lines[1][0]),
            'adult': int(lines[1][1]),
            'children': int(lines[1][2]),
            'baby': int(lines[1][3])
        }
    return None

def update_tour_stock(tour_id, adult, children, baby):
    cmd = f'c:\\xampp\\mysql\\bin\\mysql.exe -u root -P 3307 -e "UPDATE tour_management.tours SET stock_adult={adult}, stock_children={children}, stock_baby={baby} WHERE id={tour_id};"'
    subprocess.check_call(cmd, shell=True)

def post_order(order_data):
    req = urllib.request.Request(
        BASE_URL,
        data=json.dumps(order_data).encode('utf-8'),
        headers={'Content-Type': 'application/json'}
    )
    try:
        with urllib.request.urlopen(req) as resp:
            body = resp.read().decode('utf-8')
            return json.loads(body)
    except Exception as e:
        return {'code': 'exception', 'message': str(e)}

print("=== TEST 1: NORMAL ORDER & STOCK DEDUCTION ===")
update_tour_stock(1, 10, 5, 2)
before = query_tour(1)
print(f"Stock before order: adult={before['adult']}, child={before['children']}, baby={before['baby']}")

order1 = {
    "fullName": "Nguyen Van A",
    "phone": "0912345678",
    "note": "Don hang test 1",
    "paymentMethod": "money",
    "items": [
        {
            "tourId": 1,
            "quantityAdult": 2,
            "quantityChildren": 1,
            "quantityBaby": 0,
            "locationFrom": 1
        }
    ]
}

resp1 = post_order(order1)
print("Order 1 response:", resp1)

after1 = query_tour(1)
print(f"Stock after order: adult={after1['adult']}, child={after1['children']}, baby={after1['baby']}")
assert resp1.get('code') == 'success', "Order 1 should succeed!"
assert after1['adult'] == before['adult'] - 2, "Adult stock should decrease by 2!"
assert after1['children'] == before['children'] - 1, "Child stock should decrease by 1!"
assert after1['baby'] == before['baby'], "Baby stock should not change!"
print(">>> TEST 1 PASSED: Stock decreased accurately!\n")


print("=== TEST 2: ORDER EXCEEDING AVAILABLE STOCK ===")
order2 = {
    "fullName": "Nguyen Van B",
    "phone": "0987654321",
    "note": "Don hang qua so luong",
    "paymentMethod": "money",
    "items": [
        {
            "tourId": 1,
            "quantityAdult": 100, # Only 8 left
            "quantityChildren": 0,
            "quantityBaby": 0,
            "locationFrom": 1
        }
    ]
}

resp2 = post_order(order2)
print("Order 2 response:", resp2)
after2 = query_tour(1)
print(f"Stock after failed order: adult={after2['adult']}, child={after2['children']}, baby={after2['baby']}")
assert resp2.get('code') == 'error', "Order 2 should fail with error!"
assert after2['adult'] == after1['adult'], "Stock must not change when order fails!"
print(">>> TEST 2 PASSED: Insufficient stock rejected properly and stock untouched!\n")


print("=== TEST 3: CONCURRENCY TEST (2 USERS BOOKING SIMULTANEOUSLY FOR 1 REMAINING SEAT) ===")
# Set stock to exactly 1 adult seat
update_tour_stock(1, 1, 0, 0)
stock_concurrency_before = query_tour(1)
print(f"Stock before concurrency test: adult={stock_concurrency_before['adult']}")

user1_order = {
    "fullName": "Khach Hang 1",
    "phone": "0901111111",
    "note": "Dat dong thoi 1",
    "paymentMethod": "money",
    "items": [{"tourId": 1, "quantityAdult": 1, "quantityChildren": 0, "quantityBaby": 0, "locationFrom": 1}]
}

user2_order = {
    "fullName": "Khach Hang 2",
    "phone": "0902222222",
    "note": "Dat dong thoi 2",
    "paymentMethod": "money",
    "items": [{"tourId": 1, "quantityAdult": 1, "quantityChildren": 0, "quantityBaby": 0, "locationFrom": 1}]
}

# Run both requests concurrently using ThreadPoolExecutor
with concurrent.futures.ThreadPoolExecutor(max_workers=2) as executor:
    fut1 = executor.submit(post_order, user1_order)
    fut2 = executor.submit(post_order, user2_order)
    res_user1 = fut1.result()
    res_user2 = fut2.result()

print("User 1 response:", res_user1)
print("User 2 response:", res_user2)

stock_concurrency_after = query_tour(1)
print(f"Stock after concurrency test: adult={stock_concurrency_after['adult']}")

codes = [res_user1.get('code'), res_user2.get('code')]
print(f"Results of both orders: {codes}")

assert codes.count('success') == 1, f"Exactly 1 order must succeed! Got: {codes}"
assert codes.count('error') == 1, f"Exactly 1 order must fail with error! Got: {codes}"
assert stock_concurrency_after['adult'] == 0, f"Stock must be exactly 0 (not negative)! Got: {stock_concurrency_after['adult']}"
print(">>> TEST 3 PASSED: Concurrency handled perfectly! 1 succeeded, 1 rejected, stock = 0, no race condition!\n")

# Reset tour 1 stock to a good value
update_tour_stock(1, 9, 6, 8)
print("Reset tour 1 stock back to 9, 6, 8.")
print("ALL TESTS PASSED SUCCESSFULLY!")
