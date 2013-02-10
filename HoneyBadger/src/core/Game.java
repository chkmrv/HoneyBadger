package core;

import common.WorldGenerator;
import model.*;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.*;
import org.newdawn.slick.imageout.ImageOut;

import java.io.IOException;
import java.util.ArrayList;

public class Game extends BasicGame {
    private int runningType;
    public static final int RUNNING_SERVER = 0;
    public static final int RUNNING_CLIENT = 1;
    private static final int DELAY = 500;
    private static long time = System.nanoTime();

    private static int width;
    private static int height;
    private int elapsedTime;
    private Rectangle viewPort;

    private int distanceOfView = 600;
    private GameState gameState;
    private Player player;
    private final String host;
    private final String port;
    private Client client;
    private Server server;

    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Game(RUNNING_CLIENT, args[0], args[1], null));
        setWidth(800);
        setHeight(600);
        app.setDisplayMode(getWidth(), getHeight(), false);
        app.start();
    }

    public Game(int runningType, String host, String port, Server server) {
        super("core.Game!");
        this.host = host;
        this.port = port;
        setRunningType(runningType);
        this.server = server;
    }

    public int getRunningType() {
        return runningType;
    }

    public void setRunningType(int runningType) {
        this.runningType = runningType;
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        initGameState();
        if (runningType == RUNNING_CLIENT) {
            try {
                setPlayer(new Player(2000, 2000));
                viewPort = new Rectangle(getPlayer().getX() - width / 2, getPlayer().getY() - height / 2, width, height);
                client = new Client(host, Integer.parseInt(port), this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

        }

    }

    public void initGameState() {
        setGameState(new GameState());
        getGameState().setObjects(new ArrayList<Located>());
        getGameState().setMoveables(new ArrayList<Moveable>());
        getGameState().setPlayers(new ArrayList<Player>());

        getGameState().setTileMap(new int[200][200]);
        WorldGenerator.generateTiles(getGameState().getTileMap());
        WorldGenerator.generateUnits(getGameState().getObjects(), getGameState().getTileMap(), getGameState().getMoveables());
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        setElapsedTime(getElapsedTime() + delta);
        // System.out.println("elapsedTime: " + elapsedTime);
        if (getElapsedTime() <= getDelay()) {
            return;
        }
        if (runningType == RUNNING_CLIENT) {
             client.run();
           // new Thread(client).start();


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
                getPlayer().moveTo(getPlayer().getX(), getPlayer().getY() - getPlayer().getSpeed());
            }
            if (input.isKeyDown(Input.KEY_S)) {
                getPlayer().moveTo(getPlayer().getX(), getPlayer().getY() + getPlayer().getSpeed());
            }
            if (input.isKeyDown(Input.KEY_A)) {
                getPlayer().moveTo(getPlayer().getX() - getPlayer().getSpeed(), getPlayer().getY());
            }
            if (input.isKeyDown(Input.KEY_D)) {
                getPlayer().moveTo(getPlayer().getX() + getPlayer().getSpeed(), getPlayer().getY());
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
                client.requestCreatePlayer();
                //  getTiles().put(new common.GridPosition(input.getMouseX() + getViewPort().getX(), input.getMouseY() + getViewPort().getY()), model.MyTile.STONE);

            }
            if (input.isKeyPressed(Input.KEY_E)) {
                getPlayer().grabStuff(getObjects());
            }
            if (input.isKeyPressed(Input.KEY_R)) {
                getPlayer().dropStuff(getObjects());
            }
        } else if (runningType == RUNNING_SERVER) {
            moveMoveables();
            server.send();
        }
    }

    private void moveMoveables() {
        for (Moveable moveable : getGameState().getMoveables()) {
            moveable.move();
        }
    }

    @Override
    public void render(GameContainer gameContainer, Graphics g) throws SlickException {
        if (runningType == RUNNING_SERVER) {
            return;
        }
        g.translate(-getViewPort().getX(), -getViewPort().getY());

        int playerX = getPlayer().getX();
        int playerY = getPlayer().getY();
        for (int x = playerX / 32 - 15; x < playerX / 32 + 15; x++) {
            for (int y = playerY / 32 - 15; y < playerY / 32 + 15; y++) {
                int realX = x * 32 - 1;
                int realY = y * 32 - 1;
                // if (getPlayer().getDistanceTo(realX, realY) < getDistanceOfView()) {
                try {
                    MyTile.draw(getGameState().getTileMap()[x][y], realX, realY, gameContainer);
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
        for (Moveable unit : getGameState().getMoveables()) {
            if (playerX - 15 * 32 < unit.getX() && playerX + 15 * 32 > unit.getX() &&
                    playerY - 15 * 32 < unit.getY() && playerY + 15 * 32 > unit.getY()) {
                unit.draw();
            }
        }
        for (Player player1 : gameState.getPlayers()) {
            if (!getPlayer().equals(player1)) {
                player1.draw();
                g.drawString("id: " + player1.getId(), player1.getX(), player1.getY() - 40 );


            }
        }
        //g.drawString("Hello World! x: y: " + viewPort.getX() + " " + viewPort.getY(), 100, 100);
        // g.drawString(getPlayer().getInventory().printItems(), 100, 100);
        getPlayer().draw();
        g.drawString("id: " + getPlayer().getId(), getPlayer().getX(), getPlayer().getY() - 40 );
        g.drawString("x y: " + getPlayer().getX() + " " + getPlayer().getY(), getPlayer().getX() + 40, getPlayer().getY());
    }

    public Player getPlayer() {
        return this.player;
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
        return getGameState().getObjects();
    }

    public void setObjects(ArrayList<Located> objects) {
        getGameState().setObjects(objects);
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

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
