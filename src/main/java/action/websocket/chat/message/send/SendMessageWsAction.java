package action.websocket.chat.message.send;

import action.websocket.chat.ChatWsAction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controller.servlet.websocket.ChatWebSocketEndPoint;
import dao.PersistException;
import deserializer.DeserializerFactory;
import entity.Message;
import entity.User;

import javax.websocket.Session;

public class SendMessageWsAction extends ChatWsAction {
    static{
        DeserializerFactory.registrationDeserializer(SendMessageWsAction.class, SendMessageWsDeserializer.class);
    }
    @Override
    public void exec(String message, Session session) throws PersistException {
        Message message_ = (Message) DeserializerFactory.getDeserializer(this.getClass()).deserialize(message);
        if (message_ == null)
            throw new PersistException("No Json!");
        User sender = (User) getFactory().getService(User.class).getById(message_.getSender().getId());
        message_.setSender(sender);
        ChatWebSocketEndPoint endPoint = (ChatWebSocketEndPoint) getEndPoint();
        message_.setChat(endPoint.getChat());
        message_ = (Message) getFactory().getService(Message.class).persist(message_);
        String response = null;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode responseNode = mapper.valueToTree(message_);
        ((ObjectNode) responseNode).put("action", getName());
        response = responseNode.asText();
        getEndPoint().sendToAll(response);
    }
}
