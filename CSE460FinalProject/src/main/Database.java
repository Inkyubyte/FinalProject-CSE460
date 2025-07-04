package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	            	    "(1, 'Pancakes', 5.99, 'Breakfast', 'images/pancakes.png')," +
	            	    "(2, 'Eggs Benedict', 6.49, 'Breakfast', 'images/eggs_benedict.png')," +
	            	    "(3, 'Omelette', 7.25, 'Breakfast', 'images/omelette.png')," +
	            	    "(4, 'French Toast', 7.25, 'Breakfast', 'images/french_toast.png')," +
	            	    "(5, 'Breakfast Burrito', 7.25, 'Breakfast', 'images/breakfast_burrito.png')," +
	            	    "(6, 'Bagel with Lox', 7.25, 'Breakfast', 'images/bagel_lox.png')," +
	            	    "(7, 'Biscuits and Gravy', 7.25, 'Breakfast', 'images/biscuits_gravy.png')," +
	            	    "(8, 'Belgian Waffles', 7.25, 'Breakfast', 'images/belgian_waffles.png')," +
	            	    "(9, 'Breakfast Sandwich', 7.25, 'Breakfast', 'images/breakfast_sandwich.png')," +
	            	    "(10, 'Greek Yogurt Parait', 7.25, 'Breakfast', 'images/yogurt_parfait.png')," +
	            	    "(11, 'Cheeseburger', 9.99, 'Lunch', 'images/cheeseburger.png')," +
	            	    "(12, 'Club Sandwich', 8.99, 'Lunch', 'images/club_sandwich.png')," +
	            	    "(13, 'Caesar Salad', 7.99, 'Lunch', 'images/caesar_salad.png')," +
	            	    "(14, 'BBQ Ribs', 12.99, 'Lunch', 'images/bbq_ribs.png')," +
	            	    "(15, 'Philly Cheesesteak', 10.49, 'Lunch', 'images/philly_cheesesteak.png')," +
	            	    "(16, 'New England Clam Chowder', 6.99, 'Lunch', 'images/clam_chowder.png')," +
	            	    "(17, 'Cobb Salad', 9.49, 'Lunch', 'images/cobb_salad.png')," +
	            	    "(18, 'Pulled Pork Sandwich', 8.99, 'Lunch', 'images/pulled_pork.png')," +
	            	    "(19, 'Fish Tacos', 9.25, 'Lunch', 'images/fish_tacos.png')," +
	            	    "(20, 'Shrimp Scampi', 14.99, 'Dinner', 'images/shrimp_scampi.png')," +
	            	    "(21, 'BBQ Brisket', 13.99, 'Dinner', 'images/bbq_brisket.png')," +
	            	    "(22, 'Vegetarian Lasagna', 11.49, 'Dinner', 'images/vegetarian_lasagna.png')," +
	            	    "(23, 'Stuffed Bell Peppers', 10.99, 'Dinner', 'images/stuffed_peppers.png')," +
	            	    "(24, 'Lobster Tail Dinner', 19.99, 'Dinner', 'images/lobster_tail.png')," +
	            	    "(25, 'Taco Night', 12.49, 'Dinner', 'images/taco_night.png')," +
	            	    "(26, 'Buffalo Wings', 9.99, 'Dinner', 'images/buffalo_wings.png')," +
	            	    "(27, 'Steakhouse Dinner', 18.99, 'Dinner', 'images/steakhouse_dinner.png')," +
	            	    "(28, 'Grilled Salmon', 16.99, 'Dinner', 'images/grilled_salmon.png')," +
	            	    "(29, 'Roast Chicken', 13.49, 'Dinner', 'images/roast_chicken.png')," +
	            	    "(30, 'Spaghetti and Meatballs', 11.99, 'Dinner', 'images/spaghetti_meatballs.png')," +
	            	    "(31, 'Coffee', 2.49, 'Beverage', 'images/coffee.png')," +
	            	    "(32, 'Iced Tea', 2.25, 'Beverage', 'images/iced_tea.png')," +
	            	    "(33, 'Soda (Soft Drinks)', 2.00, 'Beverage', 'images/soda.png')," +
	            	    "(34, 'Craft Beer', 5.49, 'Beverage', 'images/craft_beer.png')," +
	            	    "(35, 'Bottled Water', 1.50, 'Beverage', 'images/bottled_water.png');";
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
	
}
