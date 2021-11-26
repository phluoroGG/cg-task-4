module com.vsu.cgcourse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.vsu.cgcourse to javafx.fxml;
    exports com.vsu.cgcourse;
}