package model;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Wolf extends Unit implements Moveable, Living {
    private static Image alive;
    private static Image dead;
    private boolean isAlive;

    static {
        try {
            alive = new Image("res/wolf.png");
            dead = new Image("res/wolf.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    private int maxSpeed = 8;

    public Wolf(int x, int y) {
        setX(x);
        setY(y);
        isAlive = true;
    }

    @Override
    public int getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public boolean moveTo(int x, int y) {
        setX(x);
        setY(y);
        isAlive = true;
        return false;
    }

    @Override
    public boolean move() {
        int random = (int) (Math.random() * 10);
        if (random > 2) {
            return false;
        }
        moveTo((int) (getX() + Math.random() * maxSpeed * 2 - maxSpeed + 1), (int) (getY() + Math.random() * maxSpeed * 2 - maxSpeed + 1));
        return true;
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
}
