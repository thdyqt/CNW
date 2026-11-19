<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<!-- Section 4 -->
<div class="section-4" style="padding-top: 60px">
    <div class="container">
        <div class="section-head">
            <div class="section-tag"><i class="fa-solid fa-magnifying-glass"></i> Tìm Kiếm Tour</div>
            <h2 class="title">Kết quả tìm kiếm</h2>
            <c:if test="${not empty tourList}">
                <p class="section-desc">Tìm thấy <b>${tourList.size()}</b> chuyến đi phù hợp với yêu cầu của bạn</p>
            </c:if>
        </div>
        <c:choose>
            <c:when test="${not empty tourList}">
                <div class="inner-wrap">
                    <c:forEach var="item" items="${tourList}">
                        <c:set var="item" value="${item}" scope="request" />
                        <jsp:include page="../mixins/product-item.jsp" />
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div style="text-align: center; padding: 60px 20px; background: white; border-radius: 20px; border: 1px solid #E2E8F0; max-width: 650px; margin: 0 auto; box-shadow: 0 4px 20px rgba(0,0,0,0.04);">
                    <i class="fa-solid fa-compass" style="font-size: 48px; color: #94A3B8; margin-bottom: 16px;"></i>
                    <h3 style="font-size: 20px; font-weight: 700; color: #1E293B; margin-bottom: 8px;">Không tìm thấy tour phù hợp</h3>
                    <p style="color: #64748B; font-size: 15px; margin-bottom: 24px; line-height: 1.6;">Rất tiếc, không có tour du lịch nào khớp với tiêu chí tìm kiếm của bạn. Hãy thử thay đổi địa điểm hoặc thời gian khởi hành nhé!</p>
                    <a href="${pageContext.request.contextPath}/search" class="button-outline">
                        <span>Xem tất cả các tour</span>
                        <i class="fa-solid fa-arrow-right"></i>
                    </a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
