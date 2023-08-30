module com.group3.cruisebookingsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.group3.cruisebookingsystem to javafx.fxml;
    exports com.group3.cruisebookingsystem;
}