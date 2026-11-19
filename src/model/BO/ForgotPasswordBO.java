package model.BO;

import model.DAO.ForgotPasswordDAO;

public class ForgotPasswordBO {
    private final ForgotPasswordDAO forgotPasswordDAO = new ForgotPasswordDAO();

    public boolean saveOtp(String email, String otp) {
        return forgotPasswordDAO.saveOtp(email, otp);
    }

    public boolean verifyOtp(String email, String otp) {
        return forgotPasswordDAO.verifyOtp(email, otp);
    }

    public boolean deleteOtp(String email) {
        return forgotPasswordDAO.deleteOtp(email);
    }
}
