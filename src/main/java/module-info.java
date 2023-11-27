module com.example.kalkulackaobedu {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.kalkulackaobedu to javafx.fxml;
    exports com.example.kalkulackaobedu;
}