<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../layouts/header.jsp" />

<div class="section-11" page-cart>
    <div class="container">
        <div class="inner-wrap">
            <div class="inner-head">
                <div class="inner-title">Giỏ Hàng</div>
                <a class="inner-back" href="${pageContext.request.contextPath}/">
                    Quay lại mua hàng
                    <i class="fa-solid fa-angle-right"></i>
                </a>
            </div>
            <div class="inner-tour-list" cart-list></div>
            <div class="inner-coupon">
                <form id="coupon-form" action="">
                    <input id="coupon-input" placeholder="Nhập mã giảm giá (DUT2026, DUT500K...)" name="coupon" type="text" autocomplete="off" />
                    <button type="submit" id="btn-apply-coupon">Dùng Mã</button>
                </form>
                <div id="coupon-applied-box" class="inner-applied-box" style="display: none;">
                    <div class="inner-applied-tag">
                        <i class="fa-solid fa-ticket"></i>
                        <span id="applied-coupon-code" class="inner-code"></span>
                        <span id="applied-coupon-desc" class="inner-desc"></span>
                        <button type="button" id="btn-remove-coupon" title="Gỡ mã giảm giá" class="inner-remove">&times;</button>
                    </div>
                </div>
            </div>

            <!-- Danh Sách Mã Giảm Giá Có Thể Sử Dụng -->
            <c:if test="${not empty couponList}">
                <div class="inner-cart-coupons">
                    <div class="inner-coupons-header">
                        <div class="header-left">
                            <i class="fa-solid fa-tags"></i>
                            <span>Mã giảm giá có sẵn cho bạn</span>
                            <span class="header-badge">${couponList.size()}</span>
                        </div>
                        <div class="header-hint">
                            <i class="fa-solid fa-circle-info"></i> Nhấn vào thẻ để xem chi tiết điều kiện
                        </div>
                    </div>
                    <div class="inner-coupons-grid">
                        <c:forEach var="c" items="${couponList}">
                            <div class="inner-coupon-pill" 
                                 coupon-pill="${c.code}" 
                                 data-code="${c.code}"
                                 data-title="<c:out value="${c.title}" />"
                                 data-type="${c.type}"
                                 data-value-formatted="${c.valueFormatted}"
                                 data-min="${c.minOrderValue}"
                                 data-min-formatted="${c.minOrderValueFormatted}"
                                 data-max="${c.maxDiscountValue}"
                                 data-max-formatted="${c.maxDiscountValueFormatted}"
                                 data-end="${c.endDateFormatted}"
                                 title="Bấm để xem chi tiết điều kiện mã giảm giá">
                                <div class="notch-top"></div>
                                <div class="notch-bottom"></div>
                                <div class="inner-pill-left">
                                    <div class="inner-pill-badge">${c.valueFormatted}</div>
                                    <div class="inner-pill-sub">${c.type == 'percentage' ? 'GIẢM GIÁ' : 'TRỰC TIẾP'}</div>
                                </div>
                                <div class="inner-pill-body">
                                    <div class="inner-pill-top">
                                        <span class="inner-pill-code">${c.code}</span>
                                        <button type="button" class="btn-use-coupon" button-quick-apply="${c.code}">
                                            Áp dụng
                                        </button>
                                    </div>
                                    <div class="inner-pill-condition">
                                        <c:choose>
                                            <c:when test="${c.minOrderValue > 0}">
                                                Đơn từ ${c.minOrderValueFormatted}
                                            </c:when>
                                            <c:otherwise>Mọi giá trị đơn hàng</c:otherwise>
                                        </c:choose>
                                        <c:if test="${c.maxDiscountValue > 0}">
                                            • Tối đa ${c.maxDiscountValueFormatted}
                                        </c:if>
                                    </div>
                                    <div class="inner-pill-bottom">
                                        <span class="inner-pill-hsd">
                                            <i class="fa-regular fa-clock"></i> HSD: ${empty c.endDateFormatted ? 'Vô thời hạn' : c.endDateFormatted}
                                        </span>
                                        <span class="inner-pill-detail-tag">
                                            <i class="fa-solid fa-circle-info"></i> Chi tiết
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
            <div class="inner-list-price">
                <div class="inner-item">
                    <div class="inner-label">Tạm tính:</div>
                    <div class="inner-price">
                        <span cart-sub-total>0</span>
                        <span>đ</span>
                    </div>
                </div>
                <div class="inner-item">
                    <div class="inner-label">Giảm:</div>
                    <div class="inner-price">
                        <span>-</span>
                        <span cart-discount>0</span>
                        <span>đ</span>
                    </div>
                </div>
                <div class="inner-item">
                    <div class="inner-label">Thanh toán:</div>
                    <div class="inner-total-price">
                        <span cart-total>0</span>
                        <span>đ</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="section-12">
    <div class="container">
        <div class="inner-wrap">
            <form id="order-form" action="">
                <div class="inner-info">
                    <div class="inner-title-main">Thông Tin Khách Hàng</div>
                    <div class="inner-inputs">
                        <div class="inner-input">
                            <input id="full-name-input" placeholder="Họ và tên *" name="fullName" type="text" />
                        </div>
                        <div class="inner-input">
                            <input id="phone-input" placeholder="Số điện thoại *" name="phone" type="number" />
                        </div>
                        <div class="inner-input inner-input-full">
                            <input id="email-input" placeholder="Email nhận xác nhận đơn hàng & lịch trình tour *" name="email" type="email" />
                        </div>
                    </div>
                    <textarea placeholder="Ghi chú" name="note"></textarea>
                </div>
                <div class="inner-method">
                    <div class="inner-title-main">Chọn phương thức thanh toán</div>
                    <div class="inner-list">
                        <div class="inner-item">
                            <input id="methodMoney" value="money" name="method" type="radio" />
                            <label for="methodMoney">Thanh toán tiền mặt khi đi tour</label>
                        </div>
                        <div class="inner-item">
                            <input id="methodVNPay" value="vnpay" name="method" type="radio" />
                            <label for="methodVNPay">VNPay</label>
                        </div>
                        <div class="inner-item">
                            <input id="methodBank" value="bank" name="method" type="radio" />
                            <label for="methodBank">Chuyển khoản ngân hàng</label>
                        </div>
                    </div>
                    <div class="inner-info-bank">
                        <div class="inner-title">Thông tin chuyển khoản</div>
                        <div class="inner-content">
                            <div class="inner-text">Ngân hàng: MBBank</div>
                            <div class="inner-text">Tên tài khoản: Phan Thanh Duy</div>
                            <div class="inner-text">Số tài khoản: 000002907</div>
                        </div>
                    </div>
                </div>
                <div class="inner-button">
                    <button type="submit">ĐẶT TOUR</button>
                </div>
            </form>
        </div>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
