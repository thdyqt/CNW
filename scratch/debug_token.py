import urllib.request, http.cookiejar, subprocess

token = 'tok_adm_test'
subprocess.run(['C:\\xampp\\mysql\\bin\\mysql.exe', '-u', 'root', '-P', '3307', '-e', f"UPDATE tour_management.accounts_admin SET token='{token}' WHERE id=2;"], check=True)

cj = http.cookiejar.CookieJar()
opener = urllib.request.build_opener(urllib.request.HTTPCookieProcessor(cj))
ck = http.cookiejar.Cookie(0, 'token', token, None, False, 'localhost', True, False, '/', True, False, 1899999999, False, None, None, {}, False)
cj.set_cookie(ck)

req = urllib.request.Request('http://localhost:8080/project/admin/dashboard')
resp = opener.open(req)
print('Status:', resp.status, 'Final URL:', resp.geturl())
content = resp.read().decode('utf-8')
print('Content sample:', [l.strip() for l in content.splitlines() if l.strip()][:10])
