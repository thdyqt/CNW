<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Thùng rác tour</h1>

<!-- Section-5: Thao tác & Tìm kiếm -->
<div class="section-5">
    <div class="inner-wrap">
        <div class="inner-check-status" change-multi data-api="${pageContext.request.contextPath}/${pathAdmin}/tour/change-multi">
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
            <input type="text" placeholder="Tìm kiếm" search value="${param.keyword}" />
        </div>
        <div class="inner-button-create">
            <a href="${pageContext.request.contextPath}/${pathAdmin}/tour/list">Quay lại danh sách</a>
        </div>
    </div>
</div>

<div class="section-6">
    <div class="table-2">
        <table style="min-width: 1200px;">
            <thead>
                <tr>
                    <th class="inner-center" style="width: 40px;">
                        <input class="inner-check" type="checkbox" check-all />
                    </th>
                    <th style="min-width: 220px;">Tên tour</th>
                    <th class="inner-center" style="width: 80px;">Ảnh đại diện</th>
                    <th style="white-space: nowrap; width: 140px;">Giá</th>
                    <th style="white-space: nowrap; width: 85px;">Còn lại</th>
                    <th class="inner-center" style="width: 50px;">Vị trí</th>
                    <th class="inner-center" style="white-space: nowrap; width: 110px;">Trạng thái</th>
                    <th style="white-space: nowrap; width: 145px;">Tạo bởi</th>
                    <th style="white-space: nowrap; width: 145px;">Xóa bởi</th>
                    <th class="inner-center" style="white-space: nowrap; width: 90px;">Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty tourTrashList}">
                        <c:forEach var="item" items="${tourTrashList}">
                            <tr>
                                <td class="inner-center" style="width: 40px;">
                                    <input class="inner-check" type="checkbox" check-item="${item.id}" />
                                </td>
                                <td style="min-width: 220px;">
                                    <b>${item.name}</b>
                                    <div style="font-size: 12px; color: #777; margin-top: 2px; white-space: nowrap;">Mã: ${item.code} | Khởi hành: ${item.departureDateFormatted}</div>
                                </td>
                                <td class="inner-center" style="width: 80px;">
                                    <c:set var="tourImg" value="${item.avatar.startsWith('http') ? item.avatar : (item.avatar.startsWith('/') ? pageContext.request.contextPath.concat(item.avatar) : pageContext.request.contextPath.concat('/').concat(item.avatar))}" />
                                    <img src="${tourImg}" alt="${item.name}" style="width: 65px; height: 48px; object-fit: cover; border-radius: 4px;" />
                                </td>
                                <td style="white-space: nowrap; font-size: 13px; line-height: 1.4;">
                                    <c:set var="pAdult" value="${item.priceNewAdult > 0 ? item.priceNewAdult : item.priceAdult}" />
                                    <c:set var="pChildren" value="${item.priceNewChildren > 0 ? item.priceNewChildren : item.priceChildren}" />
                                    <c:set var="pBaby" value="${item.priceNewBaby > 0 ? item.priceNewBaby : item.priceBaby}" />
                                    <div>NL: <fmt:formatNumber value="${pAdult}" pattern="#,###" />đ</div>
                                    <div>TE: <fmt:formatNumber value="${pChildren}" pattern="#,###" />đ</div>
                                    <div>EB: <fmt:formatNumber value="${pBaby}" pattern="#,###" />đ</div>
                                </td>
                                <td style="white-space: nowrap; font-size: 13px; line-height: 1.4;">
                                    <div>NL: ${item.stockAdult}</div>
                                    <div>TE: ${item.stockChildren}</div>
                                    <div>EB: ${item.stockBaby}</div>
                                </td>
                                <td class="inner-center">${item.position}</td>
                                <td class="inner-center" style="white-space: nowrap;">
                                    <c:choose>
                                        <c:when test="${item.status == 'active'}">
                                            <span class="badge badge-green">Hoạt động</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-red">Tạm dừng</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td style="white-space: nowrap;">
                                    <div>${item.createdBy}</div>
                                    <div class="inner-time" style="color: #777; margin-top: 2px;">${item.createdAtFormatted}</div>
                                </td>
                                <td style="white-space: nowrap;">
                                    <div>${item.deletedBy}</div>
                                    <div class="inner-time" style="color: #777; margin-top: 2px;">${item.deletedAtFormatted}</div>
                                </td>
                                <td class="inner-center" style="white-space: nowrap;">
                                    <div class="inner-buttons">
                                        <button class="inner-undo" type="button" button-undo data-api="${pageContext.request.contextPath}/${pathAdmin}/tour/undo/${item.id}" title="Khôi phục">
                                            <i class="fa-solid fa-rotate-left"></i>
                                        </button>
                                        <button class="inner-delete" type="button" button-delete-destroy data-api="${pageContext.request.contextPath}/${pathAdmin}/tour/delete-destroy/${item.id}" title="Xóa vĩnh viễn">
                                            <i class="fa-regular fa-trash-can"></i>
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="10" style="text-align: center; padding: 25px; color: #888;">Thùng rác trống.</td>
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

<jsp:include page="../layouts/footer.jsp" />
