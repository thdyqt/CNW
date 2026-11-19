<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Tạo nhóm quyền</h1>

<div class="section-8">
    <form id="role-create-form">
        <div class="inner-group">
            <label class="inner-label" for="name">Tên nhóm quyền</label>
            <input type="text" id="name" name="name" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="description">Mô tả ngắn</label>
            <input type="text" id="description" name="description" />
        </div>
        <div class="inner-group">
            <label class="inner-label">Phân quyền</label>
            <div class="inner-checkbox-list">
                <c:forEach var="item" items="${permissionList}">
                    <div class="inner-checkbox-item">
                        <input type="checkbox" id="${item.value}" name="permissions" value="${item.value}" />
                        <label for="${item.value}">${item.label}</label>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="inner-button inner-two-col">
            <button type="submit">Tạo mới</button>
        </div>
    </form>
    <div class="inner-back">
        <a href="${pageContext.request.contextPath}/${pathAdmin}/setting/role/list">Quay lại danh sách</a>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />