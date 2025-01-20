package com.presentation.admin.users;

import com.dao.entities.User;
import com.presentation.admin.AppConfig;
import com.presentation.outils.SharedData;
import com.presentation.outils.navigation.Navigation;
import com.presentation.outils.navigation.RouteGroup;
import com.services.users.IUsersService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

        // RouteGroup
        RouteGroup main = Navigation.getRouteGroup("main");

        // add routes
        main.addRoute("users:view", "/com/admin/users/view.fxml");
        main.addRoute("users:add", "/com/admin/users/add.fxml");

        // Add routes to the navigation
        Navigation.addRouteGroup(main);


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
        users = FXCollections.observableArrayList(usersService.getAllUsers());

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
                SharedData.putLong("selected:user:id", selectedPerson.getId());
                Navigation.pushToBackStackAndGoTo("main", "users:view");
            }
        });

        updateItem.setOnAction(event -> {
            User selectedPerson = usersTableView.getSelectionModel().getSelectedItem();
            if (selectedPerson != null) {
                SharedData.putLong("selected:user:id", selectedPerson.getId());
                Navigation.pushToBackStackAndGoTo("main", "users:view");
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
        Navigation.pushToBackStackAndGoTo("main", "users:add");
    }

    @FXML
    void refreshUserList(ActionEvent event) {
        users.setAll(usersService.getAllUsers());
    }

}

