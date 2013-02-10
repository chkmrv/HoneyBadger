package core;

import model.Located;
import model.Moveable;
import model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class GameObjects implements Serializable {
    ArrayList<Located> objects;
    ArrayList<Moveable> moveables;
    ArrayList<Player> players;

    public GameObjects() {
    }
}