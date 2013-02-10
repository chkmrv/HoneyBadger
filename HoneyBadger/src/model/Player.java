package model;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class Player extends Unit implements Located, Moveable {
    transient private Image img;
    private Inventory inventory;
    private long id;

    public Player() throws SlickException {
        this(0, 0);
    }

    public Player(int x, int y)  {
        setId(Calendar.getInstance().getTimeInMillis());
        setX(x);
        setY(y);
        setSpeed(20);
        setInventory(new Inventory());
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void draw() {
        if (img == null) {
            try {
                setImg(new Image("res/ghost.png"));
            } catch (SlickException e) {
                e.printStackTrace();
            }
        }
        getImg().draw(getX(), getY());

    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void grabStuff(ArrayList<Located> units) {
        for (Iterator<Located> iter = units.iterator(); iter.hasNext(); ) {
            Located currentUnit = iter.next();
            if (currentUnit instanceof Collective) {
                if (getDistanceTo(currentUnit.getX(), currentUnit.getY()) < 100) {
                    this.getInventory().getItems().add((Collective) currentUnit);
                    iter.remove();
                }
            }
        }  /*
        for (model.Unit unit : units) {

        }  */
    }

    public void dropStuff(ArrayList<Located> units) {
        for (Iterator<Collective> iter = this.getInventory().getItems().iterator(); iter.hasNext(); ) {
            Collective currentUnit = iter.next();
            if (currentUnit instanceof Located) {
                ((Located) currentUnit).setX((int) (getX() + Math.random() * 100 - 50));
                ((Located) currentUnit).setY((int) (getY() + Math.random() * 100 - 50));
                units.add((Located) currentUnit);
                iter.remove();
            }
        }
    }

    @Override
    public boolean moveTo(int x, int y) {
        setX(x);
        setY(y);
        return true;
    }

    @Override
    public boolean move() {
        return false;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (id != player.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public long getId() {
        return id;
    }
}
