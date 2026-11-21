<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<footer class="footer">
    <div class="container">
        <div class="inner-top">
            <h2 class="inner-title">Đăng ký ngay để không bỏ lỡ các chương trình của chúng tôi</h2>
            <form class="inner-form" id="email-form" action="">
                <input id="email-input" placeholder="Nhập email của bạn.." name="email" type="email" />
                <button type="submit">Đăng Ký Ngay</button>
            </form>
        </div>
        <div class="inner-middle">
            <nav class="inner-links">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/">Trang Chủ</a></li>
                    <li><a href="${pageContext.request.contextPath}/about">Giới Thiệu</a></li>
                    <li><a href="${pageContext.request.contextPath}/category/tour-trong-nuoc">Tour Trong Nước</a></li>
                    <li><a href="${pageContext.request.contextPath}/category/tour-nuoc-ngoai">Tour Nước Ngoài</a></li>
                    <li><a href="${pageContext.request.contextPath}/news">Tin Tức</a></li>
                    <li><a href="${pageContext.request.contextPath}/contact">Liên Hệ</a></li>
                    <li><a href="${pageContext.request.contextPath}/order/lookup">Tra Cứu Đơn Hàng</a></li>
                </ul>
            </nav>
            <nav class="inner-socials">
                <ul>
                    <li><a target="_blank" href="${not empty settingWebsiteInfo.facebook ? settingWebsiteInfo.facebook : '#'}" title="Facebook"><i class="fa-brands fa-facebook"></i></a></li>
                    <li><a target="_blank" href="${not empty settingWebsiteInfo.instagram ? settingWebsiteInfo.instagram : '#'}" title="Instagram"><i class="fa-brands fa-instagram"></i></a></li>
                    <li><a target="_blank" href="${not empty settingWebsiteInfo.github ? settingWebsiteInfo.github : '#'}" title="GitHub"><i class="fa-brands fa-github"></i></a></li>
                    <li><a target="_blank" href="${not empty settingWebsiteInfo.youtube ? settingWebsiteInfo.youtube : '#'}" title="YouTube"><i class="fa-brands fa-youtube"></i></a></li>
                </ul>
            </nav>
        </div>
        <div class="inner-bottom">
            <div class="inner-copyright">${not empty settingWebsiteInfo.copyright ? settingWebsiteInfo.copyright : '© 2026 DUTTech. All rights reserved.'}</div>
            <div class="inner-logo">
                <a href="${pageContext.request.contextPath}/">
                    <img alt="Logo" src="${settingWebsiteInfo.logo}" />
                </a>
            </div>
            <nav class="inner-links">
                <ul>
                    <li><a href="${pageContext.request.contextPath}/terms">Điều khoản dịch vụ</a></li>
                    <li><a href="${pageContext.request.contextPath}/privacy">Chính sách bảo mật</a></li>
                </ul>
            </nav>
        </div>
    </div>
</footer>
