package com.krld.core;

import com.krld.model.Located;
import com.krld.model.Moveable;
import com.krld.model.Player;

import java.io.Serializable;
import java.util.ArrayList;

public class GameObjects implements Serializable {
    ArrayList<Located> objects;
    ArrayList<Moveable> moveables;
    ArrayList<Player> players;

    public GameObjects() {
    }
}