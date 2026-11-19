<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Đổi mật khẩu</h1>

<div class="section-8">
    <form id="profile-change-password-form">
        <div class="inner-group">
            <label class="inner-label" for="password">Mật khẩu mới</label>
            <input type="password" id="password" name="password" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="confirmPassword">Nhập lại mật khẩu mới</label>
            <input type="password" id="confirmPassword" name="confirmPassword" />
        </div>
        <div class="inner-button inner-two-col">
            <button type="submit">Đổi mật khẩu</button>
        </div>
    </form>
    <div class="inner-back">
        <a href="${pageContext.request.contextPath}/${pathAdmin}/profile/edit">Quay lại thông tin cá nhân</a>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
