<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../layouts/header.jsp" />

<main class="customer-account-page">
    <section class="customer-account-card">
        <h1>Đổi mật khẩu</h1>
        <p>Nhập mật khẩu hiện tại để xác minh, sau đó đặt mật khẩu mới.</p>
        <c:if test="${not empty error}"><div class="customer-account-message is-error"><c:out value="${error}" /></div></c:if>
        <c:if test="${not empty success}"><div class="customer-account-message is-success"><c:out value="${success}" /></div></c:if>

        <form method="post" action="${pageContext.request.contextPath}/account/password/change" class="customer-account-form">
            <label>Mật khẩu hiện tại
                <span class="customer-password-field">
                    <input id="customer-current-password" type="password" name="currentPassword" autocomplete="current-password" required />
                    <button type="button" class="customer-password-toggle" data-password-toggle aria-controls="customer-current-password" aria-pressed="false">Hiện</button>
                </span>
            </label>
            <label>Mật khẩu mới
                <span class="customer-password-field">
                    <input id="customer-new-password" type="password" name="password" minlength="6" autocomplete="new-password" required />
                    <button type="button" class="customer-password-toggle" data-password-toggle aria-controls="customer-new-password" aria-pressed="false">Hiện</button>
                </span>
            </label>
            <label>Nhập lại mật khẩu mới
                <span class="customer-password-field">
                    <input id="customer-confirm-new-password" type="password" name="confirmPassword" minlength="6" autocomplete="new-password" required />
                    <button type="button" class="customer-password-toggle" data-password-toggle aria-controls="customer-confirm-new-password" aria-pressed="false">Hiện</button>
                </span>
            </label>
            <button type="submit">Đổi mật khẩu</button>
        </form>

        <div class="customer-account-links">
            <a href="${pageContext.request.contextPath}/account/profile">Quay lại hồ sơ</a>
        </div>
    </section>
</main>

<jsp:include page="../layouts/footer.jsp" />
