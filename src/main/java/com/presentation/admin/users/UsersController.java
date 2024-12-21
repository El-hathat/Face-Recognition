package com.presentation.admin.users;

import com.dao.entities.User;
import com.presentation.admin.AppConfig;
import com.presentation.admin.AppController;
import com.services.users.IUsersService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

public class UsersController {

    private IUsersService usersService = AppConfig.USERS_SERVICE;

    @FXML
    private TableView<User> usersTableView;

    @FXML
    private TableColumn<User, Long> id;

    @FXML
    private TableColumn<User, String> name;

    @FXML
    private TableColumn<User, Boolean> active;

    @FXML
    private TableColumn<User, String> tel;

    @FXML
    private TableColumn<User, String> email;


    // ObservableList to hold data
    private ObservableList<User> users;

    @FXML
    public void initialize() {

        // Set custom placeholder
        Label noUserLabel = new Label("No user is found.");
        noUserLabel.getStyleClass().addAll("h3", "text-muted");
        usersTableView.setPlaceholder(noUserLabel);


        // Configure the columns
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        active.setCellValueFactory(new PropertyValueFactory<>("active"));


        // Create and populate the data
        users = FXCollections.observableArrayList(
                new User(1L, "John Doe", "email@gmail.com", "123456789", "1234", "imagePath", true),
                new User(2L, "John Doe", "email@gmail.com", "123456789", "1234", "imagePath", true),
                new User(3L, "John Doe", "email@gmail.com", "123456789", "1234", "imagePath", true)
        );

        // Set the data in the TableView
        usersTableView.setItems(users);

        // Configure the action column
        addMenuToTable();
    }

    private void addMenuToTable() {

        // Create a ContextMenu
        ContextMenu contextMenu = new ContextMenu();

        // Add MenuItems to the ContextMenu
        MenuItem viewItem = new MenuItem("View");
        MenuItem updateItem = new MenuItem("Update");
        MenuItem deleteItem = new MenuItem("Delete");

        contextMenu.getItems().addAll(viewItem, updateItem, deleteItem);

        // Add actions to MenuItems
        viewItem.setOnAction(event -> {
            User selectedPerson = usersTableView.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                SharedData.setUserId(selectedPerson.getId());
                loadFXMLContent("/com/admin/users/view.fxml");
                System.out.println("View: " + selectedPerson.getName());
            }
        });

        updateItem.setOnAction(event -> {
            User selectedPerson = usersTableView.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                SharedData.setUserId(selectedPerson.getId());
                loadFXMLContent("/com/admin/users/view.fxml");
                System.out.println("Update: " + selectedPerson.getName());
            }
        });

        deleteItem.setOnAction(event -> {
            User selectedPerson = usersTableView.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                if (usersService.deleteUser(selectedPerson)) {
                    users.remove(selectedPerson);
                } else {
                    System.out.println("Error deleting user");
                }
            }
        });

        // Attach the ContextMenu to the TableView
        usersTableView.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnContextMenuRequested(event -> {
                if (!row.isEmpty()) {
                    usersTableView.getSelectionModel().select(row.getIndex());
                    contextMenu.show(row, event.getScreenX(), event.getScreenY());
                }
            });
            return row;
        });
    }

    @FXML
    void addNewUser(ActionEvent event) {
        loadFXMLContent("/com/admin/users/add.fxml");

    }

    private void loadFXMLContent(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane newContent = loader.load();
            AppController.MAIN_BORDER_PANEL.setCenter(newContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

