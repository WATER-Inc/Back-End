package serealization_deserealization.deserialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;

public class Deserializer {
    public <Type extends Serializable> Type deserialize(JsonNode node) throws IOException {
        if(node == null)
            return null;
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(node.asText(), new TypeReference<Type>(){});
    }
}
