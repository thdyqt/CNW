<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<!-- Section 4 -->
<div class="section-4" style="padding-top: 60px">
    <div class="container">
        <h2 class="title">Kết quả tìm kiếm</h2>
        <div class="inner-wrap">
            <c:forEach var="item" items="${tourList}">
                <jsp:include page="../mixins/product-item.jsp" />
            </c:forEach>
        </div>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
