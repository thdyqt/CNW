<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Thùng rác danh mục</h1>

<!-- Section-5: Thao tác & Tìm kiếm -->
<div class="section-5">
    <div class="inner-wrap">
        <div class="inner-check-status" change-multi data-api="${pageContext.request.contextPath}/${pathAdmin}/category/change-multi">
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
            <a href="${pageContext.request.contextPath}/${pathAdmin}/category/list">Quay lại danh sách</a>
        </div>
    </div>
</div>

<!-- Section-6: Bảng danh sách thùng rác -->
<div class="section-6">
    <div class="table-2">
        <table>
            <thead>
                <tr>
                    <th class="inner-center">
                        <input class="inner-check" type="checkbox" check-all />
                    </th>
                    <th>Tên danh mục</th>
                    <th class="inner-center">Ảnh đại diện</th>
                    <th class="inner-center">Vị trí</th>
                    <th class="inner-center">Trạng thái</th>
                    <th>Tạo bởi</th>
                    <th>Xóa bởi</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty categoryTrashList}">
                        <c:forEach var="item" items="${categoryTrashList}">
                            <tr>
                                <td class="inner-center">
                                    <input class="inner-check" type="checkbox" check-item="${item.id}" />
                                </td>
                                <td>
                                    <b>${item.name}</b>
                                </td>
                                <td class="inner-center">
                                    <c:choose>
                                        <c:when test="${not empty item.avatar}">
                                            <c:set var="imgSrc" value="${item.avatar.startsWith('http') ? item.avatar : (item.avatar.startsWith('/') ? pageContext.request.contextPath.concat(item.avatar) : pageContext.request.contextPath.concat('/').concat(item.avatar))}" />
                                            <img src="${imgSrc}" alt="${item.name}" style="width: 70px; height: 50px; object-fit: cover; border-radius: 4px;" />
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${pageContext.request.contextPath}/public/assets/images/Background-1.jpg" alt="${item.name}" style="width: 70px; height: 50px; object-fit: cover; border-radius: 4px;" />
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="inner-center">${item.position}</td>
                                <td class="inner-center">
                                    <span class="badge ${item.status == 'active' ? 'badge-green' : 'badge-red'}">
                                        ${item.status == 'active' ? 'Hoạt động' : 'Tạm dừng'}
                                    </span>
                                </td>
                                <td>
                                    <div>${item.createdBy}</div>
                                    <div style="font-size: 12px; color: #777;">${item.createdAtFormatted}</div>
                                </td>
                                <td>
                                    <div>${item.deletedBy}</div>
                                    <div style="font-size: 12px; color: #777;">${item.deletedAtFormatted}</div>
                                </td>
                                <td>
                                    <div class="inner-buttons">
                                        <button class="inner-undo" type="button" button-undo data-api="${pageContext.request.contextPath}/${pathAdmin}/category/undo/${item.id}" title="Khôi phục">
                                            <i class="fa-solid fa-rotate-left"></i>
                                        </button>
                                        <button class="inner-delete" type="button" button-delete-destroy data-api="${pageContext.request.contextPath}/${pathAdmin}/category/delete-destroy/${item.id}" title="Xóa vĩnh viễn">
                                            <i class="fa-regular fa-trash-can"></i>
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="8" style="text-align: center; padding: 25px; color: #888;">Thùng rác trống.</td>
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
