<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="currentUri" value="${not empty requestScope['javax.servlet.forward.request_uri'] ? requestScope['javax.servlet.forward.request_uri'] : pageContext.request.requestURI}" />
<c:set var="loggedInAdmin" value="${not empty sessionScope.account ? sessionScope.account : currentAccountAdmin}" />

<nav class="sider">
    <ul class="inner-menu">
        <c:if test="${empty loggedInAdmin || empty loggedInAdmin.permissions || fn:contains(loggedInAdmin.permissions, 'dashboard-view')}">
            <li>
                <a href="${pageContext.request.contextPath}/${pathAdmin}/dashboard" class="${fn:contains(currentUri, 'dashboard') ? 'active' : ''}">
                    <i class="fa-solid fa-gauge-high"></i>
                    Tổng quan
                </a>
            </li>
        </c:if>
        <c:if test="${empty loggedInAdmin || empty loggedInAdmin.permissions || fn:contains(loggedInAdmin.permissions, 'category-view')}">
            <li>
                <a href="${pageContext.request.contextPath}/${pathAdmin}/category/list" class="${fn:contains(currentUri, 'category') ? 'active' : ''}">
                    <i class="fa-solid fa-table-cells-large"></i>
                    Quản lý danh mục
                </a>
            </li>
        </c:if>
        <c:if test="${empty loggedInAdmin || empty loggedInAdmin.permissions || fn:contains(loggedInAdmin.permissions, 'tour-view')}">
            <li>
                <a href="${pageContext.request.contextPath}/${pathAdmin}/tour/list" class="${fn:contains(currentUri, 'tour') ? 'active' : ''}">
                    <i class="fa-solid fa-table-cells-large"></i>
                    Quản lý tour
                </a>
            </li>
        </c:if>
        <li>
            <a href="${pageContext.request.contextPath}/${pathAdmin}/order/list" class="${fn:contains(currentUri, 'order') ? 'active' : ''}">
                <i class="fa-solid fa-list-check"></i>
                Quản lý đơn hàng
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/${pathAdmin}/user/list" class="${fn:contains(currentUri, 'user') || fn:contains(currentUri, 'customer') ? 'active' : ''}">
                <i class="fa-solid fa-user"></i>
                Quản lý người dùng
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/${pathAdmin}/contact/list" class="${fn:contains(currentUri, 'contact') ? 'active' : ''}">
                <i class="fa-solid fa-user-group"></i>
                Thông tin liên hệ
            </a>
        </li>
    </ul>
    <hr />
    <ul class="inner-menu">
        <li>
            <a href="${pageContext.request.contextPath}/${pathAdmin}/setting/list" class="${fn:contains(currentUri, 'setting') ? 'active' : ''}">
                <i class="fa-solid fa-gear"></i>
                Cài đặt chung
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/${pathAdmin}/profile/edit" class="${fn:contains(currentUri, 'profile') ? 'active' : ''}">
                <i class="fa-solid fa-user-gear"></i>
                Thông tin cá nhân
            </a>
        </li>
        <li>
            <a class="inner-logout" href="${pageContext.request.contextPath}/${pathAdmin}/account/login">
                <i class="fa-solid fa-power-off"></i>
                Đăng xuất
            </a>
        </li>
    </ul>
</nav>
<div class="sider-overlay"></div>
