<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Thêm tài khoản quản trị</h1>

<div class="section-8">
    <form id="admin-create-form">
        <div class="inner-group">
            <label class="inner-label" for="fullName">Họ tên</label>
            <input type="text" id="fullName" name="fullName" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="email">Email</label>
            <input type="email" id="email" name="email" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="phone">Số điện thoại</label>
            <input type="text" id="phone" name="phone" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="position">Chức vụ</label>
            <input type="text" id="position" name="position" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="role">Nhóm quyền</label>
            <select id="role" name="role">
                <c:forEach var="role" items="${roleList}">
                    <option value="${role.id}">${role.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="inner-group">
            <label class="inner-label" for="status">Trạng thái</label>
            <select id="status" name="status">
                <option value="active">Hoạt động</option>
                <option value="inactive">Dừng hoạt động</option>
            </select>
        </div>
        <div class="inner-group inner-two-col">
            <label class="inner-label" for="password">Mật khẩu</label>
            <input type="password" id="password" name="password" />
        </div>
        <div class="inner-group inner-two-col">
            <label class="inner-label" for="avatar">Ảnh đại diện</label>
            <div class="inner-input-image">
                <input type="file" id="avatar" name="avatar" accept="image/*" filepond-image />
            </div>
        </div>
        <div class="inner-button inner-two-col">
            <button type="submit">Tạo mới</button>
        </div>
    </form>
    <div class="inner-back">
        <a href="${pageContext.request.contextPath}/${pathAdmin}/setting/account-admin/list">Quay lại danh sách</a>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
