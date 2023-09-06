package controller.servlet;

import controller.config.GetHttpSessionConfigurator;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpoint;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


@ServerEndpoint(value = "/websocket", configurator = GetHttpSessionConfigurator.class)//test service https://www.piesocket.com/websocket-tester
public class WebSocketEndpoint {

    private static final Logger logger = LogManager.getLogger(WebSocketEndpoint.class);

    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        System.out.println("WebSocket connection opened: " + session.getId());

        HandshakeRequest req = (HandshakeRequest) config.getUserProperties()
                .get("handshakereq");
        try {
            session.getBasicRemote().sendText("Connection established");
            logger.debug(session.getId());
            session.getBasicRemote().sendText(String.valueOf(session.getId()));
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