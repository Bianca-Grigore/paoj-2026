package com.pao.proiect.tema.repository;

import com.pao.proiect.tema.model.Restaurant;
import com.pao.proiect.tema.model.RestaurantAdmin;
import com.pao.proiect.tema.model.User;
import com.pao.proiect.tema.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RestaurantsRepository implements Repository<Restaurant, Integer>{
    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }
    private Restaurant mapRow(ResultSet rs) throws SQLException{
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String address = rs.getString("address");
        boolean isOpen = rs.getBoolean("is_open");
        int adminId = rs.getInt("admin_id");
        RestaurantAdmin admin = null;
        if (adminId !=0 ){
            UsersRepository usersRepository = new UsersRepository();
            Optional<User> userOptional = usersRepository.findById(adminId);
            if(userOptional.isPresent() && userOptional.get() instanceof RestaurantAdmin){
                admin = (RestaurantAdmin) userOptional.get();
            }
        }
        Restaurant restaurant = new Restaurant(name, address, admin);
        restaurant.setOpen(isOpen);
        restaurant.setId(id);
        return restaurant;
    }

    @Override
    public void save(Restaurant entity) throws SQLException {
        String sql = "INSERT INTO restaurants (name, address, is_open, admin_id) VALUES (?, ?, ?, ?)";
        try(PreparedStatement ps = getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getAddress());
            ps.setBoolean(3, entity.isOpen());
            if(entity.getAdmin() != null){
                ps.setInt(4, entity.getAdmin().getId());
            }
            else{
                ps.setNull(4, Types.INTEGER);
            }

            ps.executeUpdate();
            try(ResultSet keys = ps.getGeneratedKeys()){
                if(keys.next()){
                    entity.setId(keys.getInt(1));
                }
            }
        }catch (IOException e){
            throw new SQLException(e);
        }
    }

    @Override
    public Optional<Restaurant> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM restaurants WHERE id= ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(mapRow(rs));
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<Restaurant> findAll() throws SQLException {
        String sql = "SELECT * FROM restaurants ORDER BY id";
        List<Restaurant> list = new ArrayList<>();
        try(PreparedStatement ps = getConn().prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                list.add(mapRow(rs));
            }
        }catch (IOException e){
            throw new SQLException(e);
        }
        return list;
    }

    @Override
    public void update(Restaurant entity) throws SQLException {
        String sql = "UPDATE restaurants SET name = ?, address= ?, is_open= ?, admin_id = ? WHERE id= ?";
        try(PreparedStatement ps = getConn().prepareStatement(sql)){
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getAddress());
            ps.setBoolean(3, entity.isOpen());
            if(entity.getAdmin() != null){
                ps.setInt(4, entity.getAdmin().getId());
            } else {
                ps.setNull(4, Types.INTEGER);
            }
            ps.setInt(5, entity.getId());
            ps.executeUpdate();
        }catch (IOException e){
            throw new SQLException(e);
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM restaurants WHERE id = ?";
        try(PreparedStatement ps = getConn().prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch(IOException e){
            throw new SQLException(e);
        }
    }

    public void printResStatistics() throws SQLException{
        String sql = "SELECT r.name AS restaurant_name, COUNT(o.id) AS total_orders " +
                "FROM restaurants r " +
                "LEFT JOIN orders o ON r.id = o.restaurant_id " +
                "GROUP BY r.id, r.name";
        try(PreparedStatement ps = getConn().prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){
            System.out.println("Restaurant Name | Total Orders");
            while(rs.next()){
                System.out.println("Restaurant: " + rs.getString("restaurant_name") + " | total orders: " + rs.getInt("total_orders"));
            }
        }catch(IOException e){
            throw new SQLException(e);
        }
    }
}