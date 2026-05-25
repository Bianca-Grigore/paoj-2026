package com.pao.proiect.tema.repository;

import com.pao.proiect.tema.model.Menu;
import com.pao.proiect.tema.model.MenuItem;
import com.pao.proiect.tema.util.DatabaseConnection;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenusRepository implements Repository<Menu, Integer>{
    private Connection getConn() throws SQLException, IOException {
        return DatabaseConnection.getInstance().getConnection();
    }

    private Menu mapRow(ResultSet rs) throws SQLException{
        int id=rs.getInt("id");
        int restaurantId = rs.getInt("restaurant_id");
        Menu menu = new Menu();
        menu.setId(id);
        menu.setRestaurantId(restaurantId);
        MenuItemsRepository menuItemsRepository = new MenuItemsRepository();
        List<MenuItem> items = menuItemsRepository.findByMenuId(id);

        for(MenuItem item : items){
            menu.addProduct(item);
        }
        return menu;
    }

    @Override
    public void save(Menu entity) throws SQLException {
        String sql = "INSERT INTO menus (restaurant_id) VALUES (?)";

        try (PreparedStatement ps = getConn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entity.getRestaurantId());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    entity.setId(keys.getInt(1));
                }
            }

            if (entity.getItems() != null && !entity.getItems().isEmpty()) {
                MenuItemsRepository itemsRepo = new MenuItemsRepository();
                for (MenuItem produs : entity.getItems()) {
                    produs.setMenuId(entity.getId());
                    itemsRepo.save(produs);
                }
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public Optional<Menu> findById(Integer id) throws SQLException {
        String sql = "SELECT * FROM menus WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
                return Optional.empty();
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public List<Menu> findAll() throws SQLException {
        String sql = "SELECT * FROM menus ORDER BY id";
        List<Menu> list = new ArrayList<>();
        try (PreparedStatement ps = getConn().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (IOException e) {
            throw new SQLException(e);
        }
        return list;
    }

    @Override
    public void update(Menu entity) throws SQLException {
        String sql = "UPDATE menus SET restaurant_id = ? WHERE id = ?";
        try (PreparedStatement ps = getConn().prepareStatement(sql)) {
            ps.setInt(1, entity.getRestaurantId());
            ps.setInt(2, entity.getId());
            ps.executeUpdate();
        } catch (IOException e) {
            throw new SQLException(e);
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM menus WHERE id = ?";
        try(PreparedStatement ps = getConn().prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch(IOException e){
            throw new SQLException(e);
        }
}
}