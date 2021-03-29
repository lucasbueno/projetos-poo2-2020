package br.com.lucasbueno.steampoo2;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	private static Thread connection;

	@Override
	public void start(Stage stage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setTitle("Steam");
			stage.show();
		} catch (IOException e) {
			Alert alert = AlertUtil.error("Erro", "Erro ao carregar um componente",
					"Erro ao tentar carregar a janela de login", e);
			alert.showAndWait();
		}
		
		connection.start();
	}

	public static void setConnection(Thread connection) {
		App.connection = connection;
	}

}