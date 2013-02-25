package com.krld.model;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Rabbit extends Unit implements Moveable, Living {
    private static Image alive;
    private static Image dead;
    private boolean isAlive;

    static {
        try {
            alive = new Image("res/rabbit2.png");
            dead = new Image("res/rabbit2.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    private int maxSpeed = 10;

    public Rabbit(int x, int y) {
        setX(x);
        setY(y);
        isAlive = true;
    }

    @Override
    public boolean moveTo(int x, int y) {
        setX(x);
        setY(y);
        return false;
    }

    @Override
    public void draw() {
        Image img;
        if (isAlive()) {
            img = alive;
        } else {
            img = dead;
        }
        img.draw(getX(), getY());
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public boolean move() {
        int random = (int) (Math.random() * 10);
        if (random > 2) {
            return false;
        }
        moveTo(((getX() +  (Math.random() <= 0.5 ? 1 : -1))), (int) (getY() + (Math.random() <= 0.5 ? 1 : -1)));
        return true;
    }
}
