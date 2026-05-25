package com.pao.proiect.tema.repository;
import com.pao.proiect.tema.model.*;
import com.pao.proiect.tema.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepository implements Repository<User, Integer>{

    private Connection getConn() throws SQLException, IOException{
        return DatabaseConnection.getInstance().getConnection();
    }

    private User mapRow(ResultSet rs) throws SQLException {
        String role = rs.getString("role");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String phoneNum = rs.getString("phone_num");
        String password = rs.getString("password");
        User u;

        if ("CUSTOMER".equalsIgnoreCase(role)) {
            String city = rs.getString("city");
            String street = rs.getString("street");
            String bldNum = rs.getString("building_number");
            String postal = rs.getString("postal_code");
            String details = rs.getString("details");
            Address address = new Address(city, street, bldNum, postal, details);

            Customer customer = new Customer(name, email, phoneNum, password, address);
            customer.setPoints(rs.getInt("points"));
            u = customer;
        } else if ("admin".equalsIgnoreCase(role)) {
            String restaurantName = rs.getString("restaurant_name");
            String accessLevelStr = rs.getString("access_level");

            AccessLevel accessLevel = AccessLevel.valueOf(accessLevelStr.toUpperCase());
            RestaurantAdmin admin = new RestaurantAdmin(name, email, phoneNum, password, restaurantName, accessLevel);
            if (!rs.getBoolean("is_active")) {
                admin.deactivateAccount();
            }
            u = admin;
        } else if ("Delivery".equalsIgnoreCase(role)) {
            String vehicleType = rs.getString("vehicle_type");
            VehicleType vType = VehicleType.valueOf(vehicleType.toUpperCase());
            boolean isavailable = rs.getBoolean("is_available");
            DeliveryPerson deliveryPerson = new DeliveryPerson(name, email, phoneNum, password, vType, isavailable);
            deliveryPerson.setTotalDeliveries(rs.getInt("total_deliveries"));
            deliveryPerson.setRating(rs.getDouble("rating"));
            u = deliveryPerson;
        } else{
            throw new SQLException("Unknown role: " + role);
        }
        u.setId(rs.getInt("id"));
        return u;
    }

    @Override
    public void save(User entity) throws SQLException {
        String sql = "INSERT INTO users (name, email, phone_num, password, role, city, street, building_number, postal_code, details, points, restaurant_name, is_active, access_level, vehicle_type, is_available, rating, total_deliveries) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement ps = getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getPhoneNum());
            ps.setString(4, entity.getPassword());
            ps.setString(5, entity.getRole());

            ps.setNull(6, Types.VARCHAR); 
            ps.setNull(7, Types.VARCHAR);
            ps.setNull(8, Types.VARCHAR);
            ps.setNull(9, Types.VARCHAR);
            ps.setNull(10, Types.VARCHAR);
            ps.setNull(11, Types.INTEGER);
            ps.setNull(12, Types.VARCHAR);
            ps.setNull(13, Types.BOOLEAN);
            ps.setNull(14, Types.VARCHAR);
            ps.setNull(15, Types.VARCHAR);
            ps.setNull(16, Types.BOOLEAN);
            ps.setNull(17, Types.DOUBLE);
            ps.setNull(18, Types.INTEGER);

            if(entity instanceof Customer customer){
                Address address = customer.getAddress();
                ps.setString(6, address.city());
                ps.setString(7, address.street());
                ps.setString(8, address.buildingNumber());
                ps.setString(9, address.postalCode());
                ps.setString(10, address.details());
                ps.setInt(11, customer.getPoints());

            }
            else
                if(entity instanceof RestaurantAdmin admin){
                    ps.setString(12, admin.getRestaurantName());
                    ps.setBoolean(13, admin.isActive());
                    ps.setString(14, admin.getAccessLevel().name());
                }
                else if(entity instanceof DeliveryPerson deliveryPerson){
                    ps.setString(15, deliveryPerson.getVehicleType().name());
                    ps.setBoolean(16, deliveryPerson.isAvailable());
                    ps.setDouble(17, deliveryPerson.getRating());
                    ps.setInt(18, deliveryPerson.getTotalDeliveries());
                }
                ps.executeUpdate();
                try(ResultSet keys = ps.getGeneratedKeys()){
                    if(keys.next())
                        entity.setId(keys.getInt(1));
                }
        }catch(IOException e){
            throw new SQLException(e);
        }
    }

    @Override
    public Optional<User> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id= ?";
        try(PreparedStatement ps = getConn().prepareStatement(sql)){
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) return Optional.of(mapRow(rs));
                return Optional.empty();
            }
        }catch (IOException e){
            throw new SQLException(e);
        }
    }

    @Override
    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM users ORDER BY id";
        List<User> list = new ArrayList<>();
        try(PreparedStatement ps = getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next())
                list.add(mapRow(rs));
        }catch (IOException e){
            throw new SQLException(e);
        }
        return list;
    }

    @Override
    public void update(User entity) throws SQLException {
        String sql = "UPDATE users SET name=?, email=?, phone_num=?, password=?, role=?, city=?, street=?, building_number=?, postal_code=?, details=?, points=?, restaurant_name=?, is_active=?, access_level=?, vehicle_type=?, is_available=?, rating=?, total_deliveries=? WHERE id=?";
        try(PreparedStatement ps = getConn().prepareStatement(sql)){
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getEmail());
            ps.setString(3, entity.getPhoneNum());
            ps.setString(4, entity.getPassword());
            ps.setString(5, entity.getRole());

            for(int i=6; i<=18; i++){
                ps.setObject(i, null);
            }

            if(entity instanceof Customer customer){
                Address address = customer.getAddress();
                ps.setString(6, address.city());
                ps.setString(7, address.street());
                ps.setString(8, address.buildingNumber());
                ps.setString(9, address.postalCode());
                ps.setString(10, address.details());
                ps.setInt(11, customer.getPoints());

            }
            else
            if(entity instanceof RestaurantAdmin admin){
                ps.setString(12, admin.getRestaurantName());
                ps.setBoolean(13, admin.isActive());
                ps.setString(14, admin.getAccessLevel().name());
            }
            else if(entity instanceof DeliveryPerson deliveryPerson){
                ps.setString(15, deliveryPerson.getVehicleType().name());
                ps.setBoolean(16, deliveryPerson.isAvailable());
                ps.setDouble(17, deliveryPerson.getRating());
                ps.setInt(18, deliveryPerson.getTotalDeliveries());
            }
            ps.setInt(19, entity.getId());
            ps.executeUpdate();
        }catch(IOException e){
            throw new SQLException(e);
        }

    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM users WHERE id= ?";
        try(PreparedStatement ps = getConn().prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch(IOException e){
            throw new SQLException(e);
        }
    }
}