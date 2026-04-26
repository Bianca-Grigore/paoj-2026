package com.pao.proiect.tema.service;

import com.pao.proiect.tema.model.Restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RestaurantService{
    private final List<Restaurant> restaurants;

    public RestaurantService() {
        this.restaurants = new ArrayList<>();
    }

    private static class Holder{
        private static final RestaurantService INSTANCE = new RestaurantService();
    }

    public static RestaurantService getInstance(){
        return Holder.INSTANCE;
    }

    public void addRestaurant(Restaurant restaurant){
        if(restaurant == null)
            throw new IllegalArgumentException("Restaurant cannot be null");

        boolean exists = restaurants.stream().anyMatch(r -> r.getName().equalsIgnoreCase(restaurant.getName()));
        if(!exists){
            restaurants.add(restaurant);
        }
    }

    public boolean deleteRestaurantByName(String name){
        if(name == null) return false;
        boolean removed = restaurants.removeIf(r -> r.getName().equalsIgnoreCase(name));
        if(removed)
            System.out.println("Restaurant " + name + " deleted successfully.");
        return removed;
    }

    public boolean updateRestaurantName(String currentName, String newName){
        if(currentName == null || newName==null || newName.trim().isEmpty()){
            throw new IllegalArgumentException("Current name and new name cannot be null or empty");
        }
        Optional<Restaurant> restaurantOptional = findByName(currentName);
        if(restaurantOptional.isPresent()){
            boolean name = restaurants.stream().anyMatch(r -> r.getName().equalsIgnoreCase(newName));
            if(name){
                System.out.println("Restaurant with name " + newName + " already exists. Cannot update name.");
                return false;
            }
            restaurantOptional.get().setName(newName);
            System.out.println("Restaurant name updated successfully from " + currentName + " to " + newName);
            return true;
        }
        System.out.println("No restaurant found with name: " + currentName);
        return false;
    }

    public Optional<Restaurant> findByName(String name){
        return restaurants.stream().filter(r -> r.getName().equalsIgnoreCase(name)).findFirst();
    }

    public List<Restaurant> getAll(){
        return Collections.unmodifiableList(restaurants);
    }
}