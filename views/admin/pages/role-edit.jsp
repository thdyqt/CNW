<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Chỉnh sửa nhóm quyền</h1>

<div class="section-8">
    <form method="POST" action="${pageContext.request.contextPath}/${pathAdmin}/setting/role/list">
        <div class="inner-group">
            <label class="inner-label" for="name">Tên nhóm quyền</label>
            <input type="text" id="name" name="name" value="Quản trị viên cấp cao" required />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="description">Mô tả</label>
            <textarea id="description" name="description">Toàn quyền quản trị hệ thống.</textarea>
        </div>
        <div class="inner-button inner-two-col">
            <button type="submit">Cập nhật</button>
        </div>
    </form>
    <div class="inner-back">
        <a href="${pageContext.request.contextPath}/${pathAdmin}/setting/role/list">Quay lại danh sách</a>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
