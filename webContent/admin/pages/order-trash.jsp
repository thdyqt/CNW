<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Thùng rác đơn hàng</h1>

<!-- Section-5: Thao tác & Tìm kiếm -->
<div class="section-5">
    <div class="inner-wrap">
        <div class="inner-check-status" change-multi data-api="${pageContext.request.contextPath}/${pathAdmin}/order/change-multi">
            <div class="inner-item">
                <select>
                    <option value="">-- Hành động --</option>
                    <option value="undo">Khôi phục</option>
                    <option value="delete-destroy">Xóa vĩnh viễn</option>
                </select>
            </div>
            <div class="inner-item">
                <button type="button">Áp dụng</button>
            </div>
        </div>
        <div class="inner-search">
            <i class="fa-solid fa-magnifying-glass"></i>
            <input type="text" placeholder="Tìm kiếm trong thùng rác" search value="${param.keyword}" />
        </div>
        <div class="inner-button-create">
            <a href="${pageContext.request.contextPath}/${pathAdmin}/order/list">Quay lại danh sách</a>
        </div>
    </div>
</div>

<!-- Section-6: Bảng danh sách thùng rác đơn hàng -->
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
                    <th>Xóa bởi</th>
                    <th class="inner-center">Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty orderTrashList}">
                        <c:forEach var="orderDetail" items="${orderTrashList}">
                            <tr>
                                <td class="inner-center">
                                    <input class="inner-check" type="checkbox" check-item="${orderDetail.id}" />
                                </td>
                                <td class="inner-code">${orderDetail.code}</td>
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
                                                    <c:choose>
                                                        <c:when test="${not empty item.avatar}">
                                                            <c:set var="imgSrc" value="${item.avatar.startsWith('http') ? item.avatar : (item.avatar.startsWith('/') ? pageContext.request.contextPath.concat(item.avatar) : pageContext.request.contextPath.concat('/').concat(item.avatar))}" />
                                                            <img src="${imgSrc}" alt="${item.name}" />
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img src="${pageContext.request.contextPath}/public/admin/assets/images/Tour-Icon.jpg" alt="${item.name}" />
                                                        </c:otherwise>
                                                    </c:choose>
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
                                    <div>Thanh toán: ${orderDetail.totalFormatted}đ</div>
                                    <div>PTTT: ${orderDetail.paymentMethodName}</div>
                                    <div>TTTT: ${orderDetail.paymentStatusName}</div>
                                </td>
                                <td class="inner-center">
                                    <span class="badge ${orderDetail.status == 'done' ? 'badge-green' : (orderDetail.status == 'cancel' ? 'badge-red' : 'badge-orange')}">
                                        ${orderDetail.statusName}
                                    </span>
                                </td>
                                <td>
                                    <div>${orderDetail.deletedBy}</div>
                                    <div style="font-size: 12px; color: #777;">${orderDetail.deletedAtFormatted}</div>
                                </td>
                                <td class="inner-center">
                                    <div class="inner-buttons">
                                        <button class="inner-undo" type="button" button-undo data-api="${pageContext.request.contextPath}/${pathAdmin}/order/undo/${orderDetail.id}" title="Khôi phục">
                                            <i class="fa-solid fa-rotate-left"></i>
                                        </button>
                                        <button class="inner-delete" type="button" button-delete-destroy data-api="${pageContext.request.contextPath}/${pathAdmin}/order/delete-destroy/${orderDetail.id}" title="Xóa vĩnh viễn">
                                            <i class="fa-regular fa-trash-can"></i>
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="8" class="inner-center" style="padding: 30px;">Thùng rác trống!</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<!-- Section-7: Phân trang -->
<c:if test="${pagination.totalPages > 1}">
    <div class="section-7" style="display: flex; align-items: center; justify-content: flex-start; margin-top: 20px;">
        <span class="inner-label" style="font-size: 14px; font-weight: 600; color: #202224c2; margin-right: 15px;">
            Hiển thị ${pagination.start} - ${pagination.end} của ${pagination.total}
        </span>
        <select class="inner-pagination" pagination>
            <c:forEach var="i" begin="1" end="${pagination.totalPages}">
                <option value="${i}" ${pagination.currentPage == i ? 'selected' : ''}>Trang ${i}</option>
            </c:forEach>
        </select>
    </div>
</c:if>

<jsp:include page="../layouts/footer.jsp" />
