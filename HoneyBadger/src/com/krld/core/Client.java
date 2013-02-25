package com.krld.core;
  /*test ubuntu*/
import com.krld.common.RequestCreatePlayer;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.io.*;
import java.net.Socket;


public class Client implements Runnable {
    final Socket socket;
    private static Game game;
    private int sending;
    private final ObjectOutputStream out;


    public Client(String host, int port, Game game) throws IOException {
        sending = 0;
        this.game = game;
        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        new Thread(new Receiver()).start();
    }


    public void run() {
        Log.i("Try send message to server");
        try {
            out.writeObject(game.getPlayer());
            out.reset();
            out.flush();

            sending++;
            Log.i("sending player object");
        } catch (IOException e) {
            e.printStackTrace();
            //  close();
        }
    }

    public void requestCreatePlayer() {
        Log.i("Try send requestCreatePlayer to server");
        try {
            out.writeObject(new RequestCreatePlayer());
            out.reset();
            out.flush();

            sending++;
            Log.i("sending requestCreatePlayer");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void close() {
        if (!socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        game = new Game(Game.RUNNING_CLIENT, args[0], args[1], null);
        game.setWidth(800);
        game.setHeight(600);
        try {
            AppGameContainer app = new AppGameContainer(game);


            app.setDisplayMode(game.getWidth(), game.getHeight(), false);

            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }


    private class Receiver implements Runnable {

        private ObjectInputStream input;

        private Receiver() {
            try {
                input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public void run() {
            while (!socket.isClosed()) {
                String line = null;
                try {
                    Log.i("Try read object");

                    Object inputObject = input.readObject();
                    Object tmp;
                    if (inputObject instanceof GameState) {
                        Log.i("input gameState hashCode: " + ((GameState) inputObject).getMoveables().get(1).getX());
                        Log.i("current gameState hashCode: " + ((GameState) inputObject).getMoveables().get(1).getX());
                        game.setGameState((GameState) inputObject);
                        Log.i("read gameState");
                    } else if (inputObject instanceof GameObjects) {

                        game.getGameState().setGameObjects((GameObjects) inputObject);
                        Log.i("read com.krld.core.GameObjects");
                    }

                    //  close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

