<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />
<c:set var="loggedInAdmin" value="${not empty sessionScope.account ? sessionScope.account : currentAccountAdmin}" />

<h1 class="box-title">Cài đặt chung</h1>

<div class="section-9">
    <c:if test="${loggedInAdmin.hasPermission('setting-view') or loggedInAdmin.hasPermission('setting-edit')}">
        <a class="inner-item" href="${pageContext.request.contextPath}/${pathAdmin}/setting/website-info">
            <div class="inner-icon">
                <i class="fa-solid fa-info"></i>
            </div>
            <div class="inner-text">Thông tin website</div>
        </a>
    </c:if>
    <c:if test="${loggedInAdmin.hasPermission('account-view')}">
        <a class="inner-item" href="${pageContext.request.contextPath}/${pathAdmin}/setting/account-admin/list">
            <div class="inner-icon">
                <i class="fa-solid fa-user-tie"></i>
            </div>
            <div class="inner-text">Tài khoản quản trị</div>
        </a>
    </c:if>
    <c:if test="${loggedInAdmin.hasPermission('role-view')}">
        <a class="inner-item" href="${pageContext.request.contextPath}/${pathAdmin}/setting/role/list">
            <div class="inner-icon">
                <i class="fa-solid fa-user-gear"></i>
            </div>
            <div class="inner-text">Nhóm quyền</div>
        </a>
    </c:if>
</div>

<jsp:include page="../layouts/footer.jsp" />
