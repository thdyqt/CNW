<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/account-header.jsp" />

<div class="form-account">
    <h2 class="inner-title">Đăng nhập</h2>
    <p class="inner-desc">Vui lòng nhập email và mật khẩu để tiếp tục quản trị</p>
    <form class="inner-form" id="login-form" method="POST" action="${pageContext.request.contextPath}/${pathAdmin}/dashboard">
        <div class="inner-group">
            <label class="inner-label" for="email-input">Email</label>
            <input class="inner-control" type="email" name="email" id="email-input" placeholder="Ví dụ: admin@gmail.com"/>
        </div>
        <div class="inner-group">
            <label class="inner-label" for="password-input">Mật khẩu</label>
            <input class="inner-control" type="password" name="password" id="password-input"/>
        </div>
        <div class="inner-meta">
            <div class="inner-confirm">
                <input class="inner-check" type="checkbox" name="rememberPassword" id="remember-password" checked />
                <label class="inner-label" for="remember-password">Nhớ mật khẩu</label>
            </div>
            <a class="inner-forgot-password" href="${pageContext.request.contextPath}/${pathAdmin}/account/forgot-password">Quên mật khẩu?</a>
        </div>
        <button class="inner-button" type="submit">Đăng Nhập</button>
    </form>
    <div class="inner-more">
        <span>Bạn chưa có tài khoản?</span>
        <a href="${pageContext.request.contextPath}/${pathAdmin}/account/register">Tạo tài khoản</a>
    </div>
</div>

<jsp:include page="../layouts/account-footer.jsp" />
