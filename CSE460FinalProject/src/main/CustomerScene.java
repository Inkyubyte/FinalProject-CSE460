package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CustomerScene extends GenericScene {
	
	private List<MenuItem> items;
	private List<Integer> cartItemIds = new ArrayList<>();
	private GridPane itemGrid;
	private int currentPage = 0;
	private final int itemsPerPage = 4;
	private VBox checkoutBox;
	
	public CustomerScene(SceneManager sceneManager) {
		super(sceneManager, new BorderPane());
	}

	@Override
	protected void intializeScene(Parent root) {
		BorderPane borderRoot = (BorderPane) root;
		
		Button logoutButton = new Button("Log out");
		Button buyButton = new Button("Buy");
		Button orderHistoryButton = new Button("Order History");
		
		
		BorderPane.setAlignment(logoutButton, Pos.TOP_RIGHT);
		
		VBox categorySelectionBox = new VBox(10);
		categorySelectionBox.setAlignment(Pos.CENTER);
		categorySelectionBox.setVisible(false);
		
		
		Button breakfastButton = new Button("Breakfast");
        Button lunchButton = new Button("Lunch");
        Button dinnerButton = new Button("Dinner");
        Button beveragesButton = new Button("Beverages");

        categorySelectionBox.getChildren().addAll(breakfastButton, lunchButton, dinnerButton, beveragesButton);
		
        
        VBox sidebar = new VBox(3, buyButton, orderHistoryButton);
        sidebar.setPadding(new Insets(20));
        sidebar.setAlignment(Pos.CENTER);
        sidebar.setPrefWidth(150);
		
		// page of 4 items
		itemGrid = new GridPane();
		itemGrid.setHgap(100);
		itemGrid.setVgap(100);
		itemGrid.setAlignment(Pos.CENTER);
		itemGrid.setVisible(false);		
		
		
		Button checkoutButton = new Button("Checkout and Pay");
		
		checkoutBox = new VBox(10);
		checkoutBox.setPadding(new Insets(10));
		checkoutBox.setAlignment(Pos.CENTER);
		checkoutBox.setPrefWidth(200);
		checkoutBox.getChildren().addAll(
		    new Label("Checkout"),
		    new Label("Tax:\t\t0.00"),
		    new Label("Total:\t\t0.00"),
		    checkoutButton
		);
		checkoutBox.setVisible(false);

		
		logoutButton.setOnAction(e -> {
        	sceneManager.switchScene("login");
        	Main.user = null;
        });
		
		
		 buyButton.setOnAction(e -> {
			 // Set things to unmanaged because VBox still calculates itemGrid unless you specify it not to
			 itemGrid.setVisible(false);
			 itemGrid.setManaged(false);
			 checkoutBox.setVisible(false);
			 checkoutBox.setManaged(false);

			 categorySelectionBox.setVisible(true);
			 categorySelectionBox.setManaged(true);
	     });
		
		breakfastButton.setOnAction(e -> {
			System.out.println("Pressed breakfast button");
			categorySelectionBox.setVisible(!categorySelectionBox.isVisible());
			itemGrid.setVisible(true);	
			checkoutBox.setVisible(true);
			loadItemsByCategory("Breakfast");
		 });
		 
		lunchButton.setOnAction(e -> {
			System.out.println("Pressed lunch button");
			categorySelectionBox.setVisible(!categorySelectionBox.isVisible());
			itemGrid.setVisible(true);	
			checkoutBox.setVisible(true);
			loadItemsByCategory("Lunch");
		 });
		 
		 dinnerButton.setOnAction(e -> {
			 System.out.println("Pressed dinner button");
			 categorySelectionBox.setVisible(!categorySelectionBox.isVisible());
			 itemGrid.setVisible(true);	
			 checkoutBox.setVisible(true);
			 loadItemsByCategory("Dinner");
		 });
		 
		 beveragesButton.setOnAction(e -> {
			 System.out.println("Pressed breakfast button");
			 categorySelectionBox.setVisible(!categorySelectionBox.isVisible());
			 itemGrid.setVisible(true);	
			 checkoutBox.setVisible(true);
			 loadItemsByCategory("Beverage");
		 });
		 
		 checkoutButton.setOnAction(e -> {
				if (!cartItemIds.isEmpty()) {
					if (Database.addOrder(Main.user.getID(), Main.user.getUsername(), cartItemIds)) {
						System.out.println("Order added successfully");
					}
					else {
						System.out.println("Failed");
					}
					checkoutBox.getChildren().removeIf(node -> node instanceof HBox);
					cartItemIds.clear();
				}
				else {
					System.out.println("Cart empty");
				}
			});
		 
		 VBox centerBox = new VBox(3);
		 centerBox.getChildren().addAll(itemGrid, categorySelectionBox);
		 VBox centerWrapper = new VBox(centerBox);
		 centerWrapper.setAlignment(Pos.CENTER);

		 borderRoot.setTop(logoutButton);
		 borderRoot.setLeft(sidebar);
		 borderRoot.setCenter(centerWrapper);
		 borderRoot.setRight(checkoutBox);
	}
	
	private void loadItemsByCategory(String category) {
		currentPage = 0;
		items = Database.getMenuItemByCategory(category);
		updateMenuItems();
		
	    itemGrid.setVisible(true);
	    itemGrid.setManaged(true);
	    
	    Button checkoutButton = new Button("Checkout and Pay");
	    
	    checkoutBox.getChildren().clear();
	    checkoutBox.getChildren().addAll(
			    new Label("Checkout"),
			    new Label("Tax:\t\t0.00"),
			    new Label("Total:\t\t0.00"),
			    checkoutButton
			);
	    
	    checkoutButton.setOnAction(e -> {
			if (!cartItemIds.isEmpty()) {
				if (Database.addOrder(Main.user.getID(), Main.user.getUsername(), cartItemIds)) {
					System.out.println("Order added successfully");
				}
				else {
					System.out.println("Failed");
				}
				checkoutBox.getChildren().removeIf(node -> node instanceof HBox);
				cartItemIds.clear();
			}
			else {
				System.out.println("Cart empty");
			}
		});
	    
	    checkoutBox.setVisible(true);
	    checkoutBox.setManaged(true);
	}

	private void updateMenuItems() {
		System.out.println(items);
		
		itemGrid.getChildren().clear();
		
		int start = currentPage * itemsPerPage;
		int end = Math.min(start + itemsPerPage, items.size());
		
		int col = 0, row = 0;
		for(int i = start; i < end; i++) {
			VBox itemBox = createItemBox(items.get(i));
	        itemGrid.add(itemBox, col++, row);
	        if (col == 2) {
	            col = 0;
	            row++;
	        }
		}
		
		Button prevButton = new Button("Previous");
	    Button nextButton = new Button("Next");

	    prevButton.setOnAction(e -> {
	        if (currentPage > 0) {
	            currentPage--;
	            updateMenuItems();
	        }
	    });

	    nextButton.setOnAction(e -> {
	        if ((currentPage + 1) * itemsPerPage < items.size()) {
	            currentPage++;
	            updateMenuItems();
	        }
	    });

	    HBox navBox = new HBox(10, prevButton, nextButton);
	    navBox.setAlignment(Pos.CENTER);
	    itemGrid.add(navBox, 0, 2, 2, 1); // span 2 columns

	    itemGrid.setVisible(true);
		
	}

	private VBox createItemBox(MenuItem menuItem) {
		ImageView imageView = new ImageView();
		File file = new File(menuItem.getImagePath());
		if (file.exists()) {
            imageView.setImage(new Image(file.toURI().toString()));
        }
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
		
        
        Label nameLabel = new Label(menuItem.getItemName());
        Label priceLabel = new Label("$" + String.format("%.2f", menuItem.getPrice()));

        VBox itemBox = new VBox(5, imageView, nameLabel, priceLabel);
        itemBox.setAlignment(Pos.CENTER);

        itemBox.setOnMouseClicked(e -> {
            cartItemIds.add(menuItem.getItemID());
        	Button removeButton = new Button("X");
            
            
            HBox cartItem = new HBox(2, new Label("- " + menuItem.getItemName() + "\t " + menuItem.getPrice()), removeButton);
            removeButton.setOnMouseClicked(event -> {
            	checkoutBox.getChildren().remove(cartItem);
            	cartItemIds.remove((Integer) menuItem.getItemID());
            });
            checkoutBox.getChildren().add(checkoutBox.getChildren().size() - 3, cartItem);
        });

        
		return itemBox;
	}

	@Override
	protected void refreshView() {
	}
}
