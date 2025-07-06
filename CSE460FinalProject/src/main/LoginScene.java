package main;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LoginScene extends GenericScene {
	
	
	public LoginScene(SceneManager sceneManager) {
		super(sceneManager, new BorderPane());
	}
	
	
	@Override
	protected void intializeScene(Parent root) {
		BorderPane borderRoot = (BorderPane) root;
		
		Label titleLabel = new Label("ASU Cafeteria");
        TextField userField = new TextField();
        TextField passwordField = new TextField();
        Button loginButton = new Button("Login");
        Label responseLabel = new Label();

        userField.setPromptText("Enter Username...");
        passwordField.setPromptText("Enter password...");
        userField.setMaxWidth(200);
        passwordField.setMaxWidth(200);
        titleLabel.setStyle("-fx-font: 72 arial;");
        responseLabel.setTextFill(Color.color(1, 0, 0));
        
        VBox centerLayout = new VBox(15);
        centerLayout.setStyle("-fx-alignment: center;");
        
        centerLayout.getChildren().addAll(titleLabel, userField, passwordField, loginButton, responseLabel);
        
        borderRoot.setCenter(centerLayout);
        
        
        loginButton.setOnAction(e -> {
        	String username;
        	
        	username = userField.getText();

            String userRole = verifyInfo(username, passwordField.getText());

        	
            userField.setText("");
            passwordField.setText("");
            responseLabel.setText("");
            
            if (userRole != null) {
                // User is authenticated, switch scenes based on role
                switch (userRole.toLowerCase()) {
                    case "customer":
                        sceneManager.switchScene("customer");
                        
                        break;
                    case "operator":
                        sceneManager.switchScene("operator");
                        
                        break;
                    case "manager":
                        sceneManager.switchScene("manager");
                        
                        break;
                    default:
                        System.out.println("Unknown role: " + userRole);
                }
            } else {
            	responseLabel.setText("Invalid ID or password");
            }
        });
        
	}


	private String verifyInfo(String username, String password) {
		User user = Database.getUser(username);
		
		if (user == null) {
			System.out.println("Incorrect Username");
			return null;
		}
		
		if (!user.getPassword().equals(password)) {
			System.out.println("Incorrect Password");
			return null;
		}
		else {
			System.out.println("Correct Password");
		}
		
		Main.user = user;
		
		return user.getRole();
	}


	@Override
	protected void refreshView() {
	}
}