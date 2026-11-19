<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="account-header.jsp" />

<c:if test="${not empty bodyPage}">
    <jsp:include page="${bodyPage}" />
</c:if>

<jsp:include page="account-footer.jsp" />
