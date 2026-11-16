<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Chỉnh sửa danh mục</h1>

<div class="section-8">
    <form id="category-edit-form" method="POST" action="${pageContext.request.contextPath}/${pathAdmin}/category/edit/${category.id}">
        <input type="hidden" name="id" id="id" value="${category.id}" />

        <div class="inner-group">
            <label class="inner-label" for="name">Tên danh mục</label>
            <input type="text" id="name" name="name" value="${category.name}" required />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="parent">Danh mục cha</label>
            <select id="parent" name="parent">
                <option value="">-- Là danh mục gốc --</option>
                <c:set var="parent" value="${category.parentId}" scope="request" />
                <jsp:include page="../mixins/options.jsp" />
            </select>
        </div>

        <div class="inner-group">
            <label class="inner-label" for="position">Vị trí</label>
            <input type="number" id="position" name="position" value="${category.position}" />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="status">Trạng thái</label>
            <select id="status" name="status">
                <option value="active" ${category.status == 'active' ? 'selected' : ''}>Hoạt động</option>
                <option value="inactive" ${category.status == 'inactive' ? 'selected' : ''}>Tạm dừng</option>
            </select>
        </div>

        <div class="inner-group">
            <label class="inner-label" for="avatar">Ảnh đại diện</label>
            <c:set var="avatarDefaultUrl" value="${not empty category.avatar ? (category.avatar.startsWith('http') ? category.avatar : (category.avatar.startsWith('/') ? pageContext.request.contextPath.concat(category.avatar) : pageContext.request.contextPath.concat('/').concat(category.avatar))) : ''}" />
            <div class="inner-input-image" image-default="${avatarDefaultUrl}">
                <input type="file" id="avatar" name="avatar" accept="image/*" filepond-image />
            </div>
        </div>

        <div class="inner-group inner-two-col">
            <label class="inner-label" for="description">Mô tả</label>
            <textarea id="description" name="description" textarea-mce>${category.description}</textarea>
        </div>

        <div class="inner-button inner-two-col">
            <button type="submit">Cập nhật</button>
        </div>
    </form>
    <div class="inner-back">
        <a href="${pageContext.request.contextPath}/${pathAdmin}/category/list">Quay lại danh sách</a>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
