package model.BO;

import model.BEAN.Role;
import model.DAO.RoleDAO;
import java.util.List;

public class RoleBO {
    private final RoleDAO roleDAO = new RoleDAO();

    public List<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }

    public Role getRoleById(int id) {
        return roleDAO.getRoleById(id);
    }

    public boolean createRole(Role role) {
        return roleDAO.createRole(role);
    }

    public boolean updateRole(Role role) {
        return roleDAO.updateRole(role);
    }

    public boolean deleteRole(int id, String deletedBy) {
        return roleDAO.deleteRole(id, deletedBy);
    }

    public boolean undoRole(int id) {
        return roleDAO.undoRole(id);
    }

    public boolean deleteDestroyRole(int id) {
        return roleDAO.deleteDestroyRole(id);
    }

    public List<Role> getTrashRoles(String keyword, int page, int limit) {
        return roleDAO.getTrashRoles(keyword, page, limit);
    }

    public void changeMulti(List<Integer> ids, String option, String deletedBy) {
        roleDAO.changeMulti(ids, option, deletedBy);
    }
}
