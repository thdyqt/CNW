<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Chuyển hướng tới Home Servlet hoặc trang home.jsp
    response.sendRedirect(request.getContextPath() + "/home");
%>
