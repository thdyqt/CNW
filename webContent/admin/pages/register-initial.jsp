<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/account-header.jsp" />

<div class="form-account">
    <h2 class="inner-title">Tài khoản đã được khởi tạo</h2>
    <p class="inner-desc">Vui lòng chờ phê duyệt của quản trị viên</p>

    <div class="inner-more">
        <span>Đã được phê duyệt?</span>
        <a href="${pageContext.request.contextPath}/${pathAdmin}/account/login">Đăng nhập</a>
    </div>
</div>

<jsp:include page="../layouts/account-footer.jsp" />
