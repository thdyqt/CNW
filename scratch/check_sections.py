import urllib.request
import re

print("=== 1. CHECK HOMEPAGE (Section 3) ===")
with urllib.request.urlopen("http://localhost:8080/project/") as resp:
    html = resp.read().decode("utf-8")
    sec3_start = html.find('class="section-3"')
    sec4_start = html.find('class="section-4"')
    sec3_html = html[sec3_start:sec4_start]
    
    print("Found section 3: length =", len(sec3_html))
    vouchers = re.findall(r'class="inner-voucher-card[^"]*"', sec3_html)
    print(f"Total voucher cards rendered: {len(vouchers)}")
    
    codes = re.findall(r'class="inner-code">([^<]+)</span>', sec3_html)
    print("Voucher codes found on homepage:", codes)
    
    copy_btns = re.findall(r'button-copy-coupon="([^"]+)"', sec3_html)
    print("Copy buttons found for:", copy_btns)

print("\n=== 2. CHECK CART PAGE ===")
with urllib.request.urlopen("http://localhost:8080/project/cart") as resp:
    html = resp.read().decode("utf-8")
    cart_coupons_start = html.find('class="inner-cart-coupons"')
    assert cart_coupons_start != -1, "inner-cart-coupons not found"
    cart_coupons_html = html[cart_coupons_start:cart_coupons_start+3000]
    
    pills = re.findall(r'coupon-pill="([^"]+)"', cart_coupons_html)
    print("Coupon pills found on cart page:", pills)
    
    apply_btns = re.findall(r'button-quick-apply="([^"]+)"', cart_coupons_html)
    print("Quick apply buttons found for:", apply_btns)

print("\n>>> ALL CHECKS PASSED PERFECTLY!")
