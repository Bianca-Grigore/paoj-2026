package com.pao.proiect.tema.model;

import com.pao.proiect.tema.exception.InvalidAllergenException;

import java.util.HashSet;
import java.util.Set;

public abstract sealed class MenuItem implements Comparable<MenuItem> permits FoodItem, DrinkItem{
    protected String name;
    protected double calories;
    protected double price;
    protected int id;
    private static int idNum =0;
    protected String description;
    protected boolean isAvailable;
    protected int estimatedTime;
    private boolean isVegan;
    private boolean isVegetarian;
    private Set<Allergen> allergens;
    private int menuId;

    public MenuItem(String name, double calories, double price, String description, int estimatedTime, boolean isVegan, boolean isVegetarian) {
        this.name = name;
        this.calories = calories;
        this.price = price;
        this.id = idNum;
        idNum +=1;
        this.description = description;
        this.estimatedTime = estimatedTime;
        this.isAvailable = true;
        this.isVegan = isVegan;
        this.isVegetarian = isVegan || isVegetarian;
        this.allergens = new HashSet<>();
    }

    public MenuItem(String name, double price, boolean isVegan, boolean isVegetarian){
        this(name, 0.0, price, "No description available", 0, isVegan, isVegetarian);
    }

    public boolean addAllergen(Allergen allergen){
        if(allergen == null)
            return false;

        if(this.isVegan && (allergen == Allergen.LACTOSE || allergen == Allergen.EGGS || allergen == Allergen.FISH || allergen == Allergen.CRUSTACEANS || allergen == Allergen.MOLLUSCS)){
            throw new InvalidAllergenException("Adding a non-vegan allergen to a vegan menu item.");
        }

        if(this.isVegetarian && (allergen == Allergen.FISH || allergen == Allergen.CRUSTACEANS || allergen == Allergen.MOLLUSCS)){
            throw new InvalidAllergenException("Adding a non-vegetarian allergen to a vegetarian menu item.");
        }
        this.allergens.add(allergen);
        return true;
    }

    public boolean applyDiscount(double p){
        if (p > 0 && p < 100){
            double discountAmount = price * (p / 100);
            price -= discountAmount;
            return true;
        }
           return false;
        }

    @Override
    public String toString(){
        return "MenuItem: id= " + id + ", name: " + name +
                ", calories: " + calories + ", price: " + price +
                ", description: " + description + ", estimated time: " +
                estimatedTime + " minutes, vegan: " + isVegan + ", vegetarian: " + isVegetarian +
                ", allergens: " + allergens;
    }

    @Override
    public int compareTo(MenuItem other){
        int pc = Double.compare(this.price, other.price);
        if(pc == 0){
            return this.name.compareTo(other.name);
        }
        return pc;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        MenuItem menuItem = (MenuItem) o;
        return id == menuItem.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public int getId() {
        return id;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isVegan(){
        return isVegan;
    }

    public boolean isVegetarian(){
        return isVegetarian;
    }

    public Set<Allergen> getAllergens(){
        return allergens;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
}
