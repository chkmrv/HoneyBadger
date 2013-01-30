import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.Iterator;

public class Player extends Unit implements Located {
    private Image img;
    private Inventory inventory;

    public Player() throws SlickException {
        setImg(new Image("res/ghost.png"));
        setX(0);
        setY(0);
        setInventory(new Inventory());
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void draw() {
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
        for (Unit unit : units) {

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
}
