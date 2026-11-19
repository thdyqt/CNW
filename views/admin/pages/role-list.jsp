<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../layouts/header.jsp" />
<c:set var="loggedInAdmin" value="${not empty sessionScope.account ? sessionScope.account : currentAccountAdmin}" />

<h1 class="box-title">Nhóm quyền</h1>

<!-- Section-5: Thao tác, Tìm kiếm, Tạo mới, Thùng rác -->
<div class="section-5">
    <div class="inner-wrap">
        <div class="inner-check-status" change-multi data-api="${pageContext.request.contextPath}/${pathAdmin}/setting/role/change-multi">
            <div class="inner-item">
                <select>
                    <option value="">-- Hành động --</option>
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
        <c:if test="${loggedInAdmin.hasPermission('role-create')}">
            <div class="inner-button-create">
                <a href="${pageContext.request.contextPath}/${pathAdmin}/setting/role/create">+ Tạo mới</a>
            </div>
        </c:if>
        <c:if test="${loggedInAdmin.hasPermission('role-trash')}">
            <div class="inner-button-delete">
                <a href="${pageContext.request.contextPath}/${pathAdmin}/setting/role/trash">Thùng rác</a>
            </div>
        </c:if>
    </div>
</div>

<!-- Section-6: Bảng danh sách nhóm quyền -->
<div class="section-6">
    <div class="table-2">
        <table>
            <thead>
                <tr>
                    <th class="inner-center" style="width: 50px;">
                        <input class="inner-check" type="checkbox" check-all />
                    </th>
                    <th>Tên nhóm quyền</th>
                    <th>Mô tả ngắn</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty roleList}">
                        <c:forEach var="item" items="${roleList}">
                            <tr>
                                <td class="inner-center">
                                    <input class="inner-check" type="checkbox" check-item="${item.id}" />
                                </td>
                                <td>${item.name}</td>
                                <td>${item.description}</td>
                                <td>
                                    <div class="inner-buttons">
                                        <c:if test="${loggedInAdmin.hasPermission('role-edit')}">
                                            <a class="inner-edit" href="${pageContext.request.contextPath}/${pathAdmin}/setting/role/edit/${item.id}">
                                                <i class="fa-regular fa-pen-to-square"></i>
                                            </a>
                                        </c:if>
                                        <c:if test="${loggedInAdmin.hasPermission('role-delete')}">
                                            <button class="inner-delete" type="button" button-delete data-api="${pageContext.request.contextPath}/${pathAdmin}/setting/role/delete/${item.id}">
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
                            <td colspan="4" style="text-align: center; padding: 25px; color: #888;">Không tìm thấy nhóm quyền nào.</td>
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
        <c:when test="${not empty roleList && pagination.total > 0}">
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