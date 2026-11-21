<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="top-header">
    <div class="container">
        <div class="inner-wrap">
            <c:if test="${not empty settingWebsiteInfo.phone}">
                <div class="inner-item">
                    <i class="fa-solid fa-phone"></i>
                    ${settingWebsiteInfo.phone}
                </div>
            </c:if>
            <c:if test="${not empty settingWebsiteInfo.email}">
                <div class="inner-item">
                    <i class="fa-solid fa-envelope"></i>
                    ${settingWebsiteInfo.email}
                </div>
            </c:if>
            <c:if test="${not empty settingWebsiteInfo.address}">
                <div class="inner-item">
                    <i class="fa-solid fa-building"></i>
                    ${settingWebsiteInfo.address}
                </div>
            </c:if>
        </div>
    </div>
</div>

<header class="header">
    <div class="container">
        <div class="inner-wrap">
            <button class="inner-menu-mobile">
                <i class="fa-solid fa-bars"></i>
            </button>
            <a class="inner-logo" href="${pageContext.request.contextPath}/">
                <img alt="${settingWebsiteInfo.name}" src="${settingWebsiteInfo.logo}" />
            </a>
            <nav class="inner-menu">
                <ul>
                    <li>
                        <a class="${empty categoryCurrent and empty pageCurrent ? 'active' : ''}" href="${pageContext.request.contextPath}/">Trang Chủ</a>
                    </li>
                    <li>
                        <a class="${pageCurrent == 'about' ? 'active' : ''}" href="${pageContext.request.contextPath}/about">Giới Thiệu</a>
                    </li>
                    <c:forEach var="item" items="${categoryList}">
                        <li>
                            <a class="${not empty categoryCurrent && categoryCurrent.slug == item.slug ? 'active' : ''}" href="${pageContext.request.contextPath}/category/${item.slug}">${item.name}</a>
                            <c:if test="${not empty item.children}">
                                <i class="fa-solid fa-caret-down"></i>
                                <ul>
                                    <c:forEach var="itemChild" items="${item.children}">
                                        <li>
                                            <a class="${not empty categoryCurrent && categoryCurrent.slug == itemChild.slug ? 'active' : ''}" href="${pageContext.request.contextPath}/category/${itemChild.slug}">${itemChild.name}</a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                        </li>
                    </c:forEach>
                    <li>
                        <a class="${pageCurrent == 'news' ? 'active' : ''}" href="${pageContext.request.contextPath}/news">Tin Tức</a>
                    </li>
                    <li>
                        <a class="${pageCurrent == 'contact' ? 'active' : ''}" href="${pageContext.request.contextPath}/contact">Liên Hệ</a>
                    </li>
                    <li>
                        <a class="${pageCurrent == 'lookup' ? 'active' : ''}" href="${pageContext.request.contextPath}/order/lookup">Tra Cứu Đơn</a>
                    </li>
                </ul>
                <div class="inner-overlay"></div>
            </nav>
            <div class="inner-customer-account">
                <c:choose>
                    <c:when test="${not empty sessionScope.customerUser}">
                        <a href="${pageContext.request.contextPath}/account/profile" title="Thông tin cá nhân">
                            <i class="fa-regular fa-user"></i>
                            <span><c:out value="${sessionScope.customerUser.fullName}" /></span>
                        </a>
                        <a class="inner-account-secondary" href="${pageContext.request.contextPath}/account/logout">Đăng xuất</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/account/login">
                            <i class="fa-regular fa-user"></i><span>Đăng nhập</span>
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="inner-cart">
                <a class="inner-icon" href="${pageContext.request.contextPath}/cart">
                    <img alt="Cart" src="${pageContext.request.contextPath}/public/assets/images/Cart.svg" />
                    <span mini-cart>0</span>
                </a>
            </div>
        </div>
    </div>
</header>
