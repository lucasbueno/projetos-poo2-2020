package br.com.lucasbueno.steampoo2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import br.com.lucasbueno.steampoo2.db.UserDAO;
import br.com.lucasbueno.steampoo2.db.UtilDB;
import br.com.lucasbueno.steampoo2.entities.User;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

	private static Stage stage;

	@Override
	public void start(Stage stge) {

		List<String> users = consultAPI();
		consumeAPI(users);

		stage = stge;
		stage.setScene(FXMLUtil.loadScene("login"));
		changeResizable();
		stage.show();
	}

	private void consumeAPI(List<String> users) {
		for (int lineIndex = 0; lineIndex < users.size(); lineIndex++) {
			String line = users.get(lineIndex);
			if (line.contains("username")) {
				// processamos o nome de usuario
				String username = processJSONLine(line);
				System.out.println(username);

				// vamos para a proxima linha
				lineIndex++;
				line = users.get(lineIndex);

				// processamos o password
				String password = processJSONLine(line);
				System.out.println(password);

				User user = new User(username, password);
				user.setIdade(15);

				// adicionando o usuario que veio da API
				new UserDAO().persist(user);

				// criando e adicionando um novo usuario para testes
				User user2 = new User("teste", "teste");
				user2.setIdade(18);
				new UserDAO().persist(user2);
				
				// atualizando o usuario de testes
				UtilDB.getEntityManager().getTransaction().begin();
				user2.setIdade(20);
				UtilDB.getEntityManager().getTransaction().commit();
				
				// removendo o usuario de testes
				new UserDAO().remove(user2);
				
				// buscando o usuario admin
				User admin = new UserDAO().get("admin");
				System.out.println(admin.getPassword());

			}
		}

	}

	private String processJSONLine(String line) {
		String[] dividedLine = line.split(":");
		String username = dividedLine[1];
		username = username.replace(",", " ");
		username = username.replace("\"", " ");
		username = username.trim();
		return username;
	}

	private List<String> consultAPI() {
		List<String> result = new ArrayList<>();
		try {
			URL url = new URL("http://www.lucasbueno.com.br/steam.json");
			URLConnection uc = url.openConnection();
			InputStreamReader input = new InputStreamReader(uc.getInputStream());
			BufferedReader in = new BufferedReader(input);
			String inputLine;

			while ((inputLine = in.readLine()) != null)
				result.add(inputLine);

			in.close();
		} catch (Exception e) {
			Alert alert = ExceptionUtil.error("Erro", "Erro ao consumir a API!", "Erro ao consumir a API!", e);
			alert.showAndWait();
		}
		return result;
	}

	public static void setRoot(String fxml) {
		stage.setScene(FXMLUtil.loadScene(fxml));
	}

	public static void changeResizable() {
		if (stage.isResizable())
			stage.setResizable(false);
		else
			stage.setResizable(true);
	}

}