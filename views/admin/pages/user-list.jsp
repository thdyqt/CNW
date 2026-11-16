<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Quản lý người dùng</h1>

<div class="section-6">
    <div class="table-2">
        <table>
            <thead>
                <tr>
                    <th class="inner-center"><input class="inner-check" type="checkbox" /></th>
                    <th>Họ tên</th>
                    <th>Email</th>
                    <th>Số điện thoại</th>
                    <th>Trạng thái</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td class="inner-center"><input class="inner-check" type="checkbox" /></td>
                    <td><b>Lê Văn A</b></td>
                    <td>vana@gmail.com</td>
                    <td>0912345678</td>
                    <td><span class="badge badge-green">Hoạt động</span></td>
                </tr>
                <tr>
                    <td class="inner-center"><input class="inner-check" type="checkbox" /></td>
                    <td><b>Nguyễn Thị B</b></td>
                    <td>thib@gmail.com</td>
                    <td>0987654321</td>
                    <td><span class="badge badge-green">Hoạt động</span></td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
