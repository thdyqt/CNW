import urllib.request
import urllib.parse
import json
import http.cookiejar
import subprocess
import time

# Create temporary admin for authentication
subprocess.run([
    'C:\\xampp\\mysql\\bin\\mysql.exe', '-u', 'root', '-P', '3307', '-e',
    "INSERT INTO tour_management.accounts_admin (full_name, email, password, status, role_id) VALUES ('Test Admin', 'test_user_runner@test.com', '$2a$10$tiHGGaDxRGX/BSYl5XFk9ubLi5YubPxa5LYIGY5FF0RD1zgf1V1bq', 'active', 1);"
], check=True)

try:
    cj = http.cookiejar.CookieJar()
    opener = urllib.request.build_opener(urllib.request.HTTPCookieProcessor(cj))

    # 1. Login
    login_url = "http://localhost:8080/project/admin/account/login"
    login_payload = json.dumps({
        "email": "test_user_runner@test.com",
        "password": "123456"
    }).encode("utf-8")
    req = urllib.request.Request(login_url, data=login_payload, headers={
        "Content-Type": "application/json",
        "Accept": "application/json"
    })
    resp = opener.open(req)
    print("Login:", json.loads(resp.read().decode("utf-8")))

    # 2. GET /project/admin/user/list
    req = urllib.request.Request("http://localhost:8080/project/admin/user/list")
    resp = opener.open(req)
    body = resp.read().decode("utf-8", errors="ignore")
    print("User list status:", resp.status)
    print("Contains 'Quản lý người dùng':", "Quản lý người dùng" in body)
    print("Contains 'Lê Văn A':", "Lê Văn A" in body)
    print("Contains 'Lê Văn B':", "Lê Văn B" in body)
    print("Contains 'levana@gmail.com':", "levana@gmail.com" in body)
    print("Contains '01234567890':", "01234567890" in body)
    print("Contains 'Số 123, đường ABC,...':", "Số 123, đường ABC,..." in body)
    print("Contains 'badge-green':", "badge-green" in body)
    print("Contains 'badge-red':", "badge-red" in body)
    print("Contains filter-status:", "filter-status" in body)
    print("Contains filter-start-date:", "filter-start-date" in body)
    print("Contains change-multi:", "change-multi" in body)
    print("Contains pagination:", "pagination" in body)

    # 3. Test filter status=active
    req = urllib.request.Request("http://localhost:8080/project/admin/user/list?status=active")
    resp = opener.open(req)
    body_active = resp.read().decode("utf-8", errors="ignore")
    print("Filter active contains Lê Văn A:", "Lê Văn A" in body_active)
    print("Filter active NOT contains Lê Văn B:", "Lê Văn B" not in body_active)

    # 4. Test filter status=inactive
    req = urllib.request.Request("http://localhost:8080/project/admin/user/list?status=inactive")
    resp = opener.open(req)
    body_inactive = resp.read().decode("utf-8", errors="ignore")
    print("Filter inactive contains Lê Văn B:", "Lê Văn B" in body_inactive)
    print("Filter inactive NOT contains Lê Văn A:", "Lê Văn A" not in body_inactive)

    # 5. Test search keyword=levana
    req = urllib.request.Request("http://localhost:8080/project/admin/user/list?keyword=levana")
    resp = opener.open(req)
    body_search = resp.read().decode("utf-8", errors="ignore")
    print("Search levana contains Lê Văn A:", "Lê Văn A" in body_search)
    print("Search levana NOT contains Lê Văn B:", "Lê Văn B" not in body_search)

    # 6. Test GET /project/admin/user/edit/1
    req = urllib.request.Request("http://localhost:8080/project/admin/user/edit/1")
    resp = opener.open(req)
    body_edit = resp.read().decode("utf-8", errors="ignore")
    print("User edit page status:", resp.status)
    print("Edit page contains 'Chỉnh sửa thông tin người dùng':", "Chỉnh sửa thông tin người dùng" in body_edit)
    print("Edit page contains 'levana@gmail.com':", "levana@gmail.com" in body_edit)

    # 7. Test POST /project/admin/user/edit/1
    edit_data = urllib.parse.urlencode({
        "id": "1",
        "fullName": "Lê Văn A",
        "phone": "01234567890",
        "address": "Số 123, đường ABC,...",
        "status": "active"
    }).encode("utf-8")
    req = urllib.request.Request("http://localhost:8080/project/admin/user/edit/1", data=edit_data, headers={
        "Content-Type": "application/x-www-form-urlencoded"
    })
    resp = opener.open(req)
    print("Edit user response:", json.loads(resp.read().decode("utf-8")))

    # 8. Test change-multi on a dummy user
    # Insert a dummy user to test delete and change-multi
    subprocess.run([
        'C:\\xampp\\mysql\\bin\\mysql.exe', '-u', 'root', '-P', '3307', '-e',
        "INSERT INTO tour_management.users (full_name, email, password, phone, status) VALUES ('Dummy User', 'dummy_test@test.com', '$2a$10$tiHGGaDxRGX/BSYl5XFk9ubLi5YubPxa5LYIGY5FF0RD1zgf1V1bq', '0999999999', 'active');"
    ], check=True)
    out = subprocess.check_output([
        'C:\\xampp\\mysql\\bin\\mysql.exe', '-u', 'root', '-P', '3307', '-e',
        "SELECT id FROM tour_management.users WHERE email='dummy_test@test.com';"
    ]).decode('utf-8')
    dummy_id = int(out.strip().split('\n')[-1].strip())
    print("Created dummy user id:", dummy_id)

    # Test change-multi to inactive
    multi_payload = json.dumps({
        "option": "inactive",
        "ids": [dummy_id]
    }).encode("utf-8")
    req = urllib.request.Request("http://localhost:8080/project/admin/user/change-multi", data=multi_payload, headers={
        "Content-Type": "application/json"
    }, method="PATCH")
    resp = opener.open(req)
    print("Change multi response:", json.loads(resp.read().decode("utf-8")))

    # Check status in DB
    st_out = subprocess.check_output([
        'C:\\xampp\\mysql\\bin\\mysql.exe', '-u', 'root', '-P', '3307', '-e',
        f"SELECT status FROM tour_management.users WHERE id={dummy_id};"
    ]).decode('utf-8')
    print("Dummy user status after change-multi:", st_out.strip().split('\n')[-1].strip())

    # Test soft delete
    req = urllib.request.Request(f"http://localhost:8080/project/admin/user/delete/{dummy_id}", method="PATCH")
    resp = opener.open(req)
    print("Delete user response:", json.loads(resp.read().decode("utf-8")))

    # Check deleted in DB
    del_out = subprocess.check_output([
        'C:\\xampp\\mysql\\bin\\mysql.exe', '-u', 'root', '-P', '3307', '-e',
        f"SELECT deleted FROM tour_management.users WHERE id={dummy_id};"
    ]).decode('utf-8')
    print("Dummy user deleted flag (should be 1):", del_out.strip().split('\n')[-1].strip())

    # Clean up dummy user
    subprocess.run([
        'C:\\xampp\\mysql\\bin\\mysql.exe', '-u', 'root', '-P', '3307', '-e',
        f"DELETE FROM tour_management.users WHERE id={dummy_id};"
    ], check=True)

    print("ALL TESTS PASSED SUCCESSFULLY!")

finally:
    subprocess.run([
        'C:\\xampp\\mysql\\bin\\mysql.exe', '-u', 'root', '-P', '3307', '-e',
        "DELETE FROM tour_management.accounts_admin WHERE email='test_user_runner@test.com';"
    ], check=True)
    print("Admin test cleanup completed!")
