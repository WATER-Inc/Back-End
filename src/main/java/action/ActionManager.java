package action;

import dao.PersistException;
import service.ServiceFactory;

import java.sql.SQLException;

public class ActionManager<T1, T2> {
    private ServiceFactory factory;

    public ActionManager(ServiceFactory factory) {
        this.factory = factory;
    }

    public void execute(AbstractAction action, T1 t1, T2 t2) throws PersistException {
        action.setFactory(factory);
        action.exec(t1, t2);
    }

    public void close() throws SQLException {
        factory.close();
    }
}
