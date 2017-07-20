package org.mercury.oschina.synthesis.bean;

/**
 * Created by more on 2016-08-17 11:10:30.
 */
public abstract class Entity implements WebViewInterface {
    private boolean isVisited;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
