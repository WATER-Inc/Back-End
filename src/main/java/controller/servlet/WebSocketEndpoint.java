package controller.servlet;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/websocket")//test service https://www.piesocket.com/websocket-tester
public class WebSocketEndpoint {
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("WebSocket connection opened: " + session.getId());
        try {
            session.getBasicRemote().sendText("Connection established");
            sessions.add(session);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message from " + session.getId() + ": " + message);
        try {
            session.getBasicRemote().sendText("Received: " + message);
            sendToAll(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("WebSocket connection closed: " + session.getId() + ", Reason: " + reason.getReasonPhrase());
        sessions.remove(session);
    }

    @OnError
    public void onError(Throwable throwable) {
        System.out.println("WebSocket error:");
        throwable.printStackTrace();
    }
    public static void sendToAll(String message) {
        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                // Обработка ошибки отправки сообщения
            }
        }
    }
}