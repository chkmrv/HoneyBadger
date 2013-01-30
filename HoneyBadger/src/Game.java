import org.lwjgl.util.Rectangle;
import org.newdawn.slick.*;
import org.newdawn.slick.imageout.ImageOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Game extends BasicGame {
    public static final int DELAY = 50;
    private Player player;
    private static int width;
    private static int height;
    private int elapsedTime;
    Rectangle viewPort;
    public HashMap<GridPosition, Integer> tiles;
    public ArrayList<Located> units;
    private int speed = 8;


    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Game());
        width = 800;
        height = 600;
        app.setDisplayMode(width, height, false);
        app.start();
    }

    public Game() {
        super("Game!");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        units = new ArrayList<Located>();
        tiles = new HashMap<GridPosition, Integer>();
        player = new Player();
        viewPort = new Rectangle(player.getX() - width / 2, player.getY() - height / 2, width, height);
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
        if (input.isKeyDown(Input.KEY_W)) {
            player.setY(player.getY() - speed * delta);
        }
        if (input.isKeyDown(Input.KEY_S)) {
            player.setY(player.getY() + speed * delta);
        }
        if (input.isKeyDown(Input.KEY_A)) {
            player.setX(player.getX() - speed * delta);
        }
        if (input.isKeyDown(Input.KEY_D)) {
            player.setX(player.getX() + speed * delta);
        }
        viewPort.setX(player.getX() - width / 2);
        viewPort.setY(player.getY() - height / 2);
        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            tiles.put(new GridPosition(input.getMouseX() + viewPort.getX(), input.getMouseY() + viewPort.getY()), MyTile.GRASS);
            System.out.println("tiles size: " + tiles.size());
        }
        if (input.isMousePressed(Input.MOUSE_MIDDLE_BUTTON)) {
            int random = (int) (Math.random() * 10);
            System.out.println("random " + random);
            if (random > 5) {
                units.add(new Brush(input.getMouseX() + viewPort.getX(), input.getMouseY() + viewPort.getY()));
            } else {
                units.add(new Fir(input.getMouseX() + viewPort.getX(), input.getMouseY() + viewPort.getY()));
            }
        }
        if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
            tiles.put(new GridPosition(input.getMouseX() + viewPort.getX(), input.getMouseY() + viewPort.getY()), MyTile.STONE);
            System.out.println("tiles size: " + tiles.size());
        }
        if (input.isKeyPressed(Input.KEY_E)) {
            player.grabStuff(units);
        }
        if (input.isKeyPressed(Input.KEY_R)) {
            player.dropStuff(units);
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics g) throws SlickException {
        g.translate(-viewPort.getX(), -viewPort.getY());
        for (Map.Entry<GridPosition, Integer> entry : tiles.entrySet()) {
            GridPosition gridPosition = entry.getKey();
            Integer tile = entry.getValue();
            MyTile.draw(tile, gridPosition.getX(), gridPosition.getY());
            // g.drawString(gridPosition.getX() + "", gridPosition.getX(), gridPosition.getY());
            // g.drawString(gridPosition.getY() + "", gridPosition.getX(), gridPosition.getY() + 16);
        }
        for (Located unit : units) {
            unit.draw();
        }
        //g.drawString("Hello World! x: y: " + viewPort.getX() + " " + viewPort.getY(), 100, 100);
        g.drawString(player.getInventory().printItems(), 100, 100);
        player.draw();
    }
}
