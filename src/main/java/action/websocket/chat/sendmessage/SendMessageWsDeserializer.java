package action.websocket.chat.sendmessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import controller.servlet.websocket.ChatWebSocketEndPoint;
import dao.PersistException;
import deserializer.Deserializer;
import deserializer.DeserializerFactory;
import entity.Message;
import entity.User;

import java.util.Date;

public class SendMessageWsDeserializer implements Deserializer<Message> {
    static{
        DeserializerFactory.registrationDeserializer(SendMessageWsAction.class, SendMessageWsDeserializer.class);
    }
    @Override
    public Message deserialize(JsonNode jsonNode) {
        if(jsonNode == null)
            throw null;
        jsonNode = jsonNode.get("message");
        JsonNode contentNode = jsonNode.get("content");
        JsonNode senderNode = jsonNode.get("sender_id");
        JsonNode dateNode = jsonNode.get("date");
        Message message_ = new Message();
        message_.setContent(contentNode.asText());
        User sender = new User();
        sender.setId(senderNode.asText());
        message_.setSender(sender);
        message_.setDate(new Date(dateNode.asLong()));
        return message_;
    }
}
