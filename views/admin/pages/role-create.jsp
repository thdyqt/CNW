<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Tạo nhóm quyền</h1>

<div class="section-8">
    <form method="POST" action="${pageContext.request.contextPath}/${pathAdmin}/setting/role/create">
        <div class="inner-group">
            <label class="inner-label" for="name">Tên nhóm quyền</label>
            <input type="text" id="name" name="name" required />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="description">Mô tả</label>
            <textarea id="description" name="description"></textarea>
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
