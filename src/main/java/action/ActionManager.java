package action;

import dao.PersistException;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActionManager {
    private ServiceFactory factory;

    public ActionManager(ServiceFactory factory) {
        this.factory = factory;
    }

    public Action.Forward execute(Action action, HttpServletRequest request, HttpServletResponse response) throws PersistException {
        action.setFactory(factory);
        return action.exec(request, response);
    }

    public void close(){
        //TODO
    }
}
