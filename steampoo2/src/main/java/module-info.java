module br.com.lucasbueno.steampoo2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens br.com.lucasbueno.steampoo2 to javafx.fxml;
    exports br.com.lucasbueno.steampoo2;
}