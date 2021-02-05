package br.com.lucasbueno.steampoo2.controllers;

import br.com.lucasbueno.steampoo2.App;
import javafx.fxml.FXML;

public class MainController {
	
	@FXML
	private void logout() {
		App.changeResizable();
		App.setRoot("login");
	}

}
