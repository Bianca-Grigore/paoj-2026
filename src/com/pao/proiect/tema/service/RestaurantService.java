package com.pao.proiect.tema.service;

import com.pao.proiect.tema.model.Restaurant;
import com.pao.proiect.tema.repository.RestaurantsRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RestaurantService{
    RestaurantsRepository restaurantsRepository = new RestaurantsRepository();
    AuditService audit = AuditService.getInstance();

    public RestaurantService() {}

    private static class Holder{
        private static final RestaurantService INSTANCE = new RestaurantService();
    }

    public static RestaurantService getInstance(){
        return Holder.INSTANCE;
    }

    public void addRestaurant(Restaurant restaurant){
        if(restaurant == null)
            throw new IllegalArgumentException("Restaurant cannot be null");

        try {
            boolean exists = getAll().stream().anyMatch(r -> r.getName().equalsIgnoreCase(restaurant.getName()));
            if (!exists) {
                restaurantsRepository.save(restaurant);
                audit.log("add_restaurant");
            }
        }catch(SQLException e){
            System.out.println("Dabase error while adding restaurant: " + restaurant.getName());
        }
    }

    public boolean deleteRestaurantByName(String name){
        if(name == null) return false;

        Optional<Restaurant> toDelete = findByName(name);
        if(toDelete.isPresent()) {
            try {
                restaurantsRepository.delete(toDelete.get().getId());
                audit.log("delete_restaurant");
                System.out.println("Restaurant " + name + " deleted successfully.");
                return true;
            } catch(SQLException e) {
                System.out.println("Database error while deleting restaurant: " + name);
                return false;
            }
        }

        System.out.println("Restaurant " + name + " not found.");
        return false;
    }

    public boolean updateRestaurantName(String currentName, String newName){
        if(currentName == null || newName==null || newName.trim().isEmpty()){
            throw new IllegalArgumentException("Current name and new name cannot be null or empty");
        }
        Optional<Restaurant> restaurantOptional = findByName(currentName);
        if(restaurantOptional.isPresent()){
            boolean name = getAll().stream().anyMatch(r -> r.getName().equalsIgnoreCase(newName));
            if(name){
                System.out.println("Restaurant with name " + newName + " already exists. Cannot update name.");
                return false;
            }
            Restaurant r = restaurantOptional.get();
            r.setName(newName);
            try {
                restaurantsRepository.update(r);
                audit.log("update_restaurant_name");
            } catch(SQLException e){
                System.out.println("Database error while updating restaurant name from " + currentName + " to " + newName);
                return false;
            }
            System.out.println("Restaurant name updated successfully from " + currentName + " to " + newName);
            return true;
        }
        System.out.println("No restaurant found with name: " + currentName);
        return false;
    }

    public Optional<Restaurant> findByName(String name) {
            audit.log("find_by_name");
            return getAll().stream().filter(r -> r.getName().equalsIgnoreCase(name)).findFirst();
    }

    public List<Restaurant> getAll() {
        try {
            audit.log("get_all_restaurants");
            return restaurantsRepository.findAll();
        }catch(SQLException e){
            System.out.println("Database error while getting all restaurants");
             return Collections.emptyList();
        }
    }
}