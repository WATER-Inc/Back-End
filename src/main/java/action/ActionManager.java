package action;

import dao.PersistException;
import service.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

public class ActionManager {
    private ServiceFactory factory;

    public ActionManager(ServiceFactory factory) {
        this.factory = factory;
    }

    public void execute(Action action, HttpServletRequest request, HttpServletResponse response) throws PersistException {
        action.setFactory(factory);
        action.exec(request, response);
        return;
    }

    public void close() throws SQLException {
        factory.close();
    }
}
