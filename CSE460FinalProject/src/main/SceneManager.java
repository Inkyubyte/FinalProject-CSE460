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
			mainStage.setScene(sceneMap.get(sceneToSwitch).getScene());
			mainStage.setScene(sceneMap.get(sceneToSwitch).getScene());
			System.out.println("Scene is set to: " + sceneToSwitch);
			if (sceneToSwitch.equals("seller")) {
				GenericScene scene = sceneMap.get(sceneToSwitch);
				
				scene.refreshTable();
			}
			
			if (sceneToSwitch.equals("buyer")) {
				GenericScene scene = sceneMap.get(sceneToSwitch);
				
				scene.refreshTable();
			}
			
		}
		else {
			System.out.println("Scene to switch to not found in sceneMap: " + sceneToSwitch);
		}
	}
	
	public void refreshTable() {
		System.out.println("Refreshing seller page table");
	}

	
}