package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManagerScene extends GenericScene {

	private TabPane tabPane;
	private Tab breakfastTab;
    private Tab lunchTab;
    private Tab dinnerTab;
    private Tab beverageTab;
	
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
		
		
		tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        
        breakfastTab = new Tab("Breakfast", createCategoryPane("Breakfast"));
        lunchTab = new Tab("Lunch", createCategoryPane("Lunch"));
        dinnerTab = new Tab("Dinner", createCategoryPane("Dinner"));
        beverageTab = new Tab("Beverages", createCategoryPane("Beverage"));

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
	    
	    List<MenuItem> items = null;
	    
	    items = Database.getMenuItemByCategory(category);

	    int col = 0, row = 0;
	    
	    for (MenuItem item : items) {
	    	// VBox itemCard = createItemCard(item.getName(), item.getPrice());
	        VBox itemCard = createItemCard(item);
	        itemGrid.add(itemCard, col++, row);
	        if (col == 6) {
	            col = 0;
	            row++;
	        }
	    }

	    Button addNew = new Button("+ Add New");
	    addNew.setOnAction(e -> {
	    	showAddItemPopup();
	    });

	    if (col == 6)
	    	itemGrid.add(addNew, 0, row++);
	    else
	    	itemGrid.add(addNew, col, row);
	    
	    VBox pane = new VBox(20, itemGrid);
	    pane.setPadding(new Insets(10));
	    return pane;
	}
	
	private void showAddItemPopup() {
		Stage popup = new Stage();
		popup.setTitle("Add New Menu Item");
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20));
		
		TextField nameField = new TextField();
		nameField.setPromptText("Item Name");
		
		TextField priceField = new TextField();
		priceField.setPromptText("Price");
		
		ComboBox<String> categoryBox = new ComboBox<>();
	    categoryBox.getItems().addAll("Breakfast", "Lunch", "Dinner", "Beverage");
		
	    ImageView imageView = new ImageView();
	    imageView.setFitWidth(100);
	    imageView.setPreserveRatio(true);

	    Button chooseImage = new Button("Select Image");
	    final String[] imagePath = {null};
	    
	    chooseImage.setOnAction(e -> {
	    	// Get file chooser
	    	FileChooser fileChooser = new FileChooser();
	    	fileChooser.setTitle("Select Product Image");
	    	fileChooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
	    	
	    	File sourceFile = fileChooser.showOpenDialog(popup);
	    	
	    	if (sourceFile != null) {
	    		try {
	    			File imageDir = new File("images");
	    			if (!imageDir.exists())
	    				imageDir.mkdirs();
	    			
	    			File destFile = new File(imageDir, sourceFile.getName());
	    			
	    			Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
	    			
	    			imagePath[0] = "images/" + sourceFile.getName();
	    			
	    			imageView.setImage(new Image(destFile.toURI().toString()));
	    			
	    		} catch (IOException ex) {
	    			ex.printStackTrace();	    		
	    		}
	    	}
	    	
	    	});
	    
	    Button saveButton = new Button("Save");
	    saveButton.setOnAction(e -> {
	        try {
	        	// Get values that manager entered
	            String name = nameField.getText();
	            double price = Double.parseDouble(priceField.getText());
	            String category = categoryBox.getValue();

	            if (name.isEmpty() || category == null || imagePath[0] == null) {
	                System.out.println("Missing fields!");
	                return;
	            }

	            MenuItem newItem = new MenuItem(0, name, price, category, imagePath[0]);
	            if (Database.addMenuItem(newItem)) {
	                System.out.println("Item added.");
	                popup.close();
	                refreshView();
	            }
	        } catch (NumberFormatException ex) {
	            System.out.println("Invalid price.");
	        }
	    });
	    
	    layout.getChildren().addAll(
	            new Label("Name:"), nameField,
	            new Label("Price:"), priceField,
	            new Label("Category:"), categoryBox,
	            chooseImage, imageView,
	            saveButton
	        );

	    Scene scene = new Scene(layout, 300, 450);
	    popup.setScene(scene);
	    popup.initModality(Modality.APPLICATION_MODAL);
	    popup.showAndWait();
	    
	}

	private VBox createItemCard(MenuItem item) {
	    Label nameLabel = new Label(item.getItemName());
	    Label priceLabel = new Label(String.format("$%.2f", item.getPrice()));
	    Button editButton = new Button("Edit");
	    Button deleteButton = new Button("Delete");

	    HBox buttonRow = new HBox(10, editButton, deleteButton);
	    buttonRow.setAlignment(Pos.CENTER);
	    
	    ImageView imageView = new ImageView();
	    File file = new File(item.getImagePath());
	    if (file.exists()) {
	        imageView.setImage(new Image(file.toURI().toString()));
	    }
	    imageView.setFitWidth(100);
	    imageView.setFitHeight(100);
	    imageView.setPreserveRatio(true);

	    VBox box = new VBox(10, nameLabel, imageView, priceLabel, buttonRow);
	    box.setPadding(new Insets(10));
	    box.setAlignment(Pos.CENTER);
	    box.setStyle("-fx-border-color: black; -fx-border-radius: 5px;");
	    box.setPrefWidth(150);
	    
	    deleteButton.setOnAction(e -> {
	    	Pane parentPane = (Pane) box.getParent();
	    	parentPane.getChildren().remove(box);
	    });
	    
	    editButton.setOnAction(e -> {
	    	showItemEditor(item);
	    	refreshView();
	    });

	    return box;
	}
	
	 private void showItemEditor(MenuItem item) {
	 
		 Stage popup = new Stage();
			popup.setTitle("Edit Menu Item");
			
			VBox layout = new VBox(10);
			layout.setPadding(new Insets(20));
			
			TextField nameField = new TextField();
			nameField.setPromptText("Item Name");
			nameField.setText(item.getItemName());
			
			TextField priceField = new TextField();
			priceField.setPromptText("Price");
			priceField.setText(Double.toString(item.getPrice()));
			
			ComboBox<String> categoryBox = new ComboBox<>();
		    categoryBox.getItems().addAll("Breakfast", "Lunch", "Dinner", "Beverage");
		    categoryBox.getSelectionModel().select(item.getCategory());
			
		    ImageView imageView = new ImageView();
		    File file = new File(item.getImagePath());
		    if (file.exists()) {
		        imageView.setImage(new Image(file.toURI().toString()));
		    }
		    imageView.setFitWidth(100);
		    imageView.setFitHeight(100);
		    imageView.setPreserveRatio(true);


		    Button chooseImage = new Button("Select Image");
		    String[] imagePath = {item.getImagePath()};
		    
		    chooseImage.setOnAction(e -> {
		    	// Get file chooser
		    	FileChooser fileChooser = new FileChooser();
		    	fileChooser.setTitle("Select Product Image");
		    	fileChooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
		    	
		    	File sourceFile = fileChooser.showOpenDialog(popup);
		    	
		    	if (sourceFile != null) {
		    		try {
		    			File imageDir = new File("images");
		    			if (!imageDir.exists())
		    				imageDir.mkdirs();
		    			
		    			File destFile = new File(imageDir, sourceFile.getName());
		    			
		    			Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		    			
		    			imagePath[0] = "images/" + sourceFile.getName();
		    			
		    			imageView.setImage(new Image(destFile.toURI().toString()));
		    			
		    		} catch (IOException ex) {
		    			ex.printStackTrace();	    		
		    		}
		    	}
		    	
		    	});
		    
		    Button saveButton = new Button("Save");
		    saveButton.setOnAction(e -> {
		        try {
		        	// Get values that manager entered
		            String name = nameField.getText();
		            double price = Double.parseDouble(priceField.getText());
		            String category = categoryBox.getValue();

		            if (name.isEmpty() || category == null || imagePath[0] == null) {
		                System.out.println("Missing fields!");
		                return;
		            }

		            MenuItem newItem = new MenuItem(item.getItemID(), name, price, category, imagePath[0]);
		            if (Database.editMenuItem(newItem)) {
		                System.out.println("Item edited.");
		                popup.close();
		                refreshView();
		            }
		        } catch (NumberFormatException ex) {
		            System.out.println("Invalid price.");
		        }
		    });
		    
		    layout.getChildren().addAll(
		            new Label("Name:"), nameField,
		            new Label("Price:"), priceField,
		            new Label("Category:"), categoryBox,
		            chooseImage, imageView,
		            saveButton
		        );

		    Scene scene = new Scene(layout, 300, 450);
		    popup.setScene(scene);
		    popup.initModality(Modality.APPLICATION_MODAL);
		    popup.showAndWait();
		 
	 }
	 

	 @Override
	 protected void refreshView() {
		 Tab selected = tabPane.getSelectionModel().getSelectedItem();
		 
		 tabPane.getTabs().clear();
		 
		 breakfastTab = new Tab("Breakfast", createCategoryPane("Breakfast"));
	     lunchTab = new Tab("Lunch", createCategoryPane("Lunch"));
	     dinnerTab = new Tab("Dinner", createCategoryPane("Dinner"));
	     beverageTab = new Tab("Beverages", createCategoryPane("Beverage"));
	     
	     tabPane.getTabs().addAll(breakfastTab, lunchTab, dinnerTab, beverageTab);
	     
	     
	     for (Tab t : tabPane.getTabs()) {
	    	 if (t.getText().equals(selected.getText())) {
	    		 tabPane.getSelectionModel().select(t);
	             break;
	            }
	     }
		
	 }
}
