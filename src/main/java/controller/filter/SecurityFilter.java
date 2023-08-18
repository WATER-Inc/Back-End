package controller.filter;


import action.*;
import action.authentication.LoginAction;
import action.authentication.RegistrationAction;
import entity.Role;
import entity.User;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;
@WebFilter(asyncSupported = true)
public class SecurityFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(SecurityFilter.class);
    private static final User mode = User.Default.USER.getUser();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            if(httpRequest.getHeader("Upgrade") != null && httpRequest.getHeader("Upgrade").equals("websocket"))
                chain.doFilter(request, response);
            else {
                Action action = (Action) httpRequest.getAttribute("action");
                //Set<Role> allowRoles = action.getAllowRoles();TODO need to include in can execute calculation
                String userName = "unauthorized user";
                HttpSession session = httpRequest.getSession();
                User user = null;
                if (session != null) {
                    session.setMaxInactiveInterval(10000);
                    user = (User) session.getAttribute("authorizedUser");
                    action.setAuthorizedUser(user);
                    String errorMessage = (String) session.getAttribute("SecurityFilterMessage");
                    if (errorMessage != null) {
                        httpRequest.setAttribute("message", errorMessage);
                        session.removeAttribute("SecurityFilterMessage");
                    }
                }
                logger.debug("AuthorizedUser: " + user);
                boolean canExecute = action instanceof LoginAction || action instanceof RegistrationAction;
                if (user != null) {
                    canExecute = canExecute || action.getAuthorizedUser() != null;
                }
                logger.debug("CanExecute:" + canExecute);

                if (canExecute) {
                    chain.doFilter(request, response);
                } else {
                    logger.info(String.format("Trying of %s access to forbidden resource \"%s\"", userName, action.getName()));
                    if (session != null && action.getClass() != MainAction.class) {
                        session.setAttribute("SecurityFilterMessage", "Доступ запрещён");
                    }
                    httpResponse.sendRedirect(httpRequest.getContextPath() + "/");
                }
            }
        } else {
            logger.error("It is impossible to use HTTP filter");
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
    }
}
