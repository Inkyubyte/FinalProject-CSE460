package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OperatorScene extends GenericScene {
	
	private VBox orderDetailsPanel;
	private VBox itemListBox;
	private Label orderNumberLabel;
	private ComboBox<String> statusDropDown;
	
	public OperatorScene(SceneManager sceneManager) {
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
		Label welcomeLabel = new Label("Welcome, operator");
		Button logoutButton = new Button("Log out");
		topBanner.getChildren().addAll(logoutButton, welcomeLabel);
			
		borderRoot.setTop(topBanner);
		
		
		GridPane orderViewPanel = new GridPane();
		orderViewPanel.setHgap(20);
		orderViewPanel.setVgap(20);
		orderViewPanel.setAlignment(Pos.CENTER_LEFT);
		orderViewPanel.setPadding(new Insets(0, 0, 0, 150)); // Offset to the left
		
		
		// Replace with a for each loop targeting all orders in a list of order got from the Database later.
		for (int i = 0; i < 10; i++) {
			VBox orderCard = createOrderCard(i + 1);
			
			int col = i % 3;
			int row = i / 3;
			orderViewPanel.add(orderCard, col, row);
		}
		
		borderRoot.setCenter(orderViewPanel);
		
		// Making the right panel for order details
		orderDetailsPanel = new VBox(10);
		orderDetailsPanel.setPrefWidth(250);
		
		orderNumberLabel = new Label("Select an order");
		itemListBox = new VBox(10);
		statusDropDown = new ComboBox<String>();
		statusDropDown.getItems().addAll("Not Started", "In Progress", "Ready", "Completed");
		statusDropDown.prefWidth(200);
		
		orderDetailsPanel.getChildren().addAll(orderNumberLabel, itemListBox, statusDropDown);
		
		borderRoot.setRight(orderDetailsPanel);
		
		logoutButton.setOnAction(e -> {
        	sceneManager.switchScene("login");
        	Main.user = null;
        });
	}

	private VBox createOrderCard(int orderID) {
			VBox orderCard = new VBox();
			orderCard.setPrefSize(120, 120);
			orderCard.setStyle("-fx-border-color: black");
			orderCard.setAlignment(Pos.CENTER);
			
			// Label orderID = new Label(order.getOrderID());
			// Label orderItems = order.getItems().getName();
			
			orderCard.setOnMouseClicked(e -> displayOrderDetails(orderID));
			
			orderCard.getChildren().addAll(new Label("Order " + orderID), new Label("In Progress"));
			return orderCard;
		}

	private void displayOrderDetails(int orderID) {
		orderNumberLabel.setText("Order #"+ orderID);
		itemListBox.getChildren().clear();
		itemListBox.getChildren().addAll(new Label("- Pancakes"), new Label("- Coffee"), new Label("Eggs benedict"));
		statusDropDown.setValue("Not Started");
	}
}
