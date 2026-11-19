<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<div class="section-10">
    <img class="inner-image" src="${pageContext.request.contextPath}/public/admin/assets/images/Error404.jpg" alt="Error404" />
    <h1 class="inner-title">Trang này không tồn tại...</h1>
    <a class="inner-back" href="${pageContext.request.contextPath}/${pathAdmin}/dashboard">Trở về trang tổng quan</a>
</div>

<jsp:include page="../layouts/footer.jsp" />
