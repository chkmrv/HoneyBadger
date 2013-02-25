package com.krld.core;

import com.krld.common.MyRequest;
import com.krld.common.MyResponse;
import com.krld.model.Player;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server implements Runnable {
    private static Game game;
    private ServerSocket serverSocket;
    private Thread serverThread;
    private int port;
    BlockingQueue<SocketProcessor> queue = new LinkedBlockingQueue<SocketProcessor>();


    public Server(int port) throws IOException {
        this.port = port;
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Create ServerSocket port: " + this.port);
        serverThread = Thread.currentThread();
        while (true) {
            Socket socket = getNewConn();
            if (serverThread.isInterrupted()) {

                break;
            } else if (socket != null) {
                try {
                    final SocketProcessor processor = new SocketProcessor(socket);
                    final Thread thread = new Thread(processor);
                    thread.setDaemon(true);
                    thread.start();
                    queue.offer(processor);
                    System.out.println("New connect. IP:" + socket.getInetAddress());
                } catch (IOException ignored) {
                }
            }
        }
    }

    private Socket getNewConn() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
            shutdownServer();
        }
        return socket;
    }


    private synchronized void shutdownServer() {

        for (SocketProcessor socketProcessor : queue) {
            socketProcessor.close();
        }
        if (!serverSocket.isClosed()) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws IOException {
        Server server = new Server(777);
        new Thread(server).start();
        System.out.println("start game");
        game = new Game(Game.RUNNING_SERVER, "localhost", "777", server);
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


    private class SocketProcessor implements Runnable {
        Socket socket;

        private final ObjectInputStream objInput;
        private final ObjectOutputStream objOutput;
        private boolean tileSend = false;


        SocketProcessor(Socket socketParam) throws IOException {
            socket = socketParam;
            objInput = new ObjectInputStream(socket.getInputStream());
            objOutput = new ObjectOutputStream(socket.getOutputStream());
        }


        public void run() {
            receive();
        }

        private void receive() {
            while (!socket.isClosed()) {
                try {
                    Object inputObject = objInput.readObject();
                    if (inputObject instanceof MyRequest) {
                        send(((MyRequest) inputObject).run(game));
                    } else {
                        Player player = (Player) inputObject;
                        ArrayList<Player> players = game.getGameState().getPlayers();
                        try {
                            players.remove(players.indexOf(player));
                        } catch (ArrayIndexOutOfBoundsException e) {
                        }
                        players.add(player);
                        //    Log.i("receive player from client");
                    }
                } catch (IOException e) {
                    close();
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    close();
                    e.printStackTrace();
                }
            }
        }


        public synchronized void send(MyResponse response) {
            try {
                Log.i("try send info to client");
                if (tileSend) {
                    objOutput.writeObject(game.getGameState().getGameObjects());
                    Log.i("send gameObjects to client");
                } else {
                    Log.i("input gameState moveables 1 X: " + game.getGameState().getMoveables().get(1).getX());
                    objOutput.writeObject(game.getGameState());
                    tileSend = true;
                    Log.i("send gameState to client");
                }
                objOutput.reset();
                objOutput.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public synchronized void close() {
            queue.remove(this);
            if (!socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
            }
        }


        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            close();
        }
    }

    public void send() {
        for (SocketProcessor socketProcessor : queue) {

            //    socketProcessor.send(.run(game));
        }
    }
}

