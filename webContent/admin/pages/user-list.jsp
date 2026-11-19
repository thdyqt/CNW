<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../layouts/header.jsp" />
<c:set var="loggedInAdmin" value="${not empty sessionScope.account ? sessionScope.account : currentAccountAdmin}" />

<h1 class="box-title">Quản lý người dùng</h1>

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
        <div class="inner-check-status" change-multi data-api="${pageContext.request.contextPath}/${pathAdmin}/user/change-multi">
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
            <input type="text" placeholder="Tìm kiếm" search value="${param.keyword}" />
        </div>
        <c:if test="${loggedInAdmin.hasPermission('user-create')}">
            <div class="inner-button-create">
                <a href="${pageContext.request.contextPath}/${pathAdmin}/user/create">+ Tạo mới</a>
            </div>
        </c:if>
        <c:if test="${loggedInAdmin.hasPermission('user-trash') or loggedInAdmin.hasPermission('user-delete')}">
            <div class="inner-button-delete">
                <a href="${pageContext.request.contextPath}/${pathAdmin}/user/trash">Thùng rác</a>
            </div>
        </c:if>
    </div>
</div>

<!-- Section-6: Bảng danh sách người dùng -->
<div class="section-6">
    <div class="table-2">
        <table>
            <thead>
                <tr>
                    <th class="inner-center" style="width: 50px;">
                        <input class="inner-check" type="checkbox" check-all />
                    </th>
                    <th>Họ tên</th>
                    <th class="inner-center">Ảnh đại diện</th>
                    <th>Email</th>
                    <th>Số điện thoại</th>
                    <th>Địa chỉ</th>
                    <th>Trạng thái</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty userList}">
                        <c:forEach var="item" items="${userList}">
                            <tr>
                                <td class="inner-center">
                                    <input class="inner-check" type="checkbox" check-item="${item.id}" />
                                </td>
                                <td>${item.fullName}</td>
                                <td class="inner-center">
                                    <c:choose>
                                        <c:when test="${not empty item.avatar}">
                                            <c:set var="imgSrc" value="${item.avatar.startsWith('http') ? item.avatar : (item.avatar.startsWith('/') ? pageContext.request.contextPath.concat(item.avatar) : pageContext.request.contextPath.concat('/').concat(item.avatar))}" />
                                            <img src="${imgSrc}" alt="${item.fullName}" style="width: 48px; height: 48px; object-fit: cover; border-radius: 8px;" />
                                        </c:when>
                                        <c:otherwise>
                                            <img src="${pageContext.request.contextPath}/public/admin/assets/images/AvatarIcon2.jpg" alt="${item.fullName}" style="width: 48px; height: 48px; object-fit: cover; border-radius: 8px;" />
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${item.email}</td>
                                <td>${item.phone}</td>
                                <td>${item.address}</td>
                                <td>
                                    <span class="badge ${item.status == 'active' ? 'badge-green' : 'badge-red'}">
                                        ${item.status == 'active' ? 'Hoạt động' : 'Tạm dừng'}
                                    </span>
                                </td>
                                <td>
                                    <div class="inner-buttons">
                                        <c:if test="${loggedInAdmin.hasPermission('user-edit')}">
                                            <a class="inner-edit" href="${pageContext.request.contextPath}/${pathAdmin}/user/edit/${item.id}">
                                                <i class="fa-regular fa-pen-to-square"></i>
                                            </a>
                                        </c:if>
                                        <c:if test="${loggedInAdmin.hasPermission('user-delete')}">
                                            <button class="inner-delete" type="button" button-delete data-api="${pageContext.request.contextPath}/${pathAdmin}/user/delete/${item.id}">
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
                            <td colspan="8" style="text-align: center; padding: 25px; color: #888;">Không tìm thấy người dùng nào.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</div>

<!-- Section-7: Phân trang -->
<div class="section-7" style="display: flex; align-items: center; justify-content: flex-start; margin-top: 20px;">
    <c:choose>
        <c:when test="${not empty userList && pagination.total > 0}">
            <span class="inner-label" style="font-size: 14px; font-weight: 600; color: #202224c2; margin-right: 15px;">
                Hiển thị ${pagination.start} - ${pagination.end} của ${pagination.total}
            </span>
            <select class="inner-pagination" pagination>
                <c:forEach var="i" begin="1" end="${pagination.totalPages > 0 ? pagination.totalPages : 1}">
                    <option value="${i}" ${pagination.currentPage == i ? 'selected' : ''}>Trang ${i}</option>
                </c:forEach>
            </select>
        </c:when>
        <c:otherwise>
            <span class="inner-label" style="font-size: 14px; font-weight: 600; color: #202224c2;">
                Không có dữ liệu để hiển thị
            </span>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="../layouts/footer.jsp" />