package com.pao.proiect.tema.service;

import com.pao.proiect.tema.exception.EmptyCartException;
import com.pao.proiect.tema.model.*;
import com.pao.proiect.tema.repository.OrdersRepository;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class OrderService {
    AuditService audit = AuditService.getInstance();
    OrdersRepository ordersRepository = new OrdersRepository();

    private OrderService(){}

    private static class Holder{
        private static final OrderService INSTANCE = new OrderService();
    }

    public static OrderService getInstance(){
        return Holder.INSTANCE;
    }

    public Order placeOrder(ShoppingCart cart, double deliveryFee, Payment paymentMethod, String notes) throws EmptyCartException, SQLException {
        System.out.println("\n Placing order...");
        Order newOrder = cart.checkout(deliveryFee, paymentMethod, notes);
        ordersRepository.save(newOrder);
        audit.log("placing_order");
        System.out.println("Order placed successfully. \n");
        return newOrder;
    }

    public boolean deleteOrderById(int id){
            try {
                ordersRepository.delete(id);
                audit.log("delete_order_by_id");
                System.out.println("Order deleted from database.");
                return true;
            } catch (SQLException e) {
                System.err.println("Database error while deleting order: " + e.getMessage());
                return false;
            }
        }

    public void processOrderPayment(int orderId){
        Order order = findOrderById(orderId).orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " not found."));
        audit.log("process_order_payment");
        order.processOrderPayment();
        try{
            ordersRepository.update(order);
        }catch(SQLException e){
            System.out.println("Database error while processing payment for order: " + e.getMessage());
        }
        System.out.println("Payment processed for order id: " + orderId);
    }

    public void assignDriver(int orderId, DeliveryPerson driver){
        Order order = findOrderById(orderId).orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " not found."));
        audit.log("assign_driver");
        order.assignDriver(driver);
        try{
            ordersRepository.update(order);
        }catch(SQLException e){
            System.out.println("Database error while updating order with driver assignment: " + e.getMessage());
        }
        System.out.println("Driver " + driver.getName() + " assigned to order id: " + orderId);
    }

    public void deliveryOrder(int orderId){
        Order order = findOrderById(orderId).orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " not found."));
        audit.log("delivery_order");
        order.markDelivered();
        try{
            ordersRepository.update(order);
        }catch(SQLException e){
            System.out.println("Database error while marking order as delivered: " + e.getMessage());
        }
        System.out.println("Order id: " + orderId + " marked as delivered.");
    }

    public Optional<Order> findOrderById(int id){
        audit.log("find_order_by_id");
        try{
            return ordersRepository.findById(id);
        }catch(SQLException e){
            System.out.println("Database error while finding order: " + e.getMessage());
             return Optional.empty();
        }
    }

    public List<Order> getAllOrders(){
        audit.log("get_all_orders");
        try{
            return ordersRepository.findAll();
        }catch(SQLException e){
            System.out.println("Database error while retrieving orders: " + e.getMessage());
             return Collections.emptyList();
        }
    }

    public List<Order> getOrderByCustomer(Customer customer){
        audit.log("get_order_by_customer");
        return getAllOrders().stream().filter(o -> o.getCustomer().equals(customer)).toList();
    }

    public double calculateTotal(){
        audit.log("calculate_total");
        return getAllOrders().stream().filter(o -> o.getStatus() == OrderStatus.DELIVERED).mapToDouble(Order::getTotal).sum();
    }

    public List<Order> getOrdersByRestaurants(String restaurantName){
        audit.log("get_orders_by_restaurants");
        return getAllOrders().stream().filter(o -> o.getRestaurant().getName().equalsIgnoreCase(restaurantName)).toList();
    }

    public Map<Customer, List<Order>> getOrdersGroupedByCustomer(){
        audit.log("get_orders_grouped_by_customer");
        return getAllOrders().stream().collect(Collectors.groupingBy(Order::getCustomer));
    }
}