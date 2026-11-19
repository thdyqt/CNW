<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:include page="../layouts/header.jsp" />

<jsp:include page="../mixins/box-breadcrumb.jsp" />

<!-- Section 14: Trang Tin Tức & Cẩm Nang Du Lịch -->
<div class="section-14">
    <div class="container">

        <!-- 1. Thanh điều hướng danh mục & Ô tìm kiếm -->
        <div class="inner-filter-bar" data-aos="fade-down" data-aos-duration="600">
            <div class="inner-tabs">
                <a href="${pageContext.request.contextPath}/news" class="inner-tab-item ${empty newsCategoryCurrent or newsCategoryCurrent == 'all' ? 'active' : ''}">
                    Tất cả
                </a>
                <a href="${pageContext.request.contextPath}/news?category=Cẩm nang du lịch" class="inner-tab-item ${newsCategoryCurrent == 'Cẩm nang du lịch' ? 'active' : ''}">
                    Cẩm nang du lịch
                </a>
                <a href="${pageContext.request.contextPath}/news?category=Điểm đến hot" class="inner-tab-item ${newsCategoryCurrent == 'Điểm đến hot' ? 'active' : ''}">
                    Điểm đến hot
                </a>
                <a href="${pageContext.request.contextPath}/news?category=Nghỉ dưỡng" class="inner-tab-item ${newsCategoryCurrent == 'Nghỉ dưỡng' ? 'active' : ''}">
                    Nghỉ dưỡng
                </a>
                <a href="${pageContext.request.contextPath}/news?category=Ẩm thực" class="inner-tab-item ${newsCategoryCurrent == 'Ẩm thực' ? 'active' : ''}">
                    Ẩm thực
                </a>
                <a href="${pageContext.request.contextPath}/news?category=Khám phá" class="inner-tab-item ${newsCategoryCurrent == 'Khám phá' ? 'active' : ''}">
                    Khám phá
                </a>
                <a href="${pageContext.request.contextPath}/news?category=Trekking" class="inner-tab-item ${newsCategoryCurrent == 'Trekking' ? 'active' : ''}">
                    Trekking
                </a>
                <a href="${pageContext.request.contextPath}/news?category=Mẹo hay" class="inner-tab-item ${newsCategoryCurrent == 'Mẹo hay' ? 'active' : ''}">
                    Mẹo hay
                </a>
            </div>

            <form class="inner-search-box" action="${pageContext.request.contextPath}/news" method="GET">
                <c:if test="${not empty newsCategoryCurrent}">
                    <input type="hidden" name="category" value="${newsCategoryCurrent}" />
                </c:if>
                <i class="fa-solid fa-magnifying-glass"></i>
                <input type="text" name="keyword" value="${keyword}" placeholder="Tìm kiếm bài viết, điểm đến..." />
                <button type="submit">Tìm</button>
            </form>
        </div>

        <!-- 2. Bài viết tiêu điểm (Featured Hero Article) - Chỉ hiển thị khi ở trang 1 và không tìm kiếm từ khóa -->
        <c:if test="${(empty param.page or param.page == '1') and empty keyword and not empty featuredNews}">
            <div class="inner-featured-hero" data-aos="fade-up" data-aos-duration="700">
                <a href="${pageContext.request.contextPath}/news/detail/${featuredNews.slug}" class="inner-hero-wrap">
                    <div class="inner-hero-image">
                        <c:set var="featImg" value="${featuredNews.avatar}" />
                        <c:if test="${not empty featImg and not featImg.startsWith('http')}">
                            <c:set var="featImg" value="${pageContext.request.contextPath}${featImg}" />
                        </c:if>
                        <img src="${featImg}" alt="${featuredNews.title}" />
                        <div class="inner-hero-badge">
                            <i class="fa-solid fa-star"></i> TIÊU ĐIỂM
                        </div>
                    </div>
                    <div class="inner-hero-content">
                        <div class="inner-hero-tag">
                            <i class="fa-solid fa-tag"></i> ${featuredNews.category}
                        </div>
                        <h2 class="inner-hero-title">${featuredNews.title}</h2>
                        <p class="inner-hero-desc">${featuredNews.description}</p>
                        <div class="inner-hero-meta">
                            <span><i class="fa-regular fa-user"></i> ${featuredNews.author}</span>
                            <span><i class="fa-regular fa-calendar"></i> ${featuredNews.createdAtFormatted}</span>
                            <span><i class="fa-regular fa-eye"></i> ${featuredNews.views} lượt xem</span>
                        </div>
                        <div class="inner-hero-btn">
                            <span>Đọc tiếp</span>
                            <i class="fa-solid fa-arrow-right"></i>
                        </div>
                    </div>
                </a>
            </div>
        </c:if>

        <!-- 3. Khu vực chính: Lưới bài viết + Sidebar -->
        <div class="inner-layout">
            <!-- Cột trái: Lưới bài viết -->
            <div class="inner-main-col">
                <c:choose>
                    <c:when test="${not empty newsList}">
                        <div class="inner-news-grid" data-aos="fade-up" data-aos-duration="700">
                            <c:forEach var="item" items="${newsList}">
                                <article class="news-card">
                                    <a href="${pageContext.request.contextPath}/news/detail/${item.slug}" class="news-card-thumb">
                                        <c:set var="imgUrl" value="${item.avatar}" />
                                        <c:if test="${not empty imgUrl and not imgUrl.startsWith('http')}">
                                            <c:set var="imgUrl" value="${pageContext.request.contextPath}${imgUrl}" />
                                        </c:if>
                                        <img src="${imgUrl}" alt="${item.title}" />
                                        <span class="news-card-category">${item.category}</span>
                                    </a>
                                    <div class="news-card-body">
                                        <div class="news-card-meta">
                                            <span><i class="fa-regular fa-calendar"></i> ${item.createdAtFormatted}</span>
                                            <span><i class="fa-regular fa-eye"></i> ${item.views}</span>
                                        </div>
                                        <h3 class="news-card-title">
                                            <a href="${pageContext.request.contextPath}/news/detail/${item.slug}" title="${item.title}">
                                                ${item.title}
                                            </a>
                                        </h3>
                                        <p class="news-card-desc">
                                            ${item.description}
                                        </p>
                                        <div class="news-card-footer">
                                            <a href="${pageContext.request.contextPath}/news/detail/${item.slug}" class="news-card-readmore">
                                                <span>Xem chi tiết</span>
                                                <i class="fa-solid fa-arrow-right"></i>
                                            </a>
                                        </div>
                                    </div>
                                </article>
                            </c:forEach>
                        </div>

                        <!-- Phân trang -->
                        <c:if test="${pagination.totalPages > 1}">
                            <div class="inner-pagination-bar" data-aos="fade-up">
                                <c:if test="${pagination.currentPage > 1}">
                                    <a href="${pageContext.request.contextPath}/news?page=${pagination.currentPage - 1}${not empty newsCategoryCurrent ? '&category='.concat(newsCategoryCurrent) : ''}${not empty keyword ? '&keyword='.concat(keyword) : ''}" class="pagination-arrow">
                                        <i class="fa-solid fa-chevron-left"></i>
                                    </a>
                                </c:if>
                                
                                <c:forEach var="p" begin="1" end="${pagination.totalPages}">
                                    <a href="${pageContext.request.contextPath}/news?page=${p}${not empty newsCategoryCurrent ? '&category='.concat(newsCategoryCurrent) : ''}${not empty keyword ? '&keyword='.concat(keyword) : ''}" class="pagination-number ${pagination.currentPage == p ? 'active' : ''}">
                                        ${p}
                                    </a>
                                </c:forEach>

                                <c:if test="${pagination.currentPage < pagination.totalPages}">
                                    <a href="${pageContext.request.contextPath}/news?page=${pagination.currentPage + 1}${not empty newsCategoryCurrent ? '&category='.concat(newsCategoryCurrent) : ''}${not empty keyword ? '&keyword='.concat(keyword) : ''}" class="pagination-arrow">
                                        <i class="fa-solid fa-chevron-right"></i>
                                    </a>
                                </c:if>
                            </div>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <div class="inner-empty-state" data-aos="fade-up">
                            <i class="fa-regular fa-newspaper"></i>
                            <h3>Không tìm thấy bài viết nào phù hợp!</h3>
                            <p>Hãy thử tìm kiếm với từ khóa khác hoặc khám phá các danh mục bài viết khác.</p>
                            <a href="${pageContext.request.contextPath}/news" class="btn-reset-filter">
                                <i class="fa-solid fa-rotate-left"></i> Xem tất cả tin tức
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

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

                <!-- Widget 2: Banner đặt tour ưu đãi -->
                <div class="widget-box widget-promo-banner">
                    <div class="promo-bg-wrap">
                        <img src="https://images.unsplash.com/photo-1507525428034-b723cf961d3e?auto=format&fit=crop&w=600&q=80" alt="Promo" />
                        <div class="promo-overlay"></div>
                    </div>
                    <div class="promo-content">
                        <span class="promo-badge">ƯU ĐÃI ĐỘC QUYỀN</span>
                        <h3 class="promo-title">Săn Tour Giá Sốc 2026 Cùng DUT Travel</h3>
                        <p class="promo-desc">Giảm đến 30% cho khách hàng đăng ký sớm các tuyến du lịch trong và ngoài nước.</p>
                        <a href="${pageContext.request.contextPath}/category/tour-trong-nuoc" class="btn-promo-action">
                            <span>Khám Phá Tour Ngay</span>
                            <i class="fa-solid fa-arrow-right"></i>
                        </a>
                    </div>
                </div>

                <!-- Widget 3: Hộp đăng ký nhận cẩm nang -->
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

<jsp:include page="../layouts/footer.jsp" />
