import urllib.request
import re

url = 'http://localhost:8080/project/category/tour-trong-nuoc'
with urllib.request.urlopen(url) as r:
    html = r.read().decode('utf-8')
    m = re.search(r'<div class="box-breadcrumb">[\s\S]*?</div>\s*</div>', html)
    if m:
        print("BREADCRUMB HTML:")
        print(m.group(0)[:600])

url_home = 'http://localhost:8080/project/'
with urllib.request.urlopen(url_home) as r:
    html_home = r.read().decode('utf-8')
    m_badge = re.search(r'<div class="inner-badge"[\s\S]*?</div>', html_home)
    if m_badge:
        print("\nBADGE HTML:")
        print(m_badge.group(0))
