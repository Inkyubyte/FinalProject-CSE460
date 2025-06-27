package main;

import javafx.scene.Scene;
import javafx.scene.Parent;

public abstract class GenericScene {
	protected Scene scene;
	protected SceneManager sceneManager;
	
	public GenericScene(SceneManager sceneManager, Parent root) {
		this.scene = new Scene(root, 1280, 720);
		this.sceneManager = sceneManager;
		intializeScene(root);
	}
	
	protected abstract void intializeScene(Parent root);
	
	public Scene getScene() {
		return scene;
	}

	protected abstract void refreshTable();
}
