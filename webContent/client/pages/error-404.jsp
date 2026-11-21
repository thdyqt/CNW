<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../layouts/header.jsp" />

<div class="section-404-client">
    <div class="inner-box" data-aos="zoom-in" data-aos-duration="600">
        <img class="inner-image" 
             src="${pageContext.request.contextPath}/public/admin/assets/images/Error404.jpg" 
             alt="404 - Không tìm thấy trang" />
        <h1 class="inner-title">Trang này không tồn tại hoặc đã bị gỡ bỏ</h1>
        <p class="inner-desc">
            Rất tiếc, đường dẫn bạn vừa truy cập không tồn tại, đã bị đổi tên hoặc bạn đã nhập sai địa chỉ. Hãy chọn một trong các lối tắt bên dưới để tiếp tục hành trình nhé!
        </p>
        <div class="inner-actions">
            <a href="${pageContext.request.contextPath}/" class="btn-404 btn-primary">
                <i class="fa-solid fa-house"></i> Về Trang Chủ
            </a>
            <a href="${pageContext.request.contextPath}/category/tour-trong-nuoc" class="btn-404 btn-outline">
                <i class="fa-solid fa-compass"></i> Khám Phá Tour
            </a>
            <a href="${pageContext.request.contextPath}/contact" class="btn-404 btn-light">
                <i class="fa-solid fa-headset"></i> Hỗ Trợ Khách Hàng
            </a>
        </div>
    </div>
</div>

<jsp:include page="../layouts/footer.jsp" />
