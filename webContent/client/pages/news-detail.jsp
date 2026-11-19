<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../layouts/header.jsp" />

<jsp:include page="../mixins/box-breadcrumb.jsp" />

<!-- Section 14: Trang Chi Tiết Bài Viết -->
<div class="section-14 page-news-detail">
    <div class="container">
        <div class="inner-layout">
            
            <!-- Cột trái: Nội dung chi tiết bài viết -->
            <main class="inner-main-col">
                <article class="article-wrapper" data-aos="fade-up" data-aos-duration="700">
                    
                    <!-- Header bài viết -->
                    <header class="article-header">
                        <div class="article-category">
                            <i class="fa-solid fa-tag"></i> ${article.category}
                        </div>
                        <h1 class="article-title">${article.title}</h1>
                        <div class="article-meta">
                            <span class="meta-item"><i class="fa-regular fa-user"></i> <b>${article.author}</b></span>
                            <span class="meta-item"><i class="fa-regular fa-calendar"></i> ${article.createdAtFormatted}</span>
                            <span class="meta-item"><i class="fa-regular fa-eye"></i> ${article.views} lượt xem</span>
                        </div>
                    </header>

                    <!-- Ảnh đại diện chính -->
                    <div class="article-featured-image">
                        <c:set var="detailImg" value="${article.avatar}" />
                        <c:if test="${not empty detailImg and not detailImg.startsWith('http')}">
                            <c:set var="detailImg" value="${pageContext.request.contextPath}${detailImg}" />
                        </c:if>
                        <img src="${detailImg}" alt="${article.title}" />
                    </div>

                    <!-- Tóm tắt bài viết (Lead summary) -->
                    <c:if test="${not empty article.description}">
                        <div class="article-lead">
                            <p>${article.description}</p>
                        </div>
                    </c:if>

                    <!-- Nội dung chi tiết (HTML Content) -->
                    <div class="article-content">
                        ${article.content}
                    </div>

                    <!-- Tác giả & Thẻ tag -->
                    <div class="article-author-card">
                        <div class="author-avatar">
                            <i class="fa-solid fa-user-pen"></i>
                        </div>
                        <div class="author-info">
                            <span class="author-label">Tác giả bài viết</span>
                            <h4 class="author-name">${article.author}</h4>
                            <p class="author-bio">Chuyên viên trải nghiệm & biên tập viên cẩm nang du lịch tại DUT Travel, luôn đam mê khám phá những cung đường mới và chia sẻ những câu chuyện chân thực nhất.</p>
                        </div>
                    </div>

                    <!-- Nút chia sẻ mạng xã hội -->
                    <div class="article-share-bar">
                        <span class="share-label"><i class="fa-solid fa-share-nodes"></i> Chia sẻ bài viết:</span>
                        <div class="share-buttons">
                            <a href="https://www.facebook.com/sharer/sharer.php?u=${pageContext.request.requestURL}" target="_blank" class="btn-share share-fb" title="Chia sẻ Facebook">
                                <i class="fa-brands fa-facebook-f"></i> Facebook
                            </a>
                            <a href="https://twitter.com/intent/tweet?url=${pageContext.request.requestURL}&text=${article.title}" target="_blank" class="btn-share share-tw" title="Chia sẻ Twitter">
                                <i class="fa-brands fa-x-twitter"></i> X / Twitter
                            </a>
                            <button type="button" class="btn-share share-copy" onclick="copyArticleLink()" title="Sao chép liên kết">
                                <i class="fa-solid fa-link"></i> <span id="copy-btn-text">Sao chép link</span>
                            </button>
                        </div>
                    </div>

                    <!-- Nút quay lại -->
                    <div class="article-back-bar">
                        <a href="${pageContext.request.contextPath}/news" class="btn-back-news">
                            <i class="fa-solid fa-arrow-left"></i>
                            <span>Xem tất cả bài viết cẩm nang</span>
                        </a>
                    </div>

                </article>

                <!-- Bài viết liên quan -->
                <c:if test="${not empty relatedNews}">
                    <section class="related-news-section" data-aos="fade-up" data-aos-duration="700">
                        <div class="related-header">
                            <div class="related-title">
                                <i class="fa-solid fa-compass"></i>
                                <span>Bài Viết Cùng Chủ Đề</span>
                            </div>
                        </div>
                        <div class="related-grid">
                            <c:forEach var="rel" items="${relatedNews}">
                                <article class="news-card">
                                    <a href="${pageContext.request.contextPath}/news/detail/${rel.slug}" class="news-card-thumb">
                                        <c:set var="relImg" value="${rel.avatar}" />
                                        <c:if test="${not empty relImg and not relImg.startsWith('http')}">
                                            <c:set var="relImg" value="${pageContext.request.contextPath}${relImg}" />
                                        </c:if>
                                        <img src="${relImg}" alt="${rel.title}" />
                                        <span class="news-card-category">${rel.category}</span>
                                    </a>
                                    <div class="news-card-body">
                                        <div class="news-card-meta">
                                            <span><i class="fa-regular fa-calendar"></i> ${rel.createdAtFormatted}</span>
                                            <span><i class="fa-regular fa-eye"></i> ${rel.views}</span>
                                        </div>
                                        <h3 class="news-card-title">
                                            <a href="${pageContext.request.contextPath}/news/detail/${rel.slug}">${rel.title}</a>
                                        </h3>
                                        <div class="news-card-footer">
                                            <a href="${pageContext.request.contextPath}/news/detail/${rel.slug}" class="news-card-readmore">
                                                <span>Xem chi tiết</span>
                                                <i class="fa-solid fa-arrow-right"></i>
                                            </a>
                                        </div>
                                    </div>
                                </article>
                            </c:forEach>
                        </div>
                    </section>
                </c:if>

            </main>

            <!-- Cột phải: Sidebar -->
            <aside class="inner-sidebar" data-aos="fade-left" data-aos-duration="700">
                <!-- Widget 1: Bài viết xem nhiều -->
                <div class="widget-box widget-popular">
                    <div class="widget-header">
                        <i class="fa-solid fa-fire"></i>
                        <span>Được Quan Tâm Nhất</span>
                    </div>
                    <div class="widget-body">
                        <ul class="popular-list">
                            <c:forEach var="pop" items="${topViewsNews}" varStatus="loop">
                                <li class="popular-item">
                                    <span class="popular-rank rank-${loop.index + 1}">${loop.index + 1}</span>
                                    <div class="popular-info">
                                        <h4 class="popular-title">
                                            <a href="${pageContext.request.contextPath}/news/detail/${pop.slug}">
                                                ${pop.title}
                                            </a>
                                        </h4>
                                        <div class="popular-meta">
                                            <span><i class="fa-regular fa-eye"></i> ${pop.views} lượt xem</span>
                                            <span>• ${pop.createdAtFormatted}</span>
                                        </div>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>

                <!-- Widget 2: Banner đặt tour -->
                <div class="widget-box widget-promo-banner">
                    <div class="promo-bg-wrap">
                        <img src="https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80" alt="Promo" />
                        <div class="promo-overlay"></div>
                    </div>
                    <div class="promo-content">
                        <span class="promo-badge">ƯU ĐÃI ĐỘC QUYỀN</span>
                        <h3 class="promo-title">Khám Phá Các Tuyến Tour Hấp Dẫn 2026</h3>
                        <p class="promo-desc">Trọn gói dịch vụ chuẩn 5 sao, lịch trình khởi hành hàng tuần cùng DUT Travel.</p>
                        <a href="${pageContext.request.contextPath}/category/tour-trong-nuoc" class="btn-promo-action">
                            <span>Xem Tour Ngay</span>
                            <i class="fa-solid fa-arrow-right"></i>
                        </a>
                    </div>
                </div>

                <!-- Widget 3: Newsletter -->
                <div class="widget-box widget-newsletter">
                    <div class="newsletter-icon">
                        <i class="fa-regular fa-paper-plane"></i>
                    </div>
                    <h3 class="newsletter-title">Nhận Cẩm Nang Miễn Phí</h3>
                    <p class="newsletter-desc">Đăng ký để nhận những cẩm nang du lịch và ưu đãi mới nhất mỗi tuần qua email.</p>
                    <div class="newsletter-box">
                        <input type="email" id="news-sidebar-email" placeholder="Địa chỉ email của bạn..." />
                        <button type="button" id="news-sidebar-btn">Đăng Ký</button>
                    </div>
                </div>
            </aside>

        </div>
    </div>
</div>

<script>
function copyArticleLink() {
    navigator.clipboard.writeText(window.location.href).then(function() {
        var copyBtn = document.getElementById("copy-btn-text");
        if (copyBtn) {
            copyBtn.innerText = "Đã sao chép!";
            setTimeout(function() {
                copyBtn.innerText = "Sao chép link";
            }, 2500);
        }
    });
}
</script>

<jsp:include page="../layouts/footer.jsp" />
