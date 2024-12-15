module com.facerecognition {
  requires javafx.controls;
  requires javafx.fxml;
  requires opencv;
  requires java.sql;
  requires libtensorflow;

  opens com.facerecognition to javafx.fxml;

  exports com.facerecognition;
}
