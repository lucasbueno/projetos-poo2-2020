package br.com.lucasbueno.steampoo2.controllers;

import br.com.lucasbueno.steampoo2.App;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.ToggleSwitch;

import br.com.lucasbueno.steampoo2.AlertUtil;
import br.com.lucasbueno.steampoo2.db.UserDAO;
import br.com.lucasbueno.steampoo2.entities.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {
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

		try {
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main.fxml"));
			Scene scene = new Scene(fxmlLoader.load());
			Stage stage = (Stage) togglSaveLogin.getScene().getWindow();
			stage.setScene(scene);
			stage.setResizable(true);
			stage.show();

			if (togglFlatbee.isSelected())
				scene.getStylesheets().add("br/com/lucasbueno/steampoo2/flatbee.css");

			new UserDAO().persist(user);

			MainController controller = fxmlLoader.getController();
			controller.updateUserInfo(user);

		} catch (IOException e) {
			Alert alert = AlertUtil.error("Erro", "Erro ao carregar um componente",
					"Erro ao tentar carregar a janela de login", e);
			alert.showAndWait();
		}
	}

	@FXML
	private void register() {
		try {
			Stage stage = new Stage();
			FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("register.fxml"));
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
	}

	@FXML
	private void exit() {
		Platform.exit();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
