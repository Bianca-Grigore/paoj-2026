package com.pao.proiect.tema.model;

import com.pao.proiect.tema.exception.InvalidOrderStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class Order {
    private OrderStatus status;
    private int id;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private Customer customer;
    private Restaurant restaurant;
    private DeliveryPerson driver = null;
    private Map<MenuItem, Integer> items;
    private double subtotal;
    private double deliveryFee;
    private String notes;
    private Payment paymentMethod;
    private static int idNumber=0;

    public Order(Customer customer, Restaurant restaurant, Map<MenuItem, Integer>items, double deliveryFee, Payment paymentMethod, String notes){

        if(items == null || items.isEmpty()){
            throw new IllegalArgumentException("Order must contain at least one item.");
        }

        this.id = idNumber;
        idNumber +=1;
        this.orderDate = LocalDateTime.now();
        this.status = OrderStatus.PLACED;
        this.customer = customer;
        this.restaurant = restaurant;
        this.items = new HashMap<>(items);
        this.deliveryFee = deliveryFee;
        this.paymentMethod = paymentMethod;
        this.notes = notes;
        this.subtotal = calculateSubtotal();
    }

    private double calculateSubtotal(){
        double total = 0;
        for(Map.Entry<MenuItem, Integer> entry:items.entrySet()){
            MenuItem item = entry.getKey();
            int quantity = entry.getValue();
            total += (item.getPrice()*quantity);
        }
        return total;
    }

    public double getTotal(){
        return this.subtotal + this.deliveryFee;
    }

    public boolean processOrderPayment(){
        if(this.status != OrderStatus.PLACED){
            throw new InvalidOrderStatusException("Cannot process payment for order with status " + this.status);
        }
        boolean succes = this.paymentMethod.processPayment();
        if(succes){
            this.status = OrderStatus.PREPARING;
            System.out.println("Payment processed for order " + id + ". Order is now being prepared.");
        }
        else {
            this.status = OrderStatus.CANCELLED;
        }
        return succes;
    }

    public void assignDriver(DeliveryPerson driver){
        if(this.status == OrderStatus.PREPARING){
            this.driver = driver;
            this.status = OrderStatus.READY_FOR_PICKUP;
            System.out.println("Driver " + driver.getName() + " assigned to order " + id);
        }
        else{
            throw new InvalidOrderStatusException("Cannot assign driver to order with status " + this.status);
        }
    }

    public boolean cancelOrder(){
        if(this.status == OrderStatus.READY_FOR_PICKUP || this.status == OrderStatus.DELIVERED){
            throw new InvalidOrderStatusException("Cannot cancel order with status " + this.status);
        }
        this.status = OrderStatus.CANCELLED;
        if(this.paymentMethod.getStatus() == PaymentStatus.COMPLETED){
            this.paymentMethod.refund(getTotal());
            System.out.println("Order " + id + " cancelled and payment refunded.");
        }
        return true;
    }

    public void markDelivered(){
        if(this.status == OrderStatus.READY_FOR_PICKUP){
            this.status = OrderStatus.DELIVERED;
            this.deliveryDate = LocalDateTime.now();
            System.out.println("Order " + id + " was succesfuly delivered.");
        }
        else{
            throw new InvalidOrderStatusException("Cannot mark order as delivered with status " + this.status);
        }
    }

    @Override
    public String toString(){
        return "Order number " + id + ", status " + status + ", total " + getTotal();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Map<MenuItem, Integer> getItems() {
        return items;
    }

    public void setId(int id) { this.id = id; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public LocalDateTime getDeliveryDate() { return deliveryDate; }
    public DeliveryPerson getDriver() { return driver; }
    public double getDeliveryFee() { return deliveryFee; }
    public String getNotes() { return notes; }
    public Payment getPaymentMethod() { return paymentMethod; }
    public void setOrderDate(LocalDateTime date) { this.orderDate = date; }
    public void setDeliveryDate(LocalDateTime date) { this.deliveryDate = date; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public void setDriver(DeliveryPerson driver) { this.driver = driver; }
}