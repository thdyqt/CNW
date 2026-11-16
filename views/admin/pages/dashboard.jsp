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
            <img src="${pageContext.request.contextPath}/admin/assets/images/Icon-1.svg" alt="Users" />
        </div>
        <div class="inner-content">
            <div class="inner-title">Tài khoản người dùng</div>
            <div class="inner-number"><fmt:formatNumber value="${not empty overview.totalUser ? overview.totalUser : 1250}" pattern="#,###" /></div>
        </div>
    </div>
    <div class="inner-item">
        <div class="inner-icon">
            <img src="${pageContext.request.contextPath}/admin/assets/images/Icon-1.svg" alt="Admins" />
        </div>
        <div class="inner-content">
            <div class="inner-title">Tài khoản quản trị</div>
            <div class="inner-number"><fmt:formatNumber value="${not empty overview.totalAdmin ? overview.totalAdmin : 5}" pattern="#,###" /></div>
        </div>
    </div>
    <div class="inner-item">
        <div class="inner-icon">
            <img src="${pageContext.request.contextPath}/admin/assets/images/Icon-2.svg" alt="Orders" />
        </div>
        <div class="inner-content">
            <div class="inner-title">Đơn hàng tháng này</div>
            <div class="inner-number"><fmt:formatNumber value="${not empty overview.totalOrder ? overview.totalOrder : 89}" pattern="#,###" /></div>
        </div>
    </div>
    <div class="inner-item">
        <div class="inner-icon">
            <img src="${pageContext.request.contextPath}/admin/assets/images/Icon-3.svg" alt="Revenue" />
        </div>
        <div class="inner-content">
            <div class="inner-title">Doanh thu tháng này</div>
            <div class="inner-number"><fmt:formatNumber value="${not empty overview.totalRevenue ? overview.totalRevenue : 348500000}" pattern="#,###" />đ</div>
        </div>
    </div>
</div>

<!-- Section-2: Biểu đồ doanh thu -->
<div class="section-2">
    <div class="inner-head">
        <h2 class="inner-title">Biểu đồ doanh thu</h2>
        <div class="inner-filler">
            <input type="month" name="month" />
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
                                            <img src="${pageContext.request.contextPath}/admin/assets/images/Tour-Icon.jpg" alt="Tour" />
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
