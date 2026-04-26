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

    public Optional<Restaurant> findByName(String name){
        return restaurants.stream().filter(r -> r.getName().equalsIgnoreCase(name)).findFirst();
    }

    public List<Restaurant> getAll(){
        return Collections.unmodifiableList(restaurants);
    }
}