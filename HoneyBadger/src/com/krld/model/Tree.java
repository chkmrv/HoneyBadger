package com.krld.model;

public abstract class Tree extends Unit {

    private boolean cutDown;

    public Tree() {
        cutDown = false;
    }

    public boolean isCutDown() {
        return cutDown;
    }

    public void setCutDown(boolean cutDown) {
        this.cutDown = cutDown;
    }
}
