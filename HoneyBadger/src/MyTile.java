import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MyTile {
    //  private Image img;
    public static final int GRASS = 0;
    public static final int STONE = 1;
    public static final int DIRT = 2;
    public static final int GRASS2 = 3;
    public static Image GRASS_IMG;
    public static Image GRASS2_IMG;
    public static Image STONE_IMG;
    public static Image DIRT_IMG;
    private static int count = 0;

    static {
        try {
            GRASS_IMG = new Image("res/grass.png");
            GRASS2_IMG = new Image("res/grass2.png");
            STONE_IMG = new Image("res/stone.gif");
            DIRT_IMG = new Image("res/dirt.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public MyTile(Image img) {
        //   this.img = img;
        count = getCount() + 1;
        System.out.println("MyTile count: " + count);
    }

    public static int getCount() {
        return count;
    }

    public static void draw(int type, int x, int y) {
        if (type == MyTile.GRASS) {
            GRASS_IMG.draw(x, y);
        } else if (type == MyTile.GRASS2) {
            GRASS2_IMG.draw(x, y);
        }else if (type == MyTile.DIRT) {
            DIRT_IMG.draw(x, y);
        }else if (type == MyTile.STONE) {
            STONE_IMG.draw(x, y);
        }
    }
}
