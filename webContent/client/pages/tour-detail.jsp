<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../layouts/header.jsp" />

<jsp:include page="../mixins/box-breadcrumb.jsp" />

<div class="section-10">
    <div class="container">
        <div class="inner-wrap">
            <div class="inner-left">
                <div class="box-images">
                    <div class="inner-image-main">
                        <div class="swiper swiper-box-image-main">
                            <div class="swiper-wrapper">
                                <c:forEach var="image" items="${tourDetail.images}">
                                    <div class="swiper-slide">
                                        <div class="inner-image">
                                            <img alt="" src="${image}" />
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="inner-image-thumb">
                        <div class="swiper swiper-box-image-thumb">
                            <div class="swiper-wrapper">
                                <c:forEach var="image" items="${tourDetail.images}">
                                    <div class="swiper-slide">
                                        <div class="inner-image">
                                            <img alt="" src="${image}" />
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="box-tour-info">
                    <div class="inner-title">Thông Tin Tour</div>
                    <div class="inner-content">${tourDetail.information}</div>
                    <div class="inner-read-more">
                        <button class="button-outline" type="button">Xem tất cả</button>
                    </div>
                </div>

                <div class="box-tour-schedule">
                    <div class="inner-title-main">Lịch Trình Tour</div>
                    <div class="inner-list">
                        <c:forEach var="item" items="${tourDetail.schedules}">
                            <div class="inner-item">
                                <div class="inner-title">${item.title}</div>
                                <div class="inner-content">${item.description}</div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <div class="inner-right">
                <div class="box-tour-detail">
                    <div class="inner-title-main">Chuyến Đi Của Bạn</div>
                    <div class="inner-product">
                        <div class="inner-image">
                            <img alt="${tourDetail.name}" src="${tourDetail.avatar}" />
                        </div>
                        <div class="inner-info">
                            <div class="inner-title">${tourDetail.name}</div>
                            <div class="inner-rating">
                                <div class="inner-stars">
                                    <i class="fa-solid fa-star"></i>
                                    <i class="fa-solid fa-star"></i>
                                    <i class="fa-solid fa-star"></i>
                                    <i class="fa-solid fa-star"></i>
                                    <i class="fa-solid fa-star"></i>
                                </div>
                                <div class="inner-number">500 lượt đánh giá</div>
                            </div>
                        </div>
                    </div>

                    <div class="inner-meta">
                        <div class="inner-item">
                            <i class="fa-solid fa-ticket-simple"></i>
                            <span>Mã Tour: </span>
                            <span class="inner-highlight">${not empty tourDetail.code ? tourDetail.code : '28T00001'}</span>
                        </div>
                        <div class="inner-item">
                            <i class="fa-solid fa-calendar-days"></i>
                            <span>Thời Gian: </span>
                            <span class="inner-highlight">${tourDetail.time}</span>
                        </div>
                        <div class="inner-item">
                            <i class="fa-solid fa-car"></i>
                            <span>Phương Tiện: </span>
                            <span class="inner-highlight">${tourDetail.vehicle}</span>
                        </div>
                        <div class="inner-item">
                            <i class="fa-solid fa-calendar-days"></i>
                            <span>Ngày Khởi Hành: </span>
                            <span class="inner-highlight">${tourDetail.departureDateFormatted}</span>
                        </div>
                    </div>

                    <div class="inner-form">
                        <div class="inner-group">
                            <label class="inner-label" for="location-from">Khởi Hành Tại:</label>
                            <select id="location-from" location-from>
                                <c:forEach var="item" items="${cityList}">
                                    <option value="${item.id}">${item.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="inner-group">
                            <label class="inner-label" for="count">Số Lượng Hành Khách:</label>
                            <div class="inner-list">
                                <div class="inner-item">
                                    <label class="inner-item-label" for="stockAdult">Người lớn:</label>
                                    <div class="inner-item-input">
                                        <input id="stockAdult" min="0" max="${tourDetail.stockAdult}" value="1" name="stockAdult" type="number" input-stock-adult price="${tourDetail.priceNewAdult}" />
                                    </div>
                                    <div class="inner-price">
                                        <span stock-adult>1</span>
                                        <span> x </span>
                                        <span class="inner-highlight"><fmt:formatNumber value="${tourDetail.priceNewAdult}" pattern="#,###" /></span>
                                    </div>
                                </div>
                                <div class="inner-item">
                                    <label class="inner-item-label" for="stockChildren">Trẻ em:</label>
                                    <div class="inner-item-input">
                                        <input id="stockChildren" min="0" max="${tourDetail.stockChildren}" value="0" name="stockChildren" type="number" input-stock-children price="${tourDetail.priceNewChildren}" />
                                    </div>
                                    <div class="inner-price">
                                        <span stock-children>0</span>
                                        <span> x </span>
                                        <span class="inner-highlight"><fmt:formatNumber value="${tourDetail.priceNewChildren}" pattern="#,###" /></span>
                                    </div>
                                </div>
                                <div class="inner-item">
                                    <label class="inner-item-label" for="stockBaby">Em bé:</label>
                                    <div class="inner-item-input">
                                        <input id="stockBaby" min="0" max="${tourDetail.stockBaby}" value="0" name="stockBaby" type="number" input-stock-baby price="${tourDetail.priceNewBaby}" />
                                    </div>
                                    <div class="inner-price">
                                        <span stock-baby>0</span>
                                        <span> x </span>
                                        <span class="inner-highlight"><fmt:formatNumber value="${tourDetail.priceNewBaby}" pattern="#,###" /></span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="inner-total">
                            <div class="inner-total-label">Tổng cộng</div>
                            <div class="inner-total-price" total-price><fmt:formatNumber value="${tourDetail.priceNewAdult}" pattern="#,###" />đ</div>
                        </div>
                        <button class="inner-button-add-cart" type="button" tour-id="${tourDetail.id}">Thêm Vào Giỏ Hàng</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
