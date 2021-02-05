package br.com.lucasbueno.steampoo2.controllers;

import javax.persistence.EntityManager;

import br.com.lucasbueno.steampoo2.App;
import br.com.lucasbueno.steampoo2.FXMLUtil;
import br.com.lucasbueno.steampoo2.db.UtilDB;
import br.com.lucasbueno.steampoo2.entities.Game;
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
		EntityManager em = UtilDB.getEntityManager();
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
