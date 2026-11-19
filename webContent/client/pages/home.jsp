<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${empty tourListSection2}">
    <c:redirect url="/home" />
</c:if>

<jsp:include page="../layouts/header.jsp" />

<div class="section-1">
    <div class="container">
        <div class="inner-wrap">
            <div class="inner-badge" data-aos-duration="800" data-aos="fade-down">
                <i class="fa-solid fa-sparkles"></i>
                <span>Trải Nghiệm Đẳng Cấp Cùng DUT Travel</span>
            </div>
            <h1 class="inner-title" data-aos-duration="800" data-aos="fade-up">
                Du lịch Châu Á - Khám phá Mỹ, Úc, Âu<br>
                <span>Đi nơi đâu bạn muốn</span>
            </h1>
            <p class="inner-desc" data-aos-delay="150" data-aos-duration="800" data-aos="fade-up">
                Khám phá những vùng đất mới cùng các chương trình tour trọn gói hấp dẫn, chất lượng dịch vụ chuẩn 5 sao và ưu đãi độc quyền.
            </p>
            <form class="inner-form" data-aos-delay="300" data-aos-duration="800" data-aos="fade-up" action="${pageContext.request.contextPath}/search" method="GET" form-search>
                <div class="inner-box inner-location">
                    <div class="inner-input-group">
                        <img class="inner-icon" alt="" src="${pageContext.request.contextPath}/public/assets/images/Location.svg" />
                        <input class="inner-input" placeholder="Bạn muốn đi đâu?" type="text" name="locationTo" autocomplete="off" />
                        <i class="fa-solid fa-angle-down inner-down"></i>
                    </div>
                    <div class="inner-suggest">
                        <div class="inner-suggest-title">
                            <i class="fa-solid fa-location-dot"></i>
                            Địa Điểm Hot Nhất
                        </div>
                        <div class="inner-suggest-list">
                            <div class="inner-item">
                                <div class="inner-item-image">
                                    <img alt="Nhật Bản" src="https://images.unsplash.com/photo-1493976040374-85c8e12f0c0e?auto=format&fit=crop&w=800&q=80" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/public/assets/images/Product-1.jpg';" />
                                </div>
                                <div class="inner-item-content">
                                    <div class="inner-item-title">Nhật Bản</div>
                                    <div class="inner-item-desc">50+ tours khám phá</div>
                                </div>
                            </div>
                            <div class="inner-item">
                                <div class="inner-item-image">
                                    <img alt="Hàn Quốc" src="https://images.unsplash.com/photo-1538485399081-7191377e8241?auto=format&fit=crop&w=800&q=80" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/public/assets/images/Product-1.jpg';" />
                                </div>
                                <div class="inner-item-content">
                                    <div class="inner-item-title">Hàn Quốc</div>
                                    <div class="inner-item-desc">40+ tours trọn gói</div>
                                </div>
                            </div>
                            <div class="inner-item">
                                <div class="inner-item-image">
                                    <img alt="Thái Lan" src="https://images.unsplash.com/photo-1508009603885-50cf7c579365?auto=format&fit=crop&w=800&q=80" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/public/assets/images/Product-1.jpg';" />
                                </div>
                                <div class="inner-item-content">
                                    <div class="inner-item-title">Thái Lan</div>
                                    <div class="inner-item-desc">35+ tours giá tốt</div>
                                </div>
                            </div>
                            <div class="inner-item">
                                <div class="inner-item-image">
                                    <img alt="Châu Âu" src="https://images.unsplash.com/photo-1502602898657-3e91760cbb34?auto=format&fit=crop&w=800&q=80" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/public/assets/images/Product-1.jpg';" />
                                </div>
                                <div class="inner-item-content">
                                    <div class="inner-item-title">Châu Âu</div>
                                    <div class="inner-item-desc">25+ hành trình vàng</div>
                                </div>
                            </div>
                            <div class="inner-item">
                                <div class="inner-item-image">
                                    <img alt="Phú Quốc" src="https://res.cloudinary.com/r5hwa2cj/image/upload/v1791715757/t6r3pcaljnvzmjd1q1gf.jpg" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/public/assets/images/Product-1.jpg';" />
                                </div>
                                <div class="inner-item-content">
                                    <div class="inner-item-title">Phú Quốc</div>
                                    <div class="inner-item-desc">Nghỉ dưỡng biển đảo</div>
                                </div>
                            </div>
                            <div class="inner-item">
                                <div class="inner-item-image">
                                    <img alt="Đà Nẵng" src="https://res.cloudinary.com/r5hwa2cj/image/upload/v1791715898/adwwpovg9aj8np43mryj.jpg" onerror="this.onerror=null;this.src='${pageContext.request.contextPath}/public/assets/images/Product-1.jpg';" />
                                </div>
                                <div class="inner-item-content">
                                    <div class="inner-item-title">Đà Nẵng - Hội An</div>
                                    <div class="inner-item-desc">Di sản miền Trung</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="inner-box inner-user">
                    <div class="inner-input-group">
                        <img class="inner-icon" alt="" src="${pageContext.request.contextPath}/public/assets/images/User.svg" />
                        <input class="inner-input" readonly="" placeholder="Số lượng hành khách" type="text" />
                        <i class="fa-solid fa-angle-down inner-down"></i>
                    </div>
                    <div class="inner-quantity">
                        <div class="inner-item">
                            <div class="inner-label">
                                <b>Người lớn</b>
                                <span>Từ 12 tuổi trở lên</span>
                            </div>
                            <div class="inner-count">
                                <span class="inner-down">-</span>
                                <span class="inner-number" stock-adult>0</span>
                                <span class="inner-up">+</span>
                            </div>
                        </div>
                        <div class="inner-item">
                            <div class="inner-label">
                                <b>Trẻ em</b>
                                <span>Từ 2 - 11 tuổi</span>
                            </div>
                            <div class="inner-count">
                                <span class="inner-down">-</span>
                                <span class="inner-number" stock-children>0</span>
                                <span class="inner-up">+</span>
                            </div>
                        </div>
                        <div class="inner-item">
                            <div class="inner-label">
                                <b>Em bé</b>
                                <span>Dưới 2 tuổi</span>
                            </div>
                            <div class="inner-count">
                                <span class="inner-down">-</span>
                                <span class="inner-number" stock-baby>0</span>
                                <span class="inner-up">+</span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="inner-box inner-calendar">
                    <div class="inner-input-group">
                        <img class="inner-icon" alt="" src="${pageContext.request.contextPath}/public/assets/images/Calendar.svg" />
                        <input class="inner-input" placeholder="Lịch Khởi Hành" type="date" name="departureDate" />
                    </div>
                </div>
                <button class="inner-button" type="submit">
                    <i class="fa-solid fa-magnifying-glass inner-glass"></i>
                    <span>Tìm kiếm ngay</span>
                </button>
            </form>
            <div class="inner-features">
                <div class="inner-feature-item">
                    <i class="fa-solid fa-shield-halved"></i>
                    <span>Bảo hiểm du lịch trọn gói</span>
                </div>
                <div class="inner-feature-item">
                    <i class="fa-solid fa-award"></i>
                    <span>Dịch vụ chuẩn 5 sao</span>
                </div>
                <div class="inner-feature-item">
                    <i class="fa-solid fa-tags"></i>
                    <span>Cam kết giá tốt nhất</span>
                </div>
                <div class="inner-feature-item">
                    <i class="fa-solid fa-headset"></i>
                    <span>Hỗ trợ 24/7 tận tâm</span>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="section-2" data-aos-duration="800" data-aos="fade-up">
    <div class="container">
        <div class="inner-wrap">
            <div class="inner-info">
                <div class="inner-badge">
                    <i class="fa-solid fa-bolt"></i>
                    <span>GIỜ VÀNG GIÁ SỐC</span>
                </div>
                <h2 class="inner-title">
                    ƯU ĐÃI 2026<br>
                    TOUR GIỜ CHỐT
                </h2>
                <p class="inner-desc">Săn ngay tour hot giá cực sốc trong tuần, số lượng chỗ có hạn dành cho khách hàng đăng ký sớm nhất!</p>
                <h3 class="inner-clock-title">Kết thúc sau</h3>
                <div class="inner-clock-number" clock-expire="2027-01-01T00:00:00">
                    <div class="inner-item">
                        <div class="inner-time">00</div>
                        <div class="inner-label">Ngày</div>
                    </div>
                    <div class="inner-item">
                        <div class="inner-time">12</div>
                        <div class="inner-label">Giờ</div>
                    </div>
                    <div class="inner-item">
                        <div class="inner-time">20</div>
                        <div class="inner-label">Phút</div>
                    </div>
                    <div class="inner-item">
                        <div class="inner-time">30</div>
                        <div class="inner-label">Giây</div>
                    </div>
                </div>
                <div class="inner-discount-title">GIẢM ĐẾN</div>
                <div class="inner-discount-price">
                    990.000
                    <span class="unit">đ</span>
                </div>
            </div>
            <div class="inner-list">
                <div class="swiper swiper-section-2">
                    <div class="swiper-wrapper">
                        <c:forEach var="item" items="${tourListSection2}">
                            <div class="swiper-slide">
                                <c:set var="item" value="${item}" scope="request" />
                                <jsp:include page="../mixins/product-item.jsp" />
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="section-3">
    <div class="container">
        <div class="section-head" data-aos-duration="800" data-aos="fade-up">
            <div class="section-tag"><i class="fa-solid fa-fire"></i> Ưu Đãi Độc Quyền</div>
            <h2 class="title">Khuyến Mại Bùng Nổ - Đánh Tan Nóng Bức</h2>
            <p class="section-desc">Hàng loạt voucher giảm giá và quà tặng đặc sắc dành riêng cho mùa hè này</p>
        </div>
        <div class="swiper swiper-section-3" data-aos-delay="150" data-aos-duration="800" data-aos="fade-up">
            <div class="swiper-wrapper">
                <div class="swiper-slide">
                    <div class="inner-item">
                        <a href="#"><img alt="Khuyến mãi 1" src="${pageContext.request.contextPath}/public/assets/images/Background-5.jpg" /></a>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="inner-item">
                        <a href="#"><img alt="Khuyến mãi 2" src="${pageContext.request.contextPath}/public/assets/images/Background-6.jpg" /></a>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="inner-item">
                        <a href="#"><img alt="Khuyến mãi 3" src="${pageContext.request.contextPath}/public/assets/images/Background-7.jpg" /></a>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="inner-item">
                        <a href="#"><img alt="Khuyến mãi 4" src="${pageContext.request.contextPath}/public/assets/images/Background-5.jpg" /></a>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="inner-item">
                        <a href="#"><img alt="Khuyến mãi 5" src="${pageContext.request.contextPath}/public/assets/images/Background-6.jpg" /></a>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="inner-item">
                        <a href="#"><img alt="Khuyến mãi 6" src="${pageContext.request.contextPath}/public/assets/images/Background-7.jpg" /></a>
                    </div>
                </div>
            </div>
            <div class="swiper-pagination"></div>
        </div>
    </div>
</div>

<div class="section-4">
    <div class="container">
        <div class="section-head" data-aos-duration="800" data-aos="fade-up">
            <div class="section-tag"><i class="fa-solid fa-map-location-dot"></i> Điểm Đến Hấp Dẫn</div>
            <h2 class="title">Tour Du Lịch Trong Nước</h2>
            <p class="section-desc">Khám phá vẻ đẹp bất tận của non sông Việt Nam cùng dịch vụ hướng dẫn viên chuyên nghiệp</p>
        </div>
        <div class="inner-wrap" data-aos-delay="150" data-aos-duration="800" data-aos="fade-up">
            <c:forEach var="item" items="${tourListSection4}">
                <c:set var="item" value="${item}" scope="request" />
                <jsp:include page="../mixins/product-item.jsp" />
            </c:forEach>
        </div>
        <div class="inner-button" data-aos-delay="150" data-aos-duration="800" data-aos="fade-up">
            <a class="button-outline" href="${pageContext.request.contextPath}/search">
                <span>Xem tất cả tour trong nước</span>
                <i class="fa-solid fa-arrow-right"></i>
            </a>
        </div>
    </div>
</div>

<div class="section-5" data-aos-duration="800" data-aos="fade-up">
    <div class="container">
        <div class="inner-image">
            <img alt="Banner" src="${pageContext.request.contextPath}/public/assets/images/Banner.jpg" />
        </div>
    </div>
</div>

<div class="section-6">
    <div class="container">
        <div class="section-head" data-aos-duration="800" data-aos="fade-up">
            <div class="section-tag"><i class="fa-solid fa-earth-americas"></i> Chinh Phục Thế Giới</div>
            <h2 class="title">Tour Du Lịch Nước Ngoài</h2>
            <p class="section-desc">Trải nghiệm những nền văn hóa độc đáo và kỳ quan thiên nhiên năm châu với giá tốt nhất</p>
        </div>
        <div class="inner-wrap" data-aos-delay="150" data-aos-duration="800" data-aos="fade-up">
            <c:forEach var="item" items="${tourListSection6}">
                <c:set var="item" value="${item}" scope="request" />
                <jsp:include page="../mixins/product-item.jsp" />
            </c:forEach>
        </div>
        <div class="inner-button" data-aos-delay="150" data-aos-duration="800" data-aos="fade-up">
            <a class="button-outline" href="${pageContext.request.contextPath}/search">
                <span>Xem tất cả tour nước ngoài</span>
                <i class="fa-solid fa-arrow-right"></i>
            </a>
        </div>
    </div>
</div>

<div class="section-7" data-aos-duration="800" data-aos="fade-up">
    <div class="container">
        <div class="inner-image">
            <img alt="Banner" src="${pageContext.request.contextPath}/public/assets/images/Banner-2.jpg" />
        </div>
    </div>
</div>

<div class="section-8">
    <div class="container">
        <div class="section-head" data-aos-duration="800" data-aos="fade-up">
            <div class="section-tag"><i class="fa-solid fa-newspaper"></i> Cẩm Nang & Trải Nghiệm</div>
            <h2 class="title">Tin Tức Mới & Kinh Nghiệm Du Lịch</h2>
            <p class="section-desc">Cập nhật cẩm nang du lịch hữu ích, mẹo vặt và những câu chuyện hành trình truyền cảm hứng</p>
        </div>
        <div class="inner-wrap" data-aos-delay="150" data-aos-duration="800" data-aos="fade-up">
            <div class="blog blog-item">
                <a href="#">
                    <div class="inner-image">
                        <img alt="Blog-1" src="${pageContext.request.contextPath}/public/assets/images/Blog-1.jpg" />
                    </div>
                    <div class="inner-content">
                        <div class="inner-tag"><i class="fa-solid fa-tag"></i> Nghỉ dưỡng</div>
                        <div class="inner-date"><i class="fa-regular fa-calendar"></i> 28/07/2026</div>
                        <div class="inner-title">Homestay “cổ tích” giữa Đà Lạt mộng mơ</div>
                    </div>
                </a>
            </div>
            <div class="blog blog-center">
                <a href="#">
                    <div class="inner-image">
                        <img alt="Blog-2" src="${pageContext.request.contextPath}/public/assets/images/Blog-2.jpg" />
                    </div>
                    <div class="inner-content">
                        <div class="inner-badge-featured"><i class="fa-solid fa-star"></i> Tiêu điểm tuần</div>
                        <div class="inner-date"><i class="fa-regular fa-calendar"></i> 29/07/2026</div>
                        <div class="inner-title">Top 5 điểm check in độc đáo tại Singapore không thể bỏ lỡ</div>
                        <div class="inner-desc">
                            Nếu bạn nghĩ du lịch Singapore rất nhàm chán vì chỉ mãi quanh quẩn trong những khối bê tông vô cảm thì bạn “chắc chưa?”. Tuy đảo quốc Sư Tử không phải là một quốc gia rộng lớn nhưng các địa điểm du lịch tại Singapore chưa từng đánh mất sức hút đối với cộng đồng xê dịch quốc tế.
                        </div>
                        <div class="inner-read-more">
                            <span>Khám phá ngay</span>
                            <i class="fa-solid fa-arrow-right"></i>
                        </div>
                    </div>
                </a>
            </div>
            <div class="blog blog-item">
                <a href="#">
                    <div class="inner-image">
                        <img alt="Blog-3" src="${pageContext.request.contextPath}/public/assets/images/Blog-3.jpg" />
                    </div>
                    <div class="inner-content">
                        <div class="inner-tag"><i class="fa-solid fa-utensils"></i> Ẩm thực</div>
                        <div class="inner-date"><i class="fa-regular fa-calendar"></i> 27/07/2026</div>
                        <div class="inner-title">Đặc sản không thể bỏ qua khi đến với Đà Nẵng – Hội An</div>
                    </div>
                </a>
            </div>
            <div class="blog blog-item">
                <a href="#">
                    <div class="inner-image">
                        <img alt="Blog-4" src="${pageContext.request.contextPath}/public/assets/images/Blog-4.jpg" />
                    </div>
                    <div class="inner-content">
                        <div class="inner-tag"><i class="fa-solid fa-compass"></i> Khám phá</div>
                        <div class="inner-date"><i class="fa-regular fa-calendar"></i> 26/07/2026</div>
                        <div class="inner-title">Đảo Phú Quý Bình Thuận hot trend năm 2026</div>
                    </div>
                </a>
            </div>
            <div class="blog blog-item">
                <a href="#">
                    <div class="inner-image">
                        <img alt="Blog-5" src="${pageContext.request.contextPath}/public/assets/images/Blog-5.jpg" />
                    </div>
                    <div class="inner-content">
                        <div class="inner-tag"><i class="fa-solid fa-mountain"></i> Trekking</div>
                        <div class="inner-date"><i class="fa-regular fa-calendar"></i> 21/07/2026</div>
                        <div class="inner-title">Trekking cắm trại tại Đà Lạt - Cảm nhận từng bước chân</div>
                    </div>
                </a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
