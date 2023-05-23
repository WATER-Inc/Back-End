package action;

import service.ServiceFactory;

public class ActionManagerFactory {
    public static ActionManager getManager(ServiceFactory factory) {
        return new ActionManager(factory);
    }
}