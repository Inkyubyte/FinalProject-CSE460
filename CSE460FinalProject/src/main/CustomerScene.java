package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class CustomerScene extends GenericScene {
	
	
	public CustomerScene(SceneManager sceneManager) {
		super(sceneManager, new BorderPane());
	}

	@Override
	protected void intializeScene(Parent root) {
		BorderPane borderRoot = (BorderPane) root;
		
		Button logoutButton = new Button("Log out");
		
		// Filters column
		VBox filtersBox = new VBox(logoutButton, new Label("Filters"), new Button("Sort by lowest"), new Button("Sort by Highest"));
		filtersBox.setPrefWidth(150);
		filtersBox.setAlignment(Pos.CENTER);
		filtersBox.setPadding(new Insets(30, 10, 10, 10));
		
		// page of 4 items
		GridPane itemGrid = new GridPane();
		itemGrid.setHgap(100);
		itemGrid.setVgap(100);
		itemGrid.setAlignment(Pos.CENTER);
		
		Rectangle image = new Rectangle(200, 200);
		image.setFill(Color.LIGHTGREY);
		image.setStroke(Color.BLACK);
		
		Rectangle image2 = new Rectangle(200, 200);
		image2.setFill(Color.RED);
		image2.setStroke(Color.BLACK);
		
		
		Rectangle image3 = new Rectangle(200, 200);
		image3.setFill(Color.BLUE);
		image3.setStroke(Color.BLACK);
		
		Rectangle image4 = new Rectangle(200, 200);
		image4.setFill(Color.PURPLE);
		image4.setStroke(Color.BLACK);
		

		VBox firstItem = new VBox(5, image, new Label("Pancakes"), new Label("$2.99"));
		firstItem.setAlignment(Pos.CENTER);
		
		VBox secondItem = new VBox(5, image2, new Label("Breakfest Burrito"), new Label("$6.99"));
		secondItem.setAlignment(Pos.CENTER);
		
		VBox thirdItem = new VBox(5, image3, new Label("Belgian Waffles"), new Label("$11.99"));
		thirdItem.setAlignment(Pos.CENTER);
		
		VBox fourthItem = new VBox(5, image4, new Label("Omelette"), new Label("$12.99"));
		fourthItem.setAlignment(Pos.CENTER);
		
		
		itemGrid.add(firstItem, 0, 0);
		itemGrid.add(secondItem, 1, 0);
		itemGrid.add(thirdItem, 0, 1);
		itemGrid.add(fourthItem, 1, 1);
		
		VBox checkoutBox = new VBox(10);
		checkoutBox.setPadding(new Insets(10));
		checkoutBox.setAlignment(Pos.CENTER);
		checkoutBox.setPrefWidth(200);
		checkoutBox.getChildren().addAll(
		    new Label("Checkout"),
		    new Label("Tax:\t\t1.28"),
		    new Label("Total:\t\t17.27"),
		    new Button("Checkout and Pay")
		);
		
		firstItem.setOnMouseClicked(e -> {
			Button removeButton = new Button("X");
			
			HBox cartItem = new HBox(2, new Label("- Pancakes\t 2.99"), removeButton);
			
			removeButton.setOnMouseClicked(event -> {
				checkoutBox.getChildren().remove(cartItem);
			});
			
			checkoutBox.getChildren().add(checkoutBox.getChildren().size() - 3, cartItem);
		});
		
		secondItem.setOnMouseClicked(e -> {
			Button removeButton = new Button("X");
			
			HBox cartItem = new HBox(2, new Label("- Breakfest Burrito\t 6.99"), removeButton);
			
			removeButton.setOnMouseClicked(event -> {
				checkoutBox.getChildren().remove(cartItem);
			});
			
			checkoutBox.getChildren().add(checkoutBox.getChildren().size() - 3, cartItem);
			
		});
		
		
		thirdItem.setOnMouseClicked(e -> {
			Button removeButton = new Button("X");
			
			HBox cartItem = new HBox(2, new Label("- Belgian Waffles\t 11.99"), removeButton);
			
			removeButton.setOnMouseClicked(event -> {
				checkoutBox.getChildren().remove(cartItem);
			});
			
			checkoutBox.getChildren().add(checkoutBox.getChildren().size() - 3, cartItem);
		});
		
		
		fourthItem.setOnMouseClicked(e -> {
			Button removeButton = new Button("X");
			
			HBox cartItem = new HBox(2, new Label("- Omelette\t 12.99"), removeButton);
			
			removeButton.setOnMouseClicked(event -> {
				checkoutBox.getChildren().remove(cartItem);
			});
			
			checkoutBox.getChildren().add(checkoutBox.getChildren().size() - 3, cartItem);
		});
		
		logoutButton.setOnAction(e -> {
        	sceneManager.switchScene("login");
        	Main.user = null;
        });
		
		
		borderRoot.setLeft(filtersBox);
		borderRoot.setCenter(itemGrid);
		borderRoot.setRight(checkoutBox);
	}
}
