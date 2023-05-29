package action;

import dao.PersistException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainAction extends AuthorizedUserAction {
    @Override
    public void exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        return;
    }
}
