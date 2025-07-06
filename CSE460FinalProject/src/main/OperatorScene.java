package main;

import java.util.List;

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
	private List<Order> ordersToDisplay;
	private GridPane orderViewPanel;
	
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
		
		
		orderViewPanel = new GridPane();
		orderViewPanel.setHgap(20);
		orderViewPanel.setVgap(20);
		orderViewPanel.setAlignment(Pos.CENTER_LEFT);
		orderViewPanel.setPadding(new Insets(0, 0, 0, 150)); // Offset to the left
		
		ordersToDisplay = Database.getOrdersByStatus();
		
		
		int i = 0;
		
		for (Order order : ordersToDisplay) {
			VBox orderCard = createOrderCard(order);
			
			int col = i % 3;
			int row = i / 3;
			orderViewPanel.add(orderCard, col, row);
			i++;
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

	private VBox createOrderCard(Order order) {
			VBox orderCard = new VBox();
			orderCard.setPrefSize(120, 120);
			orderCard.setStyle("-fx-border-color: black");
			orderCard.setAlignment(Pos.CENTER);
			
			// Label orderID = new Label(order.getOrderID());
			// Label orderItems = order.getItems().getName();
			
			orderCard.setOnMouseClicked(e -> displayOrderDetails(order));
			
			orderCard.getChildren().addAll(new Label("Order " + order.getOrderID()), new Label("Not Started"));
			return orderCard;
		}

	private void displayOrderDetails(Order order) {
		orderNumberLabel.setText("Order #"+ order.getOrderID() + " - " + order.getName());
		itemListBox.getChildren().clear();
		
		for (MenuItem item : order.getOrderItems())
			itemListBox.getChildren().addAll(new Label("- " + item.getItemName()));		
		statusDropDown.setValue(order.getStatus());
	}
	
	@Override
	protected void refreshView() {
		ordersToDisplay = Database.getOrdersByStatus();
		
		System.out.println("Orders to display: " + ordersToDisplay);
		
		
		int i = 0;
		
		for (Order order : ordersToDisplay) {
			VBox orderCard = createOrderCard(order);
			
			int col = i % 3;
			int row = i / 3;
			orderViewPanel.add(orderCard, col, row);
			i++;
		}
		
	}
}
