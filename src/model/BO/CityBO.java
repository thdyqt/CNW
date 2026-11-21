package model.BO;

import model.BEAN.City;
import model.DAO.CityDAO;
import java.util.List;

public class CityBO {
    private final CityDAO cityDAO = new CityDAO();

    public List<City> getAllCities() {
        return cityDAO.getAllCities();
    }

    public List<City> getCitiesByTourLocations(String locations) {
        return cityDAO.getCitiesByTourLocations(locations);
    }

    public List<City> getCitiesWithTours() {
        return cityDAO.getCitiesWithTours();
    }
}
