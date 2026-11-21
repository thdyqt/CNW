<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="box-breadcrumb">
    <div class="inner-image">
        <c:set var="breadcrumbImg" value="${not empty breadcrumb.image ? breadcrumb.image : 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=1920&q=80'}" />
        <c:if test="${not empty breadcrumbImg and breadcrumbImg.startsWith('/')}">
            <c:set var="breadcrumbImg" value="${pageContext.request.contextPath}${breadcrumbImg}" />
        </c:if>
        <img alt="${breadcrumb.title}" src="${breadcrumbImg}" onerror="this.onerror=null;this.src='https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=1920&q=80';" />
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
