import common.WorldGenerator;
import model.*;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.*;
import org.newdawn.slick.imageout.ImageOut;

import java.util.ArrayList;

public class Game extends BasicGame {
    private static final int DELAY = 50;
    private static long time = System.nanoTime();
    private final WorldGenerator worldGenerator = new WorldGenerator(this);
    private Player player;
    private static int width;
    private static int height;
    private int elapsedTime;
    private Rectangle viewPort;
    private static int[][] tileMap;
    private static ArrayList<Located> objects;
    private static ArrayList<Moveable> moveables;
    private int speed = 20;
    private int distanceOfView = 600;

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Game());
        setWidth(800);
        setHeight(600);
        app.setDisplayMode(getWidth(), getHeight(), false);
        app.start();
    }

    public Game() {
        super("Game!");
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        objects = new ArrayList<Located>();

        tileMap = tileMap = new int[200][200];
        moveables = new ArrayList<Moveable>();
        worldGenerator.generateTiles(tileMap);
        worldGenerator.generateUnits(objects, tileMap, moveables);
        player = new Player(2000, 2000);
        viewPort = new Rectangle(player.getX() - width / 2, player.getY() - height / 2, width, height);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        setElapsedTime(getElapsedTime() + delta);
        // System.out.println("elapsedTime: " + elapsedTime);
        if (getElapsedTime() <= getDelay()) {
            return;
        }
        setElapsedTime(0);
        Input input = container.getInput();
        Graphics g = container.getGraphics();
        if (input.isKeyPressed(Input.KEY_P)) {
            Image target = new Image(container.getWidth(), container.getHeight());
            container.getGraphics().copyArea(target, 0, 0);
            ImageOut.write(target.getFlippedCopy(false, false), "screenshot.png", false);
            target.destroy();
        }
        if (input.isKeyDown(Input.KEY_W)) {
            getPlayer().moveTo(getPlayer().getX(), getPlayer().getY() - getSpeed() * delta);
        }
        if (input.isKeyDown(Input.KEY_S)) {
            getPlayer().moveTo(getPlayer().getX(), getPlayer().getY() + getSpeed() * delta);
        }
        if (input.isKeyDown(Input.KEY_A)) {
            getPlayer().moveTo(getPlayer().getX() - getSpeed() * delta, getPlayer().getY());
        }
        if (input.isKeyDown(Input.KEY_D)) {
            getPlayer().moveTo(getPlayer().getX() + getSpeed() * delta, getPlayer().getY());
        }
        getViewPort().setX(getPlayer().getX() - getWidth() / 2);
        getViewPort().setY(getPlayer().getY() - getHeight() / 2);
        if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            //   getTiles().put(new common.GridPosition(input.getMouseX() + getViewPort().getX(), input.getMouseY() + getViewPort().getY()), model.MyTile.GRASS);
        }
        if (input.isMousePressed(Input.MOUSE_MIDDLE_BUTTON)) {
            int random = (int) (Math.random() * 10);

            if (random > 5) {
                getObjects().add(new Brush(input.getMouseX() + getViewPort().getX(), input.getMouseY() + getViewPort().getY()));
            } else {
                getObjects().add(new Fir(input.getMouseX() + getViewPort().getX(), input.getMouseY() + getViewPort().getY()));
            }
        }
        if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
            //  getTiles().put(new common.GridPosition(input.getMouseX() + getViewPort().getX(), input.getMouseY() + getViewPort().getY()), model.MyTile.STONE);

        }
        if (input.isKeyPressed(Input.KEY_E)) {
            getPlayer().grabStuff(getObjects());
        }
        if (input.isKeyPressed(Input.KEY_R)) {
            getPlayer().dropStuff(getObjects());
        }
        moveMoveables();
    }

    private void moveMoveables() {
        for (Moveable moveable : moveables) {
            moveable.move();
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics g) throws SlickException {
        g.translate(-getViewPort().getX(), -getViewPort().getY());
        /*
        for (Map.Entry<common.GridPosition, Integer> entry : getTiles().entrySet()) {
            common.GridPosition gridPosition = entry.getKey();
            Integer tile = entry.getValue();

            if (getPlayer().getDistanceTo(gridPosition.getX(), gridPosition.getY()) < getDistanceOfView()) {
                model.MyTile.draw(tile, gridPosition.getX(), gridPosition.getY(), gameContainer);
            }
            // g.drawString(gridPosition.getX() + "", gridPosition.getX(), gridPosition.getY());
            // g.drawString(gridPosition.getY() + "", gridPosition.getX(), gridPosition.getY() + 16);
        } */
        int playerX = getPlayer().getX();
        int playerY = getPlayer().getY();
        for (int x = playerX / 32 - 15; x < playerX / 32 + 15; x++) {
            for (int y = playerY / 32 - 15; y < playerY / 32 + 15; y++) {
                int realX = x * 32 - 1;
                int realY = y * 32 - 1;
                // if (getPlayer().getDistanceTo(realX, realY) < getDistanceOfView()) {
                try {
                    MyTile.draw(tileMap[x][y], realX, realY, gameContainer);
                } catch (ArrayIndexOutOfBoundsException e) {
                }
                //}
            }
        }
        for (Located unit : getObjects()) {
            if (playerX - 15 * 32 < unit.getX() && playerX + 15 * 32 > unit.getX() &&
                    playerY - 15 * 32 < unit.getY() && playerY + 15 * 32 > unit.getY()) {
                unit.draw();
            }
        }
        for (Moveable unit : moveables) {
            if (playerX - 15 * 32 < unit.getX() && playerX + 15 * 32 > unit.getX() &&
                    playerY - 15 * 32 < unit.getY() && playerY + 15 * 32 > unit.getY()) {
                unit.draw();
            }
        }
        //g.drawString("Hello World! x: y: " + viewPort.getX() + " " + viewPort.getY(), 100, 100);
        g.drawString(getPlayer().getInventory().printItems(), 100, 100);
        getPlayer().draw();
    }

    public WorldGenerator getWorldGenerator() {
        return worldGenerator;
    }


    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public Rectangle getViewPort() {
        return viewPort;
    }

    public void setViewPort(Rectangle viewPort) {
        this.viewPort = viewPort;
    }

    public ArrayList<Located> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Located> objects) {
        this.objects = objects;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDistanceOfView() {
        return distanceOfView;
    }

    public void setDistanceOfView(int distanceOfView) {
        this.distanceOfView = distanceOfView;
    }

    public static int getDelay() {
        return DELAY;
    }

    public static long getTime() {
        return time;
    }

    public static void setTime(long time) {
        Game.time = time;
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        Game.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        Game.height = height;
    }
}
