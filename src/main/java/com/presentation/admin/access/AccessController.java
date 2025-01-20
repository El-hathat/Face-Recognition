package com.presentation.admin.access;

import com.dao.entities.AccessLog;
import com.dao.entities.User;
import com.presentation.admin.AppConfig;
import com.presentation.outils.navigation.Navigation;
import com.presentation.outils.navigation.RouteGroup;
import com.services.accesslog.IAccessLogService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AccessController {

    private IAccessLogService usersService = AppConfig.ACCESS_LOG_SERVICE;
    // ObservableList to hold data
    private ObservableList<AccessLog> accessLogs;

    @FXML
    private TableView<AccessLog> accessLogTable;

    @FXML
    private TableColumn<AccessLog, Long> log_id;

    @FXML
    private TableColumn<AccessLog, User> user_name;

    @FXML
    private TableColumn<AccessLog, String> access_date;

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
        Label noUserLabel = new Label("No access logs found");
        noUserLabel.getStyleClass().addAll("h3", "text-muted");
        accessLogTable.setPlaceholder(noUserLabel);


        // Configure the columns
        log_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        user_name.setCellValueFactory(new PropertyValueFactory<>("user"));
        access_date.setCellValueFactory(new PropertyValueFactory<>("accessTime"));


        // Create and populate the data
        accessLogs = FXCollections.observableArrayList(usersService.getAllAccessLogs());

        // Set the data in the TableView
        accessLogTable.setItems(accessLogs);

    }

    @FXML
    void refreshAccessLogList(ActionEvent event) {
        accessLogs.setAll(usersService.getAllAccessLogs());
    }

}
