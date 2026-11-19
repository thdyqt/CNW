<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach var="item" items="${categoryList}">
    <option value="${item.id}" ${parent == item.id ? 'selected' : ''}>${item.name}</option>
    <c:if test="${not empty item.children}">
        <c:forEach var="child" items="${item.children}">
            <option value="${child.id}" ${parent == child.id ? 'selected' : ''}>-- ${child.name}</option>
            <c:if test="${not empty child.children}">
                <c:forEach var="subChild" items="${child.children}">
                    <option value="${subChild.id}" ${parent == subChild.id ? 'selected' : ''}>---- ${subChild.name}</option>
                </c:forEach>
            </c:if>
        </c:forEach>
    </c:if>
</c:forEach>
