package action.websocket.chat;

import action.ActionFactory;
import action.parser.Parser;
import action.websocket.WsAction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.servlet.websocket.ChatWebSocketEndPoint;
import dao.PersistException;
import entity.Message;
import entity.User;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Date;

public class SendMessageWsAction extends ChatWsAction {
    @Override
    public void exec(String message, Session session) throws PersistException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse the JSON string into a JsonNode
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        jsonNode = jsonNode.get("message");
        JsonNode contentNode = jsonNode.get("content");
        JsonNode senderNode = jsonNode.get("sender_id");
        JsonNode dateNode = jsonNode.get("date");
        Message message_ = new Message();
        message_.setContent(contentNode.asText());
        User sender = (User) getFactory().getService(User.class).getById(senderNode.asText());
        message_.setSender(sender);
        ChatWebSocketEndPoint endPoint = (ChatWebSocketEndPoint) getEndPoint();
        message_.setChat(endPoint.getChat());
        System.out.println("Date" + dateNode.asText());
        message_.setDate(new Date(dateNode.asLong()));
        message_ = (Message) getFactory().getService(Message.class).persist(message_);
        String response = null;
        try {
            response = objectMapper.writeValueAsString(message_);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        getEndPoint().sendToAll(response);
    }
}
