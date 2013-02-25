package com.krld.common;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: krld
 * Date: 08.02.13
 * Time: 18:01
 * To change this template use File | Settings | File Templates.
 */
public interface MyResponse  extends Serializable {
    public long getRequestId();
    public Object getResponse();
}
