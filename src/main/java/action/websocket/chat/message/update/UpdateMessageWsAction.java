package action.websocket.chat.message.update;

import action.websocket.chat.ChatWsAction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dao.PersistException;
import deserializer.DeserializerFactory;
import entity.Message;

import javax.websocket.Session;

public class UpdateMessageWsAction extends ChatWsAction {
    static{
        DeserializerFactory.registrationDeserializer(UpdateMessageWsAction.class, UpdateMessageWsDeserializer.class);
    }
    @Override
    public void exec(String message, Session session) throws PersistException {
        Message message_ = (Message) DeserializerFactory.getDeserializer(this.getClass()).deserialize(message);
        if (message_ == null)
            throw new PersistException("No Json!");
        getFactory().getService(Message.class).update(message_);
        getEndPoint().sendToAll(message);
    }
}
