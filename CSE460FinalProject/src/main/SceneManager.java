package main;

import java.util.HashMap;
import java.util.Map;
import javafx.stage.Stage;

public class SceneManager {
	private Stage mainStage;
	private Map<String, GenericScene> sceneMap = new HashMap<>();
	
	public SceneManager(Stage stage) {
		mainStage = stage;
	}
	
	public void addScene(String name, GenericScene scene) {
		sceneMap.put(name, scene);
	}
	
	public void switchScene(String sceneToSwitch) {
		if (sceneMap.containsKey(sceneToSwitch)) {
			GenericScene scene = sceneMap.get(sceneToSwitch);
			scene.refreshView();
			
			mainStage.setScene(sceneMap.get(sceneToSwitch).getScene());
			System.out.println("Scene is set to: " + sceneToSwitch);
			
			
		}
		else {
			System.out.println("Scene to switch to not found in sceneMap: " + sceneToSwitch);
		}
	}
	
	public void refreshTable() {
		System.out.println("Refreshing seller page table");
	}

	
}