<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/account-header.jsp" />

<div class="form-account">
    <h2 class="inner-title">Quên mật khẩu</h2>
    <p class="inner-desc">Vui lòng nhập email để tiếp tục</p>
    <form class="inner-form" id="forgot-password-form" method="POST" action="${pageContext.request.contextPath}/${pathAdmin}/account/otp-password">
        <div class="inner-group">
            <label class="inner-label" for="email-input">Email</label>
            <input class="inner-control" type="email" id="email-input" name="email" placeholder="Ví dụ: levana@gmail.com" required />
        </div>
        <button class="inner-button" type="submit">Gửi mã OTP</button>
    </form>
    <div class="inner-more">
        <span>Bạn đã nhớ mật khẩu?</span>
        <a href="${pageContext.request.contextPath}/${pathAdmin}/account/login">Đăng nhập</a>
    </div>
</div>

<jsp:include page="../layouts/account-footer.jsp" />
