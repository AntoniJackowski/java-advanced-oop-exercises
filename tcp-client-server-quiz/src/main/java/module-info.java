module com.example.tcpclientserverquiz {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.tcpclientserverquiz to javafx.fxml;
    exports com.example.tcpclientserverquiz;
}