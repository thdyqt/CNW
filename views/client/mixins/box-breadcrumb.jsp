<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="box-breadcrumb">
    <div class="inner-image">
        <img alt="${breadcrumb.title}" src="${breadcrumb.image}" />
    </div>
    <div class="inner-content">
        <div class="container">
            <h1 class="inner-title">${breadcrumb.title}</h1>
            <nav class="inner-list">
                <ul>
                    <c:forEach var="item" items="${breadcrumb.list}">
                        <li>
                            <a href="${pageContext.request.contextPath}${item.link}">${item.title}</a>
                            <i class="fa-solid fa-angles-right"></i>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </div>
    </div>
</div>
