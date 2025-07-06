package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Database {
	private static Connection connection;

	public static void initializeDatabase() {
	    try {
	        connection = DriverManager.getConnection("jdbc:sqlite:./src/main/Database.db");
	        System.out.println("Connection to SQLite has been established.");

	        String query;
	        try (Statement statement = connection.createStatement()) {

	            // Drop existing tables
	            statement.executeUpdate("DROP TABLE IF EXISTS users");
	            statement.executeUpdate("DROP TABLE IF EXISTS menu_items");
	            statement.executeUpdate("DROP TABLE IF EXISTS orders");

	            // USERS
	            query = "CREATE TABLE IF NOT EXISTS users (" +
	                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
	                    "username TEXT NOT NULL UNIQUE," +
	                    "password TEXT NOT NULL," +
	                    "role TEXT NOT NULL)";
	            statement.executeUpdate(query);

	            query = "INSERT INTO users (id, username, password, role) VALUES " +
	                    "(1, 'customer', 'password', 'customer')," +
	                    "(2, 'operator', 'password', 'operator')," +
	                    "(3, 'manager', 'password', 'manager')";
	            statement.executeUpdate(query);

	            // MENU ITEMS
	            query = "CREATE TABLE IF NOT EXISTS menu_items (" +
	                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
	                    "name TEXT NOT NULL," +
	                    "price REAL NOT NULL," +
	                    "category TEXT NOT NULL," +
	                    "imagePath TEXT)";
	            statement.executeUpdate(query);

	            query = "INSERT INTO menu_items (id, name, price, category, imagePath) VALUES " +
	            	    "(1, 'Pancakes', 5.99, 'Breakfast', 'images/pancakes.jpg')," +
	            	    "(2, 'Eggs Benedict', 6.49, 'Breakfast', 'images/eggs_benedict.jpg')," +
	            	    "(3, 'Omelette', 7.25, 'Breakfast', 'images/omelette.jpg')," +
	            	    "(4, 'French Toast', 7.25, 'Breakfast', 'images/french_toast.jpg')," +
	            	    "(5, 'Breakfast Burrito', 7.25, 'Breakfast', 'images/breakfast_burrito.jpg')," +
	            	    "(6, 'Bagel with Lox', 7.25, 'Breakfast', 'images/bagel_lox.jpg')," +
	            	    "(7, 'Biscuits and Gravy', 7.25, 'Breakfast', 'images/biscuits_gravy.jpg')," +
	            	    "(8, 'Belgian Waffles', 7.25, 'Breakfast', 'images/belgian_waffles.jpg')," +
	            	    "(9, 'Breakfast Sandwich', 7.25, 'Breakfast', 'images/breakfast_sandwich.jpg')," +
	            	    "(10, 'Greek Yogurt Parait', 7.25, 'Breakfast', 'images/yogurt_parfait.jpg')," +
	            	    "(11, 'Cheeseburger', 9.99, 'Lunch', 'images/cheeseburger.jpg')," +
	            	    "(12, 'Club Sandwich', 8.99, 'Lunch', 'images/club_sandwich.jpg')," +
	            	    "(13, 'Caesar Salad', 7.99, 'Lunch', 'images/caesar_salad.jpg')," +
	            	    "(14, 'BBQ Ribs', 12.99, 'Lunch', 'images/bbq_ribs.jpg')," +
	            	    "(15, 'Philly Cheesesteak', 10.49, 'Lunch', 'images/philly_cheesesteak.jpg')," +
	            	    "(16, 'New England Clam Chowder', 6.99, 'Lunch', 'images/clam_chowder.jpg')," +
	            	    "(17, 'Cobb Salad', 9.49, 'Lunch', 'images/cobb_salad.jpg')," +
	            	    "(18, 'Pulled Pork Sandwich', 8.99, 'Lunch', 'images/pulled_pork.jpg')," +
	            	    "(19, 'Fish Tacos', 9.25, 'Lunch', 'images/fish_tacos.jpg')," +
	            	    "(20, 'Shrimp Scampi', 14.99, 'Dinner', 'images/shrimp_scampi.jpg')," +
	            	    "(21, 'BBQ Brisket', 13.99, 'Dinner', 'images/bbq_brisket.jpg')," +
	            	    "(22, 'Vegetarian Lasagna', 11.49, 'Dinner', 'images/vegetarian_lasagna.jpg')," +
	            	    "(23, 'Stuffed Bell Peppers', 10.99, 'Dinner', 'images/stuffed_peppers.jpg')," +
	            	    "(24, 'Lobster Tail Dinner', 19.99, 'Dinner', 'images/lobster_tail.jpg')," +
	            	    "(25, 'Taco Night', 12.49, 'Dinner', 'images/taco_night.jpg')," +
	            	    "(26, 'Buffalo Wings', 9.99, 'Dinner', 'images/buffalo_wings.jpg')," +
	            	    "(27, 'Steakhouse Dinner', 18.99, 'Dinner', 'images/steakhouse_dinner.jpg')," +
	            	    "(28, 'Grilled Salmon', 16.99, 'Dinner', 'images/grilled_salmon.jpg')," +
	            	    "(29, 'Roast Chicken', 13.49, 'Dinner', 'images/roast_chicken.jpg')," +
	            	    "(30, 'Spaghetti and Meatballs', 11.99, 'Dinner', 'images/spaghetti_meatballs.jpg')," +
	            	    "(31, 'Coffee', 2.49, 'Beverage', 'images/coffee.jpg')," +
	            	    "(32, 'Iced Tea', 2.25, 'Beverage', 'images/iced_tea.jpg')," +
	            	    "(33, 'Soda (Soft Drinks)', 2.00, 'Beverage', 'images/soda.jpg')," +
	            	    "(34, 'Craft Beer', 5.49, 'Beverage', 'images/craft_beer.jpg')," +
	            	    "(35, 'Bottled Water', 1.50, 'Beverage', 'images/bottled_water.jpg');";
	            statement.executeUpdate(query);

	            // ORDERS
	            query = "CREATE TABLE IF NOT EXISTS orders (" +
	                    "id INTEGER PRIMARY KEY," +
	                    "customer_name TEXT NOT NULL," +
	                    "item_ids TEXT NOT NULL," +
	                    "status TEXT NOT NULL DEFAULT 'Not started')";
	            statement.executeUpdate(query);

	        } catch (SQLException e) {
	            System.out.println("Error initializing schema: " + e.getMessage());
	        }

	    } catch (SQLException e) {
	        System.out.println("Connection error: " + e.getMessage());
	    }
	}
	
	public static User getUser(String username) {
		
		User user = null;

	    try {
	        String query = "SELECT * FROM users WHERE username = ?";
	        PreparedStatement stmt = connection.prepareStatement(query);
	        stmt.setString(1, username);
	        ResultSet result = stmt.executeQuery();

	        if (result.next()) {
	            user = new User(
	                result.getInt("id"),
	                result.getString("username"),
	                result.getString("role"),
	                result.getString("password")
	            );
	        }

	        result.close();
	        stmt.close();

	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }

	    return user;
		
	}
	
	public static boolean addMenuItem(MenuItem newItem) {
		String query = "INSERT INTO menu_items (name, price, category, imagePath) VALUES (?, ?, ?, ?)";

	    try (PreparedStatement stmt = connection.prepareStatement(query)) {
	        stmt.setString(1, newItem.getItemName());
	        stmt.setDouble(2, newItem.getPrice());
	        stmt.setString(3, newItem.getCategory());
	        stmt.setString(4, newItem.getImagePath());

	        stmt.executeUpdate();
	        return true;

	    } catch (SQLException e) {
	        System.out.println("Error adding menu item: " + e.getMessage());
	        return false;
	    }
	}
	
	public static List<MenuItem> getMenuItemByCategory(String filterQuery) {
		List<MenuItem> listToReturn = new ArrayList<>();

	    try {
	        String query = "SELECT * FROM menu_items WHERE category = ?";
	        PreparedStatement stmt = connection.prepareStatement(query);
	        stmt.setString(1, filterQuery);
	        ResultSet result = stmt.executeQuery();

	        while (result.next()) {
	        	MenuItem item = new MenuItem(result.getInt("id"), result.getString("name"), result.getDouble("price"), result.getString("category"), result.getString("imagePath"));
	        	listToReturn.add(item);
	        }
	        
	        result.close();
	        stmt.close();

	    } catch (SQLException e) {
	        System.out.println(e.getMessage());
	    }

	    return listToReturn;
		
	}
	
	public static boolean addOrder(String customerName, List<Integer> itemIds) {
		if (itemIds == null || itemIds.isEmpty())
			return false;
		String itemIdsString = itemIds.stream().map(String::valueOf).collect(Collectors.joining(","));
		
		String query = "INSERT INTO orders (customer_name, item_ids) VALUES (?, ?)";
		
		try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, customerName);
            stmt.setString(2, itemIdsString);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error adding order: " + e.getMessage());
            return false;
        }
	}
}
