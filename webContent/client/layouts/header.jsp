<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <title>${pageTitle}</title>
    <c:set var="favUrl" value="${settingWebsiteInfo.favicon}" />
    <c:if test="${not empty favUrl and not favUrl.startsWith('http')}">
        <c:set var="favUrl" value="${pageContext.request.contextPath}${favUrl}" />
    </c:if>
    <link href="${favUrl}" rel="icon" type="image/x-icon">
    <link href="${pageContext.request.contextPath}/public/assets/css/style.css?v=20261118_01" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css" rel="stylesheet">
    <link href="https://unpkg.com/aos@2.3.1/dist/aos.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/swiper@12/swiper-bundle.min.css" rel="stylesheet">
    <link referrerpolicy="no-referrer" crossorigin="anonymous" integrity="sha512-eLs5UFuWC/5nAsCUuBbx9W8ry51hsWDqpnhWUjE4aGAzgdO3iPB+KiaaTqH3IKT2OFAO66hOrnR6O2YlJvDdjw==" href="https://cdnjs.cloudflare.com/ajax/libs/viewerjs/1.13.0/viewer.min.css" rel="stylesheet">
</head>
<body>
    <c:if test="${not empty messages.success}">
        <div class="alert alert-success" alert-time="3000">
            <span>${messages.success}</span>
        </div>
    </c:if>

    <jsp:include page="../partials/header.jsp" />
