package deserializer;

import validator.Validator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DeserializerFactory {
    static Map<Class<?>, Class<? extends Deserializer>> deserializers = new ConcurrentHashMap<>();
    static{

    }
    public static void registrationDeserializer(Class<?> input, Class<? extends Deserializer> deserializer){
        deserializers.put(input, deserializer);
    }
    public static Deserializer getDeserializer(Class<?> input){
        try {
            return deserializers.get(input).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}
