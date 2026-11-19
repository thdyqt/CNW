<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />
<c:set var="loggedInAdmin" value="${not empty sessionScope.account ? sessionScope.account : currentAccountAdmin}" />

<h1 class="box-title">Thông tin liên hệ</h1>

<!-- Section-4: Bộ lọc -->
<div class="section-4">
    <div class="inner-wrap">
        <div class="inner-item inner-label">
            <i class="fa-solid fa-filter"></i>
            Bộ lọc
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
        <div class="inner-check-status" change-multi data-api="${pageContext.request.contextPath}/${pathAdmin}/contact/change-multi">
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
        <c:if test="${loggedInAdmin.hasPermission('contact-trash')}">
            <div class="inner-button-delete">
                <a href="${pageContext.request.contextPath}/${pathAdmin}/contact/trash">Thùng rác</a>
            </div>
        </c:if>
    </div>
</div>

<!-- Section-6: Bảng danh sách liên hệ -->
<div class="section-6">
    <div class="table-2">
        <table>
            <thead>
                <tr>
                    <th class="inner-center" style="width: 50px;">
                        <input class="inner-check" type="checkbox" check-all />
                    </th>
                    <th>Email</th>
                    <th>Ngày tạo</th>
                    <th class="inner-center" style="width: 120px;">Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty contactList}">
                        <c:forEach var="item" items="${contactList}">
                            <tr>
                                <td class="inner-center">
                                    <input class="inner-check" type="checkbox" check-item="${item.id}" />
                                </td>
                                <td>${item.email}</td>
                                <td>${item.createdAtFormatted}</td>
                                <td class="inner-center">
                                    <div class="inner-buttons">
                                        <c:if test="${loggedInAdmin.hasPermission('contact-delete')}">
                                            <button class="inner-delete" type="button" button-delete data-api="${pageContext.request.contextPath}/${pathAdmin}/contact/delete/${item.id}">
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
                            <td colspan="4" class="inner-center" style="padding: 30px;">Không có thông tin liên hệ nào!</td>
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
