package action.websocket.chat.message.update;

import com.fasterxml.jackson.databind.JsonNode;
import deserializer.Deserializer;
import deserializer.DeserializerFactory;
import entity.Message;
import entity.User;

import java.util.Date;

public class UpdateMessageWsDeserializer extends Deserializer<Message> {

    @Override
    public Message deserialize(JsonNode jsonNode) {
        if(jsonNode == null)
            throw null;
        jsonNode = jsonNode.get("message");
        JsonNode idNode = jsonNode.get("id");
        JsonNode contentNode = jsonNode.get("content");
        Message message_ = new Message();
        message_.setContent(contentNode.asText());
        message_.setId(idNode.asText());
        return message_;
    }
}
