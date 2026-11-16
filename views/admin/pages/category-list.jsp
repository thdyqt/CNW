<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Quản lý danh mục</h1>

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
                <option value="active" ${param.status == 'active' ? 'selected' : ''}>Hoạt động</option>
                <option value="inactive" ${param.status == 'inactive' ? 'selected' : ''}>Tạm dừng</option>
            </select>
        </div>
        <div class="inner-item">
            <select filter-created-by>
                <option value="">Người tạo</option>
                <option value="1" ${param.createdBy == '1' ? 'selected' : ''}>Phan Thanh Duy</option>
            </select>
        </div>
        <div class="inner-item">
            <input type="date" filter-start-date value="${param.startDate}" />
            <span>-</span>
            <input type="date" filter-end-date value="${param.endDate}" />
        </div>
        <div class="inner-item inner-reset" filter-reset>
            <i class="fa-solid fa-rotate-left"></i>
            Xóa bộ lọc
        </div>
    </div>
</div>

<!-- Section-5: Thao tác & Tìm kiếm -->
<div class="section-5">
    <div class="inner-wrap">
        <div class="inner-check-status" change-multi data-api="${pageContext.request.contextPath}/${pathAdmin}/category/change-multi">
            <div class="inner-item">
                <select>
                    <option value="">-- Hành động --</option>
                    <option value="active">Hoạt động</option>
                    <option value="inactive">Tạm dừng</option>
                    <option value="delete">Xóa</option>
                </select>
            </div>
            <div class="inner-item">
                <button type="button">Áp dụng</button>
            </div>
        </div>
        <div class="inner-search">
            <i class="fa-solid fa-magnifying-glass"></i>
            <input type="text" placeholder="Tìm kiếm danh mục" search value="${param.keyword}" />
        </div>
        <div class="inner-button-create">
            <a href="${pageContext.request.contextPath}/${pathAdmin}/category/create">+ Tạo mới</a>
        </div>
        <div class="inner-button-delete">
            <a href="${pageContext.request.contextPath}/${pathAdmin}/category/trash">Thùng rác</a>
        </div>
    </div>
</div>

<!-- Section-6: Bảng danh sách danh mục -->
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
                    <th>Cập nhật bởi</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty categoryList}">
                        <c:forEach var="item" items="${categoryList}">
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
                                            <img src="${pageContext.request.contextPath}/assets/images/Background-1.jpg" alt="${item.name}" style="width: 70px; height: 50px; object-fit: cover; border-radius: 4px;" />
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
                                    <div>${item.updatedBy}</div>
                                    <div style="font-size: 12px; color: #777;">${item.updatedAtFormatted}</div>
                                </td>
                                <td>
                                    <div class="inner-buttons">
                                        <a class="inner-edit" href="${pageContext.request.contextPath}/${pathAdmin}/category/edit/${item.id}">
                                            <i class="fa-regular fa-pen-to-square"></i>
                                        </a>
                                        <button class="inner-delete" type="button" button-delete data-api="${pageContext.request.contextPath}/${pathAdmin}/category/delete/${item.id}">
                                            <i class="fa-regular fa-trash-can"></i>
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="8" style="text-align: center; padding: 25px; color: #888;">Không tìm thấy danh mục nào phù hợp.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
