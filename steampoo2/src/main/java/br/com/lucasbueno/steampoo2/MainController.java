package br.com.lucasbueno.steampoo2;

import javafx.fxml.FXML;

public class MainController {
	
	@FXML
	private void logout() {
		App.changeResizable();
		App.setRoot("login");
	}

}
