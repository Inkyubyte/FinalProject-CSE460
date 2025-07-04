package main;

import java.util.Arrays;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ManagerScene extends GenericScene {

	public ManagerScene(SceneManager sceneManager) {
		super(sceneManager, new BorderPane());
	}

	@Override
	protected void intializeScene(Parent root) {
		BorderPane borderRoot = (BorderPane) root;
		
		// Top bar for logging out
		HBox topBanner = new HBox(10);
		topBanner.setPadding(new Insets(10));
		topBanner.setAlignment(Pos.CENTER_LEFT);
		// Label welcomeLabel = new Label("Welcome, " + user.getName());
		Label welcomeLabel = new Label("Welcome, admin");
		Button logoutButton = new Button("Log out");
		topBanner.getChildren().addAll(logoutButton, welcomeLabel);
	
		borderRoot.setTop(topBanner);
		
		
		// Side bar
		VBox sidebar = new VBox(20);
		sidebar.setPadding(new Insets(10));
		sidebar.setPrefWidth(150);
		sidebar.getChildren().addAll(new Button("Manage Menu"), new Button("View Orders"));
		
		borderRoot.setLeft(sidebar);
		
		
		TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        Tab breakfastTab = new Tab("Breakfast", createCategoryPane("Breakfast"));
        Tab lunchTab = new Tab("Lunch", createCategoryPane("Lunch"));
        Tab dinnerTab = new Tab("Dinner", createCategoryPane("Dinner"));

        tabPane.getTabs().addAll(breakfastTab, lunchTab, dinnerTab);
        
        borderRoot.setCenter(tabPane);
		
		
		logoutButton.setOnAction(e -> {
        	sceneManager.switchScene("login");
        	Main.user = null;
        });
	
	}
	
	private VBox createCategoryPane(String category) {
	    GridPane itemGrid = new GridPane();
	    itemGrid.setHgap(20);
	    itemGrid.setVgap(20);
	    itemGrid.setPadding(new Insets(10));
	    
	    List<String> items = null;
	    
	    if (category == "Breakfast") 
	    	items = Arrays.asList("Pancakes", "Item 2", "Item 3", "Pancakes", "Item 2", "Item 3", "Pancakes", "Item 2", "Item 3", "Pancakes", "Item 2", "Item 3");
	    else if (category == "Dinner")
	    	items = Arrays.asList("Steak", "Item 2", "Item 3");
	    else if (category == "Lunch")
	    	items = Arrays.asList("Sandwich", "Item 2", "Item 3");
	    else
	    	items = Arrays.asList("Item 1", "Item 2", "Item 3");

	    int col = 0, row = 0;
	    
	    for (String item : items) {
	    	// VBox itemCard = createItemCard(item.getName(), item.getPrice());
	        VBox itemCard = createItemCard(item, 9.99);
	        itemGrid.add(itemCard, col++, row);
	        if (col == 6) {
	            col = 0;
	            row++;
	        }
	    }

	    Button addNew = new Button("+ Add New");
	    addNew.setOnAction(e -> {
	    	System.out.println("ADDED NEW ITEM");
	    });

	    VBox pane = new VBox(20, itemGrid, addNew);
	    pane.setPadding(new Insets(10));
	    return pane;
	}
	
	private VBox createItemCard(String name, double price) {
	    Label nameLabel = new Label(name);
	    Label priceLabel = new Label(String.format("$%.2f", price));
	    Button editButton = new Button("Edit");
	    Button deleteButton = new Button("Delete");

	    HBox buttonRow = new HBox(10, editButton, deleteButton);
	    buttonRow.setAlignment(Pos.CENTER);

	    VBox box = new VBox(10, nameLabel, priceLabel, buttonRow);
	    box.setPadding(new Insets(10));
	    box.setAlignment(Pos.CENTER);
	    box.setStyle("-fx-border-color: black; -fx-border-radius: 5px;");
	    box.setPrefWidth(150);
	    
	    deleteButton.setOnAction(e -> {
	    	Pane parentPane = (Pane) box.getParent();
	    	parentPane.getChildren().remove(box);
	    });

	    return box;
	}
	
	 private void showItemEditor(String category, String itemToEdit) {
	        // Open a dialog or side panel for editing or adding a new item
	        System.out.println("Open editor for: " + (itemToEdit == null ? "New Item" : itemToEdit));
	    }
}
