package br.com.lucasbueno.steampoo2.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import br.com.lucasbueno.steampoo2.AlertUtil;
import br.com.lucasbueno.steampoo2.db.UserDAO;
import br.com.lucasbueno.steampoo2.entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RegisterController {

	@FXML
	private TextField txtEmail;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private ImageView imgUser;

	@FXML
	private Button btnSelectImage;
	
	@FXML
	private void selectImage() throws IOException {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Selecione sua imagem de perfil...");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("PNG", "*.png"));
		File selected = fileChooser.showOpenDialog(btnSelectImage.getScene().getWindow());
		File path = new File("./images/"+selected.getName());
		Files.copy(selected.toPath(), path.toPath(), StandardCopyOption.REPLACE_EXISTING);		
		imgUser.setImage(new Image(path.toURI().toString()));
	}

	@FXML
	private void close() {
		Stage stage = (Stage) txtEmail.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void register() {
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
		User u = new UserDAO().get(email);
		if (u != null) {
			Alert alert = AlertUtil.error("Erro!", "Erro!", "E-mail já em uso!", null);
			alert.showAndWait();
			return;
		}
		new UserDAO().persist(new User(email, password, imgUser.getImage().getUrl()));

		AlertUtil.info("Sucesso", "Sucesso", "Cadastro realizado com sucesso").show();
		close();
	}

}
