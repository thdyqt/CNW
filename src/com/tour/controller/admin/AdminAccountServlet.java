package com.tour.controller.admin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tour.dao.AccountDAO;
import com.tour.dao.ForgotPasswordDAO;
import com.tour.model.Account;
import com.tour.util.MailUtil;
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
    private final ForgotPasswordDAO forgotPasswordDAO = new ForgotPasswordDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String path = uri.substring(contextPath.length());

        if (path.endsWith("/logout")) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                Account acc = (Account) session.getAttribute("account");
                if (acc != null) {
                    accountDAO.updateToken(acc.getId(), null);
                }
                session.invalidate();
            }
            javax.servlet.http.Cookie clearCookie = new javax.servlet.http.Cookie("token", "");
            clearCookie.setPath("/");
            clearCookie.setMaxAge(0);
            clearCookie.setHttpOnly(true);
            response.addCookie(clearCookie);
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
            String email = request.getParameter("email");
            if (email == null || email.trim().isEmpty()) {
                response.sendRedirect(contextPath + "/admin/account/forgot-password");
                return;
            }
            request.setAttribute("pageTitle", "Xác thực OTP");
            request.getRequestDispatcher("/views/admin/pages/otp-password.jsp").forward(request, response);
            return;
        }

        if (path.endsWith("/reset-password")) {
            HttpSession curSession = request.getSession(false);
            String resetEmail = curSession != null ? (String) curSession.getAttribute("resetPasswordEmail") : null;
            Account acc = curSession != null ? (Account) curSession.getAttribute("account") : null;
            if (resetEmail == null && acc == null) {
                response.sendRedirect(contextPath + "/admin/account/forgot-password");
                return;
            }
            request.setAttribute("pageTitle", "Đổi mật khẩu");
            request.getRequestDispatcher("/views/admin/pages/reset-password.jsp").forward(request, response);
            return;
        }

        // Check if already authenticated via session or token cookie
        HttpSession session = request.getSession(false);
        Account currentAcc = session != null ? (Account) session.getAttribute("account") : null;
        if (currentAcc == null) {
            javax.servlet.http.Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (javax.servlet.http.Cookie c : cookies) {
                    if ("token".equals(c.getName()) && c.getValue() != null && !c.getValue().trim().isEmpty()) {
                        Account tokenAcc = accountDAO.getAccountByToken(c.getValue().trim());
                        if (tokenAcc != null && "active".equalsIgnoreCase(tokenAcc.getStatus())) {
                            currentAcc = tokenAcc;
                            request.getSession().setAttribute("account", tokenAcc);
                            break;
                        }
                    }
                }
            }
        }
        if (currentAcc != null && currentAcc.getId() > 0) {
            response.sendRedirect(contextPath + "/admin/dashboard");
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
            Account acc = (Account) session.getAttribute("account");
            if (acc != null) {
                accountDAO.updateToken(acc.getId(), null);
            }
            session.invalidate();
            javax.servlet.http.Cookie clearCookie = new javax.servlet.http.Cookie("token", "");
            clearCookie.setPath("/");
            clearCookie.setMaxAge(0);
            clearCookie.setHttpOnly(true);
            response.addCookie(clearCookie);
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
            newAcc.setStatus("initial");
            newAcc.setRoleId(0);
            newAcc.setPosition("");
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

            if (forgotPasswordDAO.existsActiveOtp(email.trim())) {
                sendJsonResponse(response, "error", "Vui lòng gửi lại yêu cầu sau 5 phút!");
                return;
            }

            // Tạo mã OTP ngẫu nhiên 6 chữ số
            String otp = String.format("%06d", new java.util.Random().nextInt(1000000));
            boolean saved = forgotPasswordDAO.saveOtp(email.trim(), otp);
            if (!saved) {
                sendJsonResponse(response, "error", "Không thể tạo mã OTP, vui lòng thử lại sau!");
                return;
            }

            // Gửi email tự động cho người dùng qua Gmail SMTP
            String subject = "Mã OTP để lấy lại mật khẩu";
            String content = "Mã OTP của bạn là <b>" + otp + "</b>. Mã OTP có hiệu lực trong 5 phút, vui lòng không cung cấp cho bất kì ai.";
            MailUtil.sendMailAsync(email.trim(), subject, content);

            sendJsonResponse(response, "success", "Gửi mã OTP thành công!");
            return;
        }

        // 4. OTP Password POST
        if (path.endsWith("/otp-password")) {
            String email = null;
            String otp = null;
            if (jsonBody != null) {
                if (jsonBody.has("email")) email = jsonBody.get("email").asText();
                if (jsonBody.has("otp")) otp = jsonBody.get("otp").asText();
            } else {
                email = request.getParameter("email");
                otp = request.getParameter("otp");
            }

            if (email == null || email.trim().isEmpty() || otp == null || otp.trim().isEmpty()) {
                sendJsonResponse(response, "error", "Vui lòng nhập đầy đủ Email và mã OTP!");
                return;
            }

            boolean isValid = forgotPasswordDAO.verifyOtp(email.trim(), otp.trim());
            if (!isValid) {
                sendJsonResponse(response, "error", "Mã OTP không chính xác!");
                return;
            }

            Account account = accountDAO.getAccountByEmail(email.trim());
            if (account == null) {
                sendJsonResponse(response, "error", "Tài khoản không tồn tại!");
                return;
            }

            session.setAttribute("resetPasswordEmail", email.trim());
            session.setAttribute("account", account);

            String token = "tok_adm_" + java.util.UUID.randomUUID().toString().replace("-", "");
            accountDAO.updateToken(account.getId(), token);
            account.setToken(token);

            javax.servlet.http.Cookie tokenCookie = new javax.servlet.http.Cookie("token", token);
            tokenCookie.setPath("/");
            tokenCookie.setHttpOnly(true);
            tokenCookie.setMaxAge(24 * 60 * 60);
            response.addCookie(tokenCookie);

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

            String resetEmail = (String) session.getAttribute("resetPasswordEmail");
            Account targetAcc = (Account) session.getAttribute("account");
            if (targetAcc == null && resetEmail != null) {
                targetAcc = accountDAO.getAccountByEmail(resetEmail);
            }

            if (targetAcc == null) {
                javax.servlet.http.Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (javax.servlet.http.Cookie c : cookies) {
                        if ("token".equals(c.getName()) && c.getValue() != null && !c.getValue().trim().isEmpty()) {
                            targetAcc = accountDAO.getAccountByToken(c.getValue().trim());
                            if (targetAcc != null) break;
                        }
                    }
                }
            }

            if (targetAcc == null || targetAcc.getId() <= 0) {
                sendJsonResponse(response, "error", "Phiên đổi mật khẩu không hợp lệ hoặc đã hết hạn!");
                return;
            }

            boolean updated = accountDAO.updatePassword(targetAcc.getId(), password.trim());
            if (!updated) {
                sendJsonResponse(response, "error", "Đổi mật khẩu thất bại, vui lòng thử lại!");
                return;
            }

            // Xóa mã OTP và kết thúc phiên đổi mật khẩu
            forgotPasswordDAO.deleteOtp(targetAcc.getEmail());
            session.removeAttribute("resetPasswordEmail");
            session.removeAttribute("account");
            accountDAO.updateToken(targetAcc.getId(), null);

            javax.servlet.http.Cookie clearCookie = new javax.servlet.http.Cookie("token", "");
            clearCookie.setPath("/");
            clearCookie.setMaxAge(0);
            clearCookie.setHttpOnly(true);
            response.addCookie(clearCookie);

            sendJsonResponse(response, "success", "Thay đổi mật khẩu thành công!");
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

        // Login success: generate token & save in DB + cookie
        String token = "tok_adm_" + java.util.UUID.randomUUID().toString().replace("-", "");
        accountDAO.updateToken(existAccount.getId(), token);
        existAccount.setToken(token);

        // Save account in session
        session.setAttribute("account", existAccount);

        // Set token cookie
        javax.servlet.http.Cookie tokenCookie = new javax.servlet.http.Cookie("token", token);
        tokenCookie.setPath("/");
        tokenCookie.setHttpOnly(true);
        if (rememberPassword) {
            // Duy trì đăng nhập 30 ngày nếu chọn "Nhớ mật khẩu"
            tokenCookie.setMaxAge(30 * 24 * 60 * 60);
        } else {
            // Mặc định 1 ngày nếu không chọn "Nhớ mật khẩu"
            tokenCookie.setMaxAge(24 * 60 * 60);
        }
        response.addCookie(tokenCookie);

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
