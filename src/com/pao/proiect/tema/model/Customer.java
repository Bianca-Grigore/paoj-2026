package com.pao.proiect.tema.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Customer extends User {
    private Address address;
    private int points;
    private List<Order> orderHistory;
    private List<CustomerCard> cards;
    private ShoppingCart cart;

    public Customer(String name, String email, String phoneNum, String password, Address address) {
        super(name, email, phoneNum, password);
        this.address = address;
        this.points = 0;
        this.orderHistory = new ArrayList<>();
        this.cards = new ArrayList<>();
        this.cart = null;
    }

    public Customer() {
        super();
    }

    @Override
    public String getRole(){
        return "Customer";
    }

    public void addLoyaltyPoints(int p){
        if(p > 0){
            this.points += p;
        }
    }

    public void addOrder(Order order){
        if(order != null){
            this.orderHistory.add(order);
        }
    }

    public void addCard(CustomerCard card){
        if(card != null && !cards.contains(card)){
            cards.add(card);
            System.out.println("Card successfully added.");
        }
    }

    @Override
    public String toString(){
        return super.toString() + " address: " + address + ", loyalty points: " + points;
    }

    public Address getAddress() {
        return address;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<CustomerCard> getSavedCards(){
        return Collections.unmodifiableList(cards);
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }
}