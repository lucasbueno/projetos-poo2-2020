module br.com.lucasbueno.steampoo2 {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;

    opens br.com.lucasbueno.steampoo2 to javafx.fxml;
    exports br.com.lucasbueno.steampoo2;
}