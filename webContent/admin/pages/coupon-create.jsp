<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Tạo mã giảm giá mới</h1>

<c:if test="${not empty error}">
    <div style="background: #fef2f2; border: 1.5px solid #ef4444; color: #b91c1c; padding: 12px 16px; border-radius: 8px; margin-bottom: 20px; font-weight: 600;">
        <i class="fa-solid fa-circle-exclamation" style="margin-right: 8px;"></i>
        ${error}
    </div>
</c:if>

<div class="section-8">
    <form id="coupon-create-form" method="POST" action="${pageContext.request.contextPath}/${pathAdmin}/coupon/create">
        <div class="inner-group">
            <label class="inner-label" for="code">Mã giảm giá (Coupon Code) <span style="color:red;">*</span></label>
            <input type="text" id="code" name="code" placeholder="Ví dụ: DUT2026, VIP500K..." style="text-transform: uppercase; font-family: monospace; font-weight: 700; letter-spacing: 1px;" required autocomplete="off" />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="title">Tiêu đề chương trình ưu đãi <span style="color:red;">*</span></label>
            <input type="text" id="title" name="title" placeholder="Ví dụ: Ưu đãi chào hè giảm 10% cho mọi tour..." required />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="type">Loại giảm giá</label>
            <select id="type" name="type" onchange="toggleCouponTypeFields(this.value)">
                <option value="percentage" selected>Theo phần trăm (%)</option>
                <option value="fixed">Số tiền cố định (VNĐ)</option>
            </select>
        </div>

        <div class="inner-group">
            <label class="inner-label" for="value" id="label-value">Mức giảm (%) <span style="color:red;">*</span></label>
            <input type="number" id="value" name="value" min="1" step="any" placeholder="Ví dụ: 10 (tương ứng 10%)" required />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="minOrderValue">Đơn hàng tối thiểu (VNĐ)</label>
            <input type="number" id="minOrderValue" name="minOrderValue" min="0" step="1000" placeholder="0 (Không yêu cầu giá trị tối thiểu)" value="0" />
        </div>

        <div class="inner-group" id="group-max-discount">
            <label class="inner-label" for="maxDiscountValue">Giảm tối đa (VNĐ)</label>
            <input type="number" id="maxDiscountValue" name="maxDiscountValue" min="0" step="1000" placeholder="0 (Không giới hạn số tiền giảm tối đa)" value="0" />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="usageLimit">Giới hạn số lượt sử dụng</label>
            <input type="number" id="usageLimit" name="usageLimit" min="0" placeholder="0 (Không giới hạn lượt dùng)" value="0" />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="status">Trạng thái</label>
            <select id="status" name="status">
                <option value="active" selected>Hoạt động</option>
                <option value="inactive">Tạm khóa</option>
            </select>
        </div>

        <div class="inner-group">
            <label class="inner-label" for="startDate">Ngày bắt đầu áp dụng</label>
            <input type="date" id="startDate" name="startDate" />
        </div>

        <div class="inner-group">
            <label class="inner-label" for="endDate">Ngày kết thúc (Hạn dùng)</label>
            <input type="date" id="endDate" name="endDate" />
        </div>

        <div class="inner-button inner-two-col">
            <button type="submit">Tạo mã giảm giá</button>
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
