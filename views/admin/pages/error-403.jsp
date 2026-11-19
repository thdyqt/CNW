<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<div class="section-10">
    <div style="font-size: 80px; color: #ef3826; margin-bottom: 16px;">
        <i class="fa-solid fa-ban"></i>
    </div>
    <h1 class="inner-title" style="color: #ef3826;">Truy cập bị từ chối (403)</h1>
    <p class="inner-desc" style="font-size: 16px; color: #666; margin-bottom: 24px;">
        Tài khoản của bạn không có quyền truy cập vào chức năng hoặc trang này.
    </p>
    <a class="inner-back" href="${pageContext.request.contextPath}/${pathAdmin}/dashboard">
        <i class="fa-solid fa-arrow-left"></i> Quay lại trang được cấp quyền
    </a>
</div>

<jsp:include page="../layouts/footer.jsp" />
