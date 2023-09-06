package action.websocket.chat.message.remove;

import com.fasterxml.jackson.databind.JsonNode;
import deserializer.Deserializer;
import deserializer.DeserializerFactory;
import entity.Message;
import entity.User;

import java.util.Date;

public class RemoveMessageWsDeserializer extends Deserializer<Message> {

    @Override
    public Message deserialize(JsonNode jsonNode) {
        if(jsonNode == null)
            throw null;
        jsonNode = jsonNode.get("message");
        JsonNode messageIdNode = jsonNode.get("id");
        Message message_ = new Message();
        message_.setId(messageIdNode.asText());//TODO add sender to check if it can
        return message_;
    }
}
