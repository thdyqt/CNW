<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Nhóm quyền</h1>

<div class="section-5">
    <div class="inner-wrap">
        <div class="inner-button-create">
            <a href="${pageContext.request.contextPath}/${pathAdmin}/setting/role/create">+ Tạo mới nhóm quyền</a>
        </div>
    </div>
</div>

<div class="section-6">
    <div class="table-2">
        <table>
            <thead>
                <tr>
                    <th>Tên nhóm quyền</th>
                    <th>Mô tả</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><b>Quản trị viên cấp cao</b></td>
                    <td>Toàn quyền quản trị hệ thống, xem, sửa, xóa tất cả các mục.</td>
                    <td>
                        <div class="inner-buttons">
                            <a class="inner-edit" href="${pageContext.request.contextPath}/${pathAdmin}/setting/role/edit/1">
                                <i class="fa-regular fa-pen-to-square"></i>
                            </a>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><b>Quản lý Tour</b></td>
                    <td>Chỉ có quyền quản lý danh mục và tour.</td>
                    <td>
                        <div class="inner-buttons">
                            <a class="inner-edit" href="${pageContext.request.contextPath}/${pathAdmin}/setting/role/edit/2">
                                <i class="fa-regular fa-pen-to-square"></i>
                            </a>
                        </div>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
