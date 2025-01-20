package com.presentation.outils.navigation;

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

    public void addRoute(String name, String path) {
        routes.put(name, new Route(this, name, path));
    }

    public Route getRoute(String name) {
        return routes.get(name);
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
