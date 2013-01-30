package model;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class MyTile {
    //  private Image img;
    public static final int GRASS = 0;
    public static final int STONE = 1;
    public static final int DIRT = 2;
    public static final int FOREST_GRASS = 3;
    public static final int WATER = 4;
    public static Image GRASS_IMG;
    public static Image FORES_GRASS_IMG;
    public static Image STONE_IMG;
    public static Image DIRT_IMG;
    public static Image WATER_IMG;
    public static Image WATER2_IMG;
    private static int count = 0;

    static {
        try {
            GRASS_IMG = new Image("res/grass.png");
            FORES_GRASS_IMG = new Image("res/grass2.png");
            STONE_IMG = new Image("res/stone.gif");
            DIRT_IMG = new Image("res/dirt.png");
            WATER_IMG = new Image("res/water.png");
            WATER2_IMG = new Image("res/water2.png");
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

    public MyTile(Image img) {
        //   this.img = img;
        count = getCount() + 1;
        System.out.println("model.MyTile count: " + count);
    }

    public static int getCount() {
        return count;
    }

    public static void draw(int type, int x, int y, GameContainer gameContainer) {
        if (type == MyTile.GRASS) {
            GRASS_IMG.draw(x, y);
        } else if (type == MyTile.FOREST_GRASS) {
            FORES_GRASS_IMG.draw(x, y);
        } else if (type == MyTile.DIRT) {
            DIRT_IMG.draw(x, y);
        } else if (type == MyTile.STONE) {
            STONE_IMG.draw(x, y);
        } else if (type == MyTile.WATER) {

            if (gameContainer.getTime()%5000 > 2500) {
                WATER_IMG.draw(x, y);
            } else {
                WATER2_IMG.draw(x, y);
            }
        }
    }
}
