<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../layouts/header.jsp" />

<main class="customer-account-page">
    <section class="customer-account-card">
        <h1>Tạo tài khoản khách hàng</h1>
        <p>Nhập thông tin và xác nhận email để hoàn tất đăng ký.</p>
        <c:if test="${not empty error}"><div class="customer-account-message is-error"><c:out value="${error}" /></div></c:if>
        <form method="post" action="${pageContext.request.contextPath}/account/register" class="customer-account-form">
            <label>Họ và tên
                <input type="text" name="fullName" maxlength="255" autocomplete="name" required />
            </label>
            <label>Email
                <input type="email" name="email" maxlength="255" autocomplete="email" required />
            </label>
            <label>Số điện thoại <span>(không bắt buộc)</span>
                <input type="tel" name="phone" maxlength="50" autocomplete="tel" />
            </label>
            <label>Địa chỉ <span>(không bắt buộc)</span>
                <input type="text" name="address" maxlength="500" autocomplete="street-address" />
            </label>
            <label>Mật khẩu
                <span class="customer-password-field">
                    <input id="customer-register-password" type="password" name="password" minlength="6" autocomplete="new-password" required />
                    <button type="button" class="customer-password-toggle" data-password-toggle aria-controls="customer-register-password" aria-pressed="false">Hiện</button>
                </span>
            </label>
            <label>Nhập lại mật khẩu
                <span class="customer-password-field">
                    <input id="customer-register-confirm-password" type="password" name="confirmPassword" minlength="6" autocomplete="new-password" required />
                    <button type="button" class="customer-password-toggle" data-password-toggle aria-controls="customer-register-confirm-password" aria-pressed="false">Hiện</button>
                </span>
            </label>
            <button type="submit">Gửi mã xác nhận</button>
        </form>
        <div class="customer-account-links">
            <span>Đã có tài khoản?</span>
            <a href="${pageContext.request.contextPath}/account/login">Đăng nhập</a>
        </div>
    </section>
</main>

<jsp:include page="../layouts/footer.jsp" />
