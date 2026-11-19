<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../layouts/header.jsp" />

<div class="container">
    <div class="section-11">
        <div class="inner-wrap">
            <h4>Cảm ơn quý khách đã đặt tour! Chúng tôi sẽ liên hệ lại với quý khách trong thời gian sớm nhất.</h4>
            <div class="inner-head">
                <div class="inner-title">Thông tin đơn hàng</div>
            </div>
            <p>Mã đơn hàng: ${orderDetail.code}</p>
            <p>Tên khách hàng: ${orderDetail.fullName}</p>
            <p>Số điện thoại: ${orderDetail.phone}</p>
            <p>Ghi chú: ${orderDetail.note}</p>
            <p>Phương thức thanh toán: ${orderDetail.paymentMethodName}</p>
            <p>Trạng thái thanh toán: ${orderDetail.paymentStatusName}</p>
            <p>Trạng thái đơn hàng: ${orderDetail.statusName}</p>
            <p>Ngày đặt: ${orderDetail.createdAtFormatted}</p>
        </div>
    </div>

    <div class="section-11">
        <div class="inner-wrap">
            <div class="inner-head">
                <div class="inner-title">Danh sách tour</div>
            </div>
            <div class="inner-tour-list">
                <c:forEach var="item" items="${orderDetail.items}">
                    <!-- Tour Item -->
                    <div class="inner-tour-item">
                        <div class="inner-product">
                            <div class="inner-image">
                                <a href="${pageContext.request.contextPath}/tour/detail/${item.slug}">
                                    <img alt="${item.name}" src="${item.avatar}" />
                                </a>
                            </div>
                            <div class="inner-content">
                                <div class="inner-title">
                                    <a href="${pageContext.request.contextPath}/tour/detail/${item.slug}">${item.name}</a>
                                </div>
                                <div class="inner-meta">
                                    <div class="inner-meta-item">
                                        Ngày Khởi Hành: 
                                        <b>${item.departureDateFormatted}</b>
                                    </div>
                                    <div class="inner-meta-item">
                                        Khởi Hành Tại: 
                                        <b>${item.locationFromName}</b>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="inner-quantity">
                            <div class="inner-label">Số Lượng Hành Khách</div>
                            <div class="inner-list">
                                <c:if test="${item.quantityAdult > 0}">
                                    <div class="inner-item">
                                        <div class="inner-item-label">Người lớn: </div>
                                        <div class="inner-price">
                                            <span>${item.quantityAdult}</span>
                                            <span> x </span>
                                            <span class="inner-highlight"><fmt:formatNumber value="${item.priceNewAdult}" pattern="#,###" /></span>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${item.quantityChildren > 0}">
                                    <div class="inner-item">
                                        <div class="inner-item-label">Trẻ em:</div>
                                        <div class="inner-price">
                                            <span>${item.quantityChildren}</span>
                                            <span> x </span>
                                            <span class="inner-highlight"><fmt:formatNumber value="${item.priceNewChildren}" pattern="#,###" /></span>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${item.quantityBaby > 0}">
                                    <div class="inner-item">
                                        <div class="inner-item-label">Em bé:</div>
                                        <div class="inner-price">
                                            <span>${item.quantityBaby}</span>
                                            <span> x </span>
                                            <span class="inner-highlight"><fmt:formatNumber value="${item.priceNewBaby}" pattern="#,###" /></span>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <!-- End Tour Item -->
                </c:forEach>
            </div>
            <div class="inner-list-price">
                <div class="inner-item">
                    <div class="inner-label">Tổng tiền:</div>
                    <div class="inner-price"><fmt:formatNumber value="${orderDetail.subTotal}" pattern="#,###" />đ</div>
                </div>
                <div class="inner-item">
                    <div class="inner-label">Giảm:</div>
                    <div class="inner-price">-0đ</div>
                </div>
                <div class="inner-item">
                    <div class="inner-label">Thanh toán:</div>
                    <div class="inner-total-price"><fmt:formatNumber value="${orderDetail.total}" pattern="#,###" />đ</div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
