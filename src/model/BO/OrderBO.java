package model.BO;

import model.BEAN.Order;
import model.DAO.OrderDAO;
import java.util.List;

public class OrderBO {
    private final OrderDAO orderDAO = new OrderDAO();

    public boolean createOrder(Order order) {
        return orderDAO.createOrder(order);
    }

    public List<Order> getAllOrders() {
        return orderDAO.getAllOrders();
    }

    public Order getOrderById(int id) {
        return orderDAO.getOrderById(id);
    }

    public Order getOrderByCodeAndPhone(String code, String phone) {
        return orderDAO.getOrderByCodeAndPhone(code, phone);
    }

    public boolean updateOrder(Order order) {
        return orderDAO.updateOrder(order);
    }

    public boolean deleteOrder(int id) {
        return orderDAO.deleteOrder(id);
    }

    public boolean undoOrder(int id) {
        return orderDAO.undoOrder(id);
    }

    public boolean deleteDestroyOrder(int id) {
        return orderDAO.deleteDestroyOrder(id);
    }
}
