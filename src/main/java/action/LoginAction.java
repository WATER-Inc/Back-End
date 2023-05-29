package action;


import dao.PersistException;
import entity.User;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

public class LoginAction extends Action {
    private static Logger logger = Logger.getLogger(String.valueOf(LoginAction.class));

    @Override
    public Action.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        String username = request.getParameter("username");
        String password = request.getParameter("userpassword");
        logger.debug("Trying to login with: [Username: " + username + "; Password: " + password + "]");
        if (username != null && password != null) {
            UserService service = factory.getService(User.class);
            User user  = service.getByUsernameAndPassword(username, password);
            if (user == null) {
                request.setAttribute("message", "Имя пользователя или пароль не опознанны");
                logger.info(String.format("user \"%s\" unsuccessfully tried to log in from %s (%s:%s)", username, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                return null;
            }
            HttpSession session = request.getSession();
            session.setAttribute("authorizedUser", user);
            logger.info(String.format("user \"%s\" is logged in from %s (%s:%s)", username, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
            return null;
        }
        return null;
    }
}

