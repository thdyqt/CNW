<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Chỉnh sửa bài viết</h1>

<div class="section-8">
    <form id="news-edit-form" method="POST" action="${pageContext.request.contextPath}/${pathAdmin}/news/edit/${article.id}">
        <input type="hidden" name="id" id="id" value="${article.id}" />

        <div class="inner-group">
            <label class="inner-label" for="title">Tiêu đề bài viết <span style="color:red;">*</span></label>
            <input type="text" id="title" name="title" value="${article.title}" required />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="category">Chuyên mục</label>
            <select id="category" name="category">
                <option value="Cẩm nang du lịch" ${article.category == 'Cẩm nang du lịch' ? 'selected' : ''}>Cẩm nang du lịch</option>
                <option value="Điểm đến hot" ${article.category == 'Điểm đến hot' ? 'selected' : ''}>Điểm đến hot</option>
                <option value="Nghỉ dưỡng" ${article.category == 'Nghỉ dưỡng' ? 'selected' : ''}>Nghỉ dưỡng</option>
                <option value="Ẩm thực" ${article.category == 'Ẩm thực' ? 'selected' : ''}>Ẩm thực</option>
                <option value="Khám phá" ${article.category == 'Khám phá' ? 'selected' : ''}>Khám phá</option>
                <option value="Trekking" ${article.category == 'Trekking' ? 'selected' : ''}>Trekking</option>
                <option value="Mẹo hay" ${article.category == 'Mẹo hay' ? 'selected' : ''}>Mẹo hay</option>
            </select>
        </div>

        <div class="inner-group">
            <label class="inner-label" for="author">Tác giả</label>
            <input type="text" id="author" name="author" value="${article.author}" />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="status">Trạng thái</label>
            <select id="status" name="status">
                <option value="active" ${article.status == 'active' ? 'selected' : ''}>Hoạt động</option>
                <option value="inactive" ${article.status == 'inactive' ? 'selected' : ''}>Tạm dừng</option>
            </select>
        </div>

        <div class="inner-group">
            <label class="inner-label" for="slug">Đường dẫn thân thiện (Slug)</label>
            <input type="text" id="slug" name="slug" value="${article.slug}" />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="featured">Bài viết tiêu điểm</label>
            <div style="display: flex; align-items: center; gap: 8px; margin-top: 10px;">
                <input type="checkbox" id="featured" name="featured" value="1" ${article.featured ? 'checked' : ''} style="width: 18px; height: 18px; cursor: pointer;" />
                <label for="featured" style="cursor: pointer; font-size: 14px; font-weight: 500;">Đặt làm bài viết tiêu điểm (Hiển thị đầu trang)</label>
            </div>
        </div>

        <div class="inner-group">
            <label class="inner-label" for="avatar">Ảnh đại diện</label>
            <c:set var="avatarDefaultUrl" value="${not empty article.avatar ? (article.avatar.startsWith('http') ? article.avatar : (article.avatar.startsWith('/') ? pageContext.request.contextPath.concat(article.avatar) : pageContext.request.contextPath.concat('/').concat(article.avatar))) : ''}" />
            <div class="inner-input-image" image-default="${avatarDefaultUrl}">
                <input type="file" id="avatar" name="avatar" accept="image/*" filepond-image />
            </div>
        </div>

        <div class="inner-group inner-two-col">
            <label class="inner-label" for="description">Tóm tắt ngắn (Mô tả)</label>
            <textarea id="description" name="description" rows="3">${article.description}</textarea>
        </div>

        <div class="inner-group inner-two-col">
            <label class="inner-label" for="content">Nội dung chi tiết bài viết</label>
            <textarea id="content" name="content" textarea-mce>${article.content}</textarea>
        </div>

        <div class="inner-button inner-two-col">
            <button type="submit">Cập nhật</button>
        </div>
    </form>

    <div class="inner-back">
        <a href="${pageContext.request.contextPath}/${pathAdmin}/news/list">Quay lại danh sách</a>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
