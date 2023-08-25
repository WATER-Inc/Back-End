package controller.servlet;

import action.AbstractAction;
import action.ActionManager;
import action.ActionManagerFactory;
import action.sender.SenderManager;
import dao.PersistException;
import service.ServiceFactory;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.SQLException;

public abstract class Dispatcher<T1, T2> extends HttpServlet {
    protected abstract AbstractAction<T1, T2> getAction(T1 t1, T2 t2);
    public static ServiceFactory getFactory() throws PersistException {
        return new ServiceFactory();
    }
    protected abstract void sendError(Exception e, T1 t1, T2 t2);
    protected void process(T1 t1, T2 t2) throws IOException {
        AbstractAction action = getAction(t1, t2);
        ActionManager actionManager = null;
        try {
            actionManager = ActionManagerFactory.getManager(getFactory());
            actionManager.execute(action, t1, t2);
        } catch (PersistException e) {
            sendError(e ,t1, t2);
        }
        finally {
            if(actionManager != null) {
                try {
                    actionManager.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
