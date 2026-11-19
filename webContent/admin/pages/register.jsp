<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/account-header.jsp" />

<div class="form-account">
    <h2 class="inner-title">Đăng ký</h2>
    <p class="inner-desc">Tạo một tài khoản để tiếp tục</p>
    <form class="inner-form" id="register-form" method="POST" action="${pageContext.request.contextPath}/${pathAdmin}/account/register-initial">
        <div class="inner-group">
            <label class="inner-label" for="full-name-input">Họ tên</label>
            <input class="inner-control" type="text" name="fullName" id="full-name-input" placeholder="Ví dụ: Lê Văn A" required />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="email-input">Email</label>
            <input class="inner-control" type="email" name="email" id="email-input" placeholder="Ví dụ: levana@gmail.com" required />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="password-input">Mật khẩu</label>
            <input class="inner-control" type="password" name="password" id="password-input" required />
        </div>
        <div class="inner-meta">
            <div class="inner-confirm">
                <input class="inner-check" type="checkbox" name="termAgreement" id="term-agreement" required />
                <label class="inner-label" for="term-agreement">Tôi chấp nhận các điều khoản và điều kiện</label>
            </div>
        </div>
        <button class="inner-button" type="submit">Đăng Ký</button>
    </form>
    <div class="inner-more">
        <span>Bạn đã có tài khoản?</span>
        <a href="${pageContext.request.contextPath}/${pathAdmin}/account/login">Đăng nhập</a>
    </div>
</div>

<jsp:include page="../layouts/account-footer.jsp" />
