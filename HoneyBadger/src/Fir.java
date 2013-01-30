import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Fir extends Unit {
    private static Image img;

    static {
        try {
            img = new Image("res/fir.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public Fir(int x, int y) {
        setX(x);
        setY(y);
    }

    @Override
    public void draw() {
        img.draw(getX(), getY());
    }
}
