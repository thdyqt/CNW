<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Thông tin website</h1>

<div class="section-8">
    <form id="website-form" method="POST" action="${pageContext.request.contextPath}/${pathAdmin}/setting/website-info">
        <div class="inner-group">
            <label class="inner-label" for="websiteName">Tên website</label>
            <input type="text" id="websiteName" name="websiteName" value="${settingWebsiteInfo.websiteName}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="phone">Số điện thoại</label>
            <input type="text" id="phone" name="phone" value="${settingWebsiteInfo.phone}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="email">Email</label>
            <input type="email" id="email" name="email" value="${settingWebsiteInfo.email}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="address">Địa chỉ</label>
            <input type="text" id="address" name="address" value="${settingWebsiteInfo.address}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="facebook">Link Facebook</label>
            <input type="text" id="facebook" name="facebook" placeholder="https://facebook.com/..." value="${settingWebsiteInfo.facebook}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="instagram">Link Instagram</label>
            <input type="text" id="instagram" name="instagram" placeholder="https://instagram.com/..." value="${settingWebsiteInfo.instagram}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="github">Link GitHub</label>
            <input type="text" id="github" name="github" placeholder="https://github.com/..." value="${settingWebsiteInfo.github}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="youtube">Link YouTube</label>
            <input type="text" id="youtube" name="youtube" placeholder="https://youtube.com/..." value="${settingWebsiteInfo.youtube}" />
        </div>
        <div class="inner-group inner-two-col">
            <label class="inner-label" for="copyright">Bản quyền chân trang (Copyright)</label>
            <input type="text" id="copyright" name="copyright" placeholder="© 2026 DUTTech. All rights reserved." value="${settingWebsiteInfo.copyright}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="logo">Logo</label>
            <div class="inner-input-image" image-default="${settingWebsiteInfo.logo}">
                <input type="file" id="logo" name="logo" accept="image/*" filepond-image />
            </div>
        </div>
        <div class="inner-group">
            <label class="inner-label" for="favicon">Favicon</label>
            <div class="inner-input-image" image-default="${settingWebsiteInfo.favicon}">
                <input type="file" id="favicon" name="favicon" accept="image/*" filepond-image />
            </div>
        </div>
        <div class="inner-button inner-two-col">
            <button type="submit">Cập nhật</button>
        </div>
    </form>
    <div class="inner-back">
        <a href="${pageContext.request.contextPath}/${pathAdmin}/setting/list">Quay lại danh sách</a>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
