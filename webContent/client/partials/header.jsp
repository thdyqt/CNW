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
                        <a class="${empty categoryCurrent ? 'active' : ''}" href="${pageContext.request.contextPath}/">Trang Chủ</a>
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
                        <a href="#">Tin Tức</a>
                    </li>
                    <li>
                        <a href="#">Liên Hệ</a>
                    </li>
                </ul>
                <div class="inner-overlay"></div>
            </nav>
            <div class="inner-cart">
                <a class="inner-icon" href="${pageContext.request.contextPath}/cart">
                    <img alt="Cart" src="${pageContext.request.contextPath}/public/assets/images/Cart.svg" />
                    <span mini-cart>0</span>
                </a>
            </div>
        </div>
    </div>
</header>
