<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/account-header.jsp" />

<div class="form-account">
    <h2 class="inner-title">Nhập mã OTP</h2>
    <p class="inner-desc">Vui lòng nhập mã OTP để tiếp tục</p>
    <form class="inner-form" id="otp-form" method="POST" action="${pageContext.request.contextPath}/${pathAdmin}/account/reset-password">
        <div class="inner-group">
            <label class="inner-label" for="otp-input">Mã OTP</label>
            <input class="inner-control" type="number" id="otp-input" name="otp" placeholder="Ví dụ: 123456" required />
        </div>
        <button class="inner-button" type="submit">Xác thực</button>
    </form>
    <div class="inner-more">
        <span>Bạn đã nhớ mật khẩu?</span>
        <a href="${pageContext.request.contextPath}/${pathAdmin}/account/login">Đăng nhập</a>
    </div>
</div>

<jsp:include page="../layouts/account-footer.jsp" />
