package com.presentation.outils.navigation;

import javafx.scene.layout.Pane;

public class Route {

    private String name;
    private String path;
    private Pane content;
    private RouteGroup routeGroup;

    public Route(RouteGroup routeGroup,String name, String path) {
        this.routeGroup = routeGroup;
        this.name = name;
        this.path = path;
    }


    public RouteGroup getRouteGroup() {
        return routeGroup;
    }

    public void setRouteGroup(RouteGroup routeGroup) {
        this.routeGroup = routeGroup;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Pane getContent() {
        return content;
    }

    public void setContent(Pane content) {
        this.content = content;
    }

}


