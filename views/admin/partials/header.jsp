<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="loggedInAdmin" value="${not empty sessionScope.account ? sessionScope.account : currentAccountAdmin}" />

<header class="header">
    <div class="inner-logo">
        <a href="${pageContext.request.contextPath}/${pathAdmin}/dashboard">
            <span>DUT</span>
            <span>Admin</span>
        </a>
    </div>
    <div class="inner-wrap">
        <div class="inner-notify">
            <img src="${pageContext.request.contextPath}/admin/assets/images/BellIcon.svg" alt="Bell" />
            <span>0</span>
        </div>
        <div class="inner-account">
            <div class="inner-avatar">
                <img src="${not empty loggedInAdmin.avatar ? loggedInAdmin.avatar : pageContext.request.contextPath.concat('/admin/assets/images/AvatarIcon.jpg')}" alt="Avatar" />
            </div>
            <div class="inner-text">
                <div class="inner-name">${not empty loggedInAdmin.fullName ? loggedInAdmin.fullName : 'Phan Thanh Duy'}</div>
                <div class="inner-role">${not empty loggedInAdmin.roleName ? loggedInAdmin.roleName : 'Quản trị viên'}</div>
            </div>
        </div>
        <button class="inner-button-menu" type="button">
            <i class="fa-solid fa-bars"></i>
        </button>
    </div>
</header>
