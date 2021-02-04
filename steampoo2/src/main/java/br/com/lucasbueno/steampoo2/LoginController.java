package br.com.lucasbueno.steampoo2;

import javax.persistence.EntityManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private Button btnLogin;

	@FXML
	private void login() {
		App.changeResizable();
		App.setRoot("main");
		Game g = new Game("primeiro jogo");
		EntityManager em = ConnDB.getEntityManager();
		em.getTransaction().begin();
		em.persist(g);
		em.getTransaction().commit();
	}

	@FXML
	private void register() {
		Stage stage = new Stage();
		stage.setScene(FXMLUtil.loadScene("register"));
		stage.setResizable(false);
		stage.show();
	}
}
