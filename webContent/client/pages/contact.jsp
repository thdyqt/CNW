<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<c:if test="${not empty sessionScope.contactSuccess}">
    <div class="alert alert-success" alert-time="4000">
        <i class="fa-solid fa-circle-check"></i>
        <span>${sessionScope.contactSuccess}</span>
    </div>
    <c:remove var="contactSuccess" scope="session" />
</c:if>

<c:if test="${not empty sessionScope.contactError}">
    <div class="alert alert-danger" alert-time="4000">
        <i class="fa-solid fa-circle-exclamation"></i>
        <span>${sessionScope.contactError}</span>
    </div>
    <c:remove var="contactError" scope="session" />
</c:if>

<jsp:include page="../mixins/box-breadcrumb.jsp" />

<!-- Section 13: Trang Liên Hệ -->
<div class="section-13">
    <div class="container">
        
        <!-- 1. Danh sách thẻ thông tin liên hệ nhanh -->
        <div class="inner-cards" data-aos="fade-up" data-aos-duration="700">
            <div class="inner-card-item">
                <div class="inner-card-icon">
                    <i class="fa-solid fa-location-dot"></i>
                </div>
                <div class="inner-card-content">
                    <span class="inner-card-tag">ĐỊA CHỈ TRỤ SỞ</span>
                    <h3 class="inner-card-title">${not empty settingWebsiteInfo.address ? settingWebsiteInfo.address : '54 Nguyễn Lương Bằng, Liên Chiểu, Đà Nẵng'}</h3>
                    <p class="inner-card-desc">Đón tiếp khách từ 8:00 - 18:00 hàng ngày</p>
                </div>
            </div>

            <div class="inner-card-item">
                <div class="inner-card-icon">
                    <i class="fa-solid fa-phone-volume"></i>
                </div>
                <div class="inner-card-content">
                    <span class="inner-card-tag">HOTLINE TƯ VẤN</span>
                    <h3 class="inner-card-title">
                        <a href="tel:${settingWebsiteInfo.phone}">${not empty settingWebsiteInfo.phone ? settingWebsiteInfo.phone : '0236 3842 308'}</a>
                    </h3>
                    <p class="inner-card-desc">Hỗ trợ & tư vấn đặt tour nhanh 24/7</p>
                </div>
            </div>

            <div class="inner-card-item">
                <div class="inner-card-icon">
                    <i class="fa-solid fa-envelope-open-text"></i>
                </div>
                <div class="inner-card-content">
                    <span class="inner-card-tag">HỘP THƯ ĐIỆN TỬ</span>
                    <h3 class="inner-card-title">
                        <a href="mailto:${settingWebsiteInfo.email}">${not empty settingWebsiteInfo.email ? settingWebsiteInfo.email : 'contact@duttravel.vn'}</a>
                    </h3>
                    <p class="inner-card-desc">Phản hồi khách hàng trong vòng 24 giờ</p>
                </div>
            </div>

            <div class="inner-card-item">
                <div class="inner-card-icon">
                    <i class="fa-solid fa-clock"></i>
                </div>
                <div class="inner-card-content">
                    <span class="inner-card-tag">GIỜ LÀM VIỆC</span>
                    <h3 class="inner-card-title">T2 - T7: 08:00 - 18:00</h3>
                    <p class="inner-card-desc">Chủ nhật: 08:00 - 12:00 (Trực tuyến 24/7)</p>
                </div>
            </div>
        </div>

        <!-- 2. Phần Form Liên Hệ & Bản Đồ Google Maps -->
        <div class="inner-main">
            <!-- Cột trái: Biểu mẫu liên hệ -->
            <div class="inner-col-form" data-aos="fade-right" data-aos-duration="800">
                <div class="inner-form-box">
                    <div class="inner-badge">
                        <i class="fa-solid fa-paper-plane"></i>
                        <span>KẾT NỐI VỚI CHÚNG TÔI</span>
                    </div>
                    <h2 class="inner-heading">Bạn Cần Hỗ Trợ Hoặc Tư Vấn Tour?</h2>
                    <p class="inner-lead">
                        Hãy để lại lời nhắn, chuyên viên tư vấn của DUT Travel sẽ liên hệ lại với bạn trong thời gian sớm nhất để hỗ trợ chi tiết.
                    </p>

                    <form id="contact-page-form" action="${pageContext.request.contextPath}/contact" method="POST">
                        <div class="inner-form-row">
                            <div class="inner-form-group">
                                <label for="contact-fullname">Họ và tên <span class="required">*</span></label>
                                <div class="inner-input-wrap">
                                    <i class="fa-regular fa-user"></i>
                                    <input type="text" id="contact-fullname" name="fullName" placeholder="Ví dụ: Nguyễn Văn A" required />
                                </div>
                            </div>
                            <div class="inner-form-group">
                                <label for="contact-phone">Số điện thoại <span class="required">*</span></label>
                                <div class="inner-input-wrap">
                                    <i class="fa-solid fa-phone"></i>
                                    <input type="tel" id="contact-phone" name="phone" placeholder="Ví dụ: 0912345678" required />
                                </div>
                            </div>
                        </div>

                        <div class="inner-form-row">
                            <div class="inner-form-group">
                                <label for="contact-email">Email <span class="required">*</span></label>
                                <div class="inner-input-wrap">
                                    <i class="fa-regular fa-envelope"></i>
                                    <input type="email" id="contact-email" name="email" placeholder="Ví dụ: yourname@gmail.com" required />
                                </div>
                            </div>
                            <div class="inner-form-group">
                                <label for="contact-subject">Nhu cầu tư vấn</label>
                                <div class="inner-input-wrap">
                                    <i class="fa-solid fa-compass"></i>
                                    <select id="contact-subject" name="subject">
                                        <option value="Tư vấn tour trong nước">Tư vấn tour trong nước</option>
                                        <option value="Tư vấn tour nước ngoài">Tư vấn tour nước ngoài</option>
                                        <option value="Tour đoàn / Doanh nghiệp">Tour đoàn / Doanh nghiệp</option>
                                        <option value="Vé máy bay & Khách sạn">Vé máy bay & Khách sạn</option>
                                        <option value="Góp ý & Khiếu nại">Góp ý & Khiếu nại</option>
                                        <option value="Khác">Nhu cầu khác</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="inner-form-group">
                            <label for="contact-message">Nội dung tin nhắn / Lời nhắn <span class="required">*</span></label>
                            <div class="inner-input-wrap inner-textarea-wrap">
                                <i class="fa-regular fa-comment-dots"></i>
                                <textarea id="contact-message" name="message" rows="5" placeholder="Nhập chi tiết yêu cầu, điểm đến mong muốn, số lượng người hoặc ngày khởi hành dự kiến..." required></textarea>
                            </div>
                        </div>

                        <button type="submit" class="inner-btn-submit" id="contact-submit-button">
                            <i class="fa-solid fa-paper-plane"></i>
                            <span>Gửi Tin Nhắn Cho Chúng Tôi</span>
                        </button>
                    </form>
                </div>
            </div>

            <!-- Cột phải: Bản đồ và cam kết -->
            <div class="inner-col-side" data-aos="fade-left" data-aos-duration="800">
                <!-- Bản đồ Google Maps -->
                <div class="inner-map-card">
                    <div class="inner-map-header">
                        <div class="inner-map-title">
                            <i class="fa-solid fa-map-location-dot"></i>
                            <span>Vị trí trên bản đồ</span>
                        </div>
                        <span class="inner-map-badge">Chỉ đường</span>
                    </div>
                    <div class="inner-map-frame">
                        <iframe 
                            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3833.802545642866!2d108.14982267583693!3d16.075732939268395!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x314218d68dff9a91%3A0xe0127ab570163426!2zVHLGsOG7nW5nIMSQ4bqhaSBI4buNYyBCw6FjaCBLaG9hIC0gxJDhuqFpIGjhu41jIMSQw6AgTuG6tW5n!5e0!3m2!1svi!2s!4v1700000000000!5m2!1svi!2s" 
                            width="100%" 
                            height="290" 
                            style="border:0;" 
                            allowfullscreen="" 
                            loading="lazy" 
                            referrerpolicy="no-referrer-when-downgrade">
                        </iframe>
                    </div>
                </div>

                <!-- Cam kết chất lượng & Kênh xã hội -->
                <div class="inner-trust-card">
                    <h3 class="inner-trust-title">Cam Kết Dịch Vụ Của DUT Travel</h3>
                    <ul class="inner-trust-list">
                        <li>
                            <i class="fa-solid fa-shield-halved"></i>
                            <div>
                                <b>Bảo mật thông tin 100%:</b> Dữ liệu khách hàng được lưu trữ an toàn tuyệt đối.
                            </div>
                        </li>
                        <li>
                            <i class="fa-solid fa-headset"></i>
                            <div>
                                <b>Tư vấn tận tâm:</b> Chuyên viên am hiểu tuyến điểm, hỗ trợ chọn tour tối ưu nhất.
                            </div>
                        </li>
                        <li>
                            <i class="fa-solid fa-bolt-lightning"></i>
                            <div>
                                <b>Phản hồi siêu tốc:</b> Tiếp nhận và gọi lại xác nhận chỉ trong 15 phút làm việc.
                            </div>
                        </li>
                    </ul>

                    <div class="inner-socials-box">
                        <div class="inner-socials-label">Kết nối cùng chúng tôi qua mạng xã hội:</div>
                        <div class="inner-social-links">
                            <a href="${not empty settingWebsiteInfo.facebook ? settingWebsiteInfo.facebook : '#'}" target="_blank" class="social-fb" title="Facebook">
                                <i class="fa-brands fa-facebook-f"></i>
                            </a>
                            <a href="${not empty settingWebsiteInfo.instagram ? settingWebsiteInfo.instagram : '#'}" target="_blank" class="social-ins" title="Instagram">
                                <i class="fa-brands fa-instagram"></i>
                            </a>
                            <a href="${not empty settingWebsiteInfo.github ? settingWebsiteInfo.github : '#'}" target="_blank" class="social-gh" title="GitHub">
                                <i class="fa-brands fa-github"></i>
                            </a>
                            <a href="${not empty settingWebsiteInfo.youtube ? settingWebsiteInfo.youtube : '#'}" target="_blank" class="social-yt" title="YouTube">
                                <i class="fa-brands fa-youtube"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 3. Phần Hỏi & Đáp Thường Gặp (FAQ Accordion) -->
        <div class="inner-faq" data-aos="fade-up" data-aos-duration="800">
            <div class="inner-faq-head text-center">
                <span class="inner-faq-badge">CÂU HỎI THƯỜNG GẶP</span>
                <h2 class="inner-faq-title">Bạn Có Những Thắc Mắc Này?</h2>
                <p class="inner-faq-desc">Giải đáp nhanh các vấn đề được đông đảo du khách quan tâm nhất khi đặt tour du lịch.</p>
            </div>

            <div class="inner-faq-list">
                <div class="inner-faq-item active">
                    <button type="button" class="inner-faq-question">
                        <span>1. Tôi nên đặt tour trước ngày khởi hành bao lâu?</span>
                        <i class="fa-solid fa-chevron-down"></i>
                    </button>
                    <div class="inner-faq-answer">
                        <p>Với các tour du lịch trong nước, bạn nên đặt trước từ 3 - 7 ngày để đảm bảo giữ chỗ tốt nhất. Đối với các tour nước ngoài hoặc dịp Lễ, Tết cao điểm, thời gian lý tưởng nhất là từ 2 - 4 tuần để hoàn tất thủ tục visa và vé máy bay theo lịch trình.</p>
                    </div>
                </div>

                <div class="inner-faq-item">
                    <button type="button" class="inner-faq-question">
                        <span>2. Các phương thức thanh toán tour tại DUT Travel là gì?</span>
                        <i class="fa-solid fa-chevron-down"></i>
                    </button>
                    <div class="inner-faq-answer">
                        <p>Chúng tôi hỗ trợ 3 hình thức thanh toán linh hoạt: Thanh toán bằng tiền mặt khi tham gia tour, Thanh toán trực tuyến qua cổng VNPay, hoặc Chuyển khoản ngân hàng. Mọi giao dịch đều có hóa đơn và phiếu xác nhận đặt tour gửi đến email của bạn.</p>
                    </div>
                </div>

                <div class="inner-faq-item">
                    <button type="button" class="inner-faq-question">
                        <span>3. Nếu tôi cần đổi ngày hoặc hủy tour thì xử lý như thế nào?</span>
                        <i class="fa-solid fa-chevron-down"></i>
                    </button>
                    <div class="inner-faq-answer">
                        <p>Bạn chỉ cần liên hệ trực tiếp hotline hoặc gửi yêu cầu cho chúng tôi. Nếu thông báo trước 48 giờ đối với tour ngắn ngày, DUT Travel hỗ trợ đổi lịch trình hoặc hoàn cọc theo chính sách ưu đãi của từng tour cụ thể.</p>
                    </div>
                </div>

                <div class="inner-faq-item">
                    <button type="button" class="inner-faq-question">
                        <span>4. Trẻ em đi cùng có được hưởng mức giá ưu đãi không?</span>
                        <i class="fa-solid fa-chevron-down"></i>
                    </button>
                    <div class="inner-faq-answer">
                        <p>Có. Trẻ em dưới 2 tuổi (em bé) được miễn phí hoặc tính phí dịch vụ rất nhỏ; trẻ em từ 2 - 11 tuổi được áp dụng chính sách giá trẻ em giảm từ 30% đến 50% so với vé người lớn tùy theo dịch vụ của từng tour.</p>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
