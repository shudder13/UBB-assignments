module com.socialnetwork {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.socialnetwork to javafx.fxml;
    exports com.socialnetwork;
    opens com.socialnetwork.controller to javafx.fxml;
}