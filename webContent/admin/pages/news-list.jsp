<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../layouts/header.jsp" />
<c:set var="loggedInAdmin" value="${not empty sessionScope.account ? sessionScope.account : currentAccountAdmin}" />

<h1 class="box-title">Quản lý bài viết</h1>

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
                <option value="inactive" ${param.status == 'inactive' ? 'selected' : ''}>Tạm dừng</option>
            </select>
        </div>
        <div class="inner-item">
            <select onchange="window.location.href = updateQueryString('category', this.value)">
                <option value="">-- Chuyên mục --</option>
                <option value="Cẩm nang du lịch" ${param.category == 'Cẩm nang du lịch' ? 'selected' : ''}>Cẩm nang du lịch</option>
                <option value="Điểm đến hot" ${param.category == 'Điểm đến hot' ? 'selected' : ''}>Điểm đến hot</option>
                <option value="Nghỉ dưỡng" ${param.category == 'Nghỉ dưỡng' ? 'selected' : ''}>Nghỉ dưỡng</option>
                <option value="Ẩm thực" ${param.category == 'Ẩm thực' ? 'selected' : ''}>Ẩm thực</option>
                <option value="Khám phá" ${param.category == 'Khám phá' ? 'selected' : ''}>Khám phá</option>
                <option value="Trekking" ${param.category == 'Trekking' ? 'selected' : ''}>Trekking</option>
                <option value="Mẹo hay" ${param.category == 'Mẹo hay' ? 'selected' : ''}>Mẹo hay</option>
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
        <c:if test="${loggedInAdmin.hasPermission('news-edit') or loggedInAdmin.hasPermission('news-delete')}">
            <div class="inner-check-status" change-multi data-api="${pageContext.request.contextPath}/${pathAdmin}/news/change-multi">
                <div class="inner-item">
                    <select>
                        <option value="">-- Hành động --</option>
                        <c:if test="${loggedInAdmin.hasPermission('news-edit')}">
                            <option value="active">Hoạt động</option>
                            <option value="inactive">Tạm dừng</option>
                        </c:if>
                        <c:if test="${loggedInAdmin.hasPermission('news-delete')}">
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
            <input type="text" placeholder="Tìm kiếm bài viết..." search value="${param.keyword}" />
        </div>

        <c:if test="${loggedInAdmin.hasPermission('news-create')}">
            <div class="inner-button-create">
                <a href="${pageContext.request.contextPath}/${pathAdmin}/news/create">
                    <i class="fa-solid fa-plus"></i> Tạo mới
                </a>
            </div>
        </c:if>

        <c:if test="${loggedInAdmin.hasPermission('news-trash')}">
            <div class="inner-button-delete">
                <a href="${pageContext.request.contextPath}/${pathAdmin}/news/trash">
                    <i class="fa-regular fa-trash-can"></i> Thùng rác
                </a>
            </div>
        </c:if>
    </div>
</div>

<!-- Section-6: Bảng danh sách bài viết -->
<div class="section-6">
    <div class="table-2">
        <table>
            <thead>
                <tr>
                    <th class="inner-center" style="width: 45px;">
                        <input class="inner-check" type="checkbox" check-all />
                    </th>
                    <th style="width: 90px;">Hình ảnh</th>
                    <th>Tiêu đề bài viết</th>
                    <th style="width: 140px;">Chuyên mục</th>
                    <th style="width: 140px;">Tác giả</th>
                    <th class="inner-center" style="width: 80px;">Lượt xem</th>
                    <th class="inner-center" style="width: 100px;">Trạng thái</th>
                    <th class="inner-center" style="width: 110px;">Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty newsList}">
                        <c:forEach var="item" items="${newsList}">
                            <tr>
                                <td class="inner-center">
                                    <input class="inner-check" type="checkbox" check-item="${item.id}" />
                                </td>
                                <td>
                                    <c:set var="thumbImg" value="${item.avatar}" />
                                    <c:if test="${not empty thumbImg and not thumbImg.startsWith('http')}">
                                        <c:set var="thumbImg" value="${pageContext.request.contextPath}${thumbImg}" />
                                    </c:if>
                                    <img src="${thumbImg}" alt="${item.title}" style="width: 75px; height: 50px; object-fit: cover; border-radius: 6px;" onerror="this.src='${pageContext.request.contextPath}/public/assets/images/Blog-1.jpg'" />
                                </td>
                                <td>
                                    <div style="font-weight: 700; color: #1e293b; font-size: 14.5px;">
                                        ${item.title}
                                    </div>
                                    <div style="font-size: 12px; color: #64748b; margin-top: 4px;">
                                        <span>Ngày đăng: ${item.createdAtFormatted}</span>
                                        <c:if test="${item.featured}">
                                            <span style="background: #fef2f2; color: #ef4444; padding: 2px 8px; border-radius: 12px; font-weight: 700; margin-left: 8px;">★ Tiêu điểm</span>
                                        </c:if>
                                    </div>
                                </td>
                                <td>
                                    <span style="background: #eff6ff; color: #2563eb; font-weight: 600; padding: 4px 10px; border-radius: 6px; font-size: 12px; display: inline-block;">
                                        ${item.category}
                                    </span>
                                </td>
                                <td>
                                    <div style="font-size: 13.5px; color: #334155; font-weight: 600;">${item.author}</div>
                                </td>
                                <td class="inner-center">
                                    <span style="font-weight: 700; color: #475569;">${item.views}</span>
                                </td>
                                <td class="inner-center">
                                    <c:choose>
                                        <c:when test="${item.status == 'active'}">
                                            <span class="badge badge-green" button-change-status data-status="active" data-api="${pageContext.request.contextPath}/${pathAdmin}/news/change-status/inactive/${item.id}" style="cursor: pointer;" title="Nhấp để thay đổi trạng thái">
                                                Hoạt động
                                            </span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-red" button-change-status data-status="inactive" data-api="${pageContext.request.contextPath}/${pathAdmin}/news/change-status/active/${item.id}" style="cursor: pointer;" title="Nhấp để thay đổi trạng thái">
                                                Tạm dừng
                                            </span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="inner-center">
                                    <div class="inner-buttons">
                                        <c:if test="${loggedInAdmin.hasPermission('news-edit')}">
                                            <a class="inner-edit" href="${pageContext.request.contextPath}/${pathAdmin}/news/edit/${item.id}" title="Chỉnh sửa">
                                                <i class="fa-regular fa-pen-to-square"></i>
                                            </a>
                                        </c:if>
                                        <c:if test="${loggedInAdmin.hasPermission('news-delete')}">
                                            <button class="inner-delete" type="button" button-delete data-api="${pageContext.request.contextPath}/${pathAdmin}/news/delete/${item.id}" title="Xóa vào thùng rác">
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
                            <td colspan="8" class="inner-center" style="padding: 40px; color: #64748b;">
                                Không tìm thấy bài viết nào phù hợp!
                            </td>
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
        Hiển thị ${pagination.start} - ${pagination.end} của ${pagination.total} bài viết
    </span>
    <select class="inner-pagination" pagination>
        <c:forEach var="i" begin="1" end="${pagination.totalPages > 0 ? pagination.totalPages : 1}">
            <option value="${i}" ${pagination.currentPage == i ? 'selected' : ''}>Trang ${i}</option>
        </c:forEach>
    </select>
</div>

<script>
function updateQueryString(key, val) {
    const url = new URL(window.location.href);
    if (val) {
        url.searchParams.set(key, val);
    } else {
        url.searchParams.delete(key);
    }
    url.searchParams.delete('page');
    return url.href;
}
</script>

<jsp:include page="../layouts/footer.jsp" />
