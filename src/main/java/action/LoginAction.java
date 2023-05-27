package action;


import dao.PersistException;
import entity.Role;
import entity.User;
import org.apache.log4j.Logger;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LoginAction extends Action {
    private static Logger logger = Logger.getLogger(String.valueOf(LoginAction.class));

    private static Map<Role, List<MenuItem>> menu = new ConcurrentHashMap<>();

    static {
//        menu.put(new Role("User"), new ArrayList<>(Arrays.asList(
//                new MenuItem("/search/book/form.html", "поиск книг"),
//                new MenuItem("/search/reader/form.html", "поиск читателей")
//        )));
//        menu.put(new Role("Admin"), new ArrayList<>(Arrays.asList(
//                new MenuItem("/reader/list.html", "читатели"),
//                new MenuItem("/user/list.html", "сотрудники")
//        )));
//        menu.put(new Role("Master"), new ArrayList<>(Arrays.asList(
//                new MenuItem("/author/list.html", "авторы")
//        )));
    }


    @Override
    public Action.Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        logger.debug("Username: " + username + "; Password: " + password);
        if (username != null && password != null) {
            UserService service = factory.getService(User.class);
            User user = service.getByUsernameAndPassword(username, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("authorizedUser", user);
                logger.info(String.format("user \"%s\" is logged in from %s (%s:%s)", username, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                return new Forward("/index.html");
            } else {
                request.setAttribute("message", "Имя пользователя или пароль не опознанны");
                logger.info(String.format("user \"%s\" unsuccessfully tried to log in from %s (%s:%s)", username, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
            }
        }
        return null;
    }
}

