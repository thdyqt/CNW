<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../layouts/header.jsp" />
<c:set var="loggedInAdmin" value="${not empty sessionScope.account ? sessionScope.account : currentAccountAdmin}" />

<h1 class="box-title">Quản lý đơn hàng</h1>

<!-- Section-4: Bộ lọc -->
<div class="section-4">
    <div class="inner-wrap">
        <div class="inner-item inner-label">
            <i class="fa-solid fa-filter"></i>
            Bộ lọc
        </div>
        <div class="inner-item">
            <select filter-status>
                <option value="">Trạng thái</option>
                <option value="initial" ${param.status == 'initial' ? 'selected' : ''}>Khởi tạo</option>
                <option value="done" ${param.status == 'done' ? 'selected' : ''}>Hoàn thành</option>
                <option value="cancel" ${param.status == 'cancel' ? 'selected' : ''}>Hủy</option>
            </select>
        </div>
        <div class="inner-item">
            <input type="date" filter-start-date value="${param.startDate}" />
            <span>-</span>
            <input type="date" filter-end-date value="${param.endDate}" />
        </div>
        <div class="inner-item">
            <select filter-payment-method>
                <option value="">Phương thức thanh toán</option>
                <option value="money" ${param.paymentMethod == 'money' ? 'selected' : ''}>Tiền mặt</option>
                <option value="vnpay" ${param.paymentMethod == 'vnpay' ? 'selected' : ''}>VNPay</option>
                <option value="bank" ${param.paymentMethod == 'bank' || param.paymentMethod == 'banking' ? 'selected' : ''}>Tài khoản ngân hàng</option>
            </select>
        </div>
        <div class="inner-item">
            <select filter-payment-status>
                <option value="">Trạng thái thanh toán</option>
                <option value="paid" ${param.paymentStatus == 'paid' ? 'selected' : ''}>Đã thanh toán</option>
                <option value="unpaid" ${param.paymentStatus == 'unpaid' ? 'selected' : ''}>Chưa thanh toán</option>
            </select>
        </div>
        <div class="inner-item inner-reset" filter-reset>
            <i class="fa-solid fa-rotate-left"></i>
            Xóa bộ lọc
        </div>
    </div>
</div>

<!-- Section-5: Tìm kiếm & Thao tác -->
<div class="section-5">
    <div class="inner-wrap">
        <div class="inner-check-status" change-multi data-api="${pageContext.request.contextPath}/${pathAdmin}/order/change-multi">
            <div class="inner-item">
                <select>
                    <option value="">-- Hành động --</option>
                    <option value="initial">Khởi tạo</option>
                    <option value="processing">Đang xử lý</option>
                    <option value="success">Hoàn thành</option>
                    <option value="cancel">Hủy</option>
                    <option value="delete">Xóa</option>
                </select>
            </div>
            <div class="inner-item">
                <button type="button">Áp dụng</button>
            </div>
        </div>
        <div class="inner-search">
            <i class="fa-solid fa-magnifying-glass"></i>
            <input type="text" placeholder="Tìm kiếm" search value="${param.keyword}" />
        </div>
        <c:if test="${loggedInAdmin.hasPermission('order-trash')}">
            <div class="inner-button-delete">
                <a href="${pageContext.request.contextPath}/${pathAdmin}/order/trash">Thùng rác</a>
            </div>
        </c:if>
    </div>
</div>

<!-- Section-6: Bảng danh sách đơn hàng -->
<div class="section-6">
    <div class="table-2">
        <table>
            <thead>
                <tr>
                    <th class="inner-center" style="width: 40px;">
                        <input class="inner-check" type="checkbox" check-all />
                    </th>
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
                <c:choose>
                    <c:when test="${not empty orderList}">
                        <c:forEach var="orderDetail" items="${orderList}">
                            <tr>
                                <td class="inner-center">
                                    <input class="inner-check" type="checkbox" check-item="${orderDetail.id}" />
                                </td>
                                <td class="inner-code">
                                    <a href="${pageContext.request.contextPath}/${pathAdmin}/order/edit/${orderDetail.id}" class="inner-code">${orderDetail.code}</a>
                                </td>
                                <td>
                                    <div>Họ tên: ${orderDetail.fullName}</div>
                                    <div>SĐT: ${orderDetail.phone}</div>
                                    <div>Ghi chú: ${orderDetail.note}</div>
                                </td>
                                <td>
                                    <div class="inner-list">
                                        <c:forEach var="item" items="${orderDetail.items}">
                                            <div class="inner-item">
                                                <div class="inner-image">
                                                    <img src="${item.avatar}" alt="${item.name}" />
                                                </div>
                                                <div class="inner-content">
                                                    <div class="inner-name">${item.name}</div>
                                                    <c:if test="${item.quantityAdult > 0}">
                                                        <div class="inner-quantity">
                                                            Người lớn: ${item.quantityAdult} x ${item.priceNewAdultFormatted}đ
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${item.quantityChildren > 0}">
                                                        <div class="inner-quantity">
                                                            Trẻ em: ${item.quantityChildren} x ${item.priceNewChildrenFormatted}đ
                                                        </div>
                                                    </c:if>
                                                    <c:if test="${item.quantityBaby > 0}">
                                                        <div class="inner-quantity">
                                                            Em bé: ${item.quantityBaby} x ${item.priceNewBabyFormatted}đ
                                                        </div>
                                                    </c:if>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </td>
                                <td>
                                    <div>Tổng tiền: ${orderDetail.subTotalFormatted}đ</div>
                                    <div>Giảm: 0đ</div>
                                    <div>Mã giảm: </div>
                                    <div>Thanh toán: ${orderDetail.totalFormatted}đ</div>
                                    <div>PTTT: ${orderDetail.paymentMethodName}</div>
                                    <div>TTTT: ${orderDetail.paymentStatusName}</div>
                                </td>
                                <td class="inner-center">
                                    <span class="badge ${orderDetail.status == 'done' ? 'badge-green' : (orderDetail.status == 'cancel' ? 'badge-red' : 'badge-orange')}">
                                        ${orderDetail.statusName}
                                    </span>
                                </td>
                                <td class="inner-center">
                                    <div>${orderDetail.createdAtFormattedDate}</div>
                                </td>
                                <td>
                                    <div class="inner-buttons">
                                        <c:if test="${loggedInAdmin.hasPermission('order-edit')}">
                                            <a class="inner-edit" href="${pageContext.request.contextPath}/${pathAdmin}/order/edit/${orderDetail.id}">
                                                <i class="fa-regular fa-pen-to-square"></i>
                                            </a>
                                        </c:if>
                                        <c:if test="${loggedInAdmin.hasPermission('order-delete')}">
                                            <button class="inner-delete" type="button" button-delete data-api="${pageContext.request.contextPath}/${pathAdmin}/order/delete/${orderDetail.id}">
                                                <i class="fa-regular fa-trash-can"></i>
                                            </button>
                                        </c:if>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="8" class="inner-center" style="padding: 30px;">Không có đơn hàng nào!</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<!-- Section-7: Phân trang -->
<div class="section-7" style="display: flex; align-items: center; justify-content: flex-start; margin-top: 20px;">
    <span class="inner-label" style="font-size: 14px; font-weight: 600; color: #202224c2; margin-right: 15px;">
        Hiển thị ${pagination.start} - ${pagination.end} của ${pagination.total}
    </span>
    <select class="inner-pagination" pagination>
        <c:forEach var="i" begin="1" end="${pagination.totalPages > 0 ? pagination.totalPages : 1}">
            <option value="${i}" ${pagination.currentPage == i ? 'selected' : ''}>Trang ${i}</option>
        </c:forEach>
    </select>
</div>

<script>
    (function() {
        const filterPayMethod = document.querySelector("[filter-payment-method]");
        if (filterPayMethod && !filterPayMethod.dataset.initialized) {
            filterPayMethod.dataset.initialized = "true";
            filterPayMethod.addEventListener("change", function() {
                const url = new URL(window.location.href);
                if (this.value) {
                    url.searchParams.set("paymentMethod", this.value);
                } else {
                    url.searchParams.delete("paymentMethod");
                }
                url.searchParams.delete("page");
                window.location.href = url.href;
            });
        }

        const filterPayStatus = document.querySelector("[filter-payment-status]");
        if (filterPayStatus && !filterPayStatus.dataset.initialized) {
            filterPayStatus.dataset.initialized = "true";
            filterPayStatus.addEventListener("change", function() {
                const url = new URL(window.location.href);
                if (this.value) {
                    url.searchParams.set("paymentStatus", this.value);
                } else {
                    url.searchParams.delete("paymentStatus");
                }
                url.searchParams.delete("page");
                window.location.href = url.href;
            });
        }
    })();
</script>

<jsp:include page="../layouts/footer.jsp" />
