import urllib.request
import re

with urllib.request.urlopen("http://localhost:8080/project/cart") as resp:
    html = resp.read().decode("utf-8")
    start = html.find('class="inner-cart-coupons"')
    end = html.find('class="inner-list-price"')
    print(html[start:end])
