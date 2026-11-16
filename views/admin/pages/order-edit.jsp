<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Chi tiết đơn hàng: ${order.code}</h1>

<div class="section-8">
    <form method="POST" action="${pageContext.request.contextPath}/${pathAdmin}/order/edit/${order.id}">
        <div class="inner-group">
            <label class="inner-label">Khách hàng</label>
            <input type="text" value="${order.fullName}" readonly />
        </div>
        <div class="inner-group">
            <label class="inner-label">Số điện thoại</label>
            <input type="text" value="${order.phone}" readonly />
        </div>
        <div class="inner-group">
            <label class="inner-label">Tổng tiền</label>
            <input type="text" value="<fmt:formatNumber value="${order.total}" pattern="#,###" />đ" readonly />
        </div>
        <div class="inner-group">
            <label class="inner-label">Trạng thái thanh toán</label>
            <select name="paymentStatus">
                <option value="unpaid" ${order.paymentStatus == 'unpaid' ? 'selected' : ''}>Chưa thanh toán</option>
                <option value="paid" ${order.paymentStatus == 'paid' ? 'selected' : ''}>Đã thanh toán</option>
            </select>
        </div>
        <div class="inner-group">
            <label class="inner-label">Trạng thái đơn hàng</label>
            <select name="status">
                <option value="initial" ${order.status == 'initial' ? 'selected' : ''}>Khởi tạo</option>
                <option value="done" ${order.status == 'done' ? 'selected' : ''}>Hoàn thành</option>
                <option value="cancel" ${order.status == 'cancel' ? 'selected' : ''}>Hủy</option>
            </select>
        </div>
        <div class="inner-button inner-two-col">
            <button type="submit">Lưu thay đổi</button>
        </div>
    </form>
    <div class="inner-back">
        <a href="${pageContext.request.contextPath}/${pathAdmin}/order/list">Quay lại danh sách</a>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
