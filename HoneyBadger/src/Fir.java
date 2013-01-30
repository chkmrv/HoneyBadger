import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Fir extends Tree {
    private static Image cutDownImg;
    private static Image aliveImg;

    static {
        try {
            setCutDownImg(new Image("res/firstump.png"));
            setAliveImg(new Image("res/fir.png"));
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public Fir(int x, int y) {
        super();
        setX(x);
        setY(y);

    }

    @Override
    public void draw() {
        Image img;
        if (isCutDown()) {
            img = getCutDownImg();
        } else {
            img = getAliveImg();
        }
        img.draw(getX(), getY());
    }

    public static Image getCutDownImg() {
        return cutDownImg;
    }

    public static void setCutDownImg(Image cutDownImg) {
        Fir.cutDownImg = cutDownImg;
    }

    public static Image getAliveImg() {
        return aliveImg;
    }

    public static void setAliveImg(Image aliveImg) {
        Fir.aliveImg = aliveImg;
    }


}
