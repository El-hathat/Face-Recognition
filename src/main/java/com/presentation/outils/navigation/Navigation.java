package com.presentation.outils.navigation;

import com.services.auth.AdminSession;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Navigation {

    private static Map<String, RouteGroup> routes;

    private static Route currentRoute;

    private static Deque<Route> backStack;


    static {
        routes = new HashMap<>();
        currentRoute = null;

        backStack = new LinkedList<>();
    }

    public static void goTo(String routeGroup, String routeName) {
        RouteGroup group = routes.get(routeGroup);
        Route route = group.getRoute(routeName);

        if (group.isAuthProtected() && !AdminSession.isLoggedIn()) {
            // check if user is logged in
            // if not, redirect to login page
            goTo("auth", "login");
            return;
        }

        // load the route
        loadRoute(route, group.getNavHost());
    }

    public static void pushToBackStackAndGoTo(String routeGroup, String routeName) {

        backStack.push(currentRoute);
        goTo(routeGroup, routeName);

    }


    public static void addRouteGroup(RouteGroup routeGroup) {
        routes.put(routeGroup.getName(), routeGroup);
    }

    public static void addRouteGroups(RouteGroup... routeGroups) {
        for (RouteGroup routeGroup : routeGroups) {
            routes.put(routeGroup.getName(), routeGroup);
        }
    }

    public static RouteGroup getRouteGroup(String name) {
        return routes.get(name);
    }

    public static Route getCurrentRoute() {
        return currentRoute;
    }

    private static void loadRoute(Route route, Pane navHost) {


        try {
            FXMLLoader loader = new FXMLLoader(Navigation.class.getResource(route.getPath()));
            route.setContent(loader.load());

            navHost.getChildren().setAll(route.getContent());

            Platform.runLater(() -> {
                route.getContent().setPrefWidth(navHost.getWidth());
            });

            currentRoute = route;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void goBack() {
        if (!backStack.isEmpty()) {
            Route route = backStack.pop();
            loadRoute(route, route.getRouteGroup().getNavHost());
        }
    }
}
