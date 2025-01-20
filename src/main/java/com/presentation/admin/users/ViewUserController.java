package com.presentation.admin.users;

import com.dao.entities.User;
import com.presentation.admin.AppConfig;
import com.presentation.outils.SharedData;
import com.presentation.outils.navigation.Navigation;
import com.services.users.IUsersService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;

public class ViewUserController {

    private IUsersService usersService = AppConfig.USERS_SERVICE;

    @FXML
    private ImageView userImage;

    @FXML
    private TextField imagepath;

    @FXML
    private TextField name;

    @FXML
    private CheckBox active;

    @FXML
    private TextField tel;

    @FXML
    private TextField id;

    @FXML
    private TextField email;

    @FXML
    private TextField passcode;

    private User user;

    @FXML
    public void initialize() {

        long user_id = SharedData.getLongAndRemove("selected:user:id");


        System.out.println("User id : " + user_id);

        Platform.runLater(() -> {
            user = usersService.getUser(user_id);
            if (user != null) {
                id.setText(String.valueOf(user.getId()));
                name.setText(user.getName());
                email.setText(user.getEmail());
                tel.setText(user.getTel());
                active.setSelected(user.isActive());
                passcode.setText(user.getPassCode());
                imagepath.setText(user.getImagePath());

                // Define the path to the image
                String imagePath = Paths.get(user.getImagePath()).toUri().toString();
                userImage.setImage(new Image(imagePath));
            }
        });
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
    void updateUser(ActionEvent event) {
        User newUser = new User(user.getId(), name.getText(), email.getText(), tel.getText(), passcode.getText(), imagepath.getText(), active.isSelected());
        if (usersService.updateUser(user, newUser)) {
            Navigation.goBack();
        } else {
            System.out.println("Error updating user");
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        Navigation.goBack();
    }

}

