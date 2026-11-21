<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../layouts/header.jsp" />

<main class="customer-account-page">
    <section class="customer-account-card">
        <h1>Đăng nhập</h1>
        <p>Đăng nhập để lưu thông tin và đặt tour nhanh hơn.</p>
        <c:if test="${not empty error}"><div class="customer-account-message is-error"><c:out value="${error}" /></div></c:if>
        <c:if test="${not empty success}"><div class="customer-account-message is-success"><c:out value="${success}" /></div></c:if>
        <form method="post" action="${pageContext.request.contextPath}/account/login" class="customer-account-form">
            <label>Email
                <input type="email" name="email" autocomplete="email" required />
            </label>
            <label>Mật khẩu
                <span class="customer-password-field">
                    <input id="customer-login-password" type="password" name="password" autocomplete="current-password" required />
                    <button type="button" class="customer-password-toggle" data-password-toggle aria-controls="customer-login-password" aria-pressed="false">Hiện</button>
                </span>
            </label>
            <button type="submit">Đăng nhập</button>
        </form>
        <div class="customer-account-links">
            <a href="${pageContext.request.contextPath}/account/forgot-password">Quên mật khẩu?</a>
            <span>Chưa có tài khoản?</span>
            <a href="${pageContext.request.contextPath}/account/register">Đăng ký</a>
        </div>
    </section>
</main>

<jsp:include page="../layouts/footer.jsp" />
