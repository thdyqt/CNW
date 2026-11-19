<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${not empty pageTitle ? pageTitle : 'Đăng nhập quản trị'}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/admin/assets/css/style.css?v=20261119_02">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script src="${pageContext.request.contextPath}/public/admin/assets/js/notify.js?v=4"></script>
</head>
<body>
    <div class="page-account">
