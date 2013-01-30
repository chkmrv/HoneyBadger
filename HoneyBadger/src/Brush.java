import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Brush extends Unit implements Collective {
    private static Image empty;
    private static Image full;

    static {
        try {
            empty = new Image("res/brush.png");
            full = new Image("res/brushberry.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }


    private boolean haveBerrys;

    public Brush(int x, int y) {
        this.setX(x);
        this.setY(y);
        setHaveBerrys(true);
    }

    @Override
    public void draw() {
        if (isHaveBerrys()) {
            full.draw(getX(), getY());
        } else {
            empty.draw(getX(), getY());
        }
    }

    public boolean isHaveBerrys() {
        return haveBerrys;
    }

    public void setHaveBerrys(boolean haveBerrys) {
        this.haveBerrys = haveBerrys;
    }

    public String toString() {
        if (isHaveBerrys()) {
            return "Brushberrys";
        } else {
            return "Empty brush";
        }
    }

}
