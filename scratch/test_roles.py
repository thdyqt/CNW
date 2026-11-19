import urllib.request
import urllib.parse
import json
import http.cookiejar
import subprocess

# 1. Create temporary test admin
subprocess.run([
    'C:\\xampp\\mysql\\bin\\mysql.exe', '-u', 'root', '-P', '3307', '-e',
    "INSERT INTO tour_management.accounts_admin (full_name, email, password, status, role_id) VALUES ('Test Admin', 'test_role_runner@test.com', '$2a$10$tiHGGaDxRGX/BSYl5XFk9ubLi5YubPxa5LYIGY5FF0RD1zgf1V1bq', 'active', 1);"
], check=True)

try:
    cj = http.cookiejar.CookieJar()
    opener = urllib.request.build_opener(urllib.request.HTTPCookieProcessor(cj))

    # 2. Login
    login_url = "http://localhost:8080/project/admin/account/login"
    login_payload = json.dumps({
        "email": "test_role_runner@test.com",
        "password": "123456"
    }).encode("utf-8")
    req = urllib.request.Request(login_url, data=login_payload, headers={
        "Content-Type": "application/json",
        "Accept": "application/json"
    })
    resp = opener.open(req)
    print("Login status:", resp.status, "body:", resp.read().decode("utf-8"))

    # 3. GET /project/admin/setting/role/list
    req = urllib.request.Request("http://localhost:8080/project/admin/setting/role/list")
    resp = opener.open(req)
    body = resp.read().decode("utf-8", errors="ignore")
    print("Role list status:", resp.status)
    print("Contains 'Nhóm quyền':", "Nhóm quyền" in body)
    print("Contains 'Quản trị viên':", "Quản trị viên" in body)
    print("Contains 'Thùng rác':", "Thùng rác" in body)
    print("Contains '+ Tạo mới':", "+ Tạo mới" in body)

    # 4. GET /project/admin/setting/role/create
    req = urllib.request.Request("http://localhost:8080/project/admin/setting/role/create")
    resp = opener.open(req)
    body = resp.read().decode("utf-8", errors="ignore")
    print("Role create status:", resp.status)
    print("Contains 'Tạo nhóm quyền':", "Tạo nhóm quyền" in body)
    print("Contains 'inner-checkbox-list':", "inner-checkbox-list" in body)
    print("Contains 'Xem trang Tổng quan':", "Xem trang Tổng quan" in body)

    # 5. GET /project/admin/setting/role/edit/1
    req = urllib.request.Request("http://localhost:8080/project/admin/setting/role/edit/1")
    resp = opener.open(req)
    body = resp.read().decode("utf-8", errors="ignore")
    print("Role edit status:", resp.status)
    print("Contains 'Chỉnh sửa nhóm quyền':", "Chỉnh sửa nhóm quyền" in body)
    print("Contains 'Quản trị viên':", "Quản trị viên" in body)

    # 6. GET /project/admin/setting/role/trash
    req = urllib.request.Request("http://localhost:8080/project/admin/setting/role/trash")
    resp = opener.open(req)
    body = resp.read().decode("utf-8", errors="ignore")
    print("Role trash status:", resp.status)
    print("Contains 'Thùng rác nhóm quyền':", "Thùng rác nhóm quyền" in body)

    # 7. POST /project/admin/setting/role/create
    new_role_payload = json.dumps({
        "name": "Nhóm thử nghiệm tự động",
        "description": "Mô tả nhóm thử nghiệm tự động",
        "permissions": ["dashboard-view", "tour-view", "category-view"]
    }).encode("utf-8")
    req = urllib.request.Request("http://localhost:8080/project/admin/setting/role/create", data=new_role_payload, headers={
        "Content-Type": "application/json"
    })
    resp = opener.open(req)
    create_res = json.loads(resp.read().decode("utf-8"))
    print("Create role response:", create_res)

    out = subprocess.check_output([
        'C:\\xampp\\mysql\\bin\\mysql.exe', '-u', 'root', '-P', '3307', '-e',
        'SELECT id FROM tour_management.roles WHERE slug="nhom-thu-nghiem-tu-dong" ORDER BY id DESC LIMIT 1;'
    ]).decode('utf-8')
    created_id = int(out.strip().split('\n')[-1].strip())
    print("Created role id:", created_id)

    # 8. PATCH /project/admin/setting/role/edit/{id}
    edit_payload = json.dumps({
        "id": created_id,
        "name": "Nhóm thử nghiệm đã cập nhật",
        "description": "Mô tả mới sau cập nhật",
        "permissions": ["dashboard-view", "tour-view", "tour-create"]
    }).encode("utf-8")
    req = urllib.request.Request(f"http://localhost:8080/project/admin/setting/role/edit/{created_id}", data=edit_payload, headers={
        "Content-Type": "application/json"
    }, method="PATCH")
    resp = opener.open(req)
    edit_res = json.loads(resp.read().decode("utf-8"))
    print("Edit role response:", edit_res)

    # 9. PATCH /project/admin/setting/role/delete/{id} (Soft delete)
    req = urllib.request.Request(f"http://localhost:8080/project/admin/setting/role/delete/{created_id}", method="PATCH")
    resp = opener.open(req)
    del_res = json.loads(resp.read().decode("utf-8"))
    print("Soft delete response:", del_res)

    # Verify in trash
    req = urllib.request.Request("http://localhost:8080/project/admin/setting/role/trash")
    resp = opener.open(req)
    trash_body = resp.read().decode("utf-8", errors="ignore")
    print("Found in trash HTML:", "Nhóm thử nghiệm đã cập nhật" in trash_body)

    # 10. PATCH /project/admin/setting/role/undo/{id} (Restore)
    req = urllib.request.Request(f"http://localhost:8080/project/admin/setting/role/undo/{created_id}", method="PATCH")
    resp = opener.open(req)
    undo_res = json.loads(resp.read().decode("utf-8"))
    print("Undo response:", undo_res)

    # Verify back in list
    req = urllib.request.Request("http://localhost:8080/project/admin/setting/role/list")
    resp = opener.open(req)
    list_body = resp.read().decode("utf-8", errors="ignore")
    print("Found back in list HTML:", "Nhóm thử nghiệm đã cập nhật" in list_body)

    # 11. Change Multi: Soft delete via change-multi
    multi_payload = json.dumps({
        "option": "delete",
        "ids": [created_id]
    }).encode("utf-8")
    req = urllib.request.Request("http://localhost:8080/project/admin/setting/role/change-multi", data=multi_payload, headers={
        "Content-Type": "application/json"
    }, method="PATCH")
    resp = opener.open(req)
    multi_res = json.loads(resp.read().decode("utf-8"))
    print("Change multi delete response:", multi_res)

    # 12. Change Multi: Undo via change-multi
    multi_undo_payload = json.dumps({
        "option": "undo",
        "ids": [created_id]
    }).encode("utf-8")
    req = urllib.request.Request("http://localhost:8080/project/admin/setting/role/change-multi", data=multi_undo_payload, headers={
        "Content-Type": "application/json"
    }, method="PATCH")
    resp = opener.open(req)
    multi_undo_res = json.loads(resp.read().decode("utf-8"))
    print("Change multi undo response:", multi_undo_res)

    # 13. Permanent delete: DELETE-DESTROY
    req = urllib.request.Request(f"http://localhost:8080/project/admin/setting/role/delete-destroy/{created_id}", method="PATCH")
    resp = opener.open(req)
    destroy_res = json.loads(resp.read().decode("utf-8"))
    print("Permanent delete response:", destroy_res)

    # Verify completely gone
    out2 = subprocess.check_output([
        'C:\\xampp\\mysql\\bin\\mysql.exe', '-u', 'root', '-P', '3307', '-e',
        f'SELECT COUNT(*) FROM tour_management.roles WHERE id={created_id};'
    ]).decode('utf-8')
    count = int(out2.strip().split('\n')[-1].strip())
    print("Count in DB after destroy (should be 0):", count)

finally:
    # Cleanup test admin
    subprocess.run([
        'C:\\xampp\\mysql\\bin\\mysql.exe', '-u', 'root', '-P', '3307', '-e',
        "DELETE FROM tour_management.accounts_admin WHERE email='test_role_runner@test.com';"
    ], check=True)
    print("Test cleanup completed!")
