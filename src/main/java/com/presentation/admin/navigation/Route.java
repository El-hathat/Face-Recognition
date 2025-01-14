package com.presentation.admin.navigation;

import javafx.scene.layout.Pane;

public class Route {

    private String name;
    private String path;
    private Pane content;
    private boolean chachable;

    public Route(String name, String path) {
        this.name = name;
        this.path = path;
        this.chachable = true;
    }

    public Route(String name, String path, boolean chachable) {
        this(name, path);
        this.chachable = chachable;
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

    public boolean isChachable() {
        return chachable;
    }
}


