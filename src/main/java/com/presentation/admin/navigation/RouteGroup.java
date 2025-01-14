package com.presentation.admin.navigation;

import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class RouteGroup {

    private String name;
    private Pane navHost;
    private Map<String, Route> routes;
    private  boolean authProtected;

    public RouteGroup(String name, Pane navHost, boolean authProtected) {
        this.name = name;
        this.navHost = navHost;
        this.authProtected = authProtected;

        routes = new HashMap<>();
    }

    public void addRoute(Route route) {
        routes.put(route.getName(), route);
    }

    public String getName() {
        return name;
    }

    public Pane getNavHost() {
        return navHost;
    }

    public Map<String, Route> getRoutes() {
        return routes;
    }

    public boolean isAuthProtected() {
        return authProtected;
    }
}
