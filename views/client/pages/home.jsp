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
            <h1 class="inner-title" data-aos-duration="800" data-aos="fade-up">
                Du lịch Châu Á - Khám phá Mỹ, Úc, Âu<br>
                Đi nơi đâu bạn muốn
            </h1>
            <p class="inner-desc" data-aos-delay="150" data-aos-duration="800" data-aos="fade-up">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua
            </p>
            <form class="inner-form" data-aos-delay="300" data-aos-duration="800" data-aos="fade-up" action="" form-search>
                <div class="inner-box inner-location">
                    <div class="inner-input-group">
                        <img class="inner-icon" alt="" src="${pageContext.request.contextPath}/assets/images/Location.svg" />
                        <input class="inner-input" placeholder="Bạn muốn đi đâu" type="text" name="locationTo" />
                        <i class="fa-solid fa-angle-down inner-down"></i>
                    </div>
                    <div class="inner-suggest">
                        <div class="inner-suggest-title">
                            <i class="fa-solid fa-location-dot"></i>
                            Địa Điểm Hot
                        </div>
                        <div class="inner-suggest-list">
                            <div class="inner-item">
                                <div class="inner-item-image">
                                    <img alt="Product-1" src="${pageContext.request.contextPath}/assets/images/Product-1.jpg" />
                                </div>
                                <div class="inner-item-content">
                                    <div class="inner-item-title">Nhật Bản</div>
                                    <div class="inner-item-desc">50 tours</div>
                                </div>
                            </div>
                            <div class="inner-item">
                                <div class="inner-item-image">
                                    <img alt="Product-1" src="${pageContext.request.contextPath}/assets/images/Product-1.jpg" />
                                </div>
                                <div class="inner-item-content">
                                    <div class="inner-item-title">Nhật Bản</div>
                                    <div class="inner-item-desc">50 tours</div>
                                </div>
                            </div>
                            <div class="inner-item">
                                <div class="inner-item-image">
                                    <img alt="Product-1" src="${pageContext.request.contextPath}/assets/images/Product-1.jpg" />
                                </div>
                                <div class="inner-item-content">
                                    <div class="inner-item-title">Nhật Bản</div>
                                    <div class="inner-item-desc">50 tours</div>
                                </div>
                            </div>
                            <div class="inner-item">
                                <div class="inner-item-image">
                                    <img alt="Product-1" src="${pageContext.request.contextPath}/assets/images/Product-1.jpg" />
                                </div>
                                <div class="inner-item-content">
                                    <div class="inner-item-title">Nhật Bản</div>
                                    <div class="inner-item-desc">50 tours</div>
                                </div>
                            </div>
                            <div class="inner-item">
                                <div class="inner-item-image">
                                    <img alt="Product-1" src="${pageContext.request.contextPath}/assets/images/Product-1.jpg" />
                                </div>
                                <div class="inner-item-content">
                                    <div class="inner-item-title">Phú Quốc</div>
                                    <div class="inner-item-desc">50 tours</div>
                                </div>
                            </div>
                            <div class="inner-item">
                                <div class="inner-item-image">
                                    <img alt="Product-1" src="${pageContext.request.contextPath}/assets/images/Product-1.jpg" />
                                </div>
                                <div class="inner-item-content">
                                    <div class="inner-item-title">Hà Nội</div>
                                    <div class="inner-item-desc">50 tours</div>
                                </div>
                            </div>
                            <div class="inner-item">
                                <div class="inner-item-image">
                                    <img alt="Product-1" src="${pageContext.request.contextPath}/assets/images/Product-1.jpg" />
                                </div>
                                <div class="inner-item-content">
                                    <div class="inner-item-title">Nhật Bản</div>
                                    <div class="inner-item-desc">50 tours</div>
                                </div>
                            </div>
                            <div class="inner-item">
                                <div class="inner-item-image">
                                    <img alt="Product-1" src="${pageContext.request.contextPath}/assets/images/Product-1.jpg" />
                                </div>
                                <div class="inner-item-content">
                                    <div class="inner-item-title">Nhật Bản</div>
                                    <div class="inner-item-desc">50 tours</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="inner-box inner-user">
                    <div class="inner-input-group">
                        <img class="inner-icon" alt="" src="${pageContext.request.contextPath}/assets/images/User.svg" />
                        <input class="inner-input" readonly="" placeholder="Số lượng" type="text" />
                        <i class="fa-solid fa-angle-down inner-down"></i>
                    </div>
                    <div class="inner-quantity">
                        <div class="inner-item">
                            <div class="inner-label">Người lớn</div>
                            <div class="inner-count">
                                <span class="inner-down">-</span>
                                <span class="inner-number" stock-adult>0</span>
                                <span class="inner-up">+</span>
                            </div>
                        </div>
                        <div class="inner-item">
                            <div class="inner-label">Trẻ em</div>
                            <div class="inner-count">
                                <span class="inner-down">-</span>
                                <span class="inner-number" stock-children>0</span>
                                <span class="inner-up">+</span>
                            </div>
                        </div>
                        <div class="inner-item">
                            <div class="inner-label">Em bé</div>
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
                        <img class="inner-icon" alt="" src="${pageContext.request.contextPath}/assets/images/Calendar.svg" />
                        <input class="inner-input" placeholder="Lịch Khởi Hành" type="date" name="departureDate" />
                    </div>
                </div>
                <button class="inner-button" type="submit">
                    <i class="fa-solid fa-magnifying-glass inner-glass"></i>
                    Tìm kiếm
                </button>
            </form>
        </div>
    </div>
</div>

<div class="section-2" data-aos-duration="800" data-aos="fade-up">
    <div class="container">
        <div class="inner-wrap">
            <div class="inner-info">
                <h2 class="inner-title">
                    ƯU ĐÃI 2024<br>
                    TOUR GIỜ CHỐT
                </h2>
                <p class="inner-desc">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor</p>
                <h3 class="inner-clock-title">Kết thúc sau</h3>
                <div class="inner-clock-number" clock-expire="2026-09-09T12:00:00">
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
        <h2 class="title" data-aos-duration="800" data-aos="fade-up">Khuyến Mại Bùng Nổ - Đánh Tan Nóng Bức</h2>
        <div class="swiper swiper-section-3" data-aos-delay="150" data-aos-duration="800" data-aos="fade-up">
            <div class="swiper-wrapper">
                <div class="swiper-slide">
                    <div class="inner-item">
                        <a href="#"><img alt="Background-5" src="${pageContext.request.contextPath}/assets/images/Background-5.jpg" /></a>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="inner-item">
                        <a href="#"><img alt="Background-6" src="${pageContext.request.contextPath}/assets/images/Background-6.jpg" /></a>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="inner-item">
                        <a href="#"><img alt="Background-7" src="${pageContext.request.contextPath}/assets/images/Background-7.jpg" /></a>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="inner-item">
                        <a href="#"><img alt="Background-5" src="${pageContext.request.contextPath}/assets/images/Background-5.jpg" /></a>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="inner-item">
                        <a href="#"><img alt="Background-6" src="${pageContext.request.contextPath}/assets/images/Background-6.jpg" /></a>
                    </div>
                </div>
                <div class="swiper-slide">
                    <div class="inner-item">
                        <a href="#"><img alt="Background-7" src="${pageContext.request.contextPath}/assets/images/Background-7.jpg" /></a>
                    </div>
                </div>
            </div>
            <div class="swiper-pagination"></div>
        </div>
    </div>
</div>

<div class="section-4">
    <div class="container">
        <h2 class="title" data-aos-duration="800" data-aos="fade-up">Tour Trong Nước</h2>
        <div class="inner-wrap" data-aos-delay="150" data-aos-duration="800" data-aos="fade-up">
            <c:forEach var="item" items="${tourListSection4}">
                <jsp:include page="../mixins/product-item.jsp" />
            </c:forEach>
        </div>
        <div class="inner-button" data-aos-delay="150" data-aos-duration="800" data-aos="fade-up">
            <a class="button-outline" href="#">Xem tất cả</a>
        </div>
    </div>
</div>

<div class="section-5" data-aos-duration="800" data-aos="fade-up">
    <div class="container">
        <div class="inner-image">
            <img alt="Banner" src="${pageContext.request.contextPath}/assets/images/Banner.jpg" />
        </div>
    </div>
</div>

<div class="section-6">
    <div class="container">
        <h2 class="title" data-aos-duration="800" data-aos="fade-up">Tour Nước Ngoài</h2>
        <div class="inner-wrap" data-aos-delay="150" data-aos-duration="800" data-aos="fade-up">
            <c:forEach var="item" items="${tourListSection6}">
                <jsp:include page="../mixins/product-item.jsp" />
            </c:forEach>
        </div>
        <div class="inner-button" data-aos-delay="150" data-aos-duration="800" data-aos="fade-up">
            <a class="button-outline" href="#">Xem tất cả</a>
        </div>
    </div>
</div>

<div class="section-7" data-aos-duration="800" data-aos="fade-up">
    <div class="container">
        <div class="inner-image">
            <img alt="Banner" src="${pageContext.request.contextPath}/assets/images/Banner-2.jpg" />
        </div>
    </div>
</div>

<div class="section-8">
    <div class="container">
        <div class="title" data-aos-duration="800" data-aos="fade-up">Tin Tức Mới</div>
        <div class="inner-wrap" data-aos-delay="150" data-aos-duration="800" data-aos="fade-up">
            <div class="blog blog-item">
                <a href="#">
                    <div class="inner-image">
                        <img alt="Blog-1" src="${pageContext.request.contextPath}/assets/images/Blog-1.jpg" />
                    </div>
                    <div class="inner-content">
                        <div class="inner-date">28/07/2026</div>
                        <div class="inner-title">Homestay “cổ tích” giữa Đà Lạt mộng mơ</div>
                    </div>
                </a>
            </div>
            <div class="blog blog-center">
                <a href="#">
                    <div class="inner-image">
                        <img alt="Blog-2" src="${pageContext.request.contextPath}/assets/images/Blog-2.jpg" />
                    </div>
                    <div class="inner-content">
                        <div class="inner-date">29/07/2026</div>
                        <div class="inner-title">Top 5 điểm check in độc đáo tại Singapore</div>
                        <div class="inner-desc">
                            Nếu bạn nghĩ du lịch Singapore rất nhàm chán vì chỉ mãi quanh quẩn
                            trong những khối bê tông vô cảm thì bạn “chắc chưa?”. Tuy đảo quốc Sư Tử không phải là
                            một quốc gia rộng lớn nhưng các địa điểm du lịch tại Singapore chưa từng đánh mất sức
                            hút đối với cộng đồng xê dịch quốc tế.
                        </div>
                    </div>
                </a>
            </div>
            <div class="blog blog-item">
                <a href="#">
                    <div class="inner-image">
                        <img alt="Blog-3" src="${pageContext.request.contextPath}/assets/images/Blog-3.jpg" />
                    </div>
                    <div class="inner-content">
                        <div class="inner-date">27/07/2026</div>
                        <div class="inner-title">Đặc sản không thể bỏ qua khi đến với Đà Nẵng – Hội An</div>
                    </div>
                </a>
            </div>
            <div class="blog blog-item">
                <a href="#">
                    <div class="inner-image">
                        <img alt="Blog-4" src="${pageContext.request.contextPath}/assets/images/Blog-4.jpg" />
                    </div>
                    <div class="inner-content">
                        <div class="inner-date">26/07/2026</div>
                        <div class="inner-title">Đảo Phú Quý Bình Thuận hot trend năm 2026</div>
                    </div>
                </a>
            </div>
            <div class="blog blog-item">
                <a href="#">
                    <div class="inner-image">
                        <img alt="Blog-5" src="${pageContext.request.contextPath}/assets/images/Blog-5.jpg" />
                    </div>
                    <div class="inner-content">
                        <div class="inner-date">21/07/2026</div>
                        <div class="inner-title">Trekking cắm trại tại Đà Lạt- Cảm nhận Đà Lạt qua từng bước chân</div>
                    </div>
                </a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
