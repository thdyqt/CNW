package model.BO;

import model.BEAN.Contact;
import model.DAO.ContactDAO;
import java.util.List;

public class ContactBO {
    private final ContactDAO contactDAO = new ContactDAO();

    public List<Contact> getAllContacts() {
        return contactDAO.getAllContacts();
    }

    public List<Contact> getContacts(String startDate, String endDate, String keyword, int page, int limit) {
        return contactDAO.getContacts(startDate, endDate, keyword, page, limit);
    }

    public boolean createContact(String email) {
        return contactDAO.createContact(email);
    }

    public boolean deleteContact(int id) {
        return contactDAO.deleteContact(id);
    }

    public boolean deleteContact(int id, String deletedBy) {
        return contactDAO.deleteContact(id, deletedBy);
    }

    public boolean undoContact(int id) {
        return contactDAO.undoContact(id);
    }

    public boolean deleteDestroyContact(int id) {
        return contactDAO.deleteDestroyContact(id);
    }

    public List<Contact> getTrashContacts(String keyword, int page, int limit) {
        return contactDAO.getTrashContacts(keyword, page, limit);
    }
}
