import urllib.request
import urllib.parse
import json
import http.cookiejar
import subprocess
import time

print("Setting up test account...")
subprocess.run([
    'C:\\xampp\\mysql\\bin\\mysql.exe', '-u', 'root', '-P', '3307', '-e',
    "INSERT INTO tour_management.accounts_admin (full_name, email, password, status, role_id) VALUES ('Token Tester', 'token_tester@test.com', '$2a$10$tiHGGaDxRGX/BSYl5XFk9ubLi5YubPxa5LYIGY5FF0RD1zgf1V1bq', 'active', 1);"
], check=True)

try:
    cj = http.cookiejar.CookieJar()
    opener = urllib.request.build_opener(urllib.request.HTTPCookieProcessor(cj))

    # 1. Login with rememberPassword = True
    login_url = "http://localhost:8080/project/admin/account/login"
    login_payload = json.dumps({
        "email": "token_tester@test.com",
        "password": "123456",
        "rememberPassword": True
    }).encode("utf-8")

    req = urllib.request.Request(login_url, data=login_payload, headers={
        "Content-Type": "application/json",
        "Accept": "application/json"
    })
    resp = opener.open(req)
    print("Login response status:", resp.status)
    print("Login response body:", resp.read().decode("utf-8"))

    # Check cookies received
    token_cookie = None
    for cookie in cj:
        print(f"Cookie received: {cookie.name}={cookie.value[:15]}... expires={cookie.expires}")
        if cookie.name == "token":
            token_cookie = cookie

    if token_cookie:
        print("SUCCESS: Cookie 'token' was created and saved!")
        print("Token value:", token_cookie.value)
    else:
        print("FAILED: No 'token' cookie found!")

    # Check database token
    out = subprocess.check_output([
        'C:\\xampp\\mysql\\bin\\mysql.exe', '-u', 'root', '-P', '3307', '-e',
        "SELECT token FROM tour_management.accounts_admin WHERE email='token_tester@test.com';"
    ]).decode('utf-8')
    db_token = out.strip().split('\n')[-1].strip()
    print("Database token:", db_token)
    assert db_token == token_cookie.value, "DB token must match cookie token!"

    # 2. Simulate closing browser / losing JSESSIONID:
    # Create fresh CookieJar with ONLY the 'token' cookie (NO JSESSIONID)
    fresh_cj = http.cookiejar.CookieJar()
    fresh_opener = urllib.request.build_opener(urllib.request.HTTPCookieProcessor(fresh_cj))
    
    # Manually add only the token cookie to fresh_cj
    ck = http.cookiejar.Cookie(
        version=0, name="token", value=token_cookie.value, port=None, port_specified=False,
        domain="localhost", domain_specified=True, domain_initial_dot=False, path="/",
        path_specified=True, secure=False, expires=token_cookie.expires, discard=False,
        comment=None, comment_url=None, rest={'HttpOnly': None}, rfc2109=False
    )
    fresh_cj.set_cookie(ck)

    # 3. Request protected page /project/admin/dashboard without any JSESSIONID
    req = urllib.request.Request("http://localhost:8080/project/admin/dashboard")
    resp2 = fresh_opener.open(req)
    body2 = resp2.read().decode("utf-8", errors="ignore")
    print("Access /project/admin/dashboard with ONLY token cookie status:", resp2.status)
    is_authenticated = "Tổng quan" in body2 or "Token Tester" in body2 or "Đăng xuất" in body2
    print("Successfully authenticated and maintained login:", is_authenticated)
    assert is_authenticated, "User must stay logged in via token cookie!"

    # 4. Access login page while authenticated -> should redirect to /dashboard
    class NoRedirect(urllib.request.HTTPRedirectHandler):
        def redirect_request(self, req, fp, code, msg, headers, newurl):
            return None # do not follow redirect automatically

    no_redirect_opener = urllib.request.build_opener(urllib.request.HTTPCookieProcessor(fresh_cj), NoRedirect)
    try:
        resp_login = no_redirect_opener.open("http://localhost:8080/project/admin/account/login")
        print("Login page status when logged in:", resp_login.status)
    except urllib.error.HTTPError as e:
        print("Login page redirected:", e.code, "Location:", e.headers.get("Location"))
        assert e.code in [301, 302], "Should redirect when already authenticated"

    # 5. Logout
    req_logout = urllib.request.Request("http://localhost:8080/project/admin/account/logout", method="POST", headers={
        "Content-Type": "application/json",
        "Accept": "application/json"
    })
    resp_logout = fresh_opener.open(req_logout)
    print("Logout response:", resp_logout.read().decode("utf-8"))

    # Check if DB token is cleared
    out_after_logout = subprocess.check_output([
        'C:\\xampp\\mysql\\bin\\mysql.exe', '-u', 'root', '-P', '3307', '-e',
        "SELECT token FROM tour_management.accounts_admin WHERE email='token_tester@test.com';"
    ]).decode('utf-8')
    db_token_after = out_after_logout.strip().split('\n')[-1].strip()
    print("Database token after logout:", db_token_after)
    assert db_token_after == "NULL" or db_token_after == "", "Token in DB must be cleared after logout"

    print("\nALL SESSION TOKEN AND REMEMBER PASSWORD TESTS PASSED 100%!")

finally:
    subprocess.run([
        'C:\\xampp\\mysql\\bin\\mysql.exe', '-u', 'root', '-P', '3307', '-e',
        "DELETE FROM tour_management.accounts_admin WHERE email='token_tester@test.com';"
    ], check=True)
    print("Cleanup test account completed.")
