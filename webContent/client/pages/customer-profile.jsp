<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="../layouts/header.jsp" />

<main class="customer-account-page">
    <section class="customer-account-card">
        <h1>Thông tin cá nhân</h1>
        <p>Cập nhật thông tin để đặt tour nhanh hơn.</p>
        <c:if test="${not empty error}"><div class="customer-account-message is-error"><c:out value="${error}" /></div></c:if>
        <c:if test="${not empty success}"><div class="customer-account-message is-success"><c:out value="${success}" /></div></c:if>
        <form method="post" action="${pageContext.request.contextPath}/account/profile" class="customer-account-form">
            <label>Email đăng nhập
                <input type="email" value="${fn:escapeXml(customerUser.email)}" readonly />
            </label>
            <label>Họ và tên
                <input type="text" name="fullName" maxlength="255" value="${fn:escapeXml(customerUser.fullName)}" required />
            </label>
            <label>Số điện thoại
                <input type="tel" name="phone" maxlength="50" value="${fn:escapeXml(customerUser.phone)}" />
            </label>
            <label>Địa chỉ
                <input type="text" name="address" maxlength="500" value="${fn:escapeXml(customerUser.address)}" />
            </label>
            <button type="submit">Lưu thay đổi</button>
        </form>
        <div class="customer-account-links">
            <a href="${pageContext.request.contextPath}/account/password/change">Đổi mật khẩu</a>
            <a href="${pageContext.request.contextPath}/cart">Đi tới giỏ hàng</a>
            <a href="${pageContext.request.contextPath}/account/logout">Đăng xuất</a>
        </div>
    </section>
</main>

<jsp:include page="../layouts/footer.jsp" />
