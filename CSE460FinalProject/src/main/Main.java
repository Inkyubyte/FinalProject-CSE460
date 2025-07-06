package main;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	public static User user;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Database.initializeDatabase();
		SceneManager sceneManager = new SceneManager(primaryStage);
		
		sceneManager.addScene("login", new LoginScene(sceneManager));
		sceneManager.addScene("manager", new ManagerScene(sceneManager));
		sceneManager.addScene("operator", new OperatorScene(sceneManager));
		sceneManager.addScene("customer", new CustomerScene(sceneManager));
		
		sceneManager.switchScene("manager");
		
		primaryStage.setTitle("Login to ASU Cafeteria");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
