package br.com.lucasbueno.steampoo2;

import java.net.InetAddress;

import javafx.application.Platform;

public class TestConnection implements Runnable {

	@Override
	public void run() {
		String ipAddress = "127.0.0.1";
		try {
			while (true) {
				InetAddress inet = InetAddress.getByName(ipAddress);
				System.out.println("Testando a conexão...");
				if (inet.isReachable(2000))
					System.out.println("Conexão funcionando.");
				else
					Platform.runLater(() -> AlertUtil.error("Erro de conexão!", "Erro de conexão!",
							"Não foi possível estabelecer a conexão com a internet. Corrija este problema antes de continuar utilizando o programa.",
							null).showAndWait());
				Thread.sleep(5000);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
