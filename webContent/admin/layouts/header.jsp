<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${not empty pageTitle ? pageTitle : 'Quản trị hệ thống'}</title>
    <link rel="stylesheet" href="https://unpkg.com/filepond@^4/dist/filepond.css">
    <link rel="stylesheet" href="https://unpkg.com/filepond-plugin-image-preview/dist/filepond-plugin-image-preview.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/admin/assets/css/style.css?v=20261119_02">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="${pageContext.request.contextPath}/public/admin/assets/js/notify.js?v=4"></script>
    <script src="https://cdn.tiny.cloud/1/g7onstdn9mpkxz9eie04kc8ods3a56qygq0xj7mo254847xh/tinymce/8/tinymce.min.js" referrerpolicy="origin" crossorigin="anonymous"></script>
    <script>
        const CONTEXT_PATH = "${pageContext.request.contextPath}";
        const pathAdmin = (CONTEXT_PATH ? CONTEXT_PATH.replace(/^\//, '') + "/" : "") + "${pathAdmin}";
    </script>
    <script src="${pageContext.request.contextPath}/public/admin/assets/js/tinymce-config.js"></script>
</head>
<body>
    <c:if test="${not empty messages.success || not empty sessionScope.flashSuccess}">
        <div class="alert alert-success" alert-time="3000">
            <i class="fa-solid fa-circle-check"></i>
            <span>${not empty messages.success ? messages.success : sessionScope.flashSuccess}</span>
            <span class="close-alert" onclick="this.parentElement.remove()">&times;</span>
        </div>
        <c:remove var="flashSuccess" scope="session" />
    </c:if>
    <c:if test="${not empty messages.error || not empty sessionScope.flashError}">
        <div class="alert alert-danger" alert-time="3000">
            <i class="fa-solid fa-circle-xmark"></i>
            <span>${not empty messages.error ? messages.error : sessionScope.flashError}</span>
            <span class="close-alert" onclick="this.parentElement.remove()">&times;</span>
        </div>
        <c:remove var="flashError" scope="session" />
    </c:if>

    <!-- Header -->
    <jsp:include page="../partials/header.jsp" />

    <!-- Sider -->
    <jsp:include page="../partials/sider.jsp" />

    <!-- Main -->
    <main class="main">
