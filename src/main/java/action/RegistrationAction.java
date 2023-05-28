package action;

import dao.PersistException;
import entity.User;
import org.apache.log4j.Logger;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RegistrationAction extends Action{
    final private static Logger logger = Logger.getLogger(String.valueOf(RegistrationAction.class));
    @Override
    public Forward exec(HttpServletRequest request, HttpServletResponse response) throws PersistException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        logger.debug("Trying to register with: [Username: " + username + "; Password: " + password + "]");
        if (username != null && password != null) {
            UserService service = factory.getService(User.class);
            User user = service.getByUsername(username);
            if (user != null) {
                request.setAttribute("message", "Пользователь уже существует!");
                logger.info(String.format("user \"%s\" unsuccessfully tried to register in from %s (%s:%s)", username, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
                return new Forward("/register.html");
            }
            user = new User();
            user.setUsername(username);
            user.setPasswordHash(password);
            service.persist(user);
            logger.info(String.format("user \"%s\" is registered in from %s (%s:%s)", username, request.getRemoteAddr(), request.getRemoteHost(), request.getRemotePort()));
            return new Forward("/login");
        }
        return new Forward("/register.html");
    }
}
