package br.com.lucasbueno.steampoo2.controllers;

import java.util.ArrayList;
import java.util.List;

import br.com.lucasbueno.steampoo2.App;
import br.com.lucasbueno.steampoo2.db.GameDAO;
import br.com.lucasbueno.steampoo2.db.UserDAO;
import br.com.lucasbueno.steampoo2.entities.Game;
import br.com.lucasbueno.steampoo2.entities.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;

public class MainController {

	private User user;

	@FXML
	private TilePane tileGames;

	@FXML
	private ListView<String> userGameList;

	@FXML
	private Button btnPlay;

	@FXML
	private Button btnRemove;

	@FXML
	private Label lblGameDescription;

	@FXML
	private ImageView imgUser;

	@FXML
	private Label lblUserInfo;

	public void updateUserInfo(User u) {
		this.user = u;
		updateLibrary();
		Image image = new Image(user.getUserImage());
		imgUser.setImage(image);
		lblUserInfo.setText("Olá " + user.getUsername());
	}

	@FXML
	private void updateDescription() {
		String gameName = userGameList.getSelectionModel().getSelectedItem();
		Game game = new GameDAO().get(gameName);
		lblGameDescription.setText(game.getDescription());
	}

	@FXML
	private void logout() {
		user = null;
		App.changeResizable();
		App.setRoot("login");
	}

	@FXML
	private void exit() {
		Platform.exit();
	}

	@FXML
	private void updateGamesStore() {
		tileGames.getChildren().clear();
		for (Game g : new GameDAO().getAll())
			if (!user.getGames().contains(g)) {
				Button btn = new Button(g.getName());
				btn.setOnAction(comprarJogo());
				tileGames.getChildren().add(btn);
			}
	}

	@FXML
	private void updateLibrary() {
		if (user == null)
			return;
		List<String> userGames = new ArrayList<>();
		for (Game g : user.getGames())
			userGames.add(g.getName());
		userGameList.setItems(FXCollections.observableArrayList(userGames));

		if (user.getGames().isEmpty()) {
			lblGameDescription.setText("Você ainda não possui nenhum jogo, compre hoje mesmo um jogo na nossa loja!");
			btnRemove.setDisable(true);
			btnPlay.setDisable(true);
		} else {
			userGameList.getSelectionModel().select(0);
			btnRemove.setDisable(false);
			btnPlay.setDisable(false);
			updateDescription();
		}
	}

	@FXML
	private void remove() {
		String gameName = userGameList.getSelectionModel().getSelectedItem();
		Game game = new GameDAO().get(gameName);
		user.getGames().remove(game);
		new UserDAO().persist(user);
		updateLibrary();
	}

	private EventHandler<ActionEvent> comprarJogo() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Button btn = (Button) event.getSource();
				String gameName = btn.getText();
				user.getGames().add(new GameDAO().get(gameName));
				new UserDAO().persist(user);
				updateGamesStore();
			}
		};
	}

}
