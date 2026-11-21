import urllib.request
import re

req = urllib.request.Request('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css', headers={'User-Agent': 'Mozilla/5.0'})
c = urllib.request.urlopen(req).read().decode('utf-8')
for name in ['crown', 'star', 'wand-magic-sparkles', 'gem']:
    m = re.search(r'\.fa-' + name + r'\{--fa:\"([^\"]+)\"\}', c)
    if m:
        print(f'{name}: {m.group(1).encode("unicode_escape").decode()}')
