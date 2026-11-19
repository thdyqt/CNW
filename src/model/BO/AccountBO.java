package model.BO;

import model.BEAN.Account;
import model.DAO.AccountDAO;
import java.util.List;

public class AccountBO {
    private final AccountDAO accountDAO = new AccountDAO();

    public List<Account> getAccounts(String status, String roleId, String startDate, String endDate, String keyword, int page, int limit) {
        return accountDAO.getAccounts(status, roleId, startDate, endDate, keyword, page, limit);
    }

    public int countAccounts(String status, String roleId, String startDate, String endDate, String keyword) {
        return accountDAO.countAccounts(status, roleId, startDate, endDate, keyword);
    }

    public Account getAccountById(int id) {
        return accountDAO.getAccountById(id);
    }

    public Account getAccountByToken(String token) {
        return accountDAO.getAccountByToken(token);
    }

    public boolean updateToken(int id, String token) {
        return accountDAO.updateToken(id, token);
    }

    public Account getAccountByEmail(String email) {
        return accountDAO.getAccountByEmail(email);
    }

    public boolean checkPassword(String plainPassword, String storedPassword) {
        return accountDAO.checkPassword(plainPassword, storedPassword);
    }

    public String hashPassword(String plainPassword) {
        return accountDAO.hashPassword(plainPassword);
    }

    public boolean createAccount(Account acc, String password) {
        return accountDAO.createAccount(acc, password);
    }

    public boolean updateAccount(Account acc, String password) {
        return accountDAO.updateAccount(acc, password);
    }

    public boolean updateProfile(int id, String fullName, String email, String phone, String avatar) {
        return accountDAO.updateProfile(id, fullName, email, phone, avatar);
    }

    public boolean updatePassword(int id, String newPassword) {
        return accountDAO.updatePassword(id, newPassword);
    }

    public boolean deleteAccount(int id) {
        return accountDAO.deleteAccount(id);
    }

    public boolean undoAccount(int id) {
        return accountDAO.undoAccount(id);
    }

    public boolean deleteDestroyAccount(int id) {
        return accountDAO.deleteDestroyAccount(id);
    }

    public List<Account> getTrashAccounts(String keyword, int page, int limit) {
        return accountDAO.getTrashAccounts(keyword, page, limit);
    }

    public int countTrashAccounts(String keyword) {
        return accountDAO.countTrashAccounts(keyword);
    }

    public boolean changeMulti(List<Integer> ids, String option) {
        return accountDAO.changeMulti(ids, option);
    }
}
