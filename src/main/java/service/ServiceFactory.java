package service;

import dao.PersistException;
import entity.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class ServiceFactory {
    private static final Map<Class<? extends Entity>, Class<? extends Service>> SERVICES = new ConcurrentHashMap<>();

    static {
        SERVICES.put(User.class, UserService.class);
        SERVICES.put(Role.class, RoleService.class);
        SERVICES.put(Message.class, MessageService.class);
        SERVICES.put(Chat.class, ChatService.class);
    }

    public <Type extends Service, ServiceType extends Entity> Type getService(Class<ServiceType> object) {
        Class<? extends Service> value = SERVICES.get(object);
        try {
            return (Type) value.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
