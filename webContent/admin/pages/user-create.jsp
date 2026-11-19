<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Tạo mới tài khoản người dùng</h1>

<div class="section-8">
    <form id="user-create-form" method="POST" action="${pageContext.request.contextPath}/${pathAdmin}/user/create" enctype="multipart/form-data">
        <div class="inner-group">
            <label class="inner-label" for="fullName">Họ tên <span style="color: red;">*</span></label>
            <input type="text" id="fullName" name="fullName" placeholder="Nhập họ và tên đầy đủ..." required />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="email">Email <span style="color: red;">*</span></label>
            <input type="email" id="email" name="email" placeholder="example@gmail.com" required />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="phone">Số điện thoại</label>
            <input type="text" id="phone" name="phone" placeholder="0901234567" />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="address">Địa chỉ</label>
            <input type="text" id="address" name="address" placeholder="Địa chỉ thường trú..." />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="password">Mật khẩu <span style="color: red;">*</span></label>
            <input type="password" id="password" name="password" placeholder="Tối thiểu 6 ký tự..." required minlength="6" />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="status">Trạng thái</label>
            <select id="status" name="status">
                <option value="active" selected>Hoạt động</option>
                <option value="inactive">Tạm dừng</option>
            </select>
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
        <a href="${pageContext.request.contextPath}/${pathAdmin}/user/list">Quay lại danh sách</a>
    </div>
</div>

<script>
    const userCreateForm = document.querySelector("#user-create-form");
    if (userCreateForm) {
        userCreateForm.addEventListener("submit", function(e) {
            e.preventDefault();

            const fullName = document.querySelector("#fullName").value.trim();
            const email = document.querySelector("#email").value.trim();
            const password = document.querySelector("#password").value;

            if (!fullName || !email) {
                if (typeof showAlert === "function") {
                    showAlert("Họ tên và email không được để trống!", "error");
                } else {
                    alert("Họ tên và email không được để trống!");
                }
                return;
            }

            if (!password || password.length < 6) {
                if (typeof showAlert === "function") {
                    showAlert("Mật khẩu phải có ít nhất 6 ký tự!", "error");
                } else {
                    alert("Mật khẩu phải có ít nhất 6 ký tự!");
                }
                return;
            }

            const formData = new FormData(userCreateForm);

            // Handle filepond avatar if available
            if (typeof filePond !== "undefined" && filePond.avatar) {
                const files = filePond.avatar.getFiles();
                if (files.length > 0) {
                    formData.set("avatar", files[0].file);
                }
            }

            fetch(userCreateForm.action, {
                method: "POST",
                body: formData
            })
            .then(res => res.json())
            .then(data => {
                if (data.code === "success") {
                    if (typeof setFlashAlert === "function") {
                        setFlashAlert(data.message || "Tạo mới người dùng thành công!", "success");
                    }
                    window.location.href = "${pageContext.request.contextPath}/${pathAdmin}/user/list";
                } else {
                    if (typeof showAlert === "function") {
                        showAlert(data.message || "Tạo mới người dùng thất bại!", "error");
                    } else {
                        alert(data.message || "Tạo mới người dùng thất bại!");
                    }
                }
            })
            .catch(err => {
                if (typeof showAlert === "function") {
                    showAlert("Có lỗi xảy ra, vui lòng thử lại!", "error");
                } else {
                    alert("Có lỗi xảy ra, vui lòng thử lại!");
                }
            });
        });
    }
</script>

<jsp:include page="../layouts/footer.jsp" />
