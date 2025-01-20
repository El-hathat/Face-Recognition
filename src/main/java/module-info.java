module com.facerecognition {
    requires javafx.fxml;
    requires opencv;
    requires java.sql;
    requires libtensorflow;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.controls;
    requires mysql.connector.j;
    requires java.desktop;

    opens com.facerecognition to javafx.fxml;

    exports com.facerecognition;
    exports com.presentation;
    exports com.presentation.client;
    opens com.presentation to javafx.fxml;
    opens com.presentation.client to javafx.fxml;
    exports com.presentation.admin;
    opens com.presentation.admin to javafx.fxml;
    exports com.presentation.admin.users;
    exports com.presentation.admin.dashboard;
    opens com.presentation.admin.dashboard to javafx.fxml;

    exports com.presentation.admin.auth;
    opens com.presentation.admin.auth to javafx.fxml;
    exports com.services;
    opens com.services to javafx.fxml;
    exports com.services.auth;
    opens com.services.auth to javafx.fxml;

    opens com.presentation.admin.users to javafx.fxml;
    opens com.dao.entities to javafx.base;

    opens com.presentation.client.welcome to javafx.fxml;
    opens com.dao to javafx.base;

    opens com.presentation.client.facescan to javafx.fxml;
    exports com.presentation.outils;
    opens com.presentation.outils to javafx.fxml;

    opens com.presentation.admin.access to javafx.fxml;
    exports com.services.facerecognition;
    opens com.services.facerecognition to javafx.fxml;
}
