package com.krld.core.rmi;

import com.krld.core.GameState;
import com.krld.core.MoveDirection;
import com.krld.model.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created with IntelliJ IDEA.
 * User: krld
 * Date: 25.02.13
 * Time: 20:54
 * To change this template use File | Settings | File Templates.
 */
public interface MyRemote extends Remote {
    public String sayHello() throws RemoteException;

    GameState getGameState() throws RemoteException;

    Player getNewPlayer() throws RemoteException;

    void move(long id, MoveDirection moveDirection) throws RemoteException;
}
