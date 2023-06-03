package service;

import dao.PersistException;
import dao.mysql.MySqlDaoFactory;
import entity.*;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.*;

public class ServiceFactory {
    private static Logger logger =LogManager.getLogger(ServiceFactory.class);
    private static final Map<Class<? extends Entity>, Class<? extends Service>> SERVICES = new ConcurrentHashMap<>();
    private MySqlDaoFactory factory;
    static {
        SERVICES.put(User.class, UserService.class);
        SERVICES.put(Role.class, RoleService.class);
        SERVICES.put(Message.class, MessageService.class);
        SERVICES.put(Chat.class, ChatService.class);
    }
    public ServiceFactory() throws PersistException {
        factory = new MySqlDaoFactory();
    }
    public <Type extends Service, ServiceType extends Entity> Type getService(Class<ServiceType> object) {
        Class<? extends Service> value = SERVICES.get(object);
        try {
            Service service = value.getConstructor(MySqlDaoFactory.class).newInstance(factory);
            logger.info("Service created: " + service.getClass());
            return (Type) service;
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() throws SQLException {
        factory.close();
    }
}
