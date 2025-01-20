package com.presentation.admin.users;

import com.dao.entities.User;
import com.presentation.admin.AppConfig;
import com.presentation.outils.navigation.Navigation;
import com.services.users.IUsersService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class AddUserController {

    private IUsersService usersService = AppConfig.USERS_SERVICE;

    @FXML
    private TextField imagepath;

    @FXML
    private TextField name;

    @FXML
    private CheckBox active;

    @FXML
    private TextField tel;

    @FXML
    private TextField email;

    @FXML
    private TextField passcode;

    @FXML
    public HBox alertContainer;

    @FXML
    public Text alertMessage;

    @FXML
    public void initialize() {
        // Add a TextFormatter to allow only numeric input
        passcode.setTextFormatter(new javafx.scene.control.TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d{0,6}")) { // Allow only digits
                return change;
            } else {
                return null; // Reject the change
            }
        }));

        // Add a TextFormatter
        tel.setTextFormatter(new javafx.scene.control.TextFormatter<>(change -> {
            if (change.getControlNewText().matches("[0-9]{0,10}")) {
                return change;
            } else {
                return null; // Reject the change
            }
        }));
    }

    @FXML
    void openFileChooser(ActionEvent event) {

        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");

        // Add filters for image files
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        // Show the open file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            imagepath.setText(selectedFile.getAbsolutePath());

        } else {
            // No image selected
        }

    }

    @FXML
    void saveUser(ActionEvent event) {

        showInfoAlert("Saving user...");

        User user = new User();

        String nameStr = name.getText();
        String emailStr = email.getText();
        String telStr = tel.getText();
        String passcodeStr = passcode.getText();
        String imagePathStr = imagepath.getText();
        boolean activeBool = active.isSelected();

        if (!nameStr.matches(AppConfig.NAME_REGEX)) {
            showErrorAlert("Name is invalid");
        } else if (!emailStr.matches(AppConfig.EMAIL_REGEX)) {
            showErrorAlert("Email is invalid");
        } else if (!telStr.matches(AppConfig.PHONE_NUMBER_REGEX)) {
            showErrorAlert("Phone number is invalid");
        } else if (!passcodeStr.matches(AppConfig.PASSCODE_REGEX)) {
            showErrorAlert("Passcode is invalid");
        } else if (imagePathStr.isBlank()) {
            showErrorAlert("Image is required");
        } else {

            user.setName(nameStr);
            user.setEmail(emailStr);
            user.setTel(telStr);
            user.setPassCode(passcodeStr);
            user.setImagePath(imagePathStr);
            user.setActive(activeBool);

            if (usersService.addUser(user)) {
                showSuccessAlert("User saved successfully");
            } else {
                showErrorAlert("An error occurred while saving the user, please try again");
            }
        }

    }

    private void showInfoAlert(String message) {
        alertMessage.setText(message);
        alertContainer.getStyleClass().removeAll("alert-danger", "alert-success");
        alertContainer.setVisible(true);
    }

    private void showSuccessAlert(String message) {
        alertMessage.setText(message);
        alertContainer.getStyleClass().add("alert-success");
        alertContainer.setVisible(true);
    }

    private void showErrorAlert(String message) {
        alertMessage.setText(message);
        alertContainer.getStyleClass().add("alert-danger");
        alertContainer.setVisible(true);
    }

    private void hideAlert() {
        alertContainer.setVisible(false);
    }


    @FXML
    void goBack(ActionEvent event) {
        Navigation.goBack();
    }

}
