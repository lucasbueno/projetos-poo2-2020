package br.com.lucasbueno.steampoo2;

import br.com.lucasbueno.steampoo2.db.UtilDB;
import javafx.application.Application;

public class Main {

	public static void main(String[] args) {

		Thread connection = new Thread(new TestConnection());
		App.setConnection(connection);
		System.out.println("A thread da classe Main está executando...");
		System.out.println("O banco de dados será inicializado...");
		UtilDB.initDB();
		System.out.println("A interface gráfica será inicializada...");
		Application.launch(App.class);
		System.out.println("A interface gráfica foi fechada.");
		System.out.println("Os recursos do banco de dados serão desalocados...");
		UtilDB.closeConn();
		
		System.out.println("A thread que testa a conexão será finalizada...");
		connection.interrupt();
		System.out.println("O programa será finalizado.");
	}
}
