package action;

import service.ServiceFactory;

import java.util.logging.Logger;

public class ActionManagerFactory {

    public static ActionManager getManager(ServiceFactory factory) {
        return new ActionManager(factory);
    }
}