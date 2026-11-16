<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Danh sách khách hàng</h1>

<div class="section-6">
    <div class="table-2">
        <table>
            <thead>
                <tr>
                    <th>Họ tên</th>
                    <th>Số điện thoại</th>
                    <th>Email</th>
                    <th>Số đơn đã đặt</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><b>Phan Thanh Duy</b></td>
                    <td>0912345678</td>
                    <td>duy@gmail.com</td>
                    <td>3 đơn hàng</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
