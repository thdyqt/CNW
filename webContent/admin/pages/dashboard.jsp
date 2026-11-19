<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Tổng quan</h1>

<!-- Section-1: Thống kê số liệu -->
<div class="section-1">
    <div class="inner-item">
        <div class="inner-icon">
            <img src="${pageContext.request.contextPath}/public/admin/assets/images/Icon-1.svg" alt="Users" />
        </div>
        <div class="inner-content">
            <div class="inner-title">Tài khoản người dùng</div>
            <div class="inner-number"><fmt:formatNumber value="${overview.totalUser != null ? overview.totalUser : 0}" pattern="#,##0" /></div>
        </div>
    </div>
    <div class="inner-item">
        <div class="inner-icon">
            <img src="${pageContext.request.contextPath}/public/admin/assets/images/Icon-1.svg" alt="Admins" />
        </div>
        <div class="inner-content">
            <div class="inner-title">Tài khoản quản trị</div>
            <div class="inner-number"><fmt:formatNumber value="${overview.totalAdmin != null ? overview.totalAdmin : 0}" pattern="#,##0" /></div>
        </div>
    </div>
    <div class="inner-item">
        <div class="inner-icon">
            <img src="${pageContext.request.contextPath}/public/admin/assets/images/Icon-2.svg" alt="Orders" />
        </div>
        <div class="inner-content">
            <div class="inner-title">Đơn hàng tháng này</div>
            <div class="inner-number"><fmt:formatNumber value="${overview.totalOrder != null ? overview.totalOrder : 0}" pattern="#,##0" /></div>
        </div>
    </div>
    <div class="inner-item">
        <div class="inner-icon">
            <img src="${pageContext.request.contextPath}/public/admin/assets/images/Icon-3.svg" alt="Revenue" />
        </div>
        <div class="inner-content">
            <div class="inner-title">Doanh thu tháng này</div>
            <div class="inner-number"><fmt:formatNumber value="${overview.totalRevenue != null ? overview.totalRevenue : 0}" pattern="#,##0" />đ</div>
        </div>
    </div>
</div>

<!-- Section-2: Biểu đồ doanh thu -->
<div class="section-2">
    <div class="inner-head">
        <h2 class="inner-title">Biểu đồ doanh thu</h2>
        <div class="inner-filler">
            <div class="vn-month-picker" id="vn-month-picker">
                <button type="button" class="vn-picker-btn" id="vn-picker-btn" aria-label="Chọn tháng và năm">
                    <i class="fa-regular fa-calendar-days"></i>
                    <span id="vn-picker-label">Tháng 11/2026</span>
                    <i class="fa-solid fa-chevron-down vn-picker-arrow"></i>
                </button>
                <input type="hidden" name="month" id="revenue-month-input" value="2026-11" />
                <div class="vn-picker-dropdown" id="vn-picker-dropdown">
                    <div class="vn-picker-header">
                        <button type="button" class="vn-picker-nav" id="vn-prev-year" title="Năm trước">
                            <i class="fa-solid fa-chevron-left"></i>
                        </button>
                        <span class="vn-picker-year" id="vn-picker-year">2026</span>
                        <button type="button" class="vn-picker-nav" id="vn-next-year" title="Năm sau">
                            <i class="fa-solid fa-chevron-right"></i>
                        </button>
                    </div>
                    <div class="vn-picker-months" id="vn-picker-months"></div>
                    <div class="vn-picker-footer">
                        <button type="button" class="vn-footer-btn vn-btn-clear" id="vn-picker-clear">Xóa</button>
                        <button type="button" class="vn-footer-btn vn-btn-today" id="vn-picker-today">Tháng này</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="inner-chart">
        <canvas id="revenue-chart"></canvas>
    </div>
</div>

<!-- Section-3: Đơn hàng mới -->
<div class="section-3">
    <h2 class="inner-title">Đơn hàng mới</h2>
    <div class="table-1">
        <table>
            <thead>
                <tr>
                    <th>Mã</th>
                    <th>Thông tin khách</th>
                    <th>Danh sách tour</th>
                    <th>Thanh toán</th>
                    <th>Trạng thái</th>
                    <th class="inner-text-right">Ngày đặt</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty recentOrders}">
                        <c:forEach var="order" items="${recentOrders}">
                            <tr>
                                <td class="inner-code">${order.code}</td>
                                <td>
                                    <div>Họ tên: ${order.fullName}</div>
                                    <div>SĐT: ${order.phone}</div>
                                    <div>Ghi chú: ${order.note}</div>
                                </td>
                                <td>
                                    <div class="inner-list">
                                        <c:forEach var="item" items="${order.items}">
                                            <div class="inner-item">
                                                <div class="inner-image">
                                                    <img src="${item.avatar}" alt="${item.name}" />
                                                </div>
                                                <div class="inner-content">
                                                    <div class="inner-name">${item.name}</div>
                                                    <c:if test="${item.quantityAdult > 0}">
                                                        <div class="inner-quantity">Người lớn: ${item.quantityAdult} x <fmt:formatNumber value="${item.priceNewAdult}" pattern="#,###" />đ</div>
                                                    </c:if>
                                                    <c:if test="${item.quantityChildren > 0}">
                                                        <div class="inner-quantity">Trẻ em: ${item.quantityChildren} x <fmt:formatNumber value="${item.priceNewChildren}" pattern="#,###" />đ</div>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </td>
                                <td>
                                    <div>Tổng tiền: <fmt:formatNumber value="${order.total}" pattern="#,###" />đ</div>
                                    <div>PTTT: ${order.paymentMethodName}</div>
                                    <div>TTTT: ${order.paymentStatusName}</div>
                                </td>
                                <td>
                                    <span class="badge ${order.status == 'done' ? 'badge-green' : (order.status == 'cancel' ? 'badge-red' : 'badge-orange')}">
                                        ${order.statusName}
                                    </span>
                                </td>
                                <td class="inner-text-right">
                                    <div>${order.createdAtFormatted}</div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td class="inner-code">OD0000000001</td>
                            <td>
                                <div>Họ tên: Lê Văn A</div>
                                <div>SĐT: 0912345678</div>
                                <div>Ghi chú: Phòng đôi view biển</div>
                            </td>
                            <td>
                                <div class="inner-list">
                                    <div class="inner-item">
                                        <div class="inner-image">
                                            <img src="${pageContext.request.contextPath}/public/admin/assets/images/Tour-Icon.jpg" alt="Tour" />
                                        </div>
                                        <div class="inner-content">
                                            <div class="inner-name">Tour Đà Nẵng - Hội An 4N3Đ</div>
                                            <div class="inner-quantity">Người lớn: 2 x 3.890.000đ</div>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <div>Tổng tiền: 7.780.000đ</div>
                                <div>PTTT: Tiền mặt</div>
                                <div>TTTT: Chưa thanh toán</div>
                            </td>
                            <td>
                                <span class="badge badge-orange">Khởi tạo</span>
                            </td>
                            <td class="inner-text-right">
                                <div>16:20</div>
                                <div>15/11/2026</div>
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
