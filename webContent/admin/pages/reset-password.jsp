<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/account-header.jsp" />

<div class="form-account">
    <h2 class="inner-title">Đổi mật khẩu</h2>
    <p class="inner-desc">Vui lòng nhập mật khẩu mới để tiếp tục</p>
    <form class="inner-form" id="change-password-form" method="POST" action="${pageContext.request.contextPath}/${pathAdmin}/account/login">
        <div class="inner-group">
            <label class="inner-label" for="new-password-input">Mật khẩu mới</label>
            <input class="inner-control" type="password" id="new-password-input" name="newPassword" required />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="confirm-password-input">Xác nhận mật khẩu</label>
            <input class="inner-control" type="password" id="confirm-password-input" name="confirmPassword" required />
        </div>
        <button class="inner-button" type="submit">Thay đổi</button>
    </form>
    <div class="inner-more">
        <span>Bạn đã nhớ mật khẩu?</span>
        <a href="${pageContext.request.contextPath}/${pathAdmin}/account/login">Đăng nhập</a>
    </div>
</div>

<jsp:include page="../layouts/account-footer.jsp" />
