import urllib.request
import urllib.parse
import json

BASE_URL = "http://localhost:8080/project"
TOKEN = "tok_adm_711659258d1b4648a261a43341d64bca"

headers = {
    "Cookie": f"token={TOKEN}",
    "Content-Type": "application/x-www-form-urlencoded"
}

# Update order 19 status to cancel
data = urllib.parse.urlencode({
    "status": "cancel"
}).encode("utf-8")

req = urllib.request.Request(f"{BASE_URL}/admin/order/edit/19", data=data, headers=headers)
with urllib.request.urlopen(req) as resp:
    print("Update status response code:", resp.status)

print("Check completed.")
