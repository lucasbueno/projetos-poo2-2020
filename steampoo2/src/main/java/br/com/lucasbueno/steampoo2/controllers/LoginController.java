package br.com.lucasbueno.steampoo2.controllers;

import br.com.lucasbueno.steampoo2.App;

import org.controlsfx.control.ToggleSwitch;

import br.com.lucasbueno.steampoo2.AlertUtil;
import br.com.lucasbueno.steampoo2.FXMLUtil;
import br.com.lucasbueno.steampoo2.db.UserDAO;
import br.com.lucasbueno.steampoo2.entities.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	private Button btnLogin;

	@FXML
	private TextField txtEmail;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private ToggleSwitch togglSaveLogin;
	
	@FXML
	private ToggleSwitch togglFlatbee;
	
	@FXML
	private void login() {
		String email = txtEmail.getText();
		String password = txtPassword.getText();

		if (email.isBlank()) {
			Alert alert = AlertUtil.error("Erro!", "Erro!", "Digite o e-mail!", null);
			alert.showAndWait();
			return;
		}
		if (password.isBlank()) {
			Alert alert = AlertUtil.error("Erro!", "Erro!", "Digite a senha!", null);
			alert.showAndWait();
			return;
		}
		User user = new UserDAO().get(email);
		if (user == null) {
			Alert alert = AlertUtil.error("Erro!", "Erro!", "E-mail ou senha inválido(s)!", null);
			alert.showAndWait();
			return;
		}
		if (!user.getPassword().contentEquals(password)) {
			Alert alert = AlertUtil.error("Erro!", "Erro!", "E-mail ou senha inválido(s)!", null);
			alert.showAndWait();
			return;
		}
		if (togglSaveLogin.isSelected())
			user.setSaveLogin(true);
		else
			user.setSaveLogin(false);
		
		if(togglFlatbee.isSelected())
			FXMLUtil.changeFlatbee(true);
		else
			FXMLUtil.changeFlatbee(false);
		
		new UserDAO().persist(user);
		App.changeResizable();
		App.setRoot("main");
		MainController controller = FXMLUtil.getMainController();
		controller.updateUserInfo(user);
	}

	@FXML
	private void register() {
		Stage stage = new Stage();
		stage.setScene(FXMLUtil.loadScene("register"));
		stage.setResizable(false);
		stage.show();
	}

	@FXML
	private void exit() {
		Platform.exit();
	}
}
