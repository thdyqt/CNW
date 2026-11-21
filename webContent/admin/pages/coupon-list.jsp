<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../layouts/header.jsp" />
<c:set var="loggedInAdmin" value="${not empty sessionScope.account ? sessionScope.account : currentAccountAdmin}" />

<h1 class="box-title">Quản lý mã giảm giá</h1>

<!-- Section-4: Bộ lọc -->
<div class="section-4">
    <div class="inner-wrap">
        <div class="inner-item inner-label">
            <i class="fa-solid fa-filter"></i>
            Bộ lọc
        </div>
        <div class="inner-item">
            <select filter-status>
                <option value="">-- Trạng thái --</option>
                <option value="active" ${param.status == 'active' ? 'selected' : ''}>Hoạt động</option>
                <option value="inactive" ${param.status == 'inactive' ? 'selected' : ''}>Tạm khóa</option>
            </select>
        </div>
        <div class="inner-item inner-reset" filter-reset>
            <i class="fa-solid fa-rotate-left"></i>
            Xóa bộ lọc
        </div>
    </div>
</div>

<!-- Section-5: Thao tác hàng loạt, Tìm kiếm, Tạo mới, Thùng rác -->
<div class="section-5">
    <div class="inner-wrap">
        <c:if test="${empty loggedInAdmin or loggedInAdmin.hasPermission('coupon-edit') or loggedInAdmin.hasPermission('coupon-delete')}">
            <div class="inner-check-status" change-multi data-api="${pageContext.request.contextPath}/${pathAdmin}/coupon/change-multi">
                <div class="inner-item">
                    <select>
                        <option value="">-- Hành động --</option>
                        <c:if test="${empty loggedInAdmin or loggedInAdmin.hasPermission('coupon-edit')}">
                            <option value="active">Hoạt động</option>
                            <option value="inactive">Tạm khóa</option>
                        </c:if>
                        <c:if test="${empty loggedInAdmin or loggedInAdmin.hasPermission('coupon-delete')}">
                            <option value="delete">Xóa vào thùng rác</option>
                        </c:if>
                    </select>
                </div>
                <div class="inner-item">
                    <button type="button">Áp dụng</button>
                </div>
            </div>
        </c:if>
        
        <div class="inner-search">
            <i class="fa-solid fa-magnifying-glass"></i>
            <input type="text" placeholder="Tìm theo mã hoặc tiêu đề..." search value="${param.keyword}" />
        </div>

        <c:if test="${empty loggedInAdmin or loggedInAdmin.hasPermission('coupon-create')}">
            <div class="inner-button-create">
                <a href="${pageContext.request.contextPath}/${pathAdmin}/coupon/create">
                    <i class="fa-solid fa-plus"></i> Tạo mã mới
                </a>
            </div>
        </c:if>

        <c:if test="${empty loggedInAdmin or loggedInAdmin.hasPermission('coupon-trash')}">
            <div class="inner-button-delete">
                <a href="${pageContext.request.contextPath}/${pathAdmin}/coupon/trash">
                    <i class="fa-regular fa-trash-can"></i> Thùng rác
                </a>
            </div>
        </c:if>
    </div>
</div>

<!-- Section-6: Bảng danh sách mã giảm giá -->
<div class="section-6">
    <div class="table-2">
        <table>
            <thead>
                <tr>
                    <th class="inner-center" style="width: 45px;">
                        <input class="inner-check" type="checkbox" check-all />
                    </th>
                    <th style="width: 140px;">Mã Coupon</th>
                    <th>Tiêu đề ưu đãi</th>
                    <th style="width: 130px;">Mức giảm</th>
                    <th style="width: 160px;">Đk & Tối đa</th>
                    <th class="inner-center" style="width: 110px;">Lượt dùng</th>
                    <th style="width: 150px;">Thời hạn</th>
                    <th class="inner-center" style="width: 110px;">Trạng thái</th>
                    <th class="inner-center" style="width: 110px;">Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty couponList}">
                        <c:forEach var="item" items="${couponList}">
                            <tr>
                                <td class="inner-center">
                                    <input class="inner-check" type="checkbox" check-item="${item.id}" />
                                </td>
                                <td>
                                    <span style="background: #eff6ff; color: #2563eb; font-weight: 800; font-family: monospace; font-size: 14px; padding: 4px 10px; border-radius: 6px; border: 1px dashed #3b82f6; display: inline-block;">
                                        ${item.code}
                                    </span>
                                </td>
                                <td>
                                    <div style="font-weight: 700; color: #1e293b; font-size: 14.5px;">
                                        ${item.title}
                                    </div>
                                    <div style="font-size: 12px; color: #64748b; margin-top: 4px;">
                                        <span>Tạo lúc: ${item.createdAtFormatted}</span>
                                        <c:if test="${not empty item.createdBy}">
                                            <span> • bởi ${item.createdBy}</span>
                                        </c:if>
                                    </div>
                                </td>
                                <td>
                                    <div style="font-weight: 800; color: #059669; font-size: 15px;">
                                        ${item.valueFormatted}
                                    </div>
                                    <div style="font-size: 11.5px; color: #64748b;">
                                        ${item.typeName}
                                    </div>
                                </td>
                                <td>
                                    <div style="font-size: 13px; color: #334155;">
                                        Đơn từ: <b><fmt:formatNumber value="${item.minOrderValue}" pattern="#,###" />đ</b>
                                    </div>
                                    <c:if test="${item.type == 'percentage' and item.maxDiscountValue > 0}">
                                        <div style="font-size: 12px; color: #64748b; margin-top: 2px;">
                                            Tối đa: <b><fmt:formatNumber value="${item.maxDiscountValue}" pattern="#,###" />đ</b>
                                        </div>
                                    </c:if>
                                </td>
                                <td class="inner-center">
                                    <div style="font-weight: 700; font-size: 13.5px; color: #1e293b;">
                                        ${item.usedCount} / <c:out value="${item.usageLimit > 0 ? item.usageLimit : '∞'}" />
                                    </div>
                                    <c:if test="${item.exhausted}">
                                        <span style="background: #fef2f2; color: #ef4444; font-size: 11px; padding: 2px 6px; border-radius: 4px; font-weight: 600; display: inline-block; margin-top: 3px;">Hết lượt</span>
                                    </c:if>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty item.startDate or not empty item.endDate}">
                                            <div style="font-size: 12.5px; color: #475569;">
                                                <c:if test="${not empty item.startDate}">
                                                    <div>Từ: <b>${item.startDateFormatted}</b></div>
                                                </c:if>
                                                <c:if test="${not empty item.endDate}">
                                                    <div>Đến: <b>${item.endDateFormatted}</b></div>
                                                </c:if>
                                            </div>
                                            <c:if test="${item.expired}">
                                                <span style="background: #fff1f2; color: #e11d48; font-size: 11px; padding: 2px 6px; border-radius: 4px; font-weight: 600; display: inline-block; margin-top: 3px;">Đã hết hạn</span>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color: #64748b; font-size: 12.5px;">Vô thời hạn</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="inner-center">
                                    <c:choose>
                                        <c:when test="${item.status == 'active'}">
                                            <span class="badge badge-green" button-change-status data-status="active" data-api="${pageContext.request.contextPath}/${pathAdmin}/coupon/change-status/inactive/${item.id}" style="cursor: pointer;" title="Nhấp để chuyển sang Tạm khóa">
                                                Hoạt động
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-red" button-change-status data-status="inactive" data-api="${pageContext.request.contextPath}/${pathAdmin}/coupon/change-status/active/${item.id}" style="cursor: pointer;" title="Nhấp để chuyển sang Hoạt động">
                                                Tạm khóa
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="inner-center">
                                    <div class="inner-buttons">
                                        <c:if test="${empty loggedInAdmin or loggedInAdmin.hasPermission('coupon-edit')}">
                                            <a class="inner-edit" href="${pageContext.request.contextPath}/${pathAdmin}/coupon/edit/${item.id}" title="Chỉnh sửa mã">
                                                <i class="fa-regular fa-pen-to-square"></i>
                                            </a>
                                        </c:if>
                                        <c:if test="${empty loggedInAdmin or loggedInAdmin.hasPermission('coupon-delete')}">
                                            <button class="inner-delete" type="button" button-delete data-api="${pageContext.request.contextPath}/${pathAdmin}/coupon/delete/${item.id}" title="Xóa vào thùng rác">
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
                            <td colspan="9" class="inner-center" style="padding: 40px 20px; color: #64748b;">
                                <i class="fa-solid fa-ticket" style="font-size: 36px; color: #cbd5e1; margin-bottom: 12px; display: block;"></i>
                                Không tìm thấy mã giảm giá nào phù hợp!
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<!-- Section-7: Phân trang -->
<c:if test="${pagination.totalPages > 1}">
    <div class="section-7">
        <div class="inner-wrap">
            <div class="inner-total">
                Hiển thị ${pagination.start} - ${pagination.end} trên tổng số ${pagination.total} mã giảm giá
            </div>
            <div class="inner-pagination">
                <c:if test="${pagination.currentPage > 1}">
                    <a href="${pageContext.request.contextPath}/${pathAdmin}/coupon/list?page=${pagination.currentPage - 1}${not empty param.status ? '&status='.concat(param.status) : ''}${not empty param.keyword ? '&keyword='.concat(param.keyword) : ''}" class="inner-item">
                        <i class="fa-solid fa-angle-left"></i>
                    </a>
                </c:if>
                <c:forEach var="p" begin="1" end="${pagination.totalPages}">
                    <a href="${pageContext.request.contextPath}/${pathAdmin}/coupon/list?page=${p}${not empty param.status ? '&status='.concat(param.status) : ''}${not empty param.keyword ? '&keyword='.concat(param.keyword) : ''}" class="inner-item ${p == pagination.currentPage ? 'active' : ''}">
                        ${p}
                    </a>
                </c:forEach>
                <c:if test="${pagination.currentPage < pagination.totalPages}">
                    <a href="${pageContext.request.contextPath}/${pathAdmin}/coupon/list?page=${pagination.currentPage + 1}${not empty param.status ? '&status='.concat(param.status) : ''}${not empty param.keyword ? '&keyword='.concat(param.keyword) : ''}" class="inner-item">
                        <i class="fa-solid fa-angle-right"></i>
                    </a>
                </c:if>
            </div>
        </div>
    </div>
</c:if>

<jsp:include page="../layouts/footer.jsp" />
