package action.websocket.chat.message.remove;

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

public class RemoveMessageWsAction extends ChatWsAction {
    static{
        DeserializerFactory.registrationDeserializer(RemoveMessageWsAction.class, RemoveMessageWsDeserializer.class);
    }
    @Override
    public void exec(String message, Session session) throws PersistException {
        Message message_ = (Message) DeserializerFactory.getDeserializer(this.getClass()).deserialize(message);
        if (message_ == null)
            throw new PersistException("No Json!");
        getFactory().getService(Message.class).delete(message_);
        getEndPoint().sendToAll(message);
    }
}
