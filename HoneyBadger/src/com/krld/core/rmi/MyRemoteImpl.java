package com.krld.core.rmi;

import com.krld.core.Game;
import com.krld.core.GameState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {
    private int dogCount = 0;
    private static Game game;

    protected MyRemoteImpl() throws RemoteException {
    }

    @Override
    public String sayHello() throws RemoteException {
        return "Server say: Hello!";
    }

    @Override
    public GameState getGameState() throws RemoteException {
        return game.getGameState();
    }

    public static void main(String[] args) {
        try {
            MyRemote service = new MyRemoteImpl();
            Naming.rebind("HoneyBadgerRemote", service);
            runGameServer(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void runGameServer(MyRemote service) {
        game = new Game(Game.RUNNING_SERVER);
        game.setWidth(80);
        game.setHeight(60);
        try {
            AppGameContainer app = new AppGameContainer(game);
            app.setDisplayMode(game.getWidth(), game.getHeight(), false);
            app.setTargetFrameRate(100);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }


    }
}
