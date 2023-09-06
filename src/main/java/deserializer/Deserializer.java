package deserializer;

import action.parser.Parser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.PersistException;

import java.io.IOException;
import java.io.Serializable;

public abstract class Deserializer<T1> implements Serializable {
    public abstract T1 deserialize(JsonNode node);
    public T1 deserialize(String message) throws PersistException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse the JSON string into a JsonNode
        JsonNode jsonNode = null;
        try {
            jsonNode = Parser.parseRequest(message);
        } catch (IOException e) {
            throw new PersistException(e);
        }
        return deserialize(jsonNode);
    }
}
