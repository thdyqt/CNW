<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Chỉnh sửa mã giảm giá: ${coupon.code}</h1>

<c:if test="${not empty error}">
    <div style="background: #fef2f2; border: 1.5px solid #ef4444; color: #b91c1c; padding: 12px 16px; border-radius: 8px; margin-bottom: 20px; font-weight: 600;">
        <i class="fa-solid fa-circle-exclamation" style="margin-right: 8px;"></i>
        ${error}
    </div>
</c:if>

<div class="section-8">
    <form id="coupon-edit-form" method="POST" action="${pageContext.request.contextPath}/${pathAdmin}/coupon/edit/${coupon.id}">
        <input type="hidden" name="id" value="${coupon.id}" />

        <div class="inner-group">
            <label class="inner-label" for="code">Mã giảm giá (Coupon Code) <span style="color:red;">*</span></label>
            <input type="text" id="code" name="code" value="${coupon.code}" style="text-transform: uppercase; font-family: monospace; font-weight: 700; letter-spacing: 1px;" required autocomplete="off" />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="title">Tiêu đề chương trình ưu đãi <span style="color:red;">*</span></label>
            <input type="text" id="title" name="title" value="${coupon.title}" required />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="type">Loại giảm giá</label>
            <select id="type" name="type" onchange="toggleCouponTypeFields(this.value)">
                <option value="percentage" ${coupon.type == 'percentage' ? 'selected' : ''}>Theo phần trăm (%)</option>
                <option value="fixed" ${coupon.type == 'fixed' ? 'selected' : ''}>Số tiền cố định (VNĐ)</option>
            </select>
        </div>

        <div class="inner-group">
            <label class="inner-label" for="value" id="label-value">
                <c:choose>
                    <c:when test="${coupon.type == 'fixed'}">Số tiền giảm (VNĐ) <span style="color:red;">*</span></c:when>
                    <c:otherwise>Mức giảm (%) <span style="color:red;">*</span></c:otherwise>
                </c:choose>
            </label>
            <input type="number" id="value" name="value" min="1" step="any" value="<fmt:formatNumber value='${coupon.value}' pattern='#' />" required />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="minOrderValue">Đơn hàng tối thiểu (VNĐ)</label>
            <input type="number" id="minOrderValue" name="minOrderValue" min="0" step="1000" value="<fmt:formatNumber value='${coupon.minOrderValue}' pattern='#' />" />
        </div>

        <div class="inner-group" id="group-max-discount" style="${coupon.type == 'fixed' ? 'opacity: 0.5;' : ''}">
            <label class="inner-label" for="maxDiscountValue">Giảm tối đa (VNĐ)</label>
            <input type="number" id="maxDiscountValue" name="maxDiscountValue" min="0" step="1000" value="<fmt:formatNumber value='${coupon.maxDiscountValue}' pattern='#' />" />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="usageLimit">Giới hạn số lượt sử dụng (Đã dùng: ${coupon.usedCount})</label>
            <input type="number" id="usageLimit" name="usageLimit" min="0" value="${coupon.usageLimit}" />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="status">Trạng thái</label>
            <select id="status" name="status">
                <option value="active" ${coupon.status == 'active' ? 'selected' : ''}>Hoạt động</option>
                <option value="inactive" ${coupon.status == 'inactive' ? 'selected' : ''}>Tạm khóa</option>
            </select>
        </div>

        <div class="inner-group">
            <label class="inner-label" for="startDate">Ngày bắt đầu áp dụng</label>
            <input type="date" id="startDate" name="startDate" value="${coupon.startDateInputFormatted}" />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="endDate">Ngày kết thúc (Hạn dùng)</label>
            <input type="date" id="endDate" name="endDate" value="${coupon.endDateInputFormatted}" />
        </div>

        <div class="inner-button inner-two-col">
            <button type="submit">Cập nhật mã giảm giá</button>
        </div>
    </form>

    <div class="inner-back">
        <a href="${pageContext.request.contextPath}/${pathAdmin}/coupon/list">Quay lại danh sách</a>
    </div>
</div>

<script>
function toggleCouponTypeFields(type) {
    const labelValue = document.getElementById('label-value');
    const inputVal = document.getElementById('value');
    const groupMax = document.getElementById('group-max-discount');
    if (type === 'fixed') {
        labelValue.innerHTML = 'Số tiền giảm (VNĐ) <span style="color:red;">*</span>';
        inputVal.placeholder = 'Ví dụ: 500000 (tương ứng 500.000đ)';
        if (groupMax) groupMax.style.opacity = '0.5';
    } else {
        labelValue.innerHTML = 'Mức giảm (%) <span style="color:red;">*</span>';
        inputVal.placeholder = 'Ví dụ: 10 (tương ứng 10%)';
        if (groupMax) groupMax.style.opacity = '1';
    }
}
</script>

<jsp:include page="../layouts/footer.jsp" />
