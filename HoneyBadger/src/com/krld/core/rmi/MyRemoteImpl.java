package com.krld.core.rmi;

import com.krld.core.Game;
import com.krld.core.GameState;
import com.krld.core.MoveDirection;
import com.krld.model.Player;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {
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

    @Override
    public Player getNewPlayer() throws RemoteException {
        Player player = new Player(2000, 2000);
        game.getGameState().getPlayers().add(player);
        return player;
    }

    @Override
    public void move(long id, MoveDirection moveDirection) throws RemoteException {
        for (Player player : game.getGameState().getPlayers()) {
            if (player.getId() == id) {
                switch (moveDirection) {
                    case TOP:
                        player.moveTo(player.getX(), player.getY() - player.getSpeed());
                        break;
                    case LEFT:
                        player.moveTo(player.getX() - player.getSpeed(), player.getY());
                        break;
                    case RIGHT:
                        player.moveTo(player.getX() + player.getSpeed(), player.getY());
                        break;
                    case BOT:
                        player.moveTo(player.getX(), player.getY() + player.getSpeed());
                        break;
                }
                break;
            }
        }
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
