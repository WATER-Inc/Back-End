package controller.servlet.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.config.GetHttpSessionConfigurator;
import dao.PersistException;
import entity.Chat;
import entity.Message;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.MessageService;
import service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@ServerEndpoint(value = "/chat/{chat_id}", configurator = GetHttpSessionConfigurator.class)
public class ChatWebSocketEndPoint extends WebSocketAbstractEndPoint {
    private static final Logger logger = LogManager.getLogger(ChatWebSocketEndPoint.class);

    private static Map<String, Set<Session>> sessions = Collections.synchronizedMap(new HashMap<>());
    private User authorizeUser;
    private Chat chat;
    private ServiceFactory factory;

    public Chat getChat() {
        return chat;
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config, @PathParam("chat_id") String chatId) throws PersistException, IOException {
        System.out.println("WebSocket connection opened: " + session.getId());
        System.out.println("Chat " + chatId);
        factory = new ServiceFactory();
        chat = (Chat) factory.getService(Chat.class).getById(chatId);
        HandshakeRequest req = (HandshakeRequest) config.getUserProperties()
                .get("handshakereq");
        HttpSession httpSession = (HttpSession) req.getHttpSession();
       // authorizeUser = (User) httpSession.getAttribute("authorizedUser");
        if(chat == null
         //       || authorizeUser == null
        ){
            logger.info("No chat or authorize user");
            session.close();
        } else{
            if(!sessions.containsKey(chatId))
                sessions.put(chatId, Collections.synchronizedSet(new HashSet<>()));
            sessions.get(chatId).add(session);
            sendAllMessages(session);
        }
    }
    void sendAllMessages(Session session) throws PersistException, IOException {
        MessageService messageService = getFactory().getService(Message.class);
        List<Message> messages = messageService.getMessages(chat);

        for(Message message : messages) {
            String response = "";
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                response = objectMapper.writeValueAsString(message);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            sendMessage(session, response);
        }
    }
    @OnMessage
    public void onMessage(String message, Session session) throws ServletException, IOException {
        System.out.println("Received message from " + session.getId() + ": " + message);
        process(message, session);
    }
    void sendMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }
    @OnClose
    public void onClose(Session session, CloseReason reason) throws PersistException {
        System.out.println("WebSocket connection closed: " + session.getId() + ", Reason: " + reason.getReasonPhrase());
        if(chat != null)
            sessions.remove(chat);
        if(factory != null) {
            try {
                factory.close();
            } catch (SQLException e) {
                throw new PersistException(e);
            }
        }
    }

    @OnError
    public void onError(Throwable throwable) {
        System.out.println("WebSocket error:");
        throwable.printStackTrace();
    }

    @Override
    public void sendToAll(String message) {
        for (Session session : sessions.get(chat.getId())) {
            try {
                System.out.println("Send to " + session + " Message " + message);
                sendMessage(session, message);
            } catch (IOException e) {
                System.out.println("Error");
                // Обработка ошибки отправки сообщения
            }
        }
    }

}
