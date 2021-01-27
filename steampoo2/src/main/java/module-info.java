module br.com.lucasbueno.steampoo2 {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
	requires transitive javafx.graphics;

    opens br.com.lucasbueno.steampoo2 to javafx.fxml;
    exports br.com.lucasbueno.steampoo2;
}