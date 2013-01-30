import org.newdawn.slick.Image;

public class MyTile {
    private Image img;
    private static int count = 0;

    public MyTile(Image img) {
        this.img = img;
        count = getCount() + 1;
        System.out.println("MyTile count: " + count);
    }

    public static int getCount() {
        return count;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    public void draw(int x, int y) {
        img.draw(x,y);

    }
}
