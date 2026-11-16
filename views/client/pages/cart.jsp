<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                    <input id="coupon-input" placeholder="Nhập mã giảm giá" name="coupon" type="text" />
                    <button type="submit">Dùng Mã</button>
                </form>
            </div>
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
                            <input id="full-name-input" placeholder="Họ và tên" name="fullName" type="text" />
                        </div>
                        <div class="inner-input">
                            <input id="phone-input" placeholder="Số điện thoại" name="phone" type="number" />
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
