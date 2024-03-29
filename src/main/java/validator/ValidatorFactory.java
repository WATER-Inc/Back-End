package validator;

import entity.*;
import entity.auxiliary.ChatLink;
import entity.auxiliary.PreChatLink;
import org.testng.internal.collections.Pair;

import java.util.HashMap;
import java.util.Map;

public class ValidatorFactory {
    private static Map<Class<? extends Entity>, Class<? extends Validator<?>>> validators = new HashMap<>();

    static {
        validators.put(User.class, UserValidator.class);
        validators.put(Role.class, RoleValidator.class);
        validators.put(Message.class, MessageValidator.class);
        validators.put(Chat.class, ChatValidator.class);
        validators.put(PreChatLink.class, PreChatLinkValidator.class);
    }

    @SuppressWarnings("unchecked")
    public static <Type extends Entity> Validator<Type> createValidator(Class<Type> entity) {
        try {
            return (Validator<Type>) validators.get(entity).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}

