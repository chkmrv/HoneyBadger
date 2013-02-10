package core;

import model.Located;
import model.Moveable;
import model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class GameState implements Serializable {
    private  GameObjects gameObjects = new GameObjects();
    private int[][] tileMap;
    //     private Player player;

    public  int[][] getTileMap() {
        return tileMap;
    }

    public  void setTileMap(int[][] tileMap) {
        this.tileMap = tileMap;
    }

    public ArrayList<Located> getObjects() {
        return getGameObjects().objects;
    }

    public void setObjects(ArrayList<Located> objects) {
        this.getGameObjects().objects = objects;
    }

    public ArrayList<Moveable> getMoveables() {
        return getGameObjects().moveables;
    }

    public void setMoveables(ArrayList<Moveable> moveables) {
        this.getGameObjects().moveables = moveables;
    }

    public ArrayList<Player> getPlayers() {
        return getGameObjects().players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.getGameObjects().players = players;
    }

    public GameObjects getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(GameObjects gameObjects) {
        this.gameObjects = gameObjects;
    }
}