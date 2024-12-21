package com.presentation.admin.users;

import com.dao.entities.User;
import com.presentation.admin.AppConfig;
import com.services.users.IUsersService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

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

        user = usersService.getUser(SharedData.getUserId());

        System.out.println("User id : " + SharedData.getUserId());
    }

    @FXML
    void openFileChooser(ActionEvent event) {

    }

    @FXML
    void updateUser(ActionEvent event) {

    }

}

