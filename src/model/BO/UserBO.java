package model.BO;

import model.BEAN.User;
import model.DAO.UserDAO;
import java.util.List;

public class UserBO {
    private final UserDAO userDAO = new UserDAO();

    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    public User getUserByToken(String token) {
        return userDAO.getUserByToken(token);
    }

    public boolean createUser(User user, String plainPassword) {
        return userDAO.createUser(user, plainPassword);
    }

    public boolean updateUser(User user, String plainPassword) {
        return userDAO.updateUser(user, plainPassword);
    }

    public boolean deleteUser(int id, String deletedBy) {
        return userDAO.deleteUser(id, deletedBy);
    }

    public boolean undoUser(int id) {
        return userDAO.undoUser(id);
    }

    public boolean deleteDestroyUser(int id) {
        return userDAO.deleteDestroyUser(id);
    }

    public int countUsers(String status, String startDate, String endDate, String keyword) {
        return userDAO.countUsers(status, startDate, endDate, keyword);
    }

    public List<User> getUsers(String status, String startDate, String endDate, String keyword, int page, int limit) {
        return userDAO.getUsers(status, startDate, endDate, keyword, page, limit);
    }

    public int countTrashUsers(String keyword) {
        return userDAO.countTrashUsers(keyword);
    }

    public List<User> getTrashUsers(String keyword, int page, int limit) {
        return userDAO.getTrashUsers(keyword, page, limit);
    }

    public void changeMulti(List<Integer> ids, String option, String actor) {
        userDAO.changeMulti(ids, option, actor);
    }

    public boolean checkPassword(String plainPassword, String storedPassword) {
        return userDAO.checkPassword(plainPassword, storedPassword);
    }

    public String hashPassword(String plainPassword) {
        return userDAO.hashPassword(plainPassword);
    }
}
