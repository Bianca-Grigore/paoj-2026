package com.pao.proiect.tema.service;

import com.pao.proiect.tema.exception.DuplicateEmailException;
import com.pao.proiect.tema.model.Customer;
import com.pao.proiect.tema.model.DeliveryPerson;
import com.pao.proiect.tema.model.RestaurantAdmin;
import com.pao.proiect.tema.model.User;
import com.pao.proiect.tema.repository.UsersRepository;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class UserService {
    UsersRepository usersRepository = new UsersRepository();
    AuditService audit = AuditService.getInstance();

    private UserService() {
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

        boolean emailUsed = getAllUsers().stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(newUser.getEmail()));
        if (emailUsed) {
            throw new DuplicateEmailException("Email is already used");
        }

        try {
            usersRepository.save(newUser);
            audit.log("register_user");
            System.out.println("User registered successfully: " + newUser.getName());
        } catch(SQLException e) {
            System.out.println("Database error while registering user: " + newUser.getEmail() + " - " + e.getMessage());
        }
    }

    public boolean deleteUserByEmail(String email) {
        if(email == null) return false;

        Optional<User> toDelete = getAllUsers().stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();

        if (toDelete.isPresent()) {
            try {
                usersRepository.delete(toDelete.get().getId());
                audit.log("delete_user");
                System.out.println("User deleted successfully.");
                return true;
            } catch (SQLException e) {
                System.out.println("Database error while deleting user.");
                return false;
            }
        }
        System.out.println("No user found with email: " + email);
        return false;
    }

    public boolean updateUserName(String email, String newName) {
        if(email == null || newName == null || newName.trim().isEmpty()){
            throw new IllegalArgumentException("Email and new name cannot be null or empty");
        }

        Optional<User> userOptional = getAllUsers().stream().filter(u -> u.getEmail().equalsIgnoreCase(email)).findFirst();

        if(userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(newName);

            try {
                usersRepository.update(user);
                audit.log("update_user_name");
                System.out.println("User name updated successfully for email: " + email);
                return true;
            } catch (SQLException e) {
                System.out.println("Database error while updating user name.");
                return false;
            }
        }
        System.out.println("No user found with email: " + email);
        return false;
    }

    public List<User> getAllUsers() {
        audit.log("get_all_users");
        try {
            return usersRepository.findAll();
        } catch (SQLException e) {
            System.out.println("Database error while retrieving users.");
            return Collections.emptyList();
        }
    }

    public Optional<User> getUserById(int id) {
        audit.log("get_user_by_id");
        try {
            return usersRepository.findById(id);
        } catch (SQLException e) {
            System.out.println("Database error while finding user by ID.");
            return Optional.empty();
        }
    }

    public Optional<User> getUserByName(String name) {
        return getAllUsers().stream().filter(user -> user.getName().equalsIgnoreCase(name)).findFirst();
    }

    public List<Customer> getAllCustomers() {
        return getAllUsers().stream().filter(user -> user instanceof Customer).map(user -> (Customer) user).toList();
    }

    public List<DeliveryPerson> getAllDeliveryPeople() {
        return getAllUsers().stream().filter(user -> user instanceof DeliveryPerson).map(user -> (DeliveryPerson) user).toList();
    }

    public List<RestaurantAdmin> getAllRestaurantAdmin() {
        return getAllUsers().stream().filter(user -> user instanceof RestaurantAdmin).map(user -> (RestaurantAdmin) user).toList();
    }

    public Optional<Customer> getCustomerByName(String name) {
        return getAllCustomers().stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Optional<DeliveryPerson> getDeliveryPersonByName(String name) {
        return getAllDeliveryPeople().stream().filter(d -> d.getName().equalsIgnoreCase(name)).findFirst();
    }

    public Optional<RestaurantAdmin> getRestaurantAdminByResName(String name) {
        return getAllRestaurantAdmin().stream().filter(r -> r.getRestaurantName().equalsIgnoreCase(name)).findFirst();
    }

    public Map<String, User> getUserIdexByEmail() {

        return getAllUsers().stream().collect(Collectors.toMap(User::getEmail, user -> user));
    }
}