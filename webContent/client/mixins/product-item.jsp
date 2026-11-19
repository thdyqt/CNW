<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="vi_VN" />

<div class="product-item">
    <div class="inner-image">
        <a href="${pageContext.request.contextPath}/tour/detail/${item.slug}">
            <c:choose>
                <c:when test="${item.avatar.startsWith('http')}">
                    <img alt="${item.name}" src="${item.avatar}" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/public/assets/images/Product-1.jpg';" />
                </c:when>
                <c:when test="${item.avatar.startsWith('/')}">
                    <img alt="${item.name}" src="${pageContext.request.contextPath}${item.avatar}" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/public/assets/images/Product-1.jpg';" />
                </c:when>
                <c:otherwise>
                    <img alt="${item.name}" src="${pageContext.request.contextPath}/${item.avatar}" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/public/assets/images/Product-1.jpg';" />
                </c:otherwise>
            </c:choose>
        </a>
        <c:if test="${item.discount > 0}">
            <div class="inner-discount">
                <i class="fa-solid fa-bolt"></i>
                <div class="inner-discount-percent">GIẢM -${item.discount}%</div>
            </div>
        </c:if>
        <div class="inner-loc-badge">
            <i class="fa-solid fa-location-dot"></i> Tour Trọn Gói
        </div>
    </div>
    <div class="inner-content">
        <h3 class="inner-title">
            <a href="${pageContext.request.contextPath}/tour/detail/${item.slug}" title="${item.name}">${item.name}</a>
        </h3>
        <div class="inner-desc">
            <div class="inner-desc-item">
                <i class="fa-regular fa-calendar-check"></i>
                <span>Khởi hành: <b>${item.departureDateFormatted}</b></span>
            </div>
            <div class="inner-desc-item">
                <i class="fa-regular fa-clock"></i>
                <span>Thời gian: <b>${item.time}</b></span>
            </div>
        </div>
        <div class="inner-meta">
            <div class="inner-rating">
                <div class="inner-stars">
                    <i class="fa-solid fa-star"></i>
                    <i class="fa-solid fa-star"></i>
                    <i class="fa-solid fa-star"></i>
                    <i class="fa-solid fa-star"></i>
                    <i class="fa-solid fa-star"></i>
                </div>
                <div class="inner-number">5.0</div>
            </div>
            <div class="inner-stock">
                <div class="inner-label">Còn lại:</div>
                <div class="inner-number">${item.stockAdult}</div>
            </div>
        </div>
        <div class="inner-footer-bar">
            <div class="inner-prices">
                <c:if test="${item.discount > 0}">
                    <div class="inner-old-price">
                        <fmt:formatNumber value="${item.priceAdult}" type="number" groupingUsed="true" />
                        <span class="unit">đ</span>
                    </div>
                </c:if>
                <div class="inner-new-price">
                    <fmt:formatNumber value="${item.priceNewAdult}" type="number" groupingUsed="true" />
                    <span class="unit">đ</span>
                </div>
            </div>
            <a class="inner-detail-btn" href="${pageContext.request.contextPath}/tour/detail/${item.slug}" title="Xem chi tiết">
                <i class="fa-solid fa-arrow-right"></i>
            </a>
        </div>
    </div>
</div>
