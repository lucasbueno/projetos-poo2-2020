package br.com.lucasbueno.steampoo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

	private static Stage stage;

	@Override
	public void start(Stage stage) {
		this.stage = stage;
		stage.setScene(FXMLUtil.loadScene("login"));
		this.changeResizable();
		stage.show();
	}

	public static void setRoot(String fxml) {
		stage.setScene(FXMLUtil.loadScene(fxml));
	}

	public static void main(String[] args) {
		launch();
	}
	
	public static void changeResizable() {
		if(stage.isResizable())
			stage.setResizable(false);
		else
			stage.setResizable(true);
	}

}