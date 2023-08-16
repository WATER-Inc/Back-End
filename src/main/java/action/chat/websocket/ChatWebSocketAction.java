package action.chat.websocket;

import entity.Message;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
@ServerEndpoint("/chat/{chatId}")
public class ChatWebSocketAction{
    private static Map<String, Set<Session>> chatSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("chatId") String chatId) {
        System.out.println("Helloooo World!!!");
        // Добавляем новую сессию в список подключенных сессий для данного чата
        if(!chatSessions.containsKey(chatId))
            chatSessions.put(chatId, new CopyOnWriteArraySet<>());
        chatSessions.get(chatId).add(session);
    }

    @OnMessage
    public void onMessage(String message, Session session, @PathParam("chatId") String chatId) {
        System.out.println("Helloooo World!!!!!!!!!!!!!!!");
        // Отправляем полученное сообщение всем подключенным сессиям для данного чата
        Set<Session> sessions = chatSessions.getOrDefault(chatId, Collections.emptySet());
        for (Session s : sessions) {
            try {
                s.getBasicRemote().sendText(message);//TODO test
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("chatId") String chatId) {
        System.out.println("Helloooo World");
        // Удаляем закрытую сессию из списка подключенных сессий для данного чата
        Set<Session> sessions = chatSessions.getOrDefault(chatId, Collections.emptySet());
        sessions.remove(session);
    }
}