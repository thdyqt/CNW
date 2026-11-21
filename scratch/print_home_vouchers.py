import urllib.request

with urllib.request.urlopen("http://localhost:8080/project/") as resp:
    html = resp.read().decode("utf-8")
    start = html.find('class="inner-voucher-list"')
    end = html.find('class="swiper swiper-section-3"')
    print(html[start:end])
