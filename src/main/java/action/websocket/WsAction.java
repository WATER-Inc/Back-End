package action.websocket;

import action.AbstractAction;
import controller.servlet.websocket.WebSocketAbstractEndPoint;

import javax.websocket.Session;

public abstract class WsAction extends AbstractAction<String, Session> {
    private WebSocketAbstractEndPoint endPoint;

    public WebSocketAbstractEndPoint getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(WebSocketAbstractEndPoint endPoint) {
        this.endPoint = endPoint;
    }
}
