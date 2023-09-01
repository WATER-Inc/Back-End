package action.websocket.chat.sendmessage;

import action.ActionFactory;
import action.parser.Parser;
import action.websocket.WsAction;
import action.websocket.chat.ChatWsAction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.servlet.websocket.ChatWebSocketEndPoint;
import dao.PersistException;
import deserializer.DeserializerFactory;
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
            jsonNode = Parser.parseRequest(message);
        } catch (IOException e) {
            throw new PersistException(e);
        }
        Message message_ = (Message) DeserializerFactory.getDeserializer(this.getClass()).deserialize(jsonNode);
        if (message_ == null)
            throw new PersistException("No Json!");
        User sender = (User) getFactory().getService(User.class).getById(message_.getSender().getId());
        message_.setSender(sender);
        ChatWebSocketEndPoint endPoint = (ChatWebSocketEndPoint) getEndPoint();
        message_.setChat(endPoint.getChat());
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
