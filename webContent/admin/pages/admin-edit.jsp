<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Chỉnh sửa tài khoản quản trị</h1>

<div class="section-8">
    <form id="admin-edit-form">
        <div class="inner-group" style="display: none;">
            <input type="text" id="id" name="id" value="${accountAdmin.id}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="fullName">Họ tên</label>
            <input type="text" id="fullName" name="fullName" value="${accountAdmin.fullName}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="email">Email</label>
            <input type="email" id="email" name="email" value="${accountAdmin.email}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="phone">Số điện thoại</label>
            <input type="text" id="phone" name="phone" value="${accountAdmin.phone}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="position">Chức vụ</label>
            <input type="text" id="position" name="position" value="${accountAdmin.position}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="role">Nhóm quyền</label>
            <select id="role" name="role">
                <c:forEach var="role" items="${roleList}">
                    <option value="${role.id}" ${accountAdmin.roleId == role.id ? 'selected' : ''}>${role.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="inner-group">
            <label class="inner-label" for="status">Trạng thái</label>
            <select id="status" name="status">
                <option value="initial" ${accountAdmin.status == 'initial' ? 'selected' : ''}>Khởi tạo</option>
                <option value="active" ${accountAdmin.status == 'active' ? 'selected' : ''}>Hoạt động</option>
                <option value="inactive" ${accountAdmin.status == 'inactive' ? 'selected' : ''}>Tạm dừng</option>
            </select>
        </div>
        <div class="inner-group inner-two-col">
            <label class="inner-label" for="password">Mật khẩu mới (để trống nếu không đổi)</label>
            <input type="password" id="password" name="password" placeholder="Nhập mật khẩu mới nếu muốn thay đổi" />
        </div>
        <div class="inner-group inner-two-col">
            <label class="inner-label" for="avatar">Ảnh đại diện</label>
            <div class="inner-input-image" image-default="${accountAdmin.avatar}">
                <input type="file" id="avatar" name="avatar" accept="image/*" filepond-image />
            </div>
        </div>
        <div class="inner-button inner-two-col">
            <button type="submit">Cập nhật</button>
        </div>
    </form>
    <div class="inner-back">
        <a href="${pageContext.request.contextPath}/${pathAdmin}/setting/account-admin/list">Quay lại danh sách</a>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
