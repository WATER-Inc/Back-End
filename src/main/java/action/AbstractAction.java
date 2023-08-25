package action;

import dao.PersistException;
import service.ServiceFactory;

public abstract class AbstractAction<T1, T2> {
    public abstract void exec(T1 t1,T2 t2) throws PersistException;
    private ServiceFactory factory;

    public ServiceFactory getFactory() {
        return factory;
    }

    public void setFactory(ServiceFactory factory) {
        this.factory = factory;
    }
}
