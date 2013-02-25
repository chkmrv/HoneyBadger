package com.krld.core.rmi;

import com.krld.core.Game;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.rmi.Naming;

public class MyRemoteClient {

    private MyRemote service;
    private Game game;

    public static void main(String[] args) {
        new MyRemoteClient().go();
    }

    private void go() {
        try {
            service = (MyRemote) Naming.lookup("rmi://127.0.0.1/HoneyBadgerRemote");
            String s = service.sayHello();
            System.out.println(s);
            runGameClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void runGameClient() {
        game = new Game(Game.RUNNING_CLIENT, service);
        game.setWidth(800);
        game.setHeight(600);
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
