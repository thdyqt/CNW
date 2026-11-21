package controller;

import model.BEAN.User;
import model.DAO.CustomerRegistrationOtpDAO;
import model.DAO.UserDAO;
import util.MailUtil;
import java.io.IOException;
import java.security.SecureRandom;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CustomerAccountServlet extends HttpServlet {
    private static final String CUSTOMER_SESSION_KEY = "customerUser";
    private static final String REGISTRATION_EMAIL_KEY = "customerRegistrationEmail";
    private static final String FORGOT_PASSWORD_EMAIL_KEY = "customerForgotPasswordEmail";
    private static final String FORGOT_PASSWORD_OTP_HASH_KEY = "customerForgotPasswordOtpHash";
    private static final String FORGOT_PASSWORD_OTP_EXPIRES_KEY = "customerForgotPasswordOtpExpiresAt";
    private static final String FORGOT_PASSWORD_OTP_RESEND_KEY = "customerForgotPasswordOtpResendAfter";
    private static final String FORGOT_PASSWORD_OTP_ATTEMPTS_KEY = "customerForgotPasswordOtpAttempts";
    private static final String FORGOT_PASSWORD_USER_KEY = "customerForgotPasswordUserId";
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);
    private static final SecureRandom RANDOM = new SecureRandom();

    private final UserDAO userDAO = new UserDAO();
    private final CustomerRegistrationOtpDAO otpDAO = new CustomerRegistrationOtpDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String path = getPath(request);
        HttpSession session = request.getSession(false);

        if (path.endsWith("/logout")) {
            if (session != null) {
                session.removeAttribute(CUSTOMER_SESSION_KEY);
                session.removeAttribute(REGISTRATION_EMAIL_KEY);
                clearForgotPasswordState(session);
            }
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        if (path.endsWith("/login")) {
            if (getActiveCustomer(session) != null) {
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }
            consumeFlash(session, request);
            request.setAttribute("pageTitle", "Đăng nhập khách hàng - DUT Travel");
            request.getRequestDispatcher("/webContent/client/pages/customer-login.jsp").forward(request, response);
            return;
        }

        if (path.endsWith("/register")) {
            request.setAttribute("pageTitle", "Đăng ký khách hàng - DUT Travel");
            request.getRequestDispatcher("/webContent/client/pages/customer-register.jsp").forward(request, response);
            return;
        }

        if (path.endsWith("/verify-otp")) {
            String email = session != null ? (String) session.getAttribute(REGISTRATION_EMAIL_KEY) : null;
            if (email == null || email.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/account/register");
                return;
            }
            request.setAttribute("registrationEmail", email);
            request.setAttribute("pageTitle", "Xác nhận email - DUT Travel");
            request.getRequestDispatcher("/webContent/client/pages/customer-verify-otp.jsp").forward(request, response);
            return;
        }

        if (path.endsWith("/forgot-password/verify")) {
            String email = session != null ? (String) session.getAttribute(FORGOT_PASSWORD_EMAIL_KEY) : null;
            if (email == null || email.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/account/forgot-password");
                return;
            }
            request.setAttribute("forgotPasswordEmail", email);
            request.setAttribute("pageTitle", "Xác minh quên mật khẩu - DUT Travel");
            request.getRequestDispatcher("/webContent/client/pages/customer-forgot-password-verify.jsp").forward(request, response);
            return;
        }

        if (path.endsWith("/forgot-password")) {
            request.setAttribute("pageTitle", "Quên mật khẩu - DUT Travel");
            request.getRequestDispatcher("/webContent/client/pages/customer-forgot-password.jsp").forward(request, response);
            return;
        }

        if (path.endsWith("/password/change")) {
            User customer = refreshActiveCustomer(session);
            if (customer == null) {
                response.sendRedirect(request.getContextPath() + "/account/login");
                return;
            }
            request.setAttribute("customerUser", customer);
            request.setAttribute("pageTitle", "Đổi mật khẩu - DUT Travel");
            request.getRequestDispatcher("/webContent/client/pages/customer-password-change.jsp").forward(request, response);
            return;
        }

        if (path.endsWith("/profile")) {
            User customer = refreshActiveCustomer(session);
            if (customer == null) {
                response.sendRedirect(request.getContextPath() + "/account/login");
                return;
            }
            request.setAttribute("customerUser", customer);
            request.setAttribute("pageTitle", "Thông tin cá nhân - DUT Travel");
            consumeFlash(session, request);
            request.getRequestDispatcher("/webContent/client/pages/customer-profile.jsp").forward(request, response);
            return;
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String path = getPath(request);

        if (path.endsWith("/login")) {
            handleLogin(request, response);
        } else if (path.endsWith("/register")) {
            handleRegister(request, response);
        } else if (path.endsWith("/verify-otp")) {
            handleVerifyOtp(request, response);
        } else if (path.endsWith("/resend-otp")) {
            handleResendOtp(request, response);
        } else if (path.endsWith("/forgot-password/verify")) {
            handleForgotPasswordVerify(request, response);
        } else if (path.endsWith("/forgot-password")) {
            handleForgotPasswordRequest(request, response);
        } else if (path.endsWith("/password/change")) {
            handlePasswordChange(request, response);
        } else if (path.endsWith("/profile")) {
            handleProfileUpdate(request, response);
        } else if (path.endsWith("/logout")) {
            doGet(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = normalizeEmail(request.getParameter("email"));
        String password = trim(request.getParameter("password"));
        if (email.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "Vui lòng nhập email và mật khẩu.");
            forwardLogin(request, response);
            return;
        }

        User user = userDAO.getUserByEmail(email);
        if (user == null || !"active".equalsIgnoreCase(user.getStatus())
                || !userDAO.checkPassword(password, user.getPassword())) {
            request.setAttribute("error", "Email hoặc mật khẩu không đúng, hoặc tài khoản chưa được kích hoạt.");
            forwardLogin(request, response);
            return;
        }

        HttpSession session = request.getSession();
        request.changeSessionId();
        clearForgotPasswordState(session);
        session.setAttribute(CUSTOMER_SESSION_KEY, safeCustomer(user));
        response.sendRedirect(request.getContextPath() + "/home");
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fullName = trim(request.getParameter("fullName"));
        String email = normalizeEmail(request.getParameter("email"));
        String password = trim(request.getParameter("password"));
        String confirmPassword = request.getParameter("confirmPassword");
        String phone = trim(request.getParameter("phone"));
        String address = trim(request.getParameter("address"));

        if (fullName.length() < 2 || fullName.length() > 255 || email.length() > 255
                || !EMAIL_PATTERN.matcher(email).matches() || !isValidPassword(password)
                || !password.equals(confirmPassword)
                || phone.length() > 50 || address.length() > 500) {
            request.setAttribute("error", "Vui lòng kiểm tra họ tên, email, mật khẩu và thông tin đã nhập.");
            forwardRegister(request, response);
            return;
        }
        int emailRows = userDAO.countEmailRows(email);
        if (emailRows < 0) {
            request.setAttribute("error", "Không kiểm tra được cơ sở dữ liệu. Vui lòng thử lại.");
            forwardRegister(request, response);
            return;
        }
        if (emailRows > 0) {
            request.setAttribute("error", "Email này đã được sử dụng.");
            forwardRegister(request, response);
            return;
        }
        if (otpDAO.isResendBlocked(email)) {
            request.setAttribute("error", "Mã xác nhận vừa được gửi. Vui lòng chờ 60 giây trước khi gửi lại.");
            forwardRegister(request, response);
            return;
        }

        String otp = createOtp();
        User pending = new User();
        pending.setFullName(fullName);
        pending.setEmail(email);
        pending.setPhone(phone);
        pending.setAddress(address);
        String passwordHash = userDAO.hashPassword(password.trim());
        String otpHash = userDAO.hashPassword(otp);
        if (!otpDAO.savePendingRegistration(pending, passwordHash, otpHash)) {
            request.setAttribute("error", "Không lưu được yêu cầu đăng ký. Vui lòng thử lại.");
            forwardRegister(request, response);
            return;
        }

        String subject = "Mã xác nhận đăng ký tài khoản DUT Travel";
        String content = "<p>Mã xác nhận đăng ký của bạn là:</p><p style='font-size:28px;font-weight:bold;letter-spacing:6px'>"
                + otp + "</p><p>Mã có hiệu lực trong 5 phút. Vui lòng không chia sẻ mã này.</p>";
        if (!MailUtil.sendMail(email, subject, content)) {
            otpDAO.deletePendingRegistration(email);
            request.setAttribute("error", "Không gửi được email xác nhận. Vui lòng kiểm tra cấu hình email và thử lại.");
            forwardRegister(request, response);
            return;
        }

        request.getSession().setAttribute(REGISTRATION_EMAIL_KEY, email);
        response.sendRedirect(request.getContextPath() + "/account/verify-otp");
    }

    private void handleVerifyOtp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String email = session != null ? (String) session.getAttribute(REGISTRATION_EMAIL_KEY) : null;
        String otp = trim(request.getParameter("otp"));
        if (email == null || email.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/account/register");
            return;
        }
        if (!otp.matches("\\d{6}")) {
            request.setAttribute("error", "Vui lòng nhập mã OTP gồm 6 chữ số.");
            forwardVerifyOtp(request, response, email);
            return;
        }

        User pending = otpDAO.verifyAndGetRegistration(email, otp, userDAO);
        if (pending == null) {
            request.setAttribute("error", "Mã OTP không đúng, đã hết hạn hoặc vượt quá số lần thử.");
            forwardVerifyOtp(request, response, email);
            return;
        }
        int emailRows = userDAO.countEmailRows(email);
        if (emailRows < 0) {
            request.setAttribute("error", "Không kiểm tra được cơ sở dữ liệu. Mã OTP vẫn còn hiệu lực để thử lại.");
            forwardVerifyOtp(request, response, email);
            return;
        }
        if (emailRows > 0) {
            otpDAO.deletePendingRegistration(email);
            session.removeAttribute(REGISTRATION_EMAIL_KEY);
            request.setAttribute("error", "Email này đã được đăng ký. Vui lòng đăng nhập.");
            forwardLogin(request, response);
            return;
        }

        if (!userDAO.createUser(pending, pending.getPassword())) {
            request.setAttribute("error", "Chưa tạo được tài khoản. Vui lòng thử xác nhận lại sau.");
            forwardVerifyOtp(request, response, email);
            return;
        }
        otpDAO.deletePendingRegistration(email);
        session.removeAttribute(REGISTRATION_EMAIL_KEY);
        request.changeSessionId();
        clearForgotPasswordState(session);
        session.setAttribute(CUSTOMER_SESSION_KEY, safeCustomer(pending));
        session.setAttribute("customerFlashSuccess", "Xác nhận email và tạo tài khoản thành công.");
        response.sendRedirect(request.getContextPath() + "/account/profile");
    }

    private void handleResendOtp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String email = session != null ? (String) session.getAttribute(REGISTRATION_EMAIL_KEY) : null;
        if (email == null || email.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/account/register");
            return;
        }
        String otp = createOtp();
        String otpHash = userDAO.hashPassword(otp);
        CustomerRegistrationOtpDAO.OtpSnapshot previousOtp = otpDAO.replaceOtpAfterCooldown(email, otpHash);
        if (previousOtp == null) {
            request.setAttribute("error", "Bạn cần chờ đủ 60 giây trước khi gửi lại mã.");
            forwardVerifyOtp(request, response, email);
            return;
        }
        String content = "<p>Mã xác nhận đăng ký mới của bạn là:</p><p style='font-size:28px;font-weight:bold;letter-spacing:6px'>"
                + otp + "</p><p>Mã có hiệu lực trong 5 phút. Vui lòng không chia sẻ mã này.</p>";
        if (!MailUtil.sendMail(email, "Mã xác nhận đăng ký tài khoản DUT Travel", content)) {
            boolean restored = otpDAO.restoreAfterFailedResend(email, otpHash, previousOtp);
            request.setAttribute("error", restored
                    ? "Không gửi được email. Mã OTP trước đó đã được khôi phục nếu còn hạn; hãy kiểm tra cấu hình email rồi thử lại."
                    : "Không gửi được email và không khôi phục được mã cũ. Vui lòng bắt đầu đăng ký lại.");
        } else {
            request.setAttribute("success", "Đã gửi mã OTP mới đến " + email + ".");
        }
        forwardVerifyOtp(request, response, email);
    }

    private void handleProfileUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User current = refreshActiveCustomer(session);
        if (current == null) {
            response.sendRedirect(request.getContextPath() + "/account/login");
            return;
        }
        String fullName = trim(request.getParameter("fullName"));
        String phone = trim(request.getParameter("phone"));
        String address = trim(request.getParameter("address"));
        if (fullName.length() < 2 || fullName.length() > 255 || phone.length() > 50 || address.length() > 500) {
            request.setAttribute("customerUser", current);
            request.setAttribute("error", "Họ tên, số điện thoại hoặc địa chỉ chưa hợp lệ.");
            request.setAttribute("pageTitle", "Thông tin cá nhân - DUT Travel");
            request.getRequestDispatcher("/webContent/client/pages/customer-profile.jsp").forward(request, response);
            return;
        }
        if (!userDAO.updateCustomerProfile(current.getId(), fullName, phone, address)) {
            request.setAttribute("customerUser", current);
            request.setAttribute("error", "Cập nhật thông tin chưa thành công. Vui lòng thử lại.");
            request.setAttribute("pageTitle", "Thông tin cá nhân - DUT Travel");
            request.getRequestDispatcher("/webContent/client/pages/customer-profile.jsp").forward(request, response);
            return;
        }
        User updated = userDAO.getUserById(current.getId());
        session.setAttribute(CUSTOMER_SESSION_KEY, safeCustomer(updated));
        session.setAttribute("customerFlashSuccess", "Đã cập nhật thông tin cá nhân.");
        response.sendRedirect(request.getContextPath() + "/account/profile");
    }

    private void handlePasswordChange(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User customer = refreshActiveCustomer(session);
        if (customer == null) {
            response.sendRedirect(request.getContextPath() + "/account/login");
            return;
        }

        String currentPassword = trim(request.getParameter("currentPassword"));
        String password = trim(request.getParameter("password"));
        String confirmPassword = trim(request.getParameter("confirmPassword"));
        User storedCustomer = userDAO.getUserById(customer.getId());
        if (currentPassword == null || currentPassword.isEmpty() || storedCustomer == null
                || !userDAO.checkPassword(currentPassword, storedCustomer.getPassword())) {
            request.setAttribute("error", "Mật khẩu hiện tại không đúng.");
            forwardPasswordChange(request, response, customer);
            return;
        }
        if (!isValidPassword(password) || !password.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu mới phải có ít nhất 6 ký tự, tối đa 72 byte UTF-8 và hai lần nhập phải khớp.");
            forwardPasswordChange(request, response, customer);
            return;
        }
        if (userDAO.checkPassword(password, storedCustomer.getPassword())) {
            request.setAttribute("error", "Mật khẩu mới phải khác mật khẩu hiện tại.");
            forwardPasswordChange(request, response, customer);
            return;
        }
        if (!userDAO.updateCustomerPassword(customer.getId(), userDAO.hashPassword(password))) {
            request.setAttribute("error", "Không cập nhật được mật khẩu. Vui lòng thử lại.");
            forwardPasswordChange(request, response, customer);
            return;
        }

        session.setAttribute("customerFlashSuccess", "Đã đổi mật khẩu thành công.");
        response.sendRedirect(request.getContextPath() + "/account/profile");
    }

    private void handleForgotPasswordRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = normalizeEmail(request.getParameter("email"));
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            request.setAttribute("error", "Vui lòng nhập email hợp lệ.");
            request.setAttribute("email", email);
            forwardForgotPassword(request, response);
            return;
        }

        HttpSession session = request.getSession();
        String previousEmail = (String) session.getAttribute(FORGOT_PASSWORD_EMAIL_KEY);
        Long resendAfter = getSessionLong(session, FORGOT_PASSWORD_OTP_RESEND_KEY);
        if (email.equals(previousEmail) && resendAfter != null && resendAfter > System.currentTimeMillis()) {
            response.sendRedirect(request.getContextPath() + "/account/forgot-password/verify");
            return;
        }

        clearForgotPasswordState(session);
        User user = userDAO.getUserByEmail(email);
        if (user == null || !"active".equalsIgnoreCase(user.getStatus())) {
            request.setAttribute("error", "Email chưa được đăng ký tài khoản khách hàng đang hoạt động.");
            request.setAttribute("email", email);
            forwardForgotPassword(request, response);
            return;
        }

        String otp = createOtp();
        String content = "<p>Mã OTP đặt lại mật khẩu DUT Travel của bạn là:</p>"
                + "<p style='font-size:28px;font-weight:bold;letter-spacing:6px'>" + otp + "</p>"
                + "<p>Mã có hiệu lực trong 5 phút. Vui lòng không chia sẻ mã này.</p>";
        if (!MailUtil.sendMail(user.getEmail(), "Mã OTP quên mật khẩu DUT Travel", content)) {
            request.setAttribute("error", "Không gửi được OTP đến email này. Vui lòng kiểm tra cấu hình email hoặc thử lại.");
            request.setAttribute("email", email);
            forwardForgotPassword(request, response);
            return;
        }

        long now = System.currentTimeMillis();
        session.setAttribute(FORGOT_PASSWORD_EMAIL_KEY, email);
        session.setAttribute(FORGOT_PASSWORD_OTP_HASH_KEY, userDAO.hashPassword(otp));
        session.setAttribute(FORGOT_PASSWORD_OTP_EXPIRES_KEY, now + 5 * 60 * 1000L);
        session.setAttribute(FORGOT_PASSWORD_OTP_RESEND_KEY, now + 60 * 1000L);
        session.setAttribute(FORGOT_PASSWORD_OTP_ATTEMPTS_KEY, 0);
        session.setAttribute(FORGOT_PASSWORD_USER_KEY, user.getId());
        response.sendRedirect(request.getContextPath() + "/account/forgot-password/verify");
    }

    private void handleForgotPasswordVerify(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String email = session != null ? (String) session.getAttribute(FORGOT_PASSWORD_EMAIL_KEY) : null;
        if (session == null || email == null || email.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/account/forgot-password");
            return;
        }

        String otp = trim(request.getParameter("otp"));
        String password = trim(request.getParameter("password"));
        String confirmPassword = trim(request.getParameter("confirmPassword"));
        if (!isValidPassword(password) || !password.equals(confirmPassword)) {
            request.setAttribute("error", "Mật khẩu mới phải có ít nhất 6 ký tự, tối đa 72 byte UTF-8 và hai lần nhập phải khớp.");
            forwardForgotPasswordVerify(request, response, email);
            return;
        }

        String otpHash = (String) session.getAttribute(FORGOT_PASSWORD_OTP_HASH_KEY);
        Long expiresAt = getSessionLong(session, FORGOT_PASSWORD_OTP_EXPIRES_KEY);
        Integer attempts = (Integer) session.getAttribute(FORGOT_PASSWORD_OTP_ATTEMPTS_KEY);
        Integer userId = (Integer) session.getAttribute(FORGOT_PASSWORD_USER_KEY);
        if (otpHash == null || expiresAt == null || expiresAt <= System.currentTimeMillis()
                || attempts == null || attempts >= 5 || userId == null) {
            request.setAttribute("error", "Mã OTP không hợp lệ hoặc đã hết hạn. Yêu cầu mã mới để tiếp tục.");
            forwardForgotPasswordVerify(request, response, email);
            return;
        }
        if (!otp.matches("\\d{6}") || !userDAO.checkPassword(otp, otpHash)) {
            session.setAttribute(FORGOT_PASSWORD_OTP_ATTEMPTS_KEY, attempts + 1);
            request.setAttribute("error", "Mã OTP không đúng.");
            forwardForgotPasswordVerify(request, response, email);
            return;
        }

        User user = userDAO.getUserById(userId);
        if (user == null || !email.equalsIgnoreCase(user.getEmail()) || !"active".equalsIgnoreCase(user.getStatus())
                || !userDAO.updateCustomerPassword(userId, userDAO.hashPassword(password))) {
            request.setAttribute("error", "Không thể đặt lại mật khẩu. Hãy yêu cầu mã OTP mới và thử lại.");
            forwardForgotPasswordVerify(request, response, email);
            return;
        }

        clearForgotPasswordState(session);
        session.removeAttribute(CUSTOMER_SESSION_KEY);
        session.setAttribute("customerFlashSuccess", "Đặt lại mật khẩu thành công. Vui lòng đăng nhập.");
        response.sendRedirect(request.getContextPath() + "/account/login");
    }

    private void forwardPasswordChange(HttpServletRequest request, HttpServletResponse response, User customer)
            throws ServletException, IOException {
        request.setAttribute("customerUser", customer);
        request.setAttribute("pageTitle", "Đổi mật khẩu - DUT Travel");
        request.getRequestDispatcher("/webContent/client/pages/customer-password-change.jsp").forward(request, response);
    }

    private void forwardForgotPassword(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pageTitle", "Quên mật khẩu - DUT Travel");
        request.getRequestDispatcher("/webContent/client/pages/customer-forgot-password.jsp").forward(request, response);
    }

    private void forwardForgotPasswordVerify(HttpServletRequest request, HttpServletResponse response, String email)
            throws ServletException, IOException {
        request.setAttribute("forgotPasswordEmail", email);
        request.setAttribute("pageTitle", "Xác minh quên mật khẩu - DUT Travel");
        request.getRequestDispatcher("/webContent/client/pages/customer-forgot-password-verify.jsp").forward(request, response);
    }

    private void clearForgotPasswordOtp(HttpSession session) {
        if (session == null) return;
        session.removeAttribute(FORGOT_PASSWORD_OTP_HASH_KEY);
        session.removeAttribute(FORGOT_PASSWORD_OTP_EXPIRES_KEY);
        session.removeAttribute(FORGOT_PASSWORD_OTP_RESEND_KEY);
        session.removeAttribute(FORGOT_PASSWORD_OTP_ATTEMPTS_KEY);
        session.removeAttribute(FORGOT_PASSWORD_USER_KEY);
    }

    private void clearForgotPasswordState(HttpSession session) {
        if (session == null) return;
        session.removeAttribute(FORGOT_PASSWORD_EMAIL_KEY);
        clearForgotPasswordOtp(session);
    }

    private Long getSessionLong(HttpSession session, String key) {
        Object value = session != null ? session.getAttribute(key) : null;
        return value instanceof Number ? ((Number) value).longValue() : null;
    }

    private User refreshActiveCustomer(HttpSession session) {
        User sessionUser = getActiveCustomer(session);
        if (sessionUser == null) return null;
        User current = userDAO.getUserById(sessionUser.getId());
        if (current == null || !"active".equalsIgnoreCase(current.getStatus())) {
            session.removeAttribute(CUSTOMER_SESSION_KEY);
            return null;
        }
        User safe = safeCustomer(current);
        session.setAttribute(CUSTOMER_SESSION_KEY, safe);
        return safe;
    }

    private User getActiveCustomer(HttpSession session) {
        if (session == null) return null;
        Object value = session.getAttribute(CUSTOMER_SESSION_KEY);
        return value instanceof User ? (User) value : null;
    }

    private User safeCustomer(User user) {
        if (user == null) return null;
        User safe = new User();
        safe.setId(user.getId());
        safe.setFullName(user.getFullName());
        safe.setEmail(user.getEmail());
        safe.setPhone(user.getPhone());
        safe.setAddress(user.getAddress());
        safe.setAvatar(user.getAvatar());
        safe.setStatus(user.getStatus());
        safe.setPassword(null);
        safe.setTokenUser(null);
        return safe;
    }

    private void consumeFlash(HttpSession session, HttpServletRequest request) {
        if (session == null) return;
        Object success = session.getAttribute("customerFlashSuccess");
        Object error = session.getAttribute("customerFlashError");
        if (success != null) {
            request.setAttribute("success", success);
            session.removeAttribute("customerFlashSuccess");
        }
        if (error != null) {
            request.setAttribute("error", error);
            session.removeAttribute("customerFlashError");
        }
    }

    private void forwardLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pageTitle", "Đăng nhập khách hàng - DUT Travel");
        request.getRequestDispatcher("/webContent/client/pages/customer-login.jsp").forward(request, response);
    }

    private void forwardRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("pageTitle", "Đăng ký khách hàng - DUT Travel");
        request.getRequestDispatcher("/webContent/client/pages/customer-register.jsp").forward(request, response);
    }

    private void forwardVerifyOtp(HttpServletRequest request, HttpServletResponse response, String email)
            throws ServletException, IOException {
        request.setAttribute("registrationEmail", email);
        request.setAttribute("pageTitle", "Xác nhận email - DUT Travel");
        request.getRequestDispatcher("/webContent/client/pages/customer-verify-otp.jsp").forward(request, response);
    }

    private String getPath(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String contextPath = request.getContextPath();
        return uri.startsWith(contextPath) ? uri.substring(contextPath.length()) : uri;
    }

    private String createOtp() {
        return String.format(Locale.ROOT, "%06d", RANDOM.nextInt(1_000_000));
    }

    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) return false;
        return password.getBytes(StandardCharsets.UTF_8).length <= 72;
    }

    private String normalizeEmail(String value) {
        return value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }
}
