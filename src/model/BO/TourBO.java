package model.BO;

import model.BEAN.Tour;
import model.DAO.TourDAO;
import java.util.List;

public class TourBO {
    private final TourDAO tourDAO = new TourDAO();

    public List<Tour> getAllTours() {
        return tourDAO.getAllTours();
    }

    public Tour getTourById(int id) {
        return tourDAO.getTourById(id);
    }

    public Tour getTourBySlug(String slug) {
        return tourDAO.getTourBySlug(slug);
    }

    public List<Tour> getToursSection2(int limit) {
        return tourDAO.getToursSection2(limit);
    }

    public List<Tour> getToursByCategories(List<Integer> categoryIds, int limit) {
        return tourDAO.getToursByCategories(categoryIds, limit);
    }

    public boolean createTour(Tour tour) {
        return tourDAO.createTour(tour);
    }

    public boolean updateTour(Tour tour) {
        return tourDAO.updateTour(tour);
    }

    public boolean deleteTour(int id) {
        return tourDAO.deleteTour(id);
    }

    public boolean undoTour(int id) {
        return tourDAO.undoTour(id);
    }

    public boolean deleteDestroyTour(int id) {
        return tourDAO.deleteDestroyTour(id);
    }

    public List<Tour> getTrashTours() {
        return tourDAO.getTrashTours();
    }
}
