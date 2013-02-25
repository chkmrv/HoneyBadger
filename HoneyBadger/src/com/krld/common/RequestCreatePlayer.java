package com.krld.common;


import com.krld.core.Game;
import com.krld.model.Player;

import java.util.Calendar;

public class RequestCreatePlayer implements MyRequest {
    private long requestId;
    public RequestCreatePlayer(){
        requestId = Calendar.getInstance().getTimeInMillis();
    }
    @Override
    public MyResponse run(Game game) {
        Player newPlayer = new Player(2000 + (int)(Math.random() * 400), 2000  + (int)(Math.random() * 400));
        game.getGameState().getPlayers().add(newPlayer);
        return new ResponseCreatePlayer(newPlayer.getId(), getRequestId());
    }

    public long getRequestId() {
        return requestId;
    }


}
