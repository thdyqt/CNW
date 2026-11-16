<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<h1 class="box-title">Thông tin liên hệ</h1>

<div class="section-6">
    <div class="table-2">
        <table>
            <thead>
                <tr>
                    <th class="inner-center">
                        <input class="inner-check" type="checkbox" />
                    </th>
                    <th>Email</th>
                    <th>Ngày tạo</th>
                    <th class="inner-center">Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${contactList}">
                    <tr>
                        <td class="inner-center">
                            <input class="inner-check" type="checkbox" />
                        </td>
                        <td>${item.email}</td>
                        <td>${item.createdAt}</td>
                        <td class="inner-center">
                            <div class="inner-buttons">
                                <button class="inner-delete" type="button" button-delete data-api="${pageContext.request.contextPath}/${pathAdmin}/contact/delete/${item.id}">
                                    <i class="fa-regular fa-trash-can"></i>
                                </button>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
