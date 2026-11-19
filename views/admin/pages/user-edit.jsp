<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Chỉnh sửa thông tin người dùng</h1>

<div class="section-8">
    <form id="user-edit-form" method="POST" action="${pageContext.request.contextPath}/${pathAdmin}/user/edit/${user.id}">
        <input type="hidden" name="id" value="${user.id}" />

        <div class="inner-group">
            <label class="inner-label" for="fullName">Họ tên</label>
            <input type="text" id="fullName" name="fullName" value="${user.fullName}" required />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="email">Email</label>
            <input type="email" id="email" name="email" value="${user.email}" readonly style="background-color: #e9ecef; cursor: not-allowed;" />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="phone">Số điện thoại</label>
            <input type="text" id="phone" name="phone" value="${user.phone}" />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="address">Địa chỉ</label>
            <input type="text" id="address" name="address" value="${user.address}" />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="status">Trạng thái</label>
            <select id="status" name="status">
                <option value="active" ${user.status == 'active' ? 'selected' : ''}>Hoạt động</option>
                <option value="inactive" ${user.status == 'inactive' ? 'selected' : ''}>Tạm dừng</option>
            </select>
        </div>

        <div class="inner-group">
            <label class="inner-label" for="password">Mật khẩu mới (bỏ trống nếu không đổi)</label>
            <input type="password" id="password" name="password" placeholder="Nhập mật khẩu mới..." />
        </div>

        <div class="inner-button inner-two-col">
            <button type="submit">Cập nhật</button>
        </div>
    </form>

    <div class="inner-back">
        <a href="${pageContext.request.contextPath}/${pathAdmin}/user/list">Quay lại danh sách</a>
    </div>
</div>

<script>
    const userEditForm = document.querySelector("#user-edit-form");
    if (userEditForm) {
        userEditForm.addEventListener("submit", function(e) {
            e.preventDefault();
            const formData = new FormData(userEditForm);
            fetch(userEditForm.action, {
                method: "POST",
                body: formData
            })
            .then(res => res.json())
            .then(data => {
                if (data.code === "success") {
                    setFlashAlert(data.message || "Cập nhật thành công!", "success");
                    window.location.href = "${pageContext.request.contextPath}/${pathAdmin}/user/list";
                } else {
                    showAlert(data.message || "Cập nhật thất bại!", "error");
                }
            })
            .catch(() => {
                showAlert("Có lỗi xảy ra, vui lòng thử lại!", "error");
            });
        });
    }
</script>

<jsp:include page="../layouts/footer.jsp" />