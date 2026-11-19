package model.BO;

import model.BEAN.Category;
import model.DAO.CategoryDAO;
import java.util.List;

public class CategoryBO {
    private final CategoryDAO categoryDAO = new CategoryDAO();

    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    public List<Category> getAllCategoriesAdmin() {
        return categoryDAO.getAllCategoriesAdmin();
    }

    public Category getCategoryById(int id) {
        return categoryDAO.getCategoryById(id);
    }

    public boolean createCategory(Category category) {
        return categoryDAO.createCategory(category);
    }

    public boolean updateCategory(Category category) {
        return categoryDAO.updateCategory(category);
    }

    public boolean deleteCategory(int id) {
        return categoryDAO.deleteCategory(id);
    }

    public boolean undoCategory(int id) {
        return categoryDAO.undoCategory(id);
    }

    public boolean deleteDestroyCategory(int id) {
        return categoryDAO.deleteDestroyCategory(id);
    }

    public List<Category> getTrashCategories() {
        return categoryDAO.getTrashCategories();
    }
}
