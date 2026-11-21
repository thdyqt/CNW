<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../layouts/header.jsp" />

<main class="customer-account-page">
    <section class="customer-account-card">
        <h1>Quên mật khẩu</h1>
        <p>Nhập email đã đăng ký. Hệ thống sẽ kiểm tra tài khoản rồi gửi mã OTP đến email đó.</p>
        <c:if test="${not empty error}"><div class="customer-account-message is-error"><c:out value="${error}" /></div></c:if>
        <form method="post" action="${pageContext.request.contextPath}/account/forgot-password" class="customer-account-form">
            <label>Email
                <input type="email" name="email" autocomplete="email" required />
            </label>
            <button type="submit">Gửi mã OTP</button>
        </form>
        <div class="customer-account-links">
            <a href="${pageContext.request.contextPath}/account/login">Quay lại đăng nhập</a>
        </div>
    </section>
</main>

<jsp:include page="../layouts/footer.jsp" />
