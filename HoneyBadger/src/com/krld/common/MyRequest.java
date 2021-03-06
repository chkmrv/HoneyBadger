package com.krld.common;


import com.krld.core.Game;

import java.io.Serializable;

public interface MyRequest extends Serializable {
    public long getRequestId();
    public MyResponse run(Game game);
}
