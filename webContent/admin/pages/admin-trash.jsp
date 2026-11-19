<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Thùng rác tài khoản quản trị</h1>

<!-- Section-5: Thao tác, Tìm kiếm & Quay lại danh sách -->
<div class="section-5">
    <div class="inner-wrap">
        <div class="inner-check-status" change-multi data-api="${pageContext.request.contextPath}/${pathAdmin}/setting/account-admin/change-multi">
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
            <a href="${pageContext.request.contextPath}/${pathAdmin}/setting/account-admin/list">Quay lại danh sách</a>
        </div>
    </div>
</div>

<!-- Section-6: Bảng danh sách thùng rác tài khoản quản trị -->
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
                    <th>Nhóm quyền</th>
                    <th>Chức vụ</th>
                    <th>Thời gian xóa</th>
                    <th class="inner-center">Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty accountTrashList}">
                        <c:forEach var="item" items="${accountTrashList}">
                            <tr>
                                <td class="inner-center">
                                    <input class="inner-check" type="checkbox" check-item="${item.id}" />
                                </td>
                                <td><b>${item.fullName}</b></td>
                                <td class="inner-center">
                                    <div class="inner-image" style="width: 40px; height: 40px; border-radius: 50%; overflow: hidden; margin: 0 auto;">
                                        <img src="${item.avatarOrDefault}" alt="${item.fullName}" style="width: 100%; height: 100%; object-fit: cover;" />
                                    </div>
                                </td>
                                <td>${item.email}</td>
                                <td>${item.phone}</td>
                                <td>${item.roleName}</td>
                                <td>${item.position}</td>
                                <td>
                                    <div style="font-size: 13px; color: #777;">${item.deletedAtFormatted}</div>
                                </td>
                                <td class="inner-center">
                                    <div class="inner-buttons">
                                        <button class="inner-undo" type="button" button-undo data-api="${pageContext.request.contextPath}/${pathAdmin}/setting/account-admin/undo/${item.id}" title="Khôi phục">
                                            <i class="fa-solid fa-rotate-left"></i>
                                        </button>
                                        <button class="inner-delete" type="button" button-delete-destroy data-api="${pageContext.request.contextPath}/${pathAdmin}/setting/account-admin/delete-destroy/${item.id}" title="Xóa vĩnh viễn">
                                            <i class="fa-regular fa-trash-can"></i>
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="9" class="inner-center" style="padding: 30px;">Thùng rác trống!</td>
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
