<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Quản lý đơn hàng</h1>

<div class="section-6">
    <div class="table-2">
        <table>
            <thead>
                <tr>
                    <th>Mã</th>
                    <th>Thông tin khách</th>
                    <th>Danh sách tour</th>
                    <th>Thanh toán</th>
                    <th class="inner-center">Trạng thái</th>
                    <th class="inner-center">Ngày đặt</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="orderDetail" items="${orderList}">
                    <tr>
                        <td class="inner-code">${orderDetail.code}</td>
                        <td>
                            <div><b>${orderDetail.fullName}</b></div>
                            <div>SĐT: ${orderDetail.phone}</div>
                            <div>Ghi chú: ${orderDetail.note}</div>
                        </td>
                        <td>
                            <div class="inner-list">
                                <c:forEach var="item" items="${orderDetail.items}">
                                    <div class="inner-item" style="margin-bottom: 5px;">
                                        <div><b>${item.name}</b></div>
                                        <div style="font-size: 13px; color: #555;">
                                            SL: ${item.quantityAdult} người lớn | Khởi hành: ${item.departureDateFormatted}
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </td>
                        <td>
                            <div>Tổng tiền: <b><fmt:formatNumber value="${orderDetail.total}" pattern="#,###" />đ</b></div>
                            <div>PTTT: ${orderDetail.paymentMethodName}</div>
                            <div>TTTT: ${orderDetail.paymentStatusName}</div>
                        </td>
                        <td class="inner-center">
                            <span class="badge ${orderDetail.status == 'done' ? 'badge-green' : (orderDetail.status == 'cancel' ? 'badge-red' : 'badge-orange')}">
                                ${orderDetail.statusName}
                            </span>
                        </td>
                        <td class="inner-center">
                            <div>${orderDetail.createdAtFormatted}</div>
                        </td>
                        <td>
                            <div class="inner-buttons">
                                <a class="inner-edit" href="${pageContext.request.contextPath}/${pathAdmin}/order/edit/${orderDetail.id}">
                                    <i class="fa-regular fa-pen-to-square"></i>
                                </a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
