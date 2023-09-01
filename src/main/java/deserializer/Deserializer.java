package deserializer;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.Serializable;

public interface Deserializer<T1> extends Serializable {
    T1 deserialize(JsonNode node);
}
