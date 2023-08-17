package controller.servlet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/websocket")
public class WebSocketEndpoint {

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WebSocket connection opened: " + session.getId());
        try {
            session.getBasicRemote().sendText("Connection established");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message from " + session.getId() + ": " + message);
        try {
            session.getBasicRemote().sendText("Received: " + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("WebSocket connection closed: " + session.getId() + ", Reason: " + reason.getReasonPhrase());
    }

    @OnError
    public void onError(Throwable throwable) {
        System.out.println("WebSocket error:");
        throwable.printStackTrace();
    }
}