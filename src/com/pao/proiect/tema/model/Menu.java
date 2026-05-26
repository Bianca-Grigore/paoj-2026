package com.pao.proiect.tema.model;

import java.util.*;
import java.util.stream.Collectors;

public class Menu {
    private List<MenuItem> items;
    private int id;
    private int restaurantId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
    public Menu(){
        this.items = new ArrayList<>();
    }

    public void addProduct(MenuItem item){
        if(item == null){
            throw new IllegalArgumentException("Menu item cannot be null");
        }

        if(!items.contains(item)){
            items.add(item);
        }
        else{
            System.out.println("Product already exists in menu: " + item.getName());
        }
    }

    public void deleteProduct(MenuItem item){
        if(item == null){
            throw new IllegalArgumentException("Menu item cannot be null");
        }
        if(items.remove(item)){
            System.out.println("Product removed from menu: " + item.getName());
        }
        else{
            System.out.println("Product not found in menu: " + item.getName());
        }
    }

    @Override
    public String toString() {
        return "Menu{" +
                "items= " + items +
                '}';
    }

    public Optional<MenuItem> findById(int id){
        return items.stream().filter(item -> item.getId() == id).findFirst();
    }

    public Optional<MenuItem> findByName(String name){
        if(name == null){
            return Optional.empty();
        }
        return items.stream().filter(item -> item.getName() != null && item.getName().equalsIgnoreCase(name)).findFirst();
    }

    public List<MenuItem> getWithPrice(double price) {
        return items.stream().filter(item -> item.getPrice() <= price).sorted().toList();
    }

    public List<MenuItem> getAvailableMenu(){
        return items.stream().filter(MenuItem::isAvailable).toList();
    }

    public boolean updatePrice(int id, double newPrice){
        if(newPrice < 0){
            throw new IllegalArgumentException("Price cannot be negative");
        }

        Optional<MenuItem> itemOptional = findById(id);
        if(itemOptional.isPresent()){
            itemOptional.get().setPrice(newPrice);
            System.out.println("Price updated for item: " + itemOptional.get().getName());
            return true;
        }
        else{
            System.out.println("Menu item not found with id: " + id);
            return false;
        }
    }

    public Set<MenuItem> getSortedMenuAfterPrice(){
        Set<MenuItem> sorted = new TreeSet<>(Comparator.comparingDouble(MenuItem::getPrice));
        sorted.addAll(items);
        return sorted;
    }


    public boolean removeItem(int id){
        return items.removeIf(item -> item.getId() == id);
    }

    public void sortMenu(){
        Collections.sort(items);
    }

    public List<MenuItem> getVeganMenu(){
        return items.stream().filter(MenuItem::isVegan).collect(Collectors.toList());
    }

    public List<MenuItem> getVegetarianMenu(){
        return items.stream().filter(MenuItem::isVegetarian).collect(Collectors.toList());
    }

    public List<MenuItem> getSafeMenu(Allergen allergen){
        return items.stream().filter(item -> !item.getAllergens().contains(allergen)).collect(Collectors.toList());
    }

    public List<MenuItem> getSafeMenuForManyAllergens(List<Allergen> allergens){
        return items.stream().filter(items -> Collections.disjoint(items.getAllergens(), allergens)).collect(Collectors.toList());
    }

    public List<DrinkItem> getDrinks(){
        return items.stream().filter(item -> item instanceof DrinkItem).map(item -> (DrinkItem) item).collect(Collectors.toList());
    }

    public List<MenuItem> getItems(){
        return Collections.unmodifiableList(items);
    }
}