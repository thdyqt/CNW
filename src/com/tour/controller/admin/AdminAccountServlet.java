package com.tour.controller.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour.dao.AccountDAO;
import com.tour.model.Account;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminAccountServlet extends HttpServlet {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());

        if (path.endsWith("/logout")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(contextPath + "/admin/account/login");
            return;
        }

        if (path.endsWith("/register")) {
            request.setAttribute("pageTitle", "Đăng ký");
            request.getRequestDispatcher("/views/admin/pages/register.jsp").forward(request, response);
            return;
        }

        if (path.endsWith("/register-initial")) {
            request.setAttribute("pageTitle", "Khởi tạo tài khoản");
            request.getRequestDispatcher("/views/admin/pages/register-initial.jsp").forward(request, response);
            return;
        }

        if (path.endsWith("/forgot-password")) {
            request.setAttribute("pageTitle", "Quên mật khẩu");
            request.getRequestDispatcher("/views/admin/pages/forgot-password.jsp").forward(request, response);
            return;
        }

        if (path.endsWith("/otp-password")) {
            request.setAttribute("pageTitle", "Xác thực OTP");
            request.getRequestDispatcher("/views/admin/pages/otp-password.jsp").forward(request, response);
            return;
        }

        if (path.endsWith("/reset-password")) {
            request.setAttribute("pageTitle", "Đổi mật khẩu");
            request.getRequestDispatcher("/views/admin/pages/reset-password.jsp").forward(request, response);
            return;
        }

        // Default: login page
        request.setAttribute("pageTitle", "Đăng nhập");
        request.getRequestDispatcher("/views/admin/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());

        String contentType = request.getContentType();
        String accept = request.getHeader("Accept");
        boolean isJson = (accept != null && accept.contains("application/json"))
                || (contentType != null && contentType.contains("application/json"));

        HttpSession session = request.getSession();

        // 1. Logout POST
        if (path.endsWith("/logout")) {
            session.invalidate();
            if (isJson) {
                sendJsonResponse(response, "success", "Đăng xuất thành công!");
                return;
            }
            response.sendRedirect(contextPath + "/admin/account/login");
            return;
        }

        // Read JSON body if present
        JsonNode jsonBody = null;
        if (contentType != null && contentType.contains("application/json")) {
            try {
                jsonBody = objectMapper.readTree(request.getReader());
            } catch (Exception ignored) {}
        }

        // 2. Register POST
        if (path.endsWith("/register")) {
            String fullName = null;
            String email = null;
            String password = null;

            if (jsonBody != null) {
                if (jsonBody.has("fullName")) fullName = jsonBody.get("fullName").asText();
                if (jsonBody.has("email")) email = jsonBody.get("email").asText();
                if (jsonBody.has("password")) password = jsonBody.get("password").asText();
            } else {
                fullName = request.getParameter("fullName");
                email = request.getParameter("email");
                password = request.getParameter("password");
            }

            if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                if (isJson) {
                    sendJsonResponse(response, "error", "Vui lòng nhập đầy đủ thông tin!");
                    return;
                }
                response.sendRedirect(contextPath + "/admin/account/register");
                return;
            }

            Account existAccount = accountDAO.getAccountByEmail(email.trim());
            if (existAccount != null) {
                if (isJson) {
                    sendJsonResponse(response, "error", "Email đã tồn tại trong hệ thống!");
                    return;
                }
                response.sendRedirect(contextPath + "/admin/account/register");
                return;
            }

            Account newAcc = new Account();
            newAcc.setFullName(fullName != null ? fullName.trim() : "Quản trị viên mới");
            newAcc.setEmail(email.trim());
            newAcc.setStatus("active");
            newAcc.setRoleId(1);
            newAcc.setPosition("Quản trị viên");
            accountDAO.createAccount(newAcc, password.trim());

            if (isJson) {
                sendJsonResponse(response, "success", "Đăng ký tài khoản thành công!");
                return;
            }
            response.sendRedirect(contextPath + "/admin/account/register-initial");
            return;
        }

        // 3. Forgot Password POST
        if (path.endsWith("/forgot-password")) {
            String email = null;
            if (jsonBody != null && jsonBody.has("email")) {
                email = jsonBody.get("email").asText();
            } else {
                email = request.getParameter("email");
            }

            if (email == null || email.trim().isEmpty()) {
                sendJsonResponse(response, "error", "Vui lòng nhập Email!");
                return;
            }

            Account existAccount = accountDAO.getAccountByEmail(email.trim());
            if (existAccount == null) {
                sendJsonResponse(response, "error", "Email không tồn tại trong hệ thống!");
                return;
            }

            sendJsonResponse(response, "success", "Gửi mã OTP thành công! (Mã thử nghiệm: 123456)");
            return;
        }

        // 4. OTP Password POST
        if (path.endsWith("/otp-password")) {
            String otp = null;
            if (jsonBody != null && jsonBody.has("otp")) {
                otp = jsonBody.get("otp").asText();
            } else {
                otp = request.getParameter("otp");
            }

            if (otp == null || otp.trim().length() != 6) {
                sendJsonResponse(response, "error", "Mã OTP không chính xác!");
                return;
            }

            sendJsonResponse(response, "success", "Xác thực OTP thành công!");
            return;
        }

        // 5. Reset Password POST
        if (path.endsWith("/reset-password")) {
            String password = null;
            if (jsonBody != null && jsonBody.has("password")) {
                password = jsonBody.get("password").asText();
            } else {
                password = request.getParameter("password");
            }

            if (password == null || password.trim().isEmpty()) {
                sendJsonResponse(response, "error", "Vui lòng nhập mật khẩu mới!");
                return;
            }

            Account sessionAcc = (Account) session.getAttribute("account");
            if (sessionAcc != null && sessionAcc.getId() > 0) {
                accountDAO.updatePassword(sessionAcc.getId(), password.trim());
            }

            sendJsonResponse(response, "success", "Thay đổi mật khẩu thành công! Vui lòng đăng nhập lại.");
            return;
        }

        // 6. Login POST (path ends with /login or is default for AdminAccountServlet)
        String email = null;
        String password = null;
        boolean rememberPassword = false;

        if (jsonBody != null) {
            if (jsonBody.has("email")) email = jsonBody.get("email").asText();
            if (jsonBody.has("password")) password = jsonBody.get("password").asText();
            if (jsonBody.has("rememberPassword")) rememberPassword = jsonBody.get("rememberPassword").asBoolean();
        } else {
            email = request.getParameter("email");
            password = request.getParameter("password");
            if (request.getParameter("rememberPassword") != null) {
                rememberPassword = "on".equalsIgnoreCase(request.getParameter("rememberPassword"))
                        || "true".equalsIgnoreCase(request.getParameter("rememberPassword"));
            }
        }

        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            if (isJson) {
                sendJsonResponse(response, "error", "Vui lòng nhập đầy đủ email và mật khẩu!");
                return;
            }
            response.sendRedirect(contextPath + "/admin/account/login");
            return;
        }

        Account existAccount = accountDAO.getAccountByEmail(email.trim());
        if (existAccount == null) {
            if (isJson) {
                sendJsonResponse(response, "error", "Email chưa tồn tại trong hệ thống!");
                return;
            }
            response.sendRedirect(contextPath + "/admin/account/login");
            return;
        }

        // Check password
        boolean isPasswordValid = accountDAO.checkPassword(password, existAccount.getPassword());
        if (!isPasswordValid) {
            if (isJson) {
                sendJsonResponse(response, "error", "Mật khẩu không đúng!");
                return;
            }
            response.sendRedirect(contextPath + "/admin/account/login");
            return;
        }

        // Check active status
        if (!"active".equalsIgnoreCase(existAccount.getStatus())) {
            if (isJson) {
                sendJsonResponse(response, "error", "Tài khoản chưa được kích hoạt!");
                return;
            }
            response.sendRedirect(contextPath + "/admin/account/login");
            return;
        }

        // Login success: save account in session
        session.setAttribute("account", existAccount);

        if (isJson) {
            sendJsonResponse(response, "success", "Đăng nhập tài khoản thành công!");
            return;
        }
        response.sendRedirect(contextPath + "/admin/dashboard");
    }

    private void sendJsonResponse(HttpServletResponse response, String code, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("message", message);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
