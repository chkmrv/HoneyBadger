package com.krld.model;

/**
 * Created with IntelliJ IDEA.
 * User: krld
 * Date: 30.01.13
 * Time: 23:13
 * To change this template use File | Settings | File Templates.
 */
public interface Moveable extends Located {
    public int getMaxSpeed();
    public void setMaxSpeed(int maxSpeed);
    public boolean moveTo(int x, int y);
    public boolean move();
}
