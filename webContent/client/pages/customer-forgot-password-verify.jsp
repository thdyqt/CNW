<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../layouts/header.jsp" />

<main class="customer-account-page">
    <section class="customer-account-card">
        <h1>Xác minh quên mật khẩu</h1>
        <p>Nhập mã OTP đã gửi đến <strong><c:out value="${forgotPasswordEmail}" /></strong>, sau đó đặt mật khẩu mới.</p>
        <c:if test="${not empty error}"><div class="customer-account-message is-error"><c:out value="${error}" /></div></c:if>
        <form method="post" action="${pageContext.request.contextPath}/account/forgot-password/verify" class="customer-account-form">
            <label>Mã OTP
                <input type="text" name="otp" inputmode="numeric" autocomplete="one-time-code" pattern="[0-9]{6}" maxlength="6" required />
            </label>
            <label>Mật khẩu mới
                <span class="customer-password-field">
                    <input id="customer-reset-password" type="password" name="password" minlength="6" autocomplete="new-password" required />
                    <button type="button" class="customer-password-toggle" data-password-toggle aria-controls="customer-reset-password" aria-pressed="false">Hiện</button>
                </span>
            </label>
            <label>Nhập lại mật khẩu mới
                <span class="customer-password-field">
                    <input id="customer-reset-confirm-password" type="password" name="confirmPassword" minlength="6" autocomplete="new-password" required />
                    <button type="button" class="customer-password-toggle" data-password-toggle aria-controls="customer-reset-confirm-password" aria-pressed="false">Hiện</button>
                </span>
            </label>
            <button type="submit">Xác nhận và đặt lại mật khẩu</button>
        </form>
        <div class="customer-account-links">
            <a href="${pageContext.request.contextPath}/account/forgot-password">Yêu cầu mã OTP mới</a>
            <a href="${pageContext.request.contextPath}/account/login">Quay lại đăng nhập</a>
        </div>
    </section>
</main>

<jsp:include page="../layouts/footer.jsp" />
