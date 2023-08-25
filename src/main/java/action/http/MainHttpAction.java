package action.http;

import dao.PersistException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainHttpAction extends AuthorizedUserHttpAction {
    public MainHttpAction() throws PersistException {
        super();
    }

    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        return;
    }
}
