import org.lwjgl.util.Rectangle;
import org.newdawn.slick.*;
import org.newdawn.slick.imageout.ImageOut;

import java.util.HashMap;
import java.util.Map;

public class HelloWorld extends BasicGame {
    public static final int DELAY = 50;
    private static int width;
    private static int height;
    private int elapsedTime;
    Rectangle viewPort;
    public HashMap<GridPosition, MyTile> tiles;
    private int speed = 32;


    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new HelloWorld());
        width = 800;
        height = 600;
        app.setDisplayMode(width, height, false);
        app.start();
    }

    public HelloWorld() {
        super("HelloWorld!");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        tiles = new HashMap<GridPosition, MyTile>();
        viewPort = new Rectangle(0, 0, width, height);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        elapsedTime += delta;
        // System.out.println("elapsedTime: " + elapsedTime);
        if (elapsedTime <= DELAY) {
            return;
        }
        elapsedTime = 0;
        Input input = container.getInput();
        Graphics g = container.getGraphics();
        if (input.isKeyPressed(Input.KEY_P)) {
            Image target = new Image(container.getWidth(), container.getHeight());
            container.getGraphics().copyArea(target, 0, 0);
            ImageOut.write(target.getFlippedCopy(false, false), "screenshot.png", false);
            target.destroy();
        }
        if (input.isKeyPressed(Input.KEY_W)) {
            viewPort.setY(viewPort.getY() + speed * delta);
        }
        if (input.isKeyPressed(Input.KEY_S)) {
            viewPort.setY(viewPort.getY() - speed * delta);
        }
        if (input.isKeyPressed(Input.KEY_A)) {
            viewPort.setX(viewPort.getX() + speed * delta);
        }
        if (input.isKeyPressed(Input.KEY_D)) {
            viewPort.setX(viewPort.getX() - speed * delta);
        }
        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            Image img = new Image("res/grass.png");
            tiles.put(new GridPosition(input.getMouseX() - viewPort.getX(), input.getMouseY() - viewPort.getY()), new MyTile(img));
            System.out.println("tiles size: " + tiles.size());
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics g) throws SlickException {
        g.translate(viewPort.getX(), viewPort.getY());
        for (Map.Entry<GridPosition, MyTile> entry : tiles.entrySet()) {
            GridPosition gridPosition = entry.getKey();
            MyTile tile = entry.getValue();
            tile.draw(gridPosition.getX(), gridPosition.getY());
        }

        g.drawString("Hello World!", 100, 100);
    }
}
