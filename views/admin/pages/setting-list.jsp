<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Cài đặt chung</h1>

<div class="section-9">
    <a class="inner-item" href="${pageContext.request.contextPath}/${pathAdmin}/setting/website-info">
        <div class="inner-icon">
            <i class="fa-solid fa-info"></i>
        </div>
        <div class="inner-text">Thông tin website</div>
    </a>
    <a class="inner-item" href="${pageContext.request.contextPath}/${pathAdmin}/setting/account-admin/list">
        <div class="inner-icon">
            <i class="fa-solid fa-user-tie"></i>
        </div>
        <div class="inner-text">Tài khoản quản trị</div>
    </a>
    <a class="inner-item" href="${pageContext.request.contextPath}/${pathAdmin}/setting/role/list">
        <div class="inner-icon">
            <i class="fa-solid fa-user-gear"></i>
        </div>
        <div class="inner-text">Nhóm quyền</div>
    </a>
</div>

<jsp:include page="../layouts/footer.jsp" />
