package send_validate.validator;

import entity.*;
import org.testng.internal.collections.Pair;

import java.util.HashMap;
import java.util.Map;

public class ValidatorFactory {
    private static Map<Class<?>, Class<? extends Validator<?>>> validators = new HashMap<>();

    static {
        validators.put(User.class, UserValidator.class);
        validators.put(Role.class, RoleValidator.class);
        validators.put(Message.class, MessageValidator.class);
        validators.put(Chat.class, ChatValidator.class);
    }

    @SuppressWarnings("unchecked")
    public static <Type> Validator<Type> createValidator(Class<Type> typeClass) {
        try {
            return (Validator<Type>) validators.get(typeClass).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}

