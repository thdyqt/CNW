<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Thông tin cá nhân</h1>

<div class="section-8">
    <form id="profile-edit-form">
        <div class="inner-group" style="display: none;">
            <input type="text" id="id" name="id" value="${currentAccountAdmin.id}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="fullName">Họ tên</label>
            <input type="text" id="fullName" name="fullName" value="${currentAccountAdmin.fullName}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="email">Email</label>
            <input type="email" id="email" name="email" value="${currentAccountAdmin.email}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="phone">Số điện thoại</label>
            <input type="text" id="phone" name="phone" value="${currentAccountAdmin.phone}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="position">Chức vụ</label>
            <input type="text" id="position" name="position" value="${currentAccountAdmin.position}" readonly />
        </div>
        <div class="inner-group inner-two-col">
            <label class="inner-label" for="role">Nhóm quyền</label>
            <input type="text" id="role" name="role" value="${currentAccountAdmin.roleName}" readonly />
        </div>
        <div class="inner-group inner-two-col">
            <label class="inner-label" for="avatar">Ảnh đại diện</label>
            <div class="inner-input-image" image-default="${currentAccountAdmin.avatar}">
                <input type="file" id="avatar" name="avatar" accept="image/*" filepond-image />
            </div>
        </div>
        <div class="inner-button inner-two-col">
            <button type="submit">Cập nhật</button>
        </div>
    </form>
    <div class="inner-back">
        <a href="${pageContext.request.contextPath}/${pathAdmin}/profile/change-password">Đổi mật khẩu</a>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
