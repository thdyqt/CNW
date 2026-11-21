<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../layouts/header.jsp" />

<main class="customer-account-page">
    <section class="customer-account-card">
        <h1>Xác nhận email</h1>
        <p>Mã xác nhận đã được gửi đến <strong><c:out value="${registrationEmail}" /></strong>. Mã có hiệu lực trong 5 phút.</p>
        <c:if test="${not empty error}"><div class="customer-account-message is-error"><c:out value="${error}" /></div></c:if>
        <c:if test="${not empty success}"><div class="customer-account-message is-success"><c:out value="${success}" /></div></c:if>
        <form method="post" action="${pageContext.request.contextPath}/account/verify-otp" class="customer-account-form">
            <label>Mã OTP
                <input type="text" name="otp" inputmode="numeric" autocomplete="one-time-code" pattern="[0-9]{6}" maxlength="6" required />
            </label>
            <button type="submit">Xác nhận và tạo tài khoản</button>
        </form>
        <form method="post" action="${pageContext.request.contextPath}/account/resend-otp" class="customer-account-resend">
            <button type="submit" class="customer-account-link-button">Gửi lại mã OTP</button>
        </form>
        <div class="customer-account-links">
            <a href="${pageContext.request.contextPath}/account/register">Quay lại đăng ký</a>
        </div>
    </section>
</main>

<jsp:include page="../layouts/footer.jsp" />
