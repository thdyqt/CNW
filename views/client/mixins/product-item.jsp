<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="product-item">
    <div class="inner-image">
        <a href="${pageContext.request.contextPath}/tour/detail/${item.slug}">
            <img alt="${item.name}" src="${item.avatar}" />
        </a>
    </div>
    <div class="inner-discount">
        <i class="fa-solid fa-bolt"></i>
        <div class="inner-discount-percent">GIẢM -${item.discount}%</div>
    </div>
    <div class="inner-content">
        <h3 class="inner-title">
            <a href="${pageContext.request.contextPath}/tour/detail/${item.slug}">${item.name}</a>
        </h3>
        <div class="inner-prices">
            <c:if test="${item.discount > 0}">
                <div class="inner-old-price">
                    <fmt:formatNumber value="${item.priceAdult}" pattern="#,###" />
                    <span class="unit">đ</span>
                </div>
            </c:if>
            <div class="inner-new-price">
                <fmt:formatNumber value="${item.priceNewAdult}" pattern="#,###" />
                <span class="unit">đ</span>
            </div>
        </div>
        <div class="inner-desc">
            <div class="inner-desc-item">
                Ngày Khởi Hành: 
                <b>${item.departureDateFormatted}</b>
            </div>
            <div class="inner-desc-item">
                Thời Gian: 
                <b>${item.time}</b>
            </div>
        </div>
        <div class="inner-meta">
            <div class="inner-stock">
                <div class="inner-label">Số chỗ còn:</div>
                <div class="inner-number">${item.stockAdult}</div>
            </div>
        </div>
    </div>
</div>
