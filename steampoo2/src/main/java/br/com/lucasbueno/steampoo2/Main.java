package br.com.lucasbueno.steampoo2;

import br.com.lucasbueno.steampoo2.db.UtilDB;
import javafx.application.Application;

public class Main {

	public static void main(String[] args) {
		Application.launch(App.class);
		UtilDB.closeConn();
	}
}
