<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<jsp:include page="../mixins/box-breadcrumb.jsp" />

<div class="about-page">
    <!-- 1. Hero Story: Câu chuyện thương hiệu & Lịch sử phát triển -->
    <section class="about-section about-story">
        <div class="container">
            <div class="about-story-grid">
                <div class="story-content" data-aos="fade-right" data-aos-duration="700">
                    <span class="about-badge">CÂU CHUYỆN THƯƠNG HIỆU</span>
                    <h2 class="about-title">DUT Travel – Người Bạn Đồng Hành Đáng Tin Cậy Trên Mọi Chặng Đường</h2>
                    <p class="story-lead">
                        Được sáng lập với tình yêu mãnh liệt dành cho văn hoá và vẻ đẹp thiên nhiên bất tận của dải đất hình chữ S, 
                        <strong>DUT Travel</strong> khởi đầu từ một nhóm chuyên gia trẻ đầy nhiệt huyết tại thành phố biển Đà Nẵng xinh đẹp.
                    </p>
                    <p class="story-text">
                        Trải qua hơn 10 năm xây dựng và không ngừng đổi mới, chúng tôi đã đưa hơn <strong>150.000+ lượt du khách</strong> khám phá mọi miền Tổ quốc 
                        cũng như đặt chân tới những danh thắng trứ danh trên toàn thế giới. Với chúng tôi, mỗi chuyến đi không chỉ đơn thuần là việc di chuyển từ điểm A tới điểm B, 
                        mà là một hành trình thức tỉnh những xúc cảm tươi đẹp, kết nối những tâm hồn và ghi dấu những kỷ niệm vô giá của cuộc đời.
                    </p>
                    <div class="story-highlights">
                        <div class="highlight-item">
                            <div class="highlight-icon"><i class="fa-solid fa-award"></i></div>
                            <div>
                                <div class="highlight-title">Top Đơn Vị Lữ Hành Uy Tín</div>
                                <div class="highlight-desc">Chứng nhận dịch vụ xuất sắc nhiều năm liền</div>
                            </div>
                        </div>
                        <div class="highlight-item">
                            <div class="highlight-icon"><i class="fa-solid fa-shield-halved"></i></div>
                            <div>
                                <div class="highlight-title">An Toàn Tuyệt Đối 100%</div>
                                <div class="highlight-desc">Bảo hiểm du lịch toàn diện và quy trình chuẩn mực</div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="story-image" data-aos="fade-left" data-aos-duration="700">
                    <div class="image-wrapper">
                        <img src="https://images.unsplash.com/photo-1528127269322-539801943592?auto=format&fit=crop&w=1000&q=80" alt="DUT Travel Story" />
                        <div class="experience-badge">
                            <span class="exp-number">10+</span>
                            <span class="exp-text">Năm Kinh Nghiệm<br>Kiến Tạo Hành Trình</span>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Timeline Mốc Son Lịch Sử -->
            <div class="timeline-container" data-aos="fade-up" data-aos-duration="800">
                <div class="section-sub-header">
                    <h3 class="sub-title"><i class="fa-solid fa-timeline"></i> Các Mốc Son Phát Triển Của DUT Travel</h3>
                </div>
                <div class="timeline-grid">
                    <div class="timeline-card">
                        <div class="timeline-year">2015</div>
                        <div class="timeline-dot"></div>
                        <h4 class="timeline-title">Khởi Nguồn Đam Mê</h4>
                        <p class="timeline-desc">Thành lập tại Đà Nẵng, chuyên tổ chức các tour trải nghiệm văn hóa di sản miền Trung (Huế - Đà Nẵng - Hội An).</p>
                    </div>
                    <div class="timeline-card">
                        <div class="timeline-year">2018</div>
                        <div class="timeline-dot"></div>
                        <h4 class="timeline-title">Mở Rộng Toàn Quốc</h4>
                        <p class="timeline-desc">Mở rộng mạng lưới phủ sóng 34 tỉnh thành và các tour biển đảo cao cấp như Phú Quốc, Nha Trang, Côn Đảo.</p>
                    </div>
                    <div class="timeline-card">
                        <div class="timeline-year">2022</div>
                        <div class="timeline-dot"></div>
                        <h4 class="timeline-title">Chuyển Đổi Số & Tour Quốc Tế</h4>
                        <p class="timeline-desc">Tiên phong số hóa quy trình đặt tour thông minh, khai thác thành công các tuyến tour nước ngoài: Đông Nam Á, Nhật Bản, Hàn Quốc.</p>
                    </div>
                    <div class="timeline-card">
                        <div class="timeline-year">2026</div>
                        <div class="timeline-dot"></div>
                        <h4 class="timeline-title">Khẳng Định Vị Thế</h4>
                        <p class="timeline-desc">Cán mốc phục vụ 150.000+ du khách, đạt chỉ số hài lòng 99.2%, khẳng định vị thế thương hiệu lữ hành dẫn đầu.</p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- 2. Sứ mệnh - Tầm nhìn - Giá trị cốt lõi -->
    <section class="about-section about-values bg-light">
        <div class="container">
            <div class="section-center-header" data-aos="fade-up" data-aos-duration="600">
                <span class="about-badge">KIM CHỈ NAM HOẠT ĐỘNG</span>
                <h2 class="about-title">Sứ Mệnh, Tầm Nhìn & Giá Trị Cốt Lõi</h2>
                <p class="about-subtitle">Những nguyên tắc vàng định hướng cho mọi hành động và dịch vụ tại DUT Travel</p>
            </div>

            <div class="values-grid">
                <!-- Sứ Mệnh -->
                <div class="value-card" data-aos="fade-up" data-aos-delay="100">
                    <div class="value-icon-box bg-blue">
                        <i class="fa-solid fa-compass"></i>
                    </div>
                    <h3 class="value-title">Sứ Mệnh</h3>
                    <p class="value-desc">
                        Mang đến cho mỗi khách hàng những hành trình đáng nhớ, an toàn và tràn ngập tiếng cười. Chúng tôi không chỉ bán vé du lịch, 
                        mà nỗ lực truyền cảm hứng khám phá, kết nối con người với thiên nhiên và trân quý các giá trị văn hóa bản địa.
                    </p>
                </div>

                <!-- Tầm Nhìn -->
                <div class="value-card" data-aos="fade-up" data-aos-delay="200">
                    <div class="value-icon-box bg-green">
                        <i class="fa-solid fa-eye"></i>
                    </div>
                    <h3 class="value-title">Tầm Nhìn</h3>
                    <p class="value-desc">
                        Trở thành biểu tượng lữ hành đáng tin cậy hàng đầu tại Việt Nam và khu vực Đông Nam Á; tiên phong xây dựng mô hình du lịch xanh, 
                        bền vững, ứng dụng công nghệ trực tuyến hiện đại nâng tầm trải nghiệm cá nhân hóa của khách hàng.
                    </p>
                </div>

                <!-- Giá Trị Cốt Lõi -->
                <div class="value-card" data-aos="fade-up" data-aos-delay="300">
                    <div class="value-icon-box bg-orange">
                        <i class="fa-solid fa-gem"></i>
                    </div>
                    <h3 class="value-title">Giá Trị Cốt Lõi (4T)</h3>
                    <ul class="value-list">
                        <li><strong>TẬN TÂM:</strong> Đặt sự hài lòng và an toàn của khách hàng lên hàng đầu.</li>
                        <li><strong>TÍN NHIỆM:</strong> Đúng lịch trình, minh bạch chi phí, trung thực trong cam kết.</li>
                        <li><strong>TRẢI NGHIỆM:</strong> Từng chi tiết nhỏ đều được chăm chút tỉ mỉ, trọn vẹn.</li>
                        <li><strong>TRÁCH NHIỆM:</strong> Du lịch gắn liền với bảo tồn môi trường và văn hóa xanh.</li>
                    </ul>
                </div>
            </div>
        </div>
    </section>

    <!-- 3. Những con số ấn tượng -->
    <section class="about-section about-stats-banner">
        <div class="container">
            <div class="stats-grid">
                <div class="stat-box" data-aos="zoom-in" data-aos-delay="100">
                    <div class="stat-number">10+</div>
                    <div class="stat-label">Năm Phát Triển Bền Vững</div>
                </div>
                <div class="stat-box" data-aos="zoom-in" data-aos-delay="200">
                    <div class="stat-number">150K+</div>
                    <div class="stat-label">Du Khách Đã Đồng Hành</div>
                </div>
                <div class="stat-box" data-aos="zoom-in" data-aos-delay="300">
                    <div class="stat-number">500+</div>
                    <div class="stat-label">Lịch Trình Tour Đa Dạng</div>
                </div>
                <div class="stat-box" data-aos="zoom-in" data-aos-delay="400">
                    <div class="stat-number">99.2%</div>
                    <div class="stat-label">Tỷ Lệ Hài Lòng Tuyệt Đối</div>
                </div>
            </div>
        </div>
    </section>

    <!-- 4. Cam kết vàng về chất lượng dịch vụ -->
    <section class="about-section about-commitments bg-light">
        <div class="container">
            <div class="section-center-header" data-aos="fade-up" data-aos-duration="600">
                <span class="about-badge">CAM KẾT VÀNG</span>
                <h2 class="about-title">Tại Sao Nên Chọn Đồng Hành Cùng DUT Travel?</h2>
                <p class="about-subtitle">Những bảo chứng vững chắc làm nên thương hiệu của chúng tôi</p>
            </div>

            <div class="commitments-grid">
                <div class="commit-item" data-aos="fade-up" data-aos-delay="100">
                    <div class="commit-icon"><i class="fa-solid fa-hand-holding-dollar"></i></div>
                    <h4 class="commit-title">Minh Bạch Chi Phí</h4>
                    <p class="commit-desc">Báo giá trọn gói, rõ ràng, tuyệt đối không có chi phí ẩn hay phụ phí bất ngờ trong suốt hành trình.</p>
                </div>
                <div class="commit-item" data-aos="fade-up" data-aos-delay="200">
                    <div class="commit-icon"><i class="fa-solid fa-hotel"></i></div>
                    <h4 class="commit-title">Tiêu Chuẩn Chuẩn Mực</h4>
                    <p class="commit-desc">Khách sạn 3-5 sao tiện nghi, xe du lịch đời mới máy lạnh êm ái, thực đơn ẩm thực phong phú đặc sản vùng miền.</p>
                </div>
                <div class="commit-item" data-aos="fade-up" data-aos-delay="300">
                    <div class="commit-icon"><i class="fa-solid fa-user-tie"></i></div>
                    <h4 class="commit-title">Hướng Dẫn Viên Tinh Tuyển</h4>
                    <p class="commit-desc">Được đào tạo bài bản, có thẻ hành nghề quốc tế/nội địa, tận tâm, chu đáo như người thân trong gia đình.</p>
                </div>
                <div class="commit-item" data-aos="fade-up" data-aos-delay="400">
                    <div class="commit-icon"><i class="fa-solid fa-headset"></i></div>
                    <h4 class="commit-title">Hỗ Trợ 24/7 Toàn Diện</h4>
                    <p class="commit-desc">Tổng đài viên luôn túc trực hỗ trợ giải quyết yêu cầu của du khách bất kể thời gian, kể cả ngày lễ tết.</p>
                </div>
            </div>
        </div>
    </section>

    <!-- 6. Call To Action -->
    <section class="about-cta">
        <div class="container">
            <div class="cta-box" data-aos="zoom-in" data-aos-duration="700">
                <h2 class="cta-title">Sẵn Sàng Cho Chuyến Du Lịch Trong Mơ Của Bạn?</h2>
                <p class="cta-desc">Hãy để DUT Travel cùng bạn vẽ nên những kỷ niệm tuyệt đẹp trên khắp mọi miền thế giới ngay hôm nay!</p>
                <div class="cta-actions">
                    <a href="${pageContext.request.contextPath}/category/tour-trong-nuoc" class="btn-cta btn-primary-cta">
                        <i class="fa-solid fa-paper-plane"></i> Khám Phá Tour Ngay
                    </a>
                    <a href="tel:${not empty settingWebsiteInfo.cleanPhone ? settingWebsiteInfo.cleanPhone : '0905383132'}" class="btn-cta btn-secondary-cta">
                        <i class="fa-solid fa-phone"></i> Hotline: ${not empty settingWebsiteInfo.phone ? settingWebsiteInfo.phone : '0905 383 132'}
                    </a>
                </div>
            </div>
        </div>
    </section>
</div>

<jsp:include page="../layouts/footer.jsp" />
