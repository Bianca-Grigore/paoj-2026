package com.pao.proiect.tema.repository;

import com.pao.proiect.tema.model.*;
import com.pao.proiect.tema.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class OrdersRepository implements  Repository<Order, Integer>{
    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    private Order mapRow(ResultSet rs) throws SQLException, IOException {
        int id = rs.getInt("id");
        int customer_id = rs.getInt("customer_id");
        int restaurant_id = rs.getInt("restaurant_id");
        int driver_id = rs.getInt("driver_id");
        Timestamp order_date = rs.getTimestamp("order_date");
        Timestamp delivery_date = rs.getTimestamp("delivery_date");

        OrderStatus status = OrderStatus.valueOf(rs.getString("status").toUpperCase());
        double delivery_fee = rs.getDouble("delivery_fee");
        double subtotal = rs.getDouble("subtotal");
        String notes = rs.getString("notes");

        Customer customer = new Customer();
        customer.setId(customer_id);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(restaurant_id);

        Map<MenuItem, Integer> items = new HashMap<>();
        String sql = "SELECT menu_item_id, quantity FROM order_items WHERE order_id= ?";

        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rsItems = ps.executeQuery()) {
                MenuItemsRepository itemsRepository = new MenuItemsRepository();

                while (rsItems.next()) {
                    int itemId = rsItems.getInt("menu_item_id");
                    int quantity = rsItems.getInt("quantity");

                    Optional<MenuItem> product = itemsRepository.findById(itemId);
                    if (product.isPresent()) {
                        items.put(product.get(), quantity);
                    }
                }
            }
        }
        Order o = new Order(customer, restaurant, items, delivery_fee, null, notes);
        o.setId(id);
        o.setStatus(status);
        o.setOrderDate(order_date.toLocalDateTime());

        if (delivery_date != null) {
            o.setDeliveryDate(delivery_date.toLocalDateTime());
        }
        if (driver_id > 0) {
            DeliveryPerson driver = new DeliveryPerson();
            driver.setId(driver_id);
            o.setDriver(driver);
        }
        return o;
    }

    @Override
    public void save(Order entity) throws SQLException {
        String sqlOrder = "INSERT INTO orders( customer_id, restaurant_id, driver_id, order_date, delivery_date, status, delivery_fee, subtotal, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlItemsOrder = "INSERT INTO order_items(order_id, menu_item_id, quantity) VALUES (?, ?, ?)";
        Connection conn = null;
        try{
            conn = getConn();
            conn.setAutoCommit(false);
            try(PreparedStatement ps = conn.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS)){
                ps.setInt(1, entity.getCustomer().getId());
                ps.setInt(2, entity.getRestaurant().getId());

                if(entity.getDriver() != null){
                    ps.setInt(3, entity.getDriver().getId());
                }
                else {
                    ps.setNull(3, Types.INTEGER);
                }

                ps.setTimestamp(4, Timestamp.valueOf(entity.getOrderDate()));
                if(entity.getDeliveryDate() != null){
                    ps.setTimestamp(5, Timestamp.valueOf(entity.getDeliveryDate()));
                }
                else{
                    ps.setNull(5, Types.TIMESTAMP);
                }

                ps.setString(6, entity.getStatus().name());
                ps.setDouble(7, entity.getDeliveryFee());
                ps.setDouble(8, entity.getTotal() - entity.getDeliveryFee());

                if(entity.getNotes() !=null){
                    ps.setString(9, entity.getNotes());
                }
                else{
                    ps.setNull(9, Types.VARCHAR);
                }

                ps.executeUpdate();
                try(ResultSet keys = ps.getGeneratedKeys()){
                    if(keys.next())
                        entity.setId(keys.getInt(1));
                }

                try(PreparedStatement psItems = conn.prepareStatement(sqlItemsOrder)){
                    for(Map.Entry<MenuItem, Integer> entry : entity.getItems().entrySet()){
                        psItems.setInt(1, entity.getId());
                        psItems.setInt(2, entry.getKey().getId());
                        psItems.setInt(3, entry.getValue());
                        psItems.executeUpdate();
                    }
                }
                conn.commit();
            }catch(SQLException e){
                if (conn != null) {
                    try {
                        conn.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                throw new SQLException("Transaction failed. Rollback executed succesfully", e);
            } finally {
                if (conn != null) {
                    try {
                        conn.setAutoCommit(true);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }catch (IOException e){
            throw new SQLException(e);
        }
    }

    @Override
    public Optional<Order> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM orders WHERE id= ?";
        try(PreparedStatement ps = getConn().prepareStatement(sql)){
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        }catch(IOException e){
            throw new SQLException(e);
        }
    }

    @Override
    public List<Order> findAll() throws SQLException {
        String sql = "SELECT * FROM orders ORDER BY id";
        List<Order> list = new ArrayList<>();
        try(PreparedStatement ps = getConn().prepareStatement(sql);
        ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                list.add(mapRow(rs));
            }
        }catch(IOException e){
            throw new SQLException(e);
        }
        return list;
    }

    @Override
    public void update(Order entity) throws SQLException {
        String sqlUpdateOrder = "UPDATE orders SET customer_id= ?, restaurant_id= ?, driver_id= ?, order_date= ?, delivery_date= ?, status= ?, delivery_fee= ?, subtotal= ?, notes= ? WHERE id= ?";
        String sqlDeleteItems = "DELETE FROM order_items WHERE order_id= ?";
        String sqlInsertItems = "INSERT INTO order_items(order_id, menu_item_id, quantity) VALUES (?, ?, ?)";
        Connection conn = null;
        try{
           conn = getConn();
           conn.setAutoCommit(false);
           try(PreparedStatement psUpdateOrder = conn.prepareStatement(sqlUpdateOrder)){
               psUpdateOrder.setInt(1, entity.getCustomer().getId());
               psUpdateOrder.setInt(2, entity.getRestaurant().getId());

               if(entity.getDriver() != null){
                   psUpdateOrder.setInt(3, entity.getDriver().getId());
               }
               else {
                   psUpdateOrder.setNull(3, Types.INTEGER);
               }

               psUpdateOrder.setTimestamp(4, Timestamp.valueOf(entity.getOrderDate()));
               if(entity.getDeliveryDate() != null){
                   psUpdateOrder.setTimestamp(5, Timestamp.valueOf(entity.getDeliveryDate()));
               }
               else{
                   psUpdateOrder.setNull(5, Types.TIMESTAMP);
               }

               psUpdateOrder.setString(6, entity.getStatus().name());
               psUpdateOrder.setDouble(7, entity.getDeliveryFee());
               psUpdateOrder.setDouble(8, entity.getTotal() - entity.getDeliveryFee());

               if(entity.getNotes() !=null){
                   psUpdateOrder.setString(9, entity.getNotes());
               }
               else{
                   psUpdateOrder.setNull(9, Types.VARCHAR);
               }
               psUpdateOrder.setInt(10, entity.getId());
               psUpdateOrder.executeUpdate();
           }

           try(PreparedStatement psDelete = conn.prepareStatement(sqlDeleteItems)){
               psDelete.setInt(1, entity.getId());
               psDelete.executeUpdate();
           }

           try(PreparedStatement psInsert = conn.prepareStatement(sqlInsertItems)){
               for(Map.Entry<MenuItem, Integer> entry : entity.getItems().entrySet()){
                   psInsert.setInt(1, entity.getId());
                   psInsert.setInt(2, entry.getKey().getId());
                   psInsert.setInt(3, entry.getValue());
                   psInsert.executeUpdate();
               }
           }
              conn.commit();
        }catch(SQLException e){
            if(conn != null){
                try{
                    conn.rollback();
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }
            throw new SQLException("Failed update. Rollback executed.", e);
        }catch(IOException e){
            throw new SQLException(e);
        }finally{
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM orders WHERE id= ?";
        try(PreparedStatement ps = getConn().prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch (IOException e){
            throw new SQLException(e);
        }
    }

    public void printOrdersDetails(int orderId) throws SQLException{
        String sql = "SELECT o.id, c.name AS customer_name, r.name AS restaurant_name " +
                "FROM orders o "+
                "JOIN users c ON o.customer_id = c.id " +
                "JOIN restaurants r ON o.restaurant_id = r.id "+
                "WHERE o.id = ?";
        try(PreparedStatement ps = getConn().prepareStatement(sql)){
            ps.setInt(1, orderId);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    System.out.println("Order " + rs.getInt("id") + " Customer " + rs.getString("customer_name") + " Restaurant name " + rs.getString("restaurant_name"));
                }
                else{
                    System.out.println("No order found with id: " + orderId);
                }
            }
        }catch(IOException e){
            throw new SQLException(e);
        }
    }

    public void printOrdersDrivers() throws SQLException{
        String sql = "SELECT o.id AS order_id, o.status, d.name AS driver_name "+
                "FROM orders o " +
                "LEFT JOIN users d ON o.driver_id = d.id";
        try(PreparedStatement ps = getConn().prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            System.out.println("Deliveries status: ");
            while(rs.next()){
                String driver = rs.getString("driver_name");
                if(driver == null){
                    driver = "Waiting for driver assignment";
                }
                System.out.println("Order " + rs.getInt("order_id") + " Status: " + rs.getString("status") + " Driver: " + driver);
            }
        }catch(IOException e){
            throw new SQLException(e);
        }
    }
}