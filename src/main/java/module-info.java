module com.facerecognition {
  requires javafx.fxml;
  requires opencv;
  requires java.sql;
  requires libtensorflow;
  requires org.kordamp.bootstrapfx.core;
  requires javafx.controls;

  opens com.facerecognition to javafx.fxml;

  exports com.facerecognition;
  exports com.presentation;
  exports com.presentation.client;
  opens com.presentation to javafx.fxml;
  opens com.presentation.client to javafx.fxml;
  exports com.presentation.admin;
  opens com.presentation.admin to javafx.fxml;
}
