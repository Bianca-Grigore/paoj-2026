package com.pao.proiect.tema.service;

import com.pao.proiect.tema.exception.DuplicateEmailException;
import com.pao.proiect.tema.model.Customer;
import com.pao.proiect.tema.model.DeliveryPerson;
import com.pao.proiect.tema.model.RestaurantAdmin;
import com.pao.proiect.tema.model.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserService {
    private List<User> users;

    private UserService() {
        this.users = new ArrayList<>();
    }

    private static class Holder {
        private static final UserService INSTANCE = new UserService();
    }

    public static UserService getInstance() {
        return UserService.Holder.INSTANCE;
    }

    public void registerUser(User newUser) {
        if (newUser == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        boolean emailUsed = users.stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(newUser.getEmail()));
        if (emailUsed) {
            throw new DuplicateEmailException("Email is already used");
        }
        users.add(newUser);
        System.out.println("User registered successfully: " + newUser);
    }

    public boolean deleteUserByEmail(String email){
        if(email == null)
            return false;
        return users.removeIf(user -> user.getEmail().equalsIgnoreCase(email));
    }

    public Optional<User> getUserById(int id){
        return users.stream().filter(user -> user.getId() == id).findFirst();
    }

    public Optional<User> getUserByName(String name){
        return users.stream().filter(user -> user.getName().equalsIgnoreCase(name)).findFirst();
    }

    public List<User> getAllUsers(){
        return Collections.unmodifiableList(users);
    }

    public List<Customer> getAllCustomers() {
        return users.stream().filter(user -> user instanceof Customer).map(user -> (Customer) user).toList();
    }

    public List<DeliveryPerson> getAllDeliveryPeople(){
        return users.stream().filter(user -> user instanceof DeliveryPerson).map(user -> (DeliveryPerson) user).toList();
    }

    public List<RestaurantAdmin> getAllRestaurantAdmin(){
        return users.stream().filter(user -> user instanceof RestaurantAdmin).map(user -> (RestaurantAdmin) user).toList();
    }

    public Optional<Customer> getCustomerByName(String name){
        return getAllCustomers().stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Optional<DeliveryPerson> getDeliveryPersonByName(String name){
        return getAllDeliveryPeople().stream().filter(d -> d.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Optional<RestaurantAdmin> getRestaurantAdminByResName(String name){
        return getAllRestaurantAdmin().stream().filter(r -> r.getRestaurantName().equalsIgnoreCase(name)).findFirst();
    }

    public Map<String, User> getUserIdexByEmail(){
        return users.stream().collect(Collectors.toMap(User::getEmail, user -> user));
    }
}