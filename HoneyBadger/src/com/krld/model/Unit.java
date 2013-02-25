package com.krld.model;

import java.io.Serializable;
import java.rmi.Remote;

import static java.lang.StrictMath.hypot;

public abstract class Unit implements Remote, MyDrawable, Located, Serializable {
    private int maxSpeed;
    private int x;
    private int y;
    private int speed;

    public abstract void draw();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    public double getDistanceTo(double x, double y) {
        return hypot(x - this.x, y - this.y);
    }

    public double getDistanceTo(Located unit) {
        return getDistanceTo(unit.getX(), unit.getY());
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
