<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Tạo bài viết mới</h1>

<div class="section-8">
    <form id="news-create-form" method="POST" action="${pageContext.request.contextPath}/${pathAdmin}/news/create">
        <div class="inner-group">
            <label class="inner-label" for="title">Tiêu đề bài viết <span style="color:red;">*</span></label>
            <input type="text" id="title" name="title" placeholder="Nhập tiêu đề bài viết..." required />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="category">Chuyên mục</label>
            <select id="category" name="category">
                <option value="Cẩm nang du lịch">Cẩm nang du lịch</option>
                <option value="Điểm đến hot">Điểm đến hot</option>
                <option value="Nghỉ dưỡng">Nghỉ dưỡng</option>
                <option value="Ẩm thực">Ẩm thực</option>
                <option value="Khám phá">Khám phá</option>
                <option value="Trekking">Trekking</option>
                <option value="Mẹo hay">Mẹo hay</option>
            </select>
        </div>

        <div class="inner-group">
            <label class="inner-label" for="author">Tác giả</label>
            <input type="text" id="author" name="author" value="${not empty sessionScope.account ? sessionScope.account.fullName : 'DUT Travel Editor'}" />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="status">Trạng thái</label>
            <select id="status" name="status">
                <option value="active" selected>Hoạt động</option>
                <option value="inactive">Tạm dừng</option>
            </select>
        </div>

        <div class="inner-group">
            <label class="inner-label" for="slug">Đường dẫn thân thiện (Slug)</label>
            <input type="text" id="slug" name="slug" placeholder="Để trống hệ thống sẽ tự sinh từ tiêu đề" />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="featured">Bài viết tiêu điểm</label>
            <div style="display: flex; align-items: center; gap: 8px; margin-top: 10px;">
                <input type="checkbox" id="featured" name="featured" value="1" style="width: 18px; height: 18px; cursor: pointer;" />
                <label for="featured" style="cursor: pointer; font-size: 14px; font-weight: 500;">Đặt làm bài viết tiêu điểm (Hiển thị đầu trang)</label>
            </div>
        </div>

        <div class="inner-group">
            <label class="inner-label" for="avatar">Ảnh đại diện</label>
            <div class="inner-input-image">
                <input type="file" id="avatar" name="avatar" accept="image/*" filepond-image />
            </div>
        </div>

        <div class="inner-group inner-two-col">
            <label class="inner-label" for="description">Tóm tắt ngắn (Mô tả)</label>
            <textarea id="description" name="description" rows="3" placeholder="Nhập đoạn tóm tắt bài viết..."></textarea>
        </div>

        <div class="inner-group inner-two-col">
            <label class="inner-label" for="content">Nội dung chi tiết bài viết</label>
            <textarea id="content" name="content" textarea-mce placeholder="Nhập nội dung bài viết..."></textarea>
        </div>

        <div class="inner-button inner-two-col">
            <button type="submit">Tạo mới</button>
        </div>
    </form>

    <div class="inner-back">
        <a href="${pageContext.request.contextPath}/${pathAdmin}/news/list">Quay lại danh sách</a>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
