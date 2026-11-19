<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Đơn hàng: ${currentOrder.code}</h1>

<div class="section-8">
    <form id="order-edit-form">
        <div class="inner-group" style="display: none;">
            <input type="text" id="id" name="id" value="${currentOrder.id}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="fullName">Tên khách hàng</label>
            <input type="text" id="fullName" name="fullName" value="${currentOrder.fullName}" />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="phone">Số điện thoại</label>
            <input type="text" id="phone" name="phone" value="${currentOrder.phone}" />
        </div>
        <div class="inner-group inner-two-col">
            <label class="inner-label" for="note">Ghi chú</label>
            <textarea id="note" name="note">${currentOrder.note}</textarea>
        </div>
        <div class="inner-group">
            <label class="inner-label" for="paymentMethod">Phương thức thanh toán</label>
            <select id="paymentMethod" name="paymentMethod">
                <option value="money" ${currentOrder.paymentMethod == 'money' ? 'selected' : ''}>Tiền mặt</option>
                <option value="vnpay" ${currentOrder.paymentMethod == 'vnpay' ? 'selected' : ''}>VNPay</option>
                <option value="bank" ${currentOrder.paymentMethod == 'bank' || currentOrder.paymentMethod == 'banking' ? 'selected' : ''}>Tài khoản ngân hàng</option>
            </select>
        </div>
        <div class="inner-group">
            <label class="inner-label" for="paymentStatus">Trạng thái thanh toán</label>
            <select id="paymentStatus" name="paymentStatus">
                <option value="unpaid" ${currentOrder.paymentStatus == 'unpaid' ? 'selected' : ''}>Chưa thanh toán</option>
                <option value="paid" ${currentOrder.paymentStatus == 'paid' ? 'selected' : ''}>Đã thanh toán</option>
            </select>
        </div>
        <div class="inner-group">
            <label class="inner-label" for="createdAt">Ngày đặt</label>
            <input id="createdAt" name="createdAt" value="${currentOrder.createdAtFormatted}" readonly />
        </div>
        <div class="inner-group">
            <label class="inner-label" for="status">Trạng thái</label>
            <select id="status" name="status">
                <option value="initial" ${currentOrder.status == 'initial' ? 'selected' : ''}>Khởi tạo</option>
                <option value="done" ${currentOrder.status == 'done' ? 'selected' : ''}>Hoàn thành</option>
                <option value="cancel" ${currentOrder.status == 'cancel' ? 'selected' : ''}>Hủy</option>
            </select>
        </div>
        <div class="inner-group">
            <label class="inner-label">Danh sách tour</label>
            <div class="inner-list">
                <c:forEach var="item" items="${currentOrder.items}">
                    <div class="inner-item">
                        <div class="inner-image">
                            <img src="${item.avatar}" alt="${item.name}" />
                        </div>
                        <div class="inner-content">
                            <div class="inner-order-name">${item.name}</div>
                            <c:if test="${item.quantityAdult > 0}">
                                <div class="inner-order-text">Người lớn: ${item.quantityAdult} x ${item.priceNewAdultFormatted}đ</div>
                            </c:if>
                            <c:if test="${item.quantityChildren > 0}">
                                <div class="inner-order-text">Trẻ em: ${item.quantityChildren} x ${item.priceNewChildrenFormatted}đ</div>
                            </c:if>
                            <c:if test="${item.quantityBaby > 0}">
                                <div class="inner-order-text">Em bé: ${item.quantityBaby} x ${item.priceNewBabyFormatted}đ</div>
                            </c:if>
                            <div class="inner-order-text">Ngày khởi hành: ${item.departureDateFormatted}</div>
                            <div class="inner-order-text">Khởi hành tại: ${item.locationFromName}</div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="inner-group">
            <label class="inner-label">Thanh toán</label>
            <div class="inner-order-total">
                <div>Tổng tiền: ${currentOrder.subTotalFormatted}đ</div>
                <div>Giảm: 0đ</div>
                <div>Mã giảm: </div>
                <div>Thanh toán: <span class="inner-order-price">${currentOrder.totalFormatted}đ</span></div>
            </div>
        </div>
        <div class="inner-button inner-two-col">
            <button type="submit">Cập nhật</button>
        </div>
    </form>
    <div class="inner-back">
        <a href="${pageContext.request.contextPath}/${pathAdmin}/order/list">Quay lại danh sách</a>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
