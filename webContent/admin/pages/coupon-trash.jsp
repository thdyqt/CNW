<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Thùng rác mã giảm giá</h1>

<!-- Section-5: Thao tác hàng loạt & Tìm kiếm -->
<div class="section-5">
    <div class="inner-wrap">
        <div class="inner-check-status" change-multi data-api="${pageContext.request.contextPath}/${pathAdmin}/coupon/change-multi">
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
            <input type="text" placeholder="Tìm kiếm trong thùng rác..." search value="${param.keyword}" />
        </div>
        <div class="inner-button-create">
            <a href="${pageContext.request.contextPath}/${pathAdmin}/coupon/list">
                <i class="fa-solid fa-arrow-left"></i> Quay lại danh sách
            </a>
        </div>
    </div>
</div>

<!-- Section-6: Bảng danh sách thùng rác mã giảm giá -->
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
                    <th style="width: 180px;">Xóa bởi</th>
                    <th class="inner-center" style="width: 120px;">Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty couponTrashList}">
                        <c:forEach var="item" items="${couponTrashList}">
                            <tr>
                                <td class="inner-center">
                                    <input class="inner-check" type="checkbox" check-item="${item.id}" />
                                </td>
                                <td>
                                    <span style="background: #f1f5f9; color: #64748b; font-weight: 800; font-family: monospace; font-size: 14px; padding: 4px 10px; border-radius: 6px; border: 1px dashed #cbd5e1; display: inline-block;">
                                        ${item.code}
                                    </span>
                                </td>
                                <td>
                                    <div style="font-weight: 700; color: #1e293b; font-size: 14px;">
                                        ${item.title}
                                    </div>
                                    <div style="font-size: 12px; color: #94a3b8; margin-top: 3px;">
                                        Đơn tối thiểu: <fmt:formatNumber value="${item.minOrderValue}" pattern="#,###" />đ
                                    </div>
                                </td>
                                <td>
                                    <div style="font-weight: 700; color: #475569; font-size: 14px;">
                                        ${item.valueFormatted}
                                    </div>
                                    <div style="font-size: 11.5px; color: #94a3b8;">
                                        ${item.typeName}
                                    </div>
                                </td>
                                <td>
                                    <div style="font-size: 13px; font-weight: 600; color: #334155;">
                                        ${not empty item.deletedBy ? item.deletedBy : 'Quản trị viên'}
                                    </div>
                                </td>
                                <td class="inner-center">
                                    <div class="inner-buttons">
                                        <button class="inner-undo" type="button" button-undo data-api="${pageContext.request.contextPath}/${pathAdmin}/coupon/undo/${item.id}" title="Khôi phục mã">
                                            <i class="fa-solid fa-rotate-left"></i>
                                        </button>
                                        <button class="inner-delete" type="button" button-delete-destroy data-api="${pageContext.request.contextPath}/${pathAdmin}/coupon/destroy/${item.id}" title="Xóa vĩnh viễn">
                                            <i class="fa-regular fa-trash-can"></i>
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="6" class="inner-center" style="padding: 35px; color: #64748b;">
                                <i class="fa-solid fa-ticket" style="font-size: 32px; color: #cbd5e1; margin-bottom: 10px; display: block;"></i>
                                Thùng rác mã giảm giá hiện đang trống!
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<!-- Section-7: Phân trang thùng rác -->
<c:if test="${pagination.totalPages > 1}">
    <div class="section-7">
        <div class="inner-wrap">
            <div class="inner-total">
                Hiển thị ${pagination.start} - ${pagination.end} trên tổng số ${pagination.total} mã giảm giá trong thùng rác
            </div>
            <div class="inner-pagination">
                <c:if test="${pagination.currentPage > 1}">
                    <a href="${pageContext.request.contextPath}/${pathAdmin}/coupon/trash?page=${pagination.currentPage - 1}${not empty param.keyword ? '&keyword='.concat(param.keyword) : ''}" class="inner-item">
                        <i class="fa-solid fa-angle-left"></i>
                    </a>
                </c:if>
                <c:forEach var="p" begin="1" end="${pagination.totalPages}">
                    <a href="${pageContext.request.contextPath}/${pathAdmin}/coupon/trash?page=${p}${not empty param.keyword ? '&keyword='.concat(param.keyword) : ''}" class="inner-item ${p == pagination.currentPage ? 'active' : ''}">
                        ${p}
                    </a>
                </c:forEach>
                <c:if test="${pagination.currentPage < pagination.totalPages}">
                    <a href="${pageContext.request.contextPath}/${pathAdmin}/coupon/trash?page=${pagination.currentPage + 1}${not empty param.keyword ? '&keyword='.concat(param.keyword) : ''}" class="inner-item">
                        <i class="fa-solid fa-angle-right"></i>
                    </a>
                </c:if>
            </div>
        </div>
    </div>
</c:if>

<jsp:include page="../layouts/footer.jsp" />
