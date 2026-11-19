<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Thùng rác bài viết</h1>

<!-- Section-5: Thao tác & Tìm kiếm -->
<div class="section-5">
    <div class="inner-wrap">
        <div class="inner-check-status" change-multi data-api="${pageContext.request.contextPath}/${pathAdmin}/news/change-multi">
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
            <a href="${pageContext.request.contextPath}/${pathAdmin}/news/list">
                <i class="fa-solid fa-arrow-left"></i> Quay lại danh sách
            </a>
        </div>
    </div>
</div>

<!-- Section-6: Bảng danh sách thùng rác bài viết -->
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
                    <th style="width: 160px;">Xóa bởi</th>
                    <th class="inner-center" style="width: 120px;">Hành động</th>
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
                                    <c:set var="trashThumb" value="${item.avatar}" />
                                    <c:if test="${not empty trashThumb and not trashThumb.startsWith('http')}">
                                        <c:set var="trashThumb" value="${pageContext.request.contextPath}${trashThumb}" />
                                    </c:if>
                                    <img src="${trashThumb}" alt="${item.title}" style="width: 75px; height: 50px; object-fit: cover; border-radius: 6px;" onerror="this.src='${pageContext.request.contextPath}/public/assets/images/Blog-1.jpg'" />
                                </td>
                                <td>
                                    <div style="font-weight: 700; color: #1e293b; font-size: 14px;">
                                        ${item.title}
                                    </div>
                                    <div style="font-size: 12px; color: #94a3b8; margin-top: 3px;">
                                        Tác giả: ${item.author}
                                    </div>
                                </td>
                                <td>
                                    <span style="background: #f1f5f9; color: #475569; font-weight: 600; padding: 4px 10px; border-radius: 6px; font-size: 12px; display: inline-block;">
                                        ${item.category}
                                    </span>
                                </td>
                                <td>
                                    <div style="font-size: 13px; font-weight: 600; color: #334155;">${item.deletedBy}</div>
                                    <div style="font-size: 11.5px; color: #94a3b8;">${item.deletedAtFormatted}</div>
                                </td>
                                <td class="inner-center">
                                    <div class="inner-buttons">
                                        <button class="inner-undo" type="button" button-undo data-api="${pageContext.request.contextPath}/${pathAdmin}/news/undo/${item.id}" title="Khôi phục">
                                            <i class="fa-solid fa-rotate-left"></i>
                                        </button>
                                        <button class="inner-delete" type="button" button-delete-destroy data-api="${pageContext.request.contextPath}/${pathAdmin}/news/destroy/${item.id}" title="Xóa vĩnh viễn">
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
                                Thùng rác bài viết hiện đang trống!
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
        Hiển thị ${pagination.start} - ${pagination.end} của ${pagination.total} bài viết đã xóa
    </span>
    <select class="inner-pagination" pagination>
        <c:forEach var="i" begin="1" end="${pagination.totalPages > 0 ? pagination.totalPages : 1}">
            <option value="${i}" ${pagination.currentPage == i ? 'selected' : ''}>Trang ${i}</option>
        </c:forEach>
    </select>
</div>

<jsp:include page="../layouts/footer.jsp" />
