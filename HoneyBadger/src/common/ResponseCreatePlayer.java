package common;

public class ResponseCreatePlayer implements MyResponse {
    public ResponseCreatePlayer(long id, long requestId) {

    }

    @Override
    public long getRequestId() {
        return 0;
    }

    @Override
    public Object getResponse() {
        return null;
    }
}
