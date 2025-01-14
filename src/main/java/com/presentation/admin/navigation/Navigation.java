package com.presentation.admin.navigation;

import com.services.auth.AdminSession;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class Navigation {

    private static Map<String, RouteGroup> routes;

    private static Route currentRoute;


    static {
        routes = new HashMap<>();
        currentRoute = null;
    }

    public static void goTo(String routeGroup, String routeName) {
        RouteGroup group = routes.get(routeGroup);
        Route route = group.getRoutes().get(routeName);

        if (group.isAuthProtected() && !AdminSession.isLoggedIn()) {
            // check if user is logged in
            // if not, redirect to login page
            goTo("auth", "login");
            return;
        }

        // load the route
        loadRoute(route, group.getNavHost());
    }

    public static void addRouteGroup(RouteGroup routeGroup) {
        routes.put(routeGroup.getName(), routeGroup);
    }

    public static void addRouteGroups(RouteGroup... routeGroups) {
        for (RouteGroup routeGroup : routeGroups) {
            routes.put(routeGroup.getName(), routeGroup);
        }
    }

    public static Route getCurrentRoute() {
        return currentRoute;
    }

    public static void addRouteToGroup(String routeGroup, Route route) {
        RouteGroup group = routes.get(routeGroup);
        group.addRoute(route);
    }

    private static void loadRoute(Route route, Pane navHost) {

        if (!route.isChachable() || route.getContent() == null) {
            try {
                FXMLLoader loader = new FXMLLoader(Navigation.class.getResource(route.getPath()));
                route.setContent(loader.load());

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error loading route: " + route.getName());
                return;
            }
        }
        navHost.getChildren().clear();
        navHost.getChildren().add(route.getContent());
        currentRoute = route;
    }
}
