package com.pao.proiect.tema.service;

import com.pao.proiect.tema.exception.EmptyCartException;
import com.pao.proiect.tema.model.*;

import java.util.*;
import java.util.stream.Collectors;

public class OrderService {
    private final List<Order> orders;

    private OrderService(){
        this.orders = new ArrayList<>();
    }

    private static class Holder{
        private static final OrderService INSTANCE = new OrderService();
    }

    public static OrderService getInstance(){
        return Holder.INSTANCE;
    }

    public Order placeOrder(ShoppingCart cart, double deliveryFee, Payment paymentMethod, String notes) throws EmptyCartException{
        System.out.println("\n Placing order...");
        Order newOrder = cart.checkout(deliveryFee, paymentMethod, notes);
        orders.add(newOrder);
        System.out.println("Order placed successfully. \n");
        return newOrder;
    }

    public boolean deleteOrderById(int id){
        boolean removed = orders.removeIf(order -> order.getId() == id);
        if(removed){
            System.out.println("Order deleted");
        }
        return removed;
    }

    public void processOrderPayment(int orderId){
        Order order = findOrderById(orderId).orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " not found."));
        order.processOrderPayment();
        System.out.println("Payment processed for order id: " + orderId);
    }

    public void assignDriver(int orderId, DeliveryPerson driver){
        Order order = findOrderById(orderId).orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " not found."));
        order.assignDriver(driver);
        System.out.println("Driver " + driver.getName() + " assigned to order id: " + orderId);
    }

    public void deliveryOrder(int orderId){
        Order order = findOrderById(orderId).orElseThrow(() -> new IllegalArgumentException("Order with id " + orderId + " not found."));
        order.markDelivered();
        System.out.println("Order id: " + orderId + " marked as delivered.");
    }

    public Optional<Order> findOrderById(int id){
        return orders.stream().filter(o -> o.getId() == id).findFirst();
    }

    public List<Order> getAllOrders(){
        return Collections.unmodifiableList(orders);
    }

    public List<Order> getOrderByCustomer(Customer customer){
        return orders.stream().filter(o -> o.getCustomer().equals(customer)).toList();
    }

    public double calculateTotal(){
        return orders.stream().filter(o -> o.getStatus() == OrderStatus.DELIVERED).mapToDouble(Order::getTotal).sum();
    }

    public List<Order> getOrdersByRestaurants(String restaurantName){
        return orders.stream().filter(o -> o.getRestaurant().getName().equalsIgnoreCase(restaurantName)).toList();
    }

    public Map<Customer, List<Order>> getOrdersGroupedByCustomer(){
        return orders.stream().collect(Collectors.groupingBy(Order::getCustomer));
    }
}