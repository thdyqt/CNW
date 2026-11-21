<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<jsp:include page="../mixins/box-breadcrumb.jsp" />

<div class="policy-page">
    <div class="container">
        
        <!-- Hero Information Card -->
        <div class="policy-hero-card" data-aos="fade-up" data-aos-duration="600">
            <div class="hero-info">
                <h2>Chính Sách Bảo Mật Quyền Riêng Tư</h2>
                <p>Cập nhật lần cuối: Tháng 11/2026. DUT Travel tôn trọng và cam kết bảo vệ tuyệt đối thông tin riêng tư và dữ liệu cá nhân của Quý khách theo Nghị định số 13/2023/NĐ-CP về Bảo vệ dữ liệu cá nhân.</p>
            </div>
            <div class="hero-badges">
                <div class="badge-item">
                    <i class="fa-solid fa-user-shield"></i>
                    <span>Bảo vệ dữ liệu cá nhân</span>
                </div>
                <div class="badge-item">
                    <i class="fa-solid fa-lock"></i>
                    <span>Mã hóa SSL 256-bit</span>
                </div>
                <div class="badge-item">
                    <i class="fa-solid fa-credit-card"></i>
                    <span>Bảo mật thanh toán PCI-DSS</span>
                </div>
            </div>
        </div>

        <div class="policy-layout">
            
            <!-- Sticky Sidebar Table of Contents -->
            <aside class="policy-sidebar" data-aos="fade-right" data-aos-duration="700">
                <div class="sidebar-title">
                    <i class="fa-solid fa-list-check"></i>
                    <span>Mục Lục Chính Sách</span>
                </div>
                <ul>
                    <li><a href="#bao-mat-1"><i class="fa-solid fa-angle-right"></i> 1. Mục đích thu thập</a></li>
                    <li><a href="#bao-mat-2"><i class="fa-solid fa-angle-right"></i> 2. Phạm vi sử dụng</a></li>
                    <li><a href="#bao-mat-3"><i class="fa-solid fa-angle-right"></i> 3. Thời gian lưu trữ</a></li>
                    <li><a href="#bao-mat-4"><i class="fa-solid fa-angle-right"></i> 4. Chia sẻ bên thứ ba</a></li>
                    <li><a href="#bao-mat-5"><i class="fa-solid fa-angle-right"></i> 5. Cam kết an toàn</a></li>
                    <li><a href="#bao-mat-6"><i class="fa-solid fa-angle-right"></i> 6. Quyền của khách hàng</a></li>
                    <li><a href="#bao-mat-7"><i class="fa-solid fa-angle-right"></i> 7. Sử dụng Cookie</a></li>
                    <li><a href="#bao-mat-8"><i class="fa-solid fa-angle-right"></i> 8. Đơn vị chủ quản</a></li>
                </ul>
            </aside>

            <!-- Main Content -->
            <main class="policy-content" data-aos="fade-up" data-aos-duration="700">
                
                <!-- Điều 1 -->
                <section class="policy-section" id="bao-mat-1">
                    <div class="section-header">
                        <span class="section-num">01</span>
                        <h3>Mục Đích & Phạm Vi Thu Thập Thông Tin</h3>
                    </div>
                    <p>DUT Travel chỉ thu thập các thông tin cá nhân cần thiết và phục vụ trực tiếp cho mục đích đặt tour, cung ứng dịch vụ lữ hành và chăm sóc khách hàng. Các thông tin được thu thập bao gồm:</p>
                    <ul>
                        <li><strong>Thông tin liên lạc cơ bản:</strong> Họ và tên, số điện thoại, địa chỉ email, địa chỉ liên hệ hoặc đón khách.</li>
                        <li><strong>Thông tin phục vụ thủ tục lữ hành:</strong> Số CCCD, số hộ chiếu, ngày cấp, nơi cấp, ngày sinh, quốc tịch, giới tính (phục vụ đặt vé máy bay, xuất vé tàu, thủ tục visa và bảo hiểm du lịch).</li>
                        <li><strong>Thông tin đơn hàng & Giao dịch:</strong> Lịch sử đặt tour, giá trị thanh toán, mã đơn hàng, phương thức thanh toán đã lựa chọn.</li>
                        <li><strong>Yêu cầu cá nhân đặc thù:</strong> Chế độ ăn uống (ăn chay, dị ứng), tình trạng sức khỏe cần hỗ trợ đặc biệt khi tham gia tour.</li>
                    </ul>
                    <div class="policy-box-note">
                        <p><i class="fa-solid fa-shield" style="color: #2563EB; margin-right: 6px;"></i> Quý khách hoàn toàn tự nguyện cung cấp thông tin. Tuy nhiên, nếu thiếu các thông tin bắt buộc theo quy định hàng không hoặc xuất nhập cảnh, DUT Travel có thể không thể hoàn tất việc đặt vé và cung cấp dịch vụ cho Quý khách.</p>
                    </div>
                </section>

                <!-- Điều 2 -->
                <section class="policy-section" id="bao-mat-2">
                    <div class="section-header">
                        <span class="section-num">02</span>
                        <h3>Phạm Vi Sử Dụng Thông Tin</h3>
                    </div>
                    <p>Thông tin của khách hàng chỉ được sử dụng cho các mục đích hợp pháp sau đây:</p>
                    <ul>
                        <li>Xác nhận đơn hàng, xuất vé và gửi phiếu xác nhận dịch vụ (Voucher) cho chuyến đi.</li>
                        <li>Đăng ký bảo hiểm du lịch theo quyền lợi bắt buộc của hành khách.</li>
                        <li>Hỗ trợ làm thủ tục xin cấp visa, thủ tục hàng không, làm thủ tục check-in khách sạn và khai báo tạm trú theo quy định pháp luật.</li>
                        <li>Liên hệ gửi các thông báo quan trọng về lịch trình, giờ đón, thay đổi chuyến bay (nếu có) hoặc xử lý các yêu cầu khẩn cấp trong chuyến đi.</li>
                        <li>Giải quyết các yêu cầu hoàn hủy vé, đổi lịch hoặc giải đáp khiếu nại của khách hàng.</li>
                        <li>Gửi bản tin khuyến mãi, ưu đãi tri ân thành viên (chỉ khi Quý khách đồng ý đăng ký nhận tin và có quyền hủy đăng ký bất kỳ lúc nào).</li>
                    </ul>
                </section>

                <!-- Điều 3 -->
                <section class="policy-section" id="bao-mat-3">
                    <div class="section-header">
                        <span class="section-num">03</span>
                        <h3>Thời Gian Lưu Trữ Thông Tin</h3>
                    </div>
                    <p>Dữ liệu cá nhân của khách hàng sẽ được lưu trữ an toàn trên máy chủ của DUT Travel cho đến khi hoàn thành xong dịch vụ hoặc cho đến khi khách hàng có yêu cầu hủy bỏ/xóa dữ liệu.</p>
                    <p>Đối với các chứng từ kế toán, hợp đồng lữ hành và hóa đơn thanh toán, thời gian lưu trữ được tuân thủ nghiêm ngặt theo quy định của Luật Kế toán và Pháp luật thuế Việt Nam (tối thiểu 5 - 10 năm).</p>
                </section>

                <!-- Điều 4 -->
                <section class="policy-section" id="bao-mat-4">
                    <div class="section-header">
                        <span class="section-num">04</span>
                        <h3>Chia Sẻ Thông Tin Với Bên Thứ Ba</h3>
                    </div>
                    <p>DUT Travel <strong>cam kết tuyệt đối không bán, cho thuê, trao đổi hoặc tiết lộ thông tin cá nhân của Quý khách cho bất kỳ bên thứ ba nào vì mục đích thương mại</strong>. Thông tin chỉ được cung cấp cho các bên liên quan trực tiếp sau:</p>
                    <ul>
                        <li><strong>Đối tác cung cấp dịch vụ trong tour:</strong> Các hãng hàng không (Vietnam Airlines, Vietjet Air, Bamboo Airways...), khách sạn, resort, công ty du thuyền, đơn vị làm thủ tục visa được ủy quyền.</li>
                        <li><strong>Công ty bảo hiểm:</strong> Nhằm mục đích lập hợp đồng bảo hiểm du lịch chính thức cho khách hàng.</li>
                        <li><strong>Cổng thanh toán điện tử (VNPay / Ngân hàng):</strong> Để thực hiện xác thực và xử lý giao dịch thanh toán trực tuyến an toàn. DUT Travel hoàn toàn không lưu trữ số thẻ tín dụng hoặc mã bảo mật CVV của khách hàng.</li>
                        <li><strong>Cơ quan chức năng có thẩm quyền:</strong> Khi có yêu cầu bằng văn bản chính thức của cơ quan nhà nước, cơ quan điều tra theo đúng trình tự pháp luật.</li>
                    </ul>
                </section>

                <!-- Điều 5 -->
                <section class="policy-section" id="bao-mat-5">
                    <div class="section-header">
                        <span class="section-num">05</span>
                        <h3>Cam Kết An Toàn & Bảo Mật Thanh Toán</h3>
                    </div>
                    <p>Hệ thống kỹ thuật của DUT Travel được trang bị các tiêu chuẩn bảo mật hiện đại:</p>
                    <ul>
                        <li>Toàn bộ dữ liệu truyền tải giữa máy tính người dùng và máy chủ được mã hóa bằng giao thức bảo mật cao cấp <strong>SSL/TLS 256-bit</strong>.</li>
                        <li>Cơ sở dữ liệu được quản lý phân quyền chặt chẽ, có hệ thống tường lửa (Firewall) ngăn chặn các cuộc tấn công mạng và rà soát lỗ hổng định kỳ.</li>
                        <li>Thanh toán trực tuyến qua cổng VNPay đạt chứng nhận an toàn quốc tế <strong>PCI-DSS Level 1</strong> cùng cơ chế xác thực OTP qua số điện thoại chính chủ của chủ thẻ.</li>
                    </ul>
                </section>

                <!-- Điều 6 -->
                <section class="policy-section" id="bao-mat-6">
                    <div class="section-header">
                        <span class="section-num">06</span>
                        <h3>Quyền Của Khách Hàng Đối Với Dữ Liệu Cá Nhân</h3>
                    </div>
                    <p>Theo quy định hiện hành, Quý khách có các quyền sau đối với dữ liệu cá nhân của mình:</p>
                    <ul>
                        <li><strong>Quyền được biết & Truy cập:</strong> Kiểm tra, tra cứu thông tin đơn hàng và tài khoản cá nhân của mình.</li>
                        <li><strong>Quyền chỉnh sửa:</strong> Yêu cầu cập nhật, điều chỉnh các thông tin chưa chính xác trước khi xuất vé.</li>
                        <li><strong>Quyền yêu cầu xóa bỏ:</strong> Yêu cầu DUT Travel xóa toàn bộ dữ liệu cá nhân sau khi chuyến đi đã kết thúc và không còn tranh chấp phát sinh.</li>
                        <li><strong>Quyền từ chối nhận quảng cáo:</strong> Hủy đăng ký nhận email tin tức khuyến mãi bất kỳ lúc nào chỉ với một thao tác.</li>
                    </ul>
                </section>

                <!-- Điều 7 -->
                <section class="policy-section" id="bao-mat-7">
                    <div class="section-header">
                        <span class="section-num">07</span>
                        <h3>Sử Dụng Cookie & Công Nghệ Theo Dõi</h3>
                    </div>
                    <p>Website DUT Travel sử dụng Cookie nhằm ghi nhớ giỏ hàng, tùy chọn ngôn ngữ và nâng cao trải nghiệm lướt web của Quý khách. Cookie không thu thập mật khẩu hoặc thông tin cá nhân nhạy cảm.</p>
                    <p>Quý khách hoàn toàn có thể chủ động tắt hoặc xóa Cookie trong phần Cài đặt trình duyệt của mình mà không làm ảnh hưởng đến việc xem thông tin tour.</p>
                </section>

                <!-- Điều 8 -->
                <section class="policy-section" id="bao-mat-8">
                    <div class="section-header">
                        <span class="section-num">08</span>
                        <h3>Đơn Vị Thu Thập & Quản Trị Thông Tin</h3>
                    </div>
                    <p>Mọi thắc mắc, yêu cầu tra soát hoặc khiếu nại liên quan đến quyền riêng tư và chính sách bảo mật, Quý khách vui lòng liên hệ trực tiếp với cơ quan chủ quản:</p>
                    <ul>
                        <li><strong>Tên đơn vị:</strong> Công ty Cổ phần Lữ hành DUT Travel</li>
                        <li><strong>Địa chỉ:</strong> 54 Nguyễn Lương Bằng, Phường Hòa Khánh Bắc, Quận Liên Chiểu, TP. Đà Nẵng</li>
                        <li><strong>Hotline chăm sóc khách hàng:</strong> 1900 6868 - (0236) 3842 345</li>
                        <li><strong>Email bảo mật & Pháp lý:</strong> privacy@duttravel.vn / contact@duttravel.vn</li>
                    </ul>
                </section>

                <!-- Callout Hotline Card -->
                <div class="policy-contact-card">
                    <div>
                        <h4>Bạn Có Câu Hỏi Về Dữ Liệu Riêng Tư?</h4>
                        <p>Bộ phận Pháp chế & An toàn thông tin của DUT Travel luôn sẵn sàng lắng nghe và giải đáp mọi thắc mắc của bạn.</p>
                    </div>
                    <a href="${pageContext.request.contextPath}/contact" class="btn-contact">
                        <i class="fa-solid fa-envelope-shield"></i>
                        Gửi Yêu Cầu Hỗ Trợ
                    </a>
                </div>

            </main>
        </div>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
