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
                <div class="inner-info-2" style="display: flex; align-items: center; justify-content: space-between; margin-bottom: 28px; padding-bottom: 14px; border-bottom: 1px solid #E2E8F0;">
                    <div class="inner-sort" style="display: flex; align-items: center; gap: 16px;">
                        <div class="inner-label" style="color: #64748B; font-weight: 600; font-size: 14px;">Sắp xếp:</div>
                        <div class="inner-list" style="display: flex; align-items: center; gap: 16px;">
                            <button type="button" button-sort="price-asc" style="outline: none; border: 0; background: transparent; cursor: pointer; font-size: 13px; font-weight: 500; color: ${param.sortKey == 'price' && param.sortValue == 'asc' ? '#2563EB' : '#64748B'};">
                                Giá tăng dần
                                <i class="fa-solid fa-square-caret-up"></i>
                            </button>
                            <button type="button" button-sort="price-desc" style="outline: none; border: 0; background: transparent; cursor: pointer; font-size: 13px; font-weight: 500; color: ${param.sortKey == 'price' && param.sortValue == 'desc' ? '#2563EB' : '#64748B'};">
                                Giá giảm dần
                                <i class="fa-solid fa-square-caret-down"></i>
                            </button>
                            <button type="button" button-sort="discount-desc" style="outline: none; border: 0; background: transparent; cursor: pointer; font-size: 13px; font-weight: 500; color: ${empty param.sortKey || (param.sortKey == 'discount' && param.sortValue == 'desc') ? '#2563EB' : '#64748B'};">
                                Khuyến Mãi Hot
                                <i class="fa-solid fa-tag"></i>
                            </button>
                            <button type="button" button-sort="view-desc" style="outline: none; border: 0; background: transparent; cursor: pointer; font-size: 13px; font-weight: 500; color: ${param.sortKey == 'view' ? '#2563EB' : '#64748B'};">
                                Xem Nhiều
                                <i class="fa-solid fa-eye"></i>
                            </button>
                        </div>
                    </div>
                </div>
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
